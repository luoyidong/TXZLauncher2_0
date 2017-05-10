package com.txznet.launcher.data.repos.weather;

import android.util.Log;

import com.txznet.launcher.LauncherApp;
import com.txznet.launcher.R;
import com.txznet.launcher.data.Constants;
import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.data.WeatherData;
import com.txznet.launcher.module.PackageManager;
import com.txznet.launcher.util.DateUtil;
import com.txznet.sdk.TXZNetDataProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by UPC on 2017/4/16.
 */

public class WeatherTxzApi implements DataApi<WeatherData, WeatherTxzApi.WeatherListener> {
    private static final String TAG = WeatherTxzApi.class.getSimpleName();

    private boolean hasGetWeathers;
    private boolean hasRequestWeather;

    private WeatherData mCacheWeatherData;
    private WeatherListener mWeatherListener;

    @Inject
    public WeatherTxzApi() {
    }

    @Override
    public void initialize(OnInitListener listener) {
        if (!PackageManager.getInstance().checkAppExist(Constants.PACKAGE_NAME.TXZ_PACKAGE_NAME)) {
            if (listener != null) {
                listener.onInit(false);
            }
            return;
        }
        if (listener != null) {
            listener.onInit(true);
        }
    }

    @Override
    public Observable<WeatherData> reqData(boolean forceReq) {
        return Observable.create(new Observable.OnSubscribe<WeatherData>() {
            @Override
            public void call(final Subscriber<? super WeatherData> subscriber) {
                if (hasGetWeathers && mCacheWeatherData != null) {
                    subscriber.onStart();
                    subscriber.onNext(mCacheWeatherData);
                    subscriber.onCompleted();
                    return;
                }
                requestWeatherData();
            }
        });
    }

    private void requestWeatherData() {
        LauncherApp.removeBackGroundCallback(reqTask);
        LauncherApp.runOnBackGround(reqTask, 0);
//        if (!hasGetWeathers) {
//            LauncherApp.runOnBackGround(this, 1000 * 5);
//        } else {
//            LauncherApp.runOnBackGround(this, 1000 * 60 * 30);//半个小时更新一次
//        }
    }

    Runnable reqTask = new Runnable() {
        @Override
        public void run() {
            if (hasRequestWeather) {
                LauncherApp.removeBackGroundCallback(this);
                if (hasGetWeathers) {
                    LauncherApp.runOnBackGround(this, 1000 * 60 * 30);
                } else {
                    LauncherApp.runOnBackGround(this, 1000 * 5);
                }
                return;
            }

            Log.d(TAG, "start getWeatherInfo...");
            TXZNetDataProvider.getInstance().getWeatherInfo(new TXZNetDataProvider.NetDataCallback<com.txznet.sdk.bean.WeatherData>() {

                @Override
                public void onResult(com.txznet.sdk.bean.WeatherData weatherData) {
                    Log.d(TAG, "parseWeatherData:" + weatherData);
                    hasGetWeathers = true;

                    WeatherData wData = new WeatherData();
                    wData.mCityCode = weatherData.cityCode;
                    wData.mCityName = weatherData.cityName;
                    wData.mUpdateTime = weatherData.updateTime;
                    if (weatherData.weatherDays != null && weatherData.weatherDays.length > 0) {
                        List<WeatherData.WeatherDetail> wds = new ArrayList<>();
                        for (int i = 0; i < weatherData.weatherDays.length; i++) {
                            com.txznet.sdk.bean.WeatherData.WeatherDay wd = weatherData.weatherDays[i];
                            WeatherData.WeatherDetail wwd = new WeatherData.WeatherDetail();
                            wwd.mCity = weatherData.cityName;
                            wwd.mCurrWeather = wd.currentTemperature + "℃";
                            wwd.mMaxWeather = wd.highestTemperature + "℃";
                            wwd.mMinWeather = wd.lowestTemperature + "℃";
                            wwd.mWeek = WEEK_CONSTANTS[wd.dayOfWeek - 1];
                            wwd.mSugguest = wd.quality;
                            wwd.mWeatherDesc = wd.weather;
                            if (i == 0) {
                                wwd.mIconResId = getResourceIdByWeather(wd.weather);
                            } else {
                                wwd.mIconResId = getSmallResByWeather(wd.weather);
                            }
                            wds.add(wwd);
                        }
                        wData.mWeatherList = wds;
                    }

                    mCacheWeatherData = wData;
                    if (mWeatherListener != null) {
                        mWeatherListener.onWeatherUpdate(wData);
                    }
                }

                @Override
                public void onError(int i) {
                    hasRequestWeather = false;
                }
            });
            hasRequestWeather = true;
        }
    };

