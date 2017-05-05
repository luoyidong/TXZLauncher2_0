package com.txznet.launcher.data;

import android.util.Log;

import com.txznet.launcher.data.api.DataCreateApi;
import com.txznet.launcher.data.api.DataConvertApi;
import com.txznet.launcher.data.model.AppInfo;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.db.CardBean;
import com.txznet.launcher.module.PackageManager;

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
            // TODO 对应从系统中获取的应用信息为空
            return null;
        }

        BaseModel bm = new BaseModel(appInfo.appPkn, appInfo.isSystemApp);
        bm.name = appInfo.appName;

        // TODO 测试方法
        return bm;
    }

    @Override
    public AppInfo createFromType(int type, boolean isCard) {
        return null;
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