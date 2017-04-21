package com.txznet.launcher.data.api;

import com.txznet.launcher.data.model.AppInfo;

/**
 * Created by TXZ-METEORLUO on 2017/4/19.
 */
public interface AppsRepoApi<T> extends AppSourceApi<T> {

    void remove(int pos);

    void addApp(AppInfo info);
}
