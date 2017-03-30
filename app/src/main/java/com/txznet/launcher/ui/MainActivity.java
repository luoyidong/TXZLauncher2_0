package com.txznet.launcher.ui;

import android.os.Bundle;

import com.txznet.launcher.R;
import com.txznet.launcher.mv.LauncherContract;
import com.txznet.launcher.mv.LauncherPresenter;

import javax.inject.Inject;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public class MainActivity extends BaseLoadingActivity implements LauncherContract.LauncherView {

    @Inject
    private LauncherPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        mPresenter.attachView(this);
    }

    @Override
    public String getLoadingMessage() {
        return null;
    }
}