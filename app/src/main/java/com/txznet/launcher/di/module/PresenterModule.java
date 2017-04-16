package com.txznet.launcher.di.module;

import com.txznet.launcher.data.repos.music.MusicLevelRepoSource;
import com.txznet.launcher.data.repos.weather.WeatherLevelRepoSource;
import com.txznet.launcher.mv.MusicPresenter;
import com.txznet.launcher.mv.SimplePresenter;
import com.txznet.launcher.mv.WeatherPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by UPC on 2017/4/16.
 */
@Module
public class PresenterModule {

    @Singleton
    @Provides
    MusicPresenter provideMusicPresenter(MusicLevelRepoSource repoSource) {
        return new MusicPresenter(repoSource);
    }

    @Singleton
    @Provides
    WeatherPresenter provideWeatherPresenter(WeatherLevelRepoSource repoSource) {
        return new WeatherPresenter(repoSource);
    }

    @Singleton
    @Provides
    SimplePresenter provideSimplePresenter() {
        return new SimplePresenter();
    }
}
