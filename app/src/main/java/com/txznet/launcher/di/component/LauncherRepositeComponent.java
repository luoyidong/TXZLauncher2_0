package com.txznet.launcher.di.component;

import com.txznet.launcher.data.repos.CardsRepositeSource;
import com.txznet.launcher.data.repos.brand.AdasRepoSource;
import com.txznet.launcher.data.repos.music.MusicLevelRepoSource;
import com.txznet.launcher.data.repos.navi.NaviLevelRepoSource;
import com.txznet.launcher.data.repos.weather.WeatherLevelRepoSource;
import com.txznet.launcher.di.module.ApplicationModule;
import com.txznet.launcher.di.module.LauncherRespositeModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by TXZ-METEORLUO on 2017/3/30.
 */
@Singleton
@Component(modules = {LauncherRespositeModule.class, ApplicationModule.class})
public interface LauncherRepositeComponent {

    CardsRepositeSource getCardsRepositeSource();

    MusicLevelRepoSource getMusicRepoSource();

    WeatherLevelRepoSource getWeatherRepoSource();

    NaviLevelRepoSource getNaviRepoSource();

    AdasRepoSource getAdasRepoSource();
}