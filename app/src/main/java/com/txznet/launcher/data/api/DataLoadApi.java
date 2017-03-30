package com.txznet.launcher.data.api;

import com.txznet.launcher.data.model.BaseModel;

import java.util.List;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/3/30.
 */
public interface DataLoadApi {

    Observable<List<BaseModel>> loadFromPackageManager();

    Observable<List<BaseModel>> loadFromDatabase();

    Observable<List<BaseModel>> loadSystemModel();

    Observable<BaseModel> createModelByType(int type);

    Observable<BaseModel> createModelByPackageName(String packageName, boolean isSystemApp);
}
