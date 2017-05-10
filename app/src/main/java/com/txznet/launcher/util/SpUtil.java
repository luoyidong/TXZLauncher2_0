package com.txznet.launcher.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.txznet.launcher.LauncherApp;

/**
 * Created by TXZ-METEORLUO on 2017/4/9.
 */
public class SpUtil {
    private static final String SP_NAME = ".Launcher.xml";
    private static final String KEY_FIRST_ENTRY = "firstEntry";

    private static SharedPreferences sp = null;
    private static SharedPreferences.Editor editor = null;
    private static SpUtil spUtil = new SpUtil();

    static {
        sp = LauncherApp.sApp.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SpUtil getInstance() {
        return spUtil;
    }

    public void setFirstLoadFlag(boolean isFirst) {
        putBoolean(KEY_FIRST_ENTRY, isFirst);
    }

    public boolean isFirstLoadCards() {
        return getBoolean(KEY_FIRST_ENTRY, true);
    }

    private void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    private boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }
}
