package com.txznet.launcher.di.module;

import android.content.Context;

import com.txznet.launcher.data.api.CardsSourceApi;
import com.txznet.launcher.data.source.CardsDbSource;
import com.txznet.launcher.data.source.CardsPmSource;
import com.txznet.launcher.di.Db;
import com.txznet.launcher.di.Pm;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TXZ-METEORLUO on 2017/3/30.
 */
@Module
public class LauncherRespositeModule {

    @Singleton
    @Provides
    @Pm
    CardsSourceApi providePmCardsSourceApi(Context context) {
        return new CardsPmSource(context);
    }

    @Singleton
    @Provides
    @Db
    CardsSourceApi provideDbCardsSourceApi(Context context) {
        return new CardsDbSource(context);
    }
}
