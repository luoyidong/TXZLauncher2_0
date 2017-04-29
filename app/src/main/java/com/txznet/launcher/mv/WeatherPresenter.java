package com.txznet.launcher.mv;

import android.util.Log;

import com.txznet.launcher.data.data.WeatherData;
import com.txznet.launcher.data.repos.weather.WeatherLevelRepoSource;
import com.txznet.launcher.mv.contract.WeatherContract;

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

    @Inject
    public WeatherPresenter(WeatherLevelRepoSource repoSource) {
        this.mRepoSource = repoSource;
    }

    @Override
    public void attachView(WeatherContract.View view) {
        super.attachView(view);

        mCompositeSubscription.add(mRepoSource.reqData(mIsFirstRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherData>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                        mIsFirstRequest = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");

                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        Log.d(TAG, "onNext:" + weatherData);
                        parseWeatherData(weatherData);
                    }
                }));
    }

    private void parseWeatherData(WeatherData wd) {
        if (wd != null && wd.mWeatherList != null && wd.mWeatherList.size() > 0) {
            // 今天的天气取第一个
            WeatherData.WeatherDetail today = wd.mWeatherList.get(0);
            getMvpView().setWeatherIcon(today.mIconResId);
            getMvpView().setWeather(today.mCurrWeather);
            getMvpView().setWeatherDescAndSugg(today.mWeatherDesc + "  " + today.mSugguest);
            getMvpView().setCity(today.mCity);

            // 设置天气详情
            wd.mWeatherList.remove(0);
            getMvpView().bindWeathers(wd.mWeatherList);
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