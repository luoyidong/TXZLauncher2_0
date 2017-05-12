package com.txznet.launcher.data;

import android.util.Log;

import com.txznet.launcher.data.api.DataConvertApi;
import com.txznet.launcher.data.api.DataCreateApi;
import com.txznet.launcher.data.model.AppInfo;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.db.CardBean;
import com.txznet.launcher.module.PackageManager;
import com.txznet.launcher.util.PropertUtil;

/**
 * Created by TXZ-METEORLUO on 2017/4/9.
 */
public class DataConvertor implements DataCreateApi, DataConvertApi {
    private static final String TAG = DataConvertor.class.getSimpleName();
    private static DataConvertor sCreator = new DataConvertor();

    public static DataConvertor getInstance() {
        return sCreator;
    }

    @Override
    public BaseModel createCard(String packageName) {
        PackageManager.AppInfo appInfo = PackageManager.getInstance().getAppInfo(packageName);
        if (appInfo == null) {
            Log.e(TAG, "createCard:" + packageName);
            return null;
        }

        BaseModel bm = new BaseModel(appInfo.appPkn, appInfo.isSystemApp);
        bm.name = appInfo.appName;

        if (packageName.equals(PropertUtil.getInstance().getDefaultNav())) {
            bm.type = AppInfo.TYPE_NAV;
        } else if (packageName.equals(PropertUtil.getInstance().getDefaultMusic())) {
            bm.type = AppInfo.TYPE_MUSIC;
        } else if (packageName.equals(PropertUtil.getInstance().getDefaultRealCar())) {
            bm.type = AppInfo.TYPE_REAL;
        } else if (packageName.equals(PropertUtil.getInstance().getDefaultWeather())) {
            bm.type = AppInfo.TYPE_WEATHER;
        } else {
            if (appInfo.isSystemApp) {
                bm.type = AppInfo.TYPE_SYSTEM_APP;
            } else {
                bm.type = AppInfo.TYPE_THIRD_APP;
            }
        }

        return bm;
    }

    @Override
    public AppInfo createFromType(int type, boolean isCard) {
        AppInfo appInfo = null;
        if (isCard) {
            appInfo = new BaseModel(type);
        } else {
            appInfo = new AppInfo(type);
        }
        return appInfo;
    }

    @Override
    public AppInfo convertFromCardBean(CardBean cb) {
        AppInfo app = null;
        switch (cb.type) {
            case CardBean.TYPE_APP:
                app = AppInfo.createByJson(cb.jsonData);
                break;
            case CardBean.TYPE_CARD:
                app = BaseModel.createByJson(cb.jsonData);
                break;
        }
        return app;
    }

    @Override
    public CardBean convertFromModel(AppInfo info) {
        CardBean cb = new CardBean();
        if (info instanceof BaseModel) {
            cb.type = CardBean.TYPE_CARD;
        } else {
            cb.type = CardBean.TYPE_APP;
        }

        return cb;
    }
}