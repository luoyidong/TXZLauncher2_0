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
public class InitService extends IntentService implements TXZConfigManager.InitListener {
    private static final String TAG = InitService.class.getSimpleName();
    private static final String KEY_CMD = "cmd";

    public static void start(Context context) {
        Intent intent = new Intent(context, InitService.class);
        intent.putExtra(KEY_CMD, 1);
        context.startService(intent);
    }

    public InitService() {
        super("init-launcher-service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int key = intent.getIntExtra(KEY_CMD, -1);
        if (key == 1) {
            startInit();
        }
    }

    private void startInit() {
        Log.d(TAG, "startInit...");
        // 初始化ImageLoader
        ImageLoaderInitialize.init(getApplication());
        // 初始化同行者
        TXZConfigManager.getInstance().initialize(getApplicationContext(), this);
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "txz init success!");
    }

    @Override
    public void onError(int i, String s) {
        Log.d(TAG, "txz init error:" + s);
    }
}
