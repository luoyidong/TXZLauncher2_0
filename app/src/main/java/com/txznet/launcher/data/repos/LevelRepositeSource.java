package com.txznet.launcher.data.repos;

import android.util.Log;

import com.txznet.launcher.data.api.DataApi;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
public abstract class LevelRepositeSource<T, F> extends BaseDataRepo<T, Integer, F> {
    private static final String TAG = LevelRepositeSource.class.getSimpleName();

    private DataApi<T, F>[] mInters;

    public LevelRepositeSource(DataApi<T, F>... inters) {
        this.mInters = inters;
    }

    @Override
    public void initialize(OnInitListener listener) {
        if (mInters == null) {
            if (listener != null) {
                listener.onInit(false);
            }
            return;
        }

        for (DataApi<T, F> inter : mInters) {
            inter.initialize(null);
        }

        if (listener != null) {
            listener.onInit(true);
        }

        // 开始生成优先级工具
        checkDataInterfaceKey();
    }

    public void checkDataInterfaceKey() {
        if (mInters != null && mInters.length > 0) {
            int fixPos = 0;
            for (int i = 1; i < mInters.length; i++) {
                DataApi<T, F> inter = mInters[i];
                if (mInters[fixPos].getInterfacePriority() < inter.getInterfacePriority()) {
                    fixPos = i;
                }
            }
            mCurrReqKey = fixPos;
            return;
        }
        mCurrReqKey = 0;
    }

    @Override
    public final Observable<T> reqData(boolean forceReq) {
        if (mInters == null) {
            throw new NullPointerException("DataApi is null ！");
        }
        return super.reqData(forceReq);
    }

    @Override
    protected Observable<T> getNewData() {
        DataApi<T, F> inter = getCurrInterface();
        return inter.reqData(true);
    }

    public DataApi<T, F> getCurrInterface() {
        int index = 0;
        if (mCurrReqKey != null) {
            index = mCurrReqKey;
        }
        DataApi<T, F> inter = mInters[index];
        Log.d(TAG, "getNewData with index:" + index + ",class:" + inter.getClass().getSimpleName());
        return inter;
    }

    /**
     * 数据源接口发生改变时触发
     */
    public Observable<T> onDataApiChange() {
        Integer lastKey = mCurrReqKey;
        checkDataInterfaceKey();
        if (lastKey == null) {
            lastKey = 0;
        }

        Observable<T> tmpData;
        if (mCurrReqKey != null && lastKey == mCurrReqKey) {
            tmpData = reqData(true);
        } else {
            tmpData = reqData(false);
        }

        return tmpData;
    }
}