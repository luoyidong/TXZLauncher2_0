package com.txznet.launcher.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.j256.ormlite.stmt.query.OrderBy;
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

    @Inject
    public DbSource(Context context, CardDao cd) {
        mContext = context;
        mCardDao = cd;
    }

    @Override
    public void swapCards(int before, int after) {

    }

    @Override
    public void closeCard(@NonNull BaseModel bm) {

    }

    @Override
    public void closeCard(int pos) {

    }

    @Override
    public void addCard(@NonNull BaseModel bm) {

    }

    @Override
    public void saveCard(@NonNull BaseModel bm) {

    }

    @Override
    public Observable<List<BaseModel>> loadCards() {
        List<CardBean> beans = getAllDbs();
        return Observable.from(beans).filter(new Func1<CardBean, Boolean>() {
            @Override
            public Boolean call(CardBean cardBean) {
                return cardBean.type == CardBean.TYPE_CARD;
            }
        }).map(new Func1<CardBean, BaseModel>() {
            @Override
            public BaseModel call(CardBean cardBean) {
//                return LocalCardCreator.getInstance().createFromCardBean(cardBean);
                return null;
            }
        }).toList();
    }

    private List<CardBean> getAllDbs() {
        List<CardBean> cbs = null;
        if (mAllTmpList != null && mAllTmpList.size() > 0) {
            cbs = new ArrayList<>();
            synchronized (mAllTmpList) {
                cbs.addAll(mAllTmpList);
            }
            return cbs;
        }

        Map<String, Object> kvs = new HashMap<>();
        OrderBy ob = new OrderBy("pos", true);
        cbs = mCardDao.findByCondition(kvs, "type", ob);

        synchronized (mAllTmpList) {
            if (cbs != null && cbs.size() > 0) {
                mAllTmpList.clear();
                mAllTmpList.addAll(cbs);
            } else if (cbs == null) {
                cbs = new ArrayList<>();
            }
        }
        Log.d(TAG, "getAllDbs:" + cbs);
        return cbs;
    }

//    private BaseModel convert(CardBean cb) {
////        BaseModel bm = LocalCardCreator.getInstance().createCard(cb.type);
////        bm.packageName = cb.packageName;
////        PackageManager.AppInfo appInfo = PackageManager.getInstance().getAppInfo(cb.packageName);
////        bm.name = appInfo.appName;
////        bm.isSystemApp = appInfo.isSystemApp;
////
////        if (!TextUtils.isEmpty(cb.jsonData)) {
////        }
//        return bm;
//    }

    @Override
    public boolean isDbReady() {
        return mCardDao != null;
    }

    @Override
    public void remove(int pos) {

    }

    @Override
    public void addApp(AppInfo info) {
    }

    @Override
    public Observable<List<AppInfo>> loadApps() {
        return Observable.from(getAllDbs()).filter(new Func1<CardBean, Boolean>() {
            @Override
            public Boolean call(CardBean cardBean) {
                return cardBean.type == CardBean.TYPE_APP;
            }
        }).map(new Func1<CardBean, AppInfo>() {
            @Override
            public AppInfo call(CardBean cardBean) {
                AppInfo info = new AppInfo();
                info.fromJson(cardBean.jsonData);
                return info;
            }
        }).toList();
    }
}