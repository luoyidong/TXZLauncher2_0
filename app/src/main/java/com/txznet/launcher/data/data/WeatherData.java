package com.txznet.launcher.data.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
public class WeatherData {
    private String mCityName;
    private String mCityCode;
    private List<WeatherDetail> mWeatherList;

    public void setCityName(String cityName) {
        this.mCityName = cityName;
    }

    public String getCityName() {
        return this.mCityName;
    }

    public void setCityCode(String cityCode) {
        mCityCode = cityCode;
    }

    public String getCityCode() {
        return this.mCityCode;
    }

    public void setWeatherList(List<WeatherDetail> weatherList) {
        mWeatherList = weatherList;
    }

    public List<WeatherDetail> getWeatherList() {
        return mWeatherList;
    }

    public static class WeatherDetail {
        public String mWeek;
        public int mIconResId;
        public String mMaxWeather;
        public String mCurrWeather;
        public String mMinWeather;
        public String mWeatherDesc;
        public String mSugguest;
        public String mCity;

        public String toJson() {
            JSONObject jo = new JSONObject();
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    jo.put(field.getName(), field.get(this));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return jo.toString();
        }

        public WeatherDetail assignFromJson(String json) {
            try {
                JSONObject jo = new JSONObject(json);
                Field[] fields = this.getClass().getDeclaredFields();
                for (Field field : fields) {
                    try {
                        field.set(this, jo.opt(field.getName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return this;
        }
    }
}