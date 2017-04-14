package com.txznet.launcher.data.repos.weather;

import com.txznet.launcher.data.api.DataInterface;
import com.txznet.launcher.data.data.WeatherData;
import com.txznet.launcher.data.repos.LevelRepositeSource;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
public class WeatherLevelRepoSource extends LevelRepositeSource<WeatherData> {
    public WeatherLevelRepoSource(DataInterface<WeatherData>... wds) {
        super(wds);
    }
}
