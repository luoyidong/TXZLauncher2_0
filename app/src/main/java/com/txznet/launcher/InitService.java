package com.txznet.launcher;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public class InitService extends IntentService {
    private static final String KEY_CMD = "cmd";

    public static void start(Context context) {
        Intent intent = new Intent(context, InitService.class);
        intent.putExtra(KEY_CMD, 1);
        context.startService(intent);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
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

    }
}
