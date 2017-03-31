package com.txznet.launcher.di.module;

import android.app.Application;
import android.content.Context;

import com.txznet.launcher.data.api.CardsSourceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
@Module
public class ApplicationModule {
    Application mApp;

    public ApplicationModule(Application app) {
        mApp = app;
    }

    @Provides
    Application provideApplication() {
        return mApp;
    }

    @Provides
    Context provideContext() {
        return mApp;
    }
}