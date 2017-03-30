package com.txznet.libmvp;

import android.support.annotation.UiThread;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public interface MvpPresenter<T extends MvpView> {

    @UiThread
    void attachView(T view);

    @UiThread
    void detachView();
}