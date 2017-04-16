package com.txznet.launcher.data.repos.weather;

import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.data.WeatherData;
import com.txznet.launcher.data.repos.LevelRepositeSource;

import javax.inject.Inject;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
public class WeatherLevelRepoSource extends LevelRepositeSource<WeatherData> {

    @Inject
    public WeatherLevelRepoSource(DataApi<WeatherData>... wds) {
        super(wds);
    }
}