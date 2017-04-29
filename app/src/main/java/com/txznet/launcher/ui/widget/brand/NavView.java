package com.txznet.launcher.ui.widget.brand;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.txznet.launcher.R;
import com.txznet.launcher.mv.contract.NavContract;
import com.txznet.launcher.mv.NavPresenter;

import javax.inject.Inject;

/**
 * Created by TXZ-METEORLUO on 2017/2/7.
 */
public class NavView extends FrameLayout implements NavContract.View {
    private TextView mRemainTv;
    private ImageView mDirIv;
    private TextView mDirDisTv;
    private TextView mRoadTv;

    @Inject
    NavPresenter mPresenter;

    public NavView(Context context) {
        this(context, null);
    }

    public NavView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.brand_nav_ly, this);
        mRemainTv = (TextView) findViewById(R.id.remain_tv);
        mDirIv = (ImageView) findViewById(R.id.icon_iv);
        mDirDisTv = (TextView) findViewById(R.id.dir_distance_tv);
        mRoadTv = (TextView) findViewById(R.id.road_tv);
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

    @Override
    public void setRemainInfo(String info) {
        mRemainTv.setText(info);
    }

    @Override
    public void setDirectionIcon(int resId) {
        mDirIv.setImageResource(resId);
    }

    @Override
    public void setDirDistance(String distance) {
        mDirDisTv.setText(distance);
    }

    @Override
    public void setRoadName(String roadName) {
        mRoadTv.setText(roadName);
    }
}
