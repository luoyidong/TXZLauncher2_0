package com.txznet.launcher;

import android.content.Context;
import android.util.Log;

import com.txznet.launcher.util.ImageLoaderInitialize;
import com.txznet.sdk.TXZConfigManager;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public class Initialize {
    private static final String TAG = Initialize.class.getSimpleName();

    public static void init(Context context) {
        Log.d(TAG, "startInit...");
        // 初始化ImageLoader
        ImageLoaderInitialize.init(context);
        // 初始化同行者
        TXZConfigManager.getInstance().initialize(context, new TXZConfigManager.InitListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
