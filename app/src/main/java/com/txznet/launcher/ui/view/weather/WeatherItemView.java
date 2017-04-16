package com.txznet.launcher.ui.view.weather;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.txznet.launcher.R;
import com.txznet.launcher.data.data.WeatherData;

/**
 * Created by TXZ-METEORLUO on 2017/1/11.
 */
public class WeatherItemView extends FrameLayout {
    private TextView mWeekTv;
    private ImageView mWeatherIv;
    private TextView mWeatherDesTv;
    private TextView mMaxWeatherTv;
    private TextView mMinWeatherTv;

    public WeatherItemView(Context context) {
        this(context, null);
    }

    public WeatherItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }

    private void setUpView() {
        View.inflate(getContext(), R.layout.weather_item_ly, this);
        mWeekTv = (TextView) findViewById(R.id.week_tv);
        mWeatherDesTv = (TextView) findViewById(R.id.weather_des_tv);
        mWeatherIv = (ImageView) findViewById(R.id.weather_iv);
        mMaxWeatherTv = (TextView) findViewById(R.id.max_weather_tv);
        mMinWeatherTv = (TextView) findViewById(R.id.min_weather_tv);
    }

    public void refreshUi(WeatherData.WeatherDetail weather) {
        if (weather == null) {
            return;
        }

        showWeek(weather.mWeek);
        showWeatherIcon(weather.mIconResId);
        showWeatherDes(weather.mWeatherDesc);
        showWeatherVal(weather.mMaxWeather, weather.mMinWeather);
    }

    private void showWeek(String week) {
        this.mWeekTv.setText(week);
    }

    private void showWeatherIcon(int resId) {
        this.mWeatherIv.setImageResource(resId);
    }

    private void showWeatherDes(String des) {
        mWeatherDesTv.setText(des);
    }

    private void showWeatherVal(String max, String min) {
        this.mMaxWeatherTv.setText(max);
        this.mMinWeatherTv.setText(min);
    }
}