package com.txznet.launcher.di.component;

import com.txznet.launcher.di.CardScope;
import com.txznet.launcher.di.module.LauncherModule;
import com.txznet.launcher.ui.MainActivity;
import com.txznet.launcher.ui.widget.AppCardView;
import com.txznet.launcher.ui.widget.MusicCardView;
import com.txznet.launcher.ui.widget.SysCardView;
import com.txznet.launcher.ui.widget.ThirdCardView;
import com.txznet.launcher.ui.widget.WeatherCardView;

import dagger.Component;

/**
 * Created by TXZ-METEORLUO on 2017/3/30.
 */
@CardScope
@Component(dependencies = {LauncherRepositeComponent.class}, modules = LauncherModule.class)
public interface LauncherComponent {

    void inject(MainActivity activity);

    void inject(WeatherCardView bcv);

    void inject(AppCardView acv);

    void inject(MusicCardView mcv);

    void inject(SysCardView scv);

    void inject(ThirdCardView tcv);
}