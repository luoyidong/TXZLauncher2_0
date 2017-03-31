package com.txznet.launcher;

import android.support.multidex.MultiDexApplication;

import com.txznet.launcher.di.component.DaggerLauncherRepositeComponent;
import com.txznet.launcher.di.component.LauncherRepositeComponent;
import com.txznet.launcher.di.module.ApplicationModule;
import com.txznet.launcher.di.module.LauncherRespositeModule;

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

    public static abstract class Runnable1<T1> implements Runnable {
        protected T1 mP1;

        public Runnable1(T1 p1) {
            mP1 = p1;
        }

        public void update(T1 p1) {
            mP1 = p1;
        }
    }
}