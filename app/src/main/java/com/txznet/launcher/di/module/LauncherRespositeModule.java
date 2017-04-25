package com.txznet.launcher.di.module;

import android.content.Context;

import com.txznet.launcher.data.api.CardsSourceApi;
import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.data.MusicData;
import com.txznet.launcher.data.data.NavData;
import com.txznet.launcher.data.data.WeatherData;
import com.txznet.launcher.data.repos.music.MusicTxzApi;
import com.txznet.launcher.data.repos.navi.NavTxzApi;
import com.txznet.launcher.data.repos.weather.WeatherTxzApi;
import com.txznet.launcher.data.source.DbSource;
import com.txznet.launcher.data.source.PmSource;
import com.txznet.launcher.db.dao.CardDao;
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
        return new PmSource(context);
    }

    @Singleton
    @Provides
    @Db
    CardsSourceApi provideDbCardsSourceApi(Context context) {
        return new DbSource(context, CardDao.getInstance());
    }

    @Singleton
    @Provides
    DataApi<MusicData>[] provideMusicInterface(Context context) {
        DataApi<MusicData>[] faces = new DataApi[1];
        faces[0] = new MusicTxzApi(context);
        return faces;
    }

    @Singleton
    @Provides
    DataApi<WeatherData>[] provideWeatherInterface(Context context) {
        DataApi<WeatherData>[] faces = new DataApi[1];
        faces[0] = new WeatherTxzApi();
        return faces;
    }

    @Singleton
    @Provides
    DataApi<NavData>[] provideNavInterface(Context context) {
        DataApi<NavData>[] faces = new DataApi[1];
        faces[0] = new NavTxzApi(context);
        return faces;
    }
}