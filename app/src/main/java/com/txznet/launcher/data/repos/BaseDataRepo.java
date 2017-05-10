package com.txznet.launcher.data.repos;

import android.util.Log;

import com.txznet.launcher.data.api.DataApi;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 * TODO 这一层负责做缓存管理(DataApi这一层其实不能是rxjava数据层，应该是真正的获取数据层，不应该介入rxJava)
 */
public abstract class BaseDataRepo<T, F extends Integer, H> implements DataApi<T, H> {
    private static final String TAG = BaseDataRepo.class.getSimpleName();
    private boolean mUseCache = false;
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
    protected abstract Observable<T> getNewData();

    protected void saveToCacheData(F key, Observable<T> data) {
        if (!mUseCache) {
            return;
        }

        if (mCacheData == null) {
            mCacheData = new HashMap<>();
        }
        if (mCacheData.containsKey(key)) {
            mCacheData.remove(key);
        }
        mCacheData.put(key, data);
    }

    protected Observable<T> getCacheDataByKey(F key) {
        if (!mUseCache) {
            return null;
        }

        if (mCacheData == null) {
            return null;
        }
        Log.d(TAG, "getCacheDataByKey:" + key);

        return mCacheData.get(key);
    }

    @Override
    public void register(H listener) {

    }

    @Override
    public void unRegister() {

    }

    @Override
    public int getInterfacePriority() {
        return 0;
    }
}
