package com.txznet.launcher.data.api;

/**
 * Created by UPC on 2017/4/15.
 */

public interface AppApi<T> extends AsyncApi<T> {

    void startApp();

    void stopApp();

    void reqState();

    boolean isReady();
}
