package com.txznet.launcher.di.component;

import com.txznet.launcher.data.repos.CardsRepositeSource;
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
}