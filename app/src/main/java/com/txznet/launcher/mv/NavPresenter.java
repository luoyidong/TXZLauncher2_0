package com.txznet.launcher.mv;

import com.txznet.launcher.data.data.NavData;
import com.txznet.launcher.data.repos.navi.NaviLevelRepoSource;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TXZ-METEORLUO on 2017/4/24.
 */
public class NavPresenter extends NavContract.Presenter {

    private NaviLevelRepoSource mRepoSource;

    @Inject
    public NavPresenter(NaviLevelRepoSource nrs) {
        mRepoSource = nrs;
    }

    @Override
    public void attachView(NavContract.View view) {
        super.attachView(view);
        loadNavInfo();
    }

    @Override
    void loadNavInfo() {
        mCompositeSubscription.add(mRepoSource.reqData(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NavData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(NavData navData) {
                    }
                }));
    }
}
