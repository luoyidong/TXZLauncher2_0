package com.txznet.libmvp;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public class RxMvpPresenter<T extends MvpView> extends BasePresenter<T> {
    CompositeSubscription mCompositeSubscription;

    @Override
    public void attachView(T view) {
        super.attachView(view);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();
        mCompositeSubscription.clear();
        mCompositeSubscription = null;
    }
}