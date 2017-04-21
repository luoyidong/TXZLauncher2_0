package com.txznet.launcher.data.api;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/4/17.
 * TODO 3.0版本要将BaseDaoRepo实现的接口换为该接口
 */
public interface RepoApi<T> {

    interface OnInitListener {
        void onInit(boolean bSucc);
    }

    void initialize(OnInitListener listener);

    /**
     * 请求获取数据
     *
     * @param forceReq 是否强制获取，如果不是则取缓存
     * @return
     */
    Observable<T> reqData(boolean forceReq);
}
