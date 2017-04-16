package com.txznet.launcher.data.api;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 * 用于提供卡片数据的信息源
 */
public interface DataApi<T> {

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

    /**
     * 数据源触发器，确保引用一样，否则调用处处理不了类通知事件
     *
     * @return
     */
//    Subscriber<T> getSubscriber();

    /**
     * 得到该工具的优先级
     *
     * @return
     */
    int getInterfacePriority();

}