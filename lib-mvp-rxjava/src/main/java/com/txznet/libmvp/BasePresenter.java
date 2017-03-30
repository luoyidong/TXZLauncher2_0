package com.txznet.libmvp;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public abstract class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    T mView;

    boolean isViewActive() {
        return mView != null;
    }

    T getMvpView() {
        return mView;
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void checkViewActive() {
        if (!isViewActive()) {
            throw new NullPointerException("please check mvpView is notNullable！");
        }
    }
}