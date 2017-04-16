package com.txznet.launcher.data.repos.weather;

import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.data.WeatherData;

import rx.Observable;

/**
 * Created by UPC on 2017/4/16.
 */

public class WeatherTxzApi implements DataApi<WeatherData>{

    @Override
    public void initialize(OnInitListener listener) {

    }

    @Override
    public Observable<WeatherData> reqData(boolean forceReq) {
        return null;
    }

    @Override
    public int getInterfacePriority() {
        return 0;
    }
}
