package com.txznet.launcher;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.txznet.launcher.di.component.DaggerLauncherRepositeComponent;
import com.txznet.launcher.di.component.LauncherRepositeComponent;
import com.txznet.launcher.di.module.ApplicationModule;
import com.txznet.launcher.di.module.LauncherRespositeModule;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public class LauncherApp extends MultiDexApplication {
    private LauncherRepositeComponent mRepositeComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mRepositeComponent = DaggerLauncherRepositeComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .launcherRespositeModule(new LauncherRespositeModule())
                .build();

        InitService.start(this);
    }

    public LauncherRepositeComponent getLauncherRespositeComponent() {
        return mRepositeComponent;
    }
}