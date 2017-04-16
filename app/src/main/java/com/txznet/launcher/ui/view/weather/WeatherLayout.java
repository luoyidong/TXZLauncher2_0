package com.txznet.launcher.ui.view.weather;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.txznet.launcher.R;
import com.txznet.launcher.data.data.WeatherData;

import java.util.List;

/**
 * Created by TXZ-METEORLUO on 2017/1/11.
 */
public class WeatherLayout extends LinearLayout {
    private int mColorSingle;
    private int mColorDouble;

    public WeatherLayout(Context context) {
        this(context, null);
    }

    public WeatherLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);
        mColorSingle = getResources().getColor(R.color.color_weather_right_single);
        mColorDouble = getResources().getColor(R.color.color_weather_right_double);
    }

    public void setWeatherData(List<WeatherData.WeatherDetail> details) {
        if (details == null) {
            return;
        }

        removeAllViewsInLayout();

        int size = details.size();
        if (size >= 4) {
            size = 4;
        }
        for (int i = 0; i < size; i++) {
            WeatherItemView itemView = new WeatherItemView(getContext());
            itemView.refreshUi(details.get(i));
            if (i % 2 == 0) {
                itemView.setBackgroundColor(mColorSingle);
            } else {
                itemView.setBackgroundColor(mColorDouble);
            }

            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            lp.weight = 1;

            addViewInLayout(itemView, i, lp, true);
        }
        requestLayout();
    }
}