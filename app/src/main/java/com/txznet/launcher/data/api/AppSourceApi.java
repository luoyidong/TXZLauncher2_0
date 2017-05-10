package com.txznet.launcher.data.api;

import java.util.List;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/4/19.
 * <p/>
 * TODO
 * CardsSourceApi返回的值应该也要换成List，由仓库层统一管理
 */
public interface AppSourceApi<T> {

    /**
     * 加载所有APP
     *
     * @return
     */
    Observable<List<T>> loadApps();

    /**
     * 加载快捷入口APP
     *
     * @return
     */
    Observable<List<T>> loadQuickApps();

}
