package com.txznet.launcher.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.txznet.launcher.data.LocalCardCreator;
import com.txznet.launcher.data.api.CardsRepoApi;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.db.CardBean;
import com.txznet.launcher.db.dao.CardDao;
import com.txznet.launcher.module.PackageManager;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
@Singleton
public class CardsDbSource implements CardsRepoApi<BaseModel> {
    private static final String TAG = CardsDbSource.class.getSimpleName();
    private Context mContext;
    @Inject
    private CardDao mCardDao;

    public CardsDbSource(Context context) {
        mContext = context;
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
    public void addCard(BaseModel bm) {

    }

    @Override
    public void saveCard(BaseModel bm) {

    }

    @Override
    public Observable<List<BaseModel>> loadCards() {
        List<CardBean> beans = mCardDao.findAll();
        Collections.sort(beans, new Comparator<CardBean>() {
            @Override
            public int compare(CardBean o1, CardBean o2) {
                if (o1.pos < o2.pos) {
                    return -1;
                } else if (o1.pos > o2.pos) {
                    return 1;
                }
                return 0;
            }
        });

        return Observable.from(beans).map(new Func1<CardBean, BaseModel>() {
            @Override
            public BaseModel call(CardBean cardBean) {
                return null;
            }
        }).toList();
    }

    private BaseModel convert(CardBean cb) {
        BaseModel bm = LocalCardCreator.getInstance().createCard(cb.type);
        bm.packageName = cb.packageName;
        PackageManager.AppInfo appInfo = PackageManager.getInstance().getAppInfo(cb.packageName);
        bm.name = appInfo.appName;
        bm.isSystemApp = appInfo.isSystemApp;

        if (!TextUtils.isEmpty(cb.jsonData)) {
        }
        return bm;
    }

    @Override
    public boolean isDbReady() {
        return mCardDao != null;
    }
}