package com.txznet.launcher.data;

import com.txznet.launcher.data.api.CardCreateApi;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.module.PackageManager;

import javax.inject.Singleton;

/**
 * Created by TXZ-METEORLUO on 2017/4/9.
 */
@Singleton
public class LocalCardCreator implements CardCreateApi {

    private static LocalCardCreator sCreator = new LocalCardCreator();

    public static LocalCardCreator getInstance() {
        return sCreator;
    }

    @Override
    public BaseModel createCard(String packageName) {
        boolean isSystemApp = PackageManager.getInstance().isSystemApp(packageName);
        BaseModel bm = new BaseModel(packageName,isSystemApp);
        bm.packageName = packageName;

        // TODO 测试方法
        return bm;
    }

    @Override
    public BaseModel createCard(int type) {
        return null;
    }
}