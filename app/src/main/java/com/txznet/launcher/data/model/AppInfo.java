package com.txznet.launcher.data.model;

import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * Created by TXZ-METEORLUO on 2017/3/31.
 */
public class AppInfo {

    public String packageName;

    public boolean isSystemApp;

    public AppInfo(@NonNull String packageName, boolean isSystemApp) {
        this.packageName = packageName;
        this.isSystemApp = isSystemApp;
    }
}
