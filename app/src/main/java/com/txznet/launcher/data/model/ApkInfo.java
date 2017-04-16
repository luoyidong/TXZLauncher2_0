package com.txznet.launcher.data.model;

import android.support.annotation.NonNull;

/**
 * Created by UPC on 2017/4/15.
 */

public class ApkInfo extends AppInfo {

    public ApkInfo(@NonNull String packageName, boolean isSystemApp) {
        super(packageName, isSystemApp);
    }
}
