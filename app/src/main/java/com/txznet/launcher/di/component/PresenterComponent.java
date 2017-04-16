package com.txznet.launcher.di.component;

import com.txznet.launcher.di.CardScope;
import com.txznet.launcher.di.module.ApplicationModule;
import com.txznet.launcher.di.module.PresenterModule;
import com.txznet.launcher.mv.MusicPresenter;
import com.txznet.launcher.mv.SimplePresenter;
import com.txznet.launcher.mv.WeatherPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by UPC on 2017/4/16.
 */
@Component(dependencies = LauncherRepositeComponent.class,
        modules = {PresenterModule.class, ApplicationModule.class})
public interface PresenterComponent {

    MusicPresenter getMusicPresenter();

    WeatherPresenter getWeatherPresenter();

    SimplePresenter getSimplePresenter();
}
