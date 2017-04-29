package com.txznet.launcher.ui.widget.brand;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.txznet.launcher.R;
import com.txznet.launcher.mv.contract.AdasContract;
import com.txznet.launcher.mv.AdasPresenter;

import javax.inject.Inject;

/**
 * Created by TXZ-METEORLUO on 2017/4/25.
 */
public class AdasView extends FrameLayout implements AdasContract.View {
    private TextView mSpeedTv;
    private TextView mWeekTv;
    private TextView mTimeTv;
    private ImageView mDirectionIcon;

    @Inject
    AdasPresenter mPresenter;

    public AdasView(Context context) {
        this(context, null);
    }

    public AdasView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.brand_adas_ly, this);
        mSpeedTv = (TextView) findViewById(R.id.speed_tv);
        mWeekTv = (TextView) findViewById(R.id.week_tv);
        mTimeTv = (TextView) findViewById(R.id.time_tv);
        mDirectionIcon = (ImageView) findViewById(R.id.icon_iv);
    }

    @Override
    protected void onAttachedToWindow() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDetachedFromWindow();
    }

    public void showSpeed(String speed) {
        mSpeedTv.setText(speed);
    }

    @Override
    public void showDirection(int dirIcon) {
        mDirectionIcon.setImageResource(dirIcon);
    }

    @Override
    public void showCurrentTraffic(String path) {
        BitmapDrawable drawable = (BitmapDrawable) BitmapDrawable.createFromPath(path);
        if (drawable != null) {
            mDirectionIcon.setImageDrawable(drawable);
        }
    }

    @Override
    public void moveCarDirection(float degree) {
        mDirectionIcon.setRotation(degree);
    }

    @Override
    public void setTimeWeek(String week, String time) {
        mWeekTv.setText(week);
        mTimeTv.setText(time);
    }
}