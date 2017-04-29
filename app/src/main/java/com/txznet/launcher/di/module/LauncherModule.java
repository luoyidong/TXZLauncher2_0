package com.txznet.launcher.di.module;

import com.txznet.launcher.mv.contract.LauncherContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TXZ-METEORLUO on 2017/3/30.
 */
@Module
public class LauncherModule {
    LauncherContract.View mView;

    public LauncherModule(LauncherContract.View view) {
        mView = view;
    }

    @Provides
    LauncherContract.View provideLauncherContractView() {
        return mView;
    }
}
