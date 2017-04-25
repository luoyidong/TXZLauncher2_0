package com.txznet.launcher;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.txznet.launcher.util.ImageLoaderInitialize;
import com.txznet.sdk.TXZConfigManager;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public class InitService {
    private static final String TAG = InitService.class.getSimpleName();

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
