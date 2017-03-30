package com.txznet.libmvp;

import android.support.annotation.UiThread;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public interface LoadMvpView extends MvpView {

    @UiThread
    void showLoading();

    @UiThread
    void dismissLoading();

    @UiThread
    void showError(Throwable e);
}