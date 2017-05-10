package com.txznet.launcher.data.api;

import com.txznet.launcher.data.data.NavData;

/**
 * Created by UPC on 2017/4/15.
 */

public interface NavApi {

    void navigateHome();

    void navigateCompany();

    interface OnNavListener {
        void onNavUpdate(NavData nd);
    }
}
