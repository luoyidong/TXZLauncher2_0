package com.txznet.launcher.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.txznet.launcher.ui.widget.LoadingView;
import com.txznet.libmvp.LoadMvpView;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public abstract class BaseLoadingActivity extends AppCompatActivity implements LoadMvpView {
    private LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingView = new LoadingView(this, getLoadingMessage());
    }

    public abstract String getLoadingMessage();

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void dismissLoading() {
        mLoadingView.dismiss();
    }

    @Override
    public void showError(Throwable e) {
        e.printStackTrace();
    }
}
