package com.txznet.launcher.data.repos;

import android.util.Log;

import com.txznet.launcher.data.api.DataInterface;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
public abstract class LevelRepositeSource<T> extends BaseDataRepo<T, Integer> {
    private static final String TAG = LevelRepositeSource.class.getSimpleName();

    private DataInterface<T>[] mInters;

    public LevelRepositeSource(DataInterface<T>... inters) {
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

        for (DataInterface<T> inter : mInters) {
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
                DataInterface<T> inter = mInters[i];
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
            throw new NullPointerException("DataInterface is null ！");
        }
        return super.reqData(forceReq);
    }

    @Override
    public Observable<T> getNewData() {
        int index = 0;
        if (mCurrReqKey != null) {
            index = mCurrReqKey;
        }
        DataInterface<T> inter = mInters[index];
        Log.d(TAG, "getNewData with index:" + index + ",class:" + inter.getClass().getSimpleName());
        return inter.reqData(true);
    }

    /**
     * 数据源接口发生改变时触发
     */
    public void onDataApiChange() {
        Integer lastKey = mCurrReqKey;
        checkDataInterfaceKey();
        if (lastKey == null) {
            lastKey = 0;
        }

        if (mCurrReqKey != null && lastKey == mCurrReqKey) {
            reqData(true);
        } else {
            reqData(false);
        }
    }
}