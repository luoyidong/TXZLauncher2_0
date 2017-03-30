package com.txznet.launcher.di.component;

import android.app.Application;
import android.content.Context;

import com.txznet.launcher.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    Application application();

}