    public static final String[] WEEK_CONSTANTS = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    @Override
    public int getInterfacePriority() {
        return Constants.Repo_Level.Weather_Level.LEVEL_TXZ_WEATHER;
    }

    public int getResourceIdByWeather(String weather) {
        int id = 0;
        if (weather == null || weather.equals(""))
            return id;
        if (weather.contains("到")) {
            int index = weather.indexOf("到");
            weather = weather.substring(index + 1, weather.length());
        }

        if (weather.contains("转")) {
            int index = weather.indexOf("转");
            weather = weather.substring(index + 1, weather.length());
        }

        if ("暴雪".equals(weather)) {
            id = R.drawable.weather_xue;
        } else if ("暴雨".equals(weather)) {
            id = R.drawable.weather_yu;
        } else if ("冰雨".equals(weather)) {
            id = R.drawable.weather_bingyu;
        } else if ("大暴雨".equals(weather)) {
            id = R.drawable.weather_yu;
        } else if ("大雪".equals(weather)) {
            id = R.drawable.weather_xue;
        } else if ("大雨".equals(weather)) {
            id = R.drawable.weather_yu;
        } else if ("多云".equals(weather)) {
            if (DateUtil.isNight()) {
                id = R.drawable.weather_duoyun_night;
            } else {
                id = R.drawable.weather_duoyun;
            }
        } else if ("浮尘".equals(weather)) {
            id = R.drawable.weather_sha;
        } else if ("雷阵雨".equals(weather)) {
            id = R.drawable.weather_leizhenyu;
        } else if ("雷阵雨伴有冰雹".equals(weather)) {
            id = R.drawable.weather_yujiaxue;
        } else if ("霾".equals(weather)) {
            id = R.drawable.weather_mai;
        } else if ("晴".equals(weather)) {
            if (DateUtil.isNight()) {
                id = R.drawable.weather_qing_night;
            } else {
                id = R.drawable.weather_qing;
            }
        } else if ("沙尘暴".equals(weather)) {
            id = R.drawable.weather_shachenbao;
        } else if ("特大暴雨".equals(weather)) {
            id = R.drawable.weather_yu;
        } else if ("雾".equals(weather)) {
            id = R.drawable.weather_wu;
        } else if ("小雪".equals(weather)) {
            id = R.drawable.weather_xue;
        } else if ("小雨".equals(weather)) {
            id = R.drawable.weather_yu;
        } else if ("扬沙".equals(weather)) {
            id = R.drawable.weather_sha;
        } else if ("阴".equals(weather)) {
            id = R.drawable.weather_yin;
        } else if ("雨夹雪".equals(weather)) {
            id = R.drawable.weather_yujiaxue;
        } else if ("阵雨".equals(weather)) {
            if (DateUtil.isNight()) {
                id = R.drawable.weather_zhenyu_night;
            } else {
                id = R.drawable.weather_zhenyu;
            }
        } else if ("阵雪".equals(weather)) {
            if (DateUtil.isNight()) {
                id = R.drawable.weather_xue;
            } else {
                id = R.drawable.weather_xue;
            }
        } else if ("中雪".equals(weather)) {
            id = R.drawable.weather_xue;
        } else if ("中雨".equals(weather)) {
            id = R.drawable.weather_yu;
        } else {
            id = R.drawable.weather_na;
        }
        return id;
    }

