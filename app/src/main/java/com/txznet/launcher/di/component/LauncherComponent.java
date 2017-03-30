package com.txznet.launcher.di.component;

import android.app.LauncherActivity;

import com.txznet.launcher.di.module.LauncherModule;

import dagger.Component;

/**
 * Created by TXZ-METEORLUO on 2017/3/30.
 */
@Component(dependencies = LauncherRepositeComponent.class, modules = LauncherModule.class)
public interface LauncherComponent {

    void inject(LauncherActivity activity);
}
