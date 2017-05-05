package com.txznet.launcher.ui.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.txznet.launcher.R;
import com.txznet.launcher.data.data.WeatherData;
import com.txznet.launcher.mv.WeatherPresenter;
import com.txznet.launcher.mv.contract.WeatherContract;
import com.txznet.launcher.ui.MainActivity;
import com.txznet.launcher.ui.view.weather.WeatherLayout;

import java.util.List;

/**
 * Created by UPC on 2017/4/16.
 */

public class WeatherCardView extends ExpandCardView<WeatherPresenter> implements WeatherContract.View {
    public WeatherCardView(Context context) {
        super(context);
    }

    public WeatherCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void inject(MainActivity ma) {
        ma.getComponent().inject(this);
    }

    @Override
    public void setWeatherIcon(int resId) {
        ImageView iv = (ImageView) findViewById(R.id.icon_iv);
        iv.setImageResource(resId);
    }

    @Override
    public void setWeather(String currWeather) {
        TextView tv = (TextView) findViewById(R.id.card_name_tv);
        tv.setText(currWeather);
    }

    @Override
    public void setWeatherDescAndSugg(String descAndSugg) {
        TextView tv = (TextView) findViewById(R.id.card_des_tv);
        tv.setText(descAndSugg);
    }

    @Override
    public void setCity(String name) {
        TextView tv = (TextView) findViewById(R.id.city_tv);
        tv.setText(name);
    }

    @Override
    public void bindWeathers(List<WeatherData.WeatherDetail> wds) {
        View view = getExpandableView();
        if (view != null && view instanceof WeatherLayout) {
            ((WeatherLayout) view).setWeatherData(wds);
        }
    }

    @Override
    public View getExpandableView() {
        return findViewById(R.id.weather_ly);
    }

    @Override
    public int getLayoutId() {
        return R.layout.card_weather_ly;
    }

    @Override
    public void findViewById(View view) {
        mCardView = (CardView) view.findViewById(R.id.card_view);
        mCardLy = view.findViewById(R.id.card_ly);
    }
}
