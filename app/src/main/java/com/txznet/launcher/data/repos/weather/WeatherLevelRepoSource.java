package com.txznet.launcher.data.repos.weather;

import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.data.WeatherData;
import com.txznet.launcher.data.repos.LevelRepositeSource;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
@Singleton
public class WeatherLevelRepoSource extends LevelRepositeSource<WeatherData, WeatherTxzApi.WeatherListener> {

    @Inject
    public WeatherLevelRepoSource(DataApi<WeatherData, WeatherTxzApi.WeatherListener>... wds) {
        super(wds);

        initialize(new OnInitListener() {
            @Override
            public void onInit(boolean bSucc) {
            }
        });
    }

    @Override
    public void register(WeatherTxzApi.WeatherListener listener) {
        if (getCurrInterface() != null) {
            getCurrInterface().register(listener);
        }
    }

    @Override
    public void unRegister() {
        if (getCurrInterface() != null) {
            getCurrInterface().unRegister();
        }
    }
}