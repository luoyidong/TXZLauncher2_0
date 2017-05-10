package com.txznet.launcher.mv;

import android.util.Log;

import com.txznet.launcher.data.data.WeatherData;
import com.txznet.launcher.data.repos.weather.WeatherLevelRepoSource;
import com.txznet.launcher.data.repos.weather.WeatherTxzApi;
import com.txznet.launcher.mv.contract.WeatherContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by UPC on 2017/4/16.
 */

public class WeatherPresenter extends WeatherContract.Presenter {
    private static final String TAG = WeatherPresenter.class.getSimpleName();
    private boolean mIsFirstRequest = true;
    private WeatherLevelRepoSource mRepoSource;

    private WeatherTxzApi.WeatherListener mWeatherListener = new WeatherTxzApi.WeatherListener() {
        @Override
        public void onWeatherUpdate(WeatherData wd) {
            parseWeatherData(wd);
        }
    };

    @Inject
    public WeatherPresenter(WeatherLevelRepoSource repoSource) {
        this.mRepoSource = repoSource;
    }

    @Override
    public void attachView(WeatherContract.View view) {
        super.attachView(view);

        mRepoSource.register(mWeatherListener);

        mCompositeSubscription.add(mRepoSource.reqData(mIsFirstRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherData>() {
                    @Override
                    public void onCompleted() {
                        mIsFirstRequest = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        parseWeatherData(weatherData);
                    }
                }));
    }

    @Override
    public void detachView() {
        super.detachView();
        mRepoSource.unRegister();
    }

    private void parseWeatherData(WeatherData wd) {
        Log.d(TAG, "parseWeatherData:" + wd.toString());

        if (wd != null && wd.mWeatherList != null && wd.mWeatherList.size() > 0) {
            // 今天的天气取第一个
            WeatherData.WeatherDetail today = wd.mWeatherList.get(0);
            getMvpView().setWeatherIcon(today.mIconResId);
            getMvpView().setWeather(today.mCurrWeather);
            getMvpView().setWeatherDescAndSugg(today.mWeatherDesc + "  " + today.mSugguest);
            getMvpView().setCity(today.mCity);

            // 设置天气详情
            List<WeatherData.WeatherDetail> wds = new ArrayList<>();
            wds.addAll(wd.mWeatherList);
            wds.remove(0);
            getMvpView().bindWeathers(wds);
        }
    }

    @Override
    public void onClickBlank() {
        if (getMvpView().isExpand()) {
            getMvpView().retractView();
        } else {
            getMvpView().expandView();
        }
    }
}