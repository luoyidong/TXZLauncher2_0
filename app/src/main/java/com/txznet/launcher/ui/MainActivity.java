package com.txznet.launcher.ui;

import android.os.Bundle;

import com.txznet.launcher.LauncherApp;
import com.txznet.launcher.R;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.di.component.DaggerLauncherComponent;
import com.txznet.launcher.mv.LauncherContract;
import com.txznet.launcher.mv.LauncherPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public class MainActivity extends BaseLoadingActivity implements LauncherContract.View {

    @Inject
    LauncherPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_main);

        mPresenter.attachView(this);

        DaggerLauncherComponent.builder()
                .launcherRepositeComponent(((LauncherApp) getApplication()).getLauncherRespositeComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onStart() {
        mPresenter.attachView(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.detachView();
        super.onStop();
    }

    @Override
    public String getLoadingMessage() {
        return null;
    }

    @Override
    public void showCards(List<BaseModel> cards) {

    }
}