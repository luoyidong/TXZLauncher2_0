package com.txznet.launcher;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.txznet.launcher.di.component.ApplicationComponent;
import com.txznet.launcher.di.component.DaggerApplicationComponent;
import com.txznet.launcher.di.module.ApplicationModule;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public class LauncherApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        InitService.start(this);
    }

    public static LauncherApp get(Context context) {
        return (LauncherApp) context.getApplicationContext();
    }

    ApplicationComponent mComponent;

    public ApplicationComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerApplicationComponent
                    .builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mComponent;
    }
}