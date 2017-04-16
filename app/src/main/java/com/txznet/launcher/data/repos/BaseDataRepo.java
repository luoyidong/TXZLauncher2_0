package com.txznet.launcher.data.repos;

import android.util.Log;

import com.txznet.launcher.data.api.DataApi;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 * 这一层负责做缓存管理
 */
public abstract class BaseDataRepo<T, F extends Integer> implements DataApi<T> {
    private static final String TAG = BaseDataRepo.class.getSimpleName();

    protected F mCurrReqKey;
    private Map<F, Observable<T>> mCacheData;

    @Override
    public Observable<T> reqData(boolean forceReq) {
        if (mCurrReqKey != null && mCacheData != null) {
            Observable<T> data = getCacheDataByKey(mCurrReqKey);
            if (data != null) {
                return data;
            }
        }

        Observable<T> newData = getNewData();
        if (newData != null) {
            saveToCacheData(mCurrReqKey, newData);
            return newData;
        }
        return null;
    }

    /**
     * 获取真实的数据
     */
    public abstract Observable<T> getNewData();

    public void saveToCacheData(F key, Observable<T> data) {
        if (mCacheData == null) {
            mCacheData = new HashMap<>();
        }
        if (mCacheData.containsKey(key)) {
            mCacheData.remove(key);
        }
        mCacheData.put(key, data);
    }

    public Observable<T> getCacheDataByKey(F key) {
        if (mCacheData == null) {
            return null;
        }
        Log.d(TAG, "getCacheDataByKey:" + key);

        return mCacheData.get(key);
    }

    @Override
    public int getInterfacePriority() {
        return 0;
    }
}
