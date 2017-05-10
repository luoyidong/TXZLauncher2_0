package com.txznet.launcher.data.api;

/**
 * Created by TXZ-METEORLUO on 2017/5/9.
 */
public interface AsyncApi<T> {

    void register(T listener);

    void unRegister();
}
