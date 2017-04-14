package com.txznet.launcher.data.api;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 * 用于提供卡片数据的信息源
 */
public interface DataInterface<T> {

    public static interface OnInitListener {
        void onInit(boolean bSucc);
    }

    public void initialize(OnInitListener listener);

    /**
     * 请求获取数据
     *
     * @param forceReq 是否强制获取，如果不是则取缓存
     * @return
     */
    public Observable<T> reqData(boolean forceReq);

//    public Observable<T> notifyData();

    /**
     * 得到该工具的优先级
     *
     * @return
     */
    public int getInterfacePriority();

}