    public int getSmallResByWeather(String weather) {
        int id = 0;
        if (weather == null || weather.equals(""))
            return id;
        // 过滤小到XXXX 中到XXX 大到XXXXX
        if (weather.contains("到")) {
            int index = weather.indexOf("到");
            weather = weather.substring(index + 1, weather.length());
        }

        if (weather.contains("转")) {
            int index = weather.indexOf("转");
            weather = weather.substring(index + 1, weather.length());
        }

        if ("暴雪".equals(weather)) {
            id = R.drawable.weather_xue_small;
        } else if ("暴雨".equals(weather)) {
            id = R.drawable.weather_yu_small;
        } else if ("冰雨".equals(weather)) {
            id = R.drawable.weather_bingyu_small;
        } else if ("大暴雨".equals(weather)) {
            id = R.drawable.weather_yu_small;
        } else if ("大雪".equals(weather)) {
            id = R.drawable.weather_xue_small;
        } else if ("大雨".equals(weather)) {
            id = R.drawable.weather_yu_small;
        } else if ("多云".equals(weather)) {
            if (DateUtil.isNight()) {
                id = R.drawable.weather_duoyun_night_small;
            } else {
                id = R.drawable.weather_duoyun_small;
            }
        } else if ("浮尘".equals(weather)) {
            id = R.drawable.weather_sha_small;
        } else if ("雷阵雨".equals(weather)) {
            id = R.drawable.weather_leizhenyu_small;
        } else if ("雷阵雨伴有冰雹".equals(weather)) {
            id = R.drawable.weather_yujiaxue_small;
        } else if ("霾".equals(weather)) {
            id = R.drawable.weather_mai_small;
        } else if ("晴".equals(weather)) {
            if (DateUtil.isNight()) {
                id = R.drawable.weather_qing_night_small;
            } else {
                id = R.drawable.weather_qing_small;
            }
        } else if ("沙尘暴".equals(weather)) {
            id = R.drawable.weather_shachenbao_small;
        } else if ("特大暴雨".equals(weather)) {
            id = R.drawable.weather_yu_small;
        } else if ("雾".equals(weather)) {
            id = R.drawable.weather_wu_small;
        } else if ("小雪".equals(weather)) {
            id = R.drawable.weather_xue_small;
        } else if ("小雨".equals(weather)) {
            id = R.drawable.weather_yu_small;
        } else if ("扬沙".equals(weather)) {
            id = R.drawable.weather_sha_small;
        } else if ("阴".equals(weather)) {
            id = R.drawable.weather_yin_small;
        } else if ("雨夹雪".equals(weather)) {
            id = R.drawable.weather_yujiaxue_small;
        } else if ("阵雨".equals(weather)) {
            if (DateUtil.isNight()) {
                id = R.drawable.weather_zhenyu_night_small;
            } else {
                id = R.drawable.weather_zhenyu_small;
            }
        } else if ("阵雪".equals(weather)) {
            if (DateUtil.isNight()) {
                id = R.drawable.weather_xue_small;
            } else {
                id = R.drawable.weather_xue_small;
            }
        } else if ("中雪".equals(weather)) {
            id = R.drawable.weather_xue_small;
        } else if ("中雨".equals(weather)) {
            id = R.drawable.weather_yu_small;
        } else {
            id = R.drawable.weather_na_small;
        }
        return id;
    }

    @Override
    public void register(WeatherListener listener) {
        mWeatherListener = listener;
    }

    @Override
    public void unRegister() {
        mWeatherListener = null;
    }

    public interface WeatherListener {
        void onWeatherUpdate(WeatherData wd);
    }
}