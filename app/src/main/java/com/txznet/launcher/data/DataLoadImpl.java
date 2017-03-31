package com.txznet.launcher.data;

import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Singleton;

/**
 * 数据加载器
 */
@Singleton
public class DataLoadImpl {
    private Context mContext;

    @Nullable
    private static DataLoadImpl INSTANCE = null;

    private DataLoadImpl(Context context) {
        mContext = context;
    }

    public static DataLoadImpl getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DataLoadImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataLoadImpl(context);
                }
            }
        }
        return INSTANCE;
    }
}