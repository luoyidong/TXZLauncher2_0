package com.txznet.launcher.mv;

import com.txznet.launcher.data.data.AdasData;
import com.txznet.launcher.data.repos.brand.AdasRepoSource;
import com.txznet.launcher.service.TimeService;
import com.txznet.launcher.util.DateUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TXZ-METEORLUO on 2017/4/25.
 */
public class AdasPresenter extends AdasContract.Presenter {

    private AdasRepoSource mRepoSource;

    @Inject
    public AdasPresenter(AdasRepoSource repoSource) {
        this.mRepoSource = repoSource;

        TimeService.TIME_OBSERVABLE.registerObserver(new TimeService.TimeObservable.TimeObserver() {
            @Override
            public void onTimeChange() {
                getMvpView().setTimeWeek(DateUtil.getSimpleWeek(), DateUtil.getCurrSimpleTime());
            }
        });
    }

    private void refreshCurrTime() {
        String weekStr = DateUtil.getSimpleWeek();
        String timeStr = DateUtil.getCurrSimpleTime();
        getMvpView().setTimeWeek(weekStr, timeStr);
    }

    @Override
    public void attachView(AdasContract.View view) {
        super.attachView(view);
        loadAdasInfo();
    }

    @Override
    void loadAdasInfo() {
        refreshCurrTime();
        mCompositeSubscription.add(mRepoSource
                .reqData(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AdasData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(AdasData adasData) {
                    }
                }));
    }

    private void parseAdasData(AdasData ad){
        if(ad != null) {

        }
    }
}