package com.txznet.launcher.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.j256.ormlite.stmt.query.OrderBy;
import com.txznet.launcher.data.DataConvertor;
import com.txznet.launcher.data.api.AppsRepoApi;
import com.txznet.launcher.data.api.CardsRepoApi;
import com.txznet.launcher.data.model.AppInfo;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.db.CardBean;
import com.txznet.launcher.db.dao.CardDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
@Singleton
public class DbSource implements CardsRepoApi<BaseModel>, AppsRepoApi<AppInfo> {
    private static final String TAG = DbSource.class.getSimpleName();

    CardDao mCardDao;
    Context mContext;

    private List<CardBean> mAllTmpList = new ArrayList<>();
    private List<CardBean> mAppsList = new ArrayList<>();
    private List<CardBean> mCardsList = new ArrayList<>();

    @Inject
    public DbSource(Context context) {
        mContext = context;
        mCardDao = CardDao.getInstance();
    }

    @Override
    public boolean swapCards(int before, int after) {
        List<CardBean> allbeans = mCardsList;
        if (allbeans != null
                && allbeans.size() > 0
                && before >= 0
                && after >= 0
                && allbeans.size() - 1 >= before
                && allbeans.size() - 1 >= after) {
            CardBean bcb = findBeanByPos(before, mCardsList);
            CardBean acb = findBeanByPos(after, mCardsList);
            if (bcb != null && acb != null) {
                bcb.pos = after;
                acb.pos = before;
                Log.d(TAG, "bcb:" + bcb.packageName + " acb:" + acb.packageName + " swap:" + before + "," + after);

                // 更新数据库
                mCardDao.update(bcb);
                mCardDao.update(acb);
            }
        }
        return true;
    }

    @Override
    public boolean closeCard(@NonNull BaseModel bm) {
        CardBean bean = findCardBeanByInfo(bm, mCardsList);
        if (bean != null) {
            int cardPos = bean.pos;
//            int count = mAppsList.size();
//            bean.type = CardBean.TYPE_APP;
//            bean.pos = count;
//            mCardDao.update(bean);
            mCardsList.remove(bean);
            updateCardBeansPos(cardPos, false);
            return false;
        }
        return false;
    }

    private void removeBeanFromList(List<CardBean> cbs) {

    }

    private void updateCardBeansPos(int basePos, boolean isInc) {
        for (int i = 0; i < mCardsList.size(); i++) {
            CardBean cb = mCardsList.get(i);
            if (cb.pos >= basePos) {
                if (isInc) {
                    cb.pos++;
                } else {
                    cb.pos--;
                }
            }
        }
    }

    @Override
    public boolean closeCard(int pos) {
        CardBean bean = findBeanByPos(pos, mCardsList);
        if (bean != null) {
            int cardPos = bean.pos;
            mCardsList.remove(bean);
            updateCardBeansPos(cardPos, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean addCard(@NonNull BaseModel bm, int pos) {
        CardBean cb = DataConvertor.getInstance().convertFromModel(bm);
        if (cb != null) {
            if (pos == -1 || pos >= mCardsList.size()) {
                mCardsList.add(cb);
                cb.pos = mCardsList.size() - 1;
            } else {
                cb.pos = pos;
                mCardsList.add(pos, cb);
                updateCardBeansPos(pos, true);
            }
            mCardDao.save(cb);
            return true;
        }
        return false;
    }

    @Override
    public boolean saveCard(@NonNull BaseModel bm) {
        CardBean cb = findCardBeanByInfo(bm, mCardsList);
        if (cb != null) {
            cb.jsonData = bm.toString();
            mCardDao.update(cb);
            return true;
        }
        return false;
    }

    @Override
    public Observable<List<BaseModel>> loadCards() {
        List<CardBean> beans = getAllDbs();
        return Observable.from(beans).filter(new Func1<CardBean, Boolean>() {
            @Override
            public Boolean call(CardBean cardBean) {
                return cardBean.type == CardBean.TYPE_CARD;
            }
        }).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                mCardsList.clear();
            }
        }).doOnNext(new Action1<CardBean>() {
            @Override
            public void call(CardBean cardBean) {
                mCardsList.add(cardBean);
            }
        }).map(new Func1<CardBean, BaseModel>() {
            @Override
            public BaseModel call(CardBean cardBean) {
                return (BaseModel) DataConvertor.getInstance().convertFromCardBean(cardBean);
            }
        }).toList();
    }

    private List<CardBean> getAllDbs() {
        synchronized (mAllTmpList) {
            if (mAllTmpList != null && mAllTmpList.size() > 0) {
                return mAllTmpList;
            }

            Map<String, Object> kvs = new HashMap<>();
            OrderBy ob = new OrderBy("pos", true);
            mAllTmpList = mCardDao.findByCondition(kvs, "type", ob);

            return mAllTmpList;
        }
    }

    @Override
    public boolean isDbReady() {
        return mCardDao != null;
    }

    /**
     * 物理删除APP
     *
     * @param pos
     */
    @Override
    public void remove(int pos) {
        CardBean cb = findBeanByPos(pos, mAppsList);
        if (cb != null) {
            int appPos = cb.pos;
            mAllTmpList.remove(cb);
            mCardDao.delete(cb);
        }
    }

    /**
     * 物理增加
     *
     * @param info
     */
    @Override
    public void addApp(AppInfo info) {
        CardBean cb = DataConvertor.getInstance().convertFromModel(info);
        if (cb != null) {

        }
    }

    @Override
    public Observable<List<AppInfo>> loadApps() {
        return getAppByCondition(false);
    }

    @Override
    public Observable<List<AppInfo>> loadQuickApps() {
        return getAppByCondition(true);
    }

    private Observable<List<AppInfo>> getAppByCondition(final boolean isQuickApp) {
        return Observable.from(getAllDbs()).filter(new Func1<CardBean, Boolean>() {
            @Override
            public Boolean call(CardBean cardBean) {
                return cardBean.type == CardBean.TYPE_APP;
            }
        }).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                mAppsList.clear();
            }
        }).doOnNext(new Action1<CardBean>() {
            @Override
            public void call(CardBean cardBean) {
                mAppsList.add(cardBean);
            }
        }).map(new Func1<CardBean, AppInfo>() {
            @Override
            public AppInfo call(CardBean cardBean) {
                AppInfo info = new AppInfo();
                info.fromJson(cardBean.jsonData);
                return info;
            }
        }).filter(new Func1<AppInfo, Boolean>() {
            @Override
            public Boolean call(AppInfo info) {
                return isQuickApp ? info.isQuickApp : !info.isQuickApp;
            }
        }).toList();
    }

    private CardBean findBeanByPos(int pos, List<CardBean> cbs) {
        for (CardBean cb : cbs) {
            if (cb.pos == pos) {
                return cb;
            }
        }
        return null;
    }

    private CardBean findCardBeanByInfo(AppInfo info, List<CardBean> cbs) {
        if (cbs != null) {
            boolean isCard = info instanceof BaseModel;
            for (int i = 0; i < cbs.size(); i++) {
                CardBean bean = cbs.get(i);
                int type = isCard ? CardBean.TYPE_CARD : CardBean.TYPE_APP;
                if (bean.type == type && bean.packageName.equals(info.packageName)) {
                    return bean;
                }
            }
        }
        return null;
    }
}