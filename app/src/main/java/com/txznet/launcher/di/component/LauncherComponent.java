package com.txznet.launcher.di.component;

import android.app.LauncherActivity;

import com.txznet.launcher.di.CardScope;
import com.txznet.launcher.di.module.LauncherModule;
import com.txznet.launcher.ui.MainActivity;

import dagger.Component;

/**
 * Created by TXZ-METEORLUO on 2017/3/30.
 */
@CardScope
@Component(dependencies = {LauncherRepositeComponent.class, PresenterComponent.class}, modules = LauncherModule.class)
public interface LauncherComponent {

    void inject(MainActivity activity);
}
