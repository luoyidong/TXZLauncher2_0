package com.txznet.launcher.mv.contract;

import com.txznet.launcher.data.data.WeatherData;
import com.txznet.launcher.mv.contract.CardContract;

import java.util.List;

/**
 * Created by UPC on 2017/4/16.
 */

public interface WeatherContract {

    interface View extends CardContract.View {
        void setWeatherIcon(int resId);

        void setWeather(String currWeather);

        void setWeatherDescAndSugg(String descAndSugg);

        void setCity(String name);

        void bindWeathers(List<WeatherData.WeatherDetail> wds);

        void expandView();

        void retractView();

        boolean isExpand();
    }

    abstract class Presenter extends CardContract.Presenter<View> {
    }
}