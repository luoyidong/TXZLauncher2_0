package com.txznet.launcher;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;

import com.txznet.launcher.di.component.DaggerLauncherRepositeComponent;
import com.txznet.launcher.di.component.LauncherRepositeComponent;
import com.txznet.launcher.di.module.ApplicationModule;
import com.txznet.launcher.di.module.LauncherRespositeModule;

public class LauncherApp extends MultiDexApplication {
    private LauncherRepositeComponent mRepositeComponent;
    protected static HandlerThread mBackThread;
    protected static Handler mBackHandler;
    protected static HandlerThread mSlowThread;
    protected static Handler mSlowHandler;
    protected static Handler mUiHandler = new Handler(
            Looper.getMainLooper());
    public static LauncherApp sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        mRepositeComponent = DaggerLauncherRepositeComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .launcherRespositeModule(new LauncherRespositeModule())
                .build();
        initHandler();

        InitService.start(this);
    }

    private void initHandler() {
        // 后台进程
        mBackThread = new HandlerThread("AppBack");
        mBackThread.start();
        mBackHandler = new Handler(mBackThread.getLooper());
        mSlowThread = new HandlerThread("AppSlow");
        mSlowThread.start();
        mSlowHandler = new Handler(mSlowThread.getLooper());
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

    public static void runOnBackGround(Runnable r, long delay) {
        if (delay > 0) {
            mBackHandler.postDelayed(r, delay);
        } else {
            mBackHandler.post(r);
        }
    }

    public static void removeBackGroundCallback(Runnable r) {
        mBackHandler.removeCallbacks(r);
    }

    public static void runOnUiGround(Runnable r, long delay) {
        if (delay > 0) {
            mUiHandler.postDelayed(r, delay);
        } else {
            mUiHandler.post(r);
        }
    }

    public static void removeUiGroundCallback(Runnable r) {
        mUiHandler.removeCallbacks(r);
    }

    public static void runOnSlowGround(Runnable r, long delay) {
        mSlowHandler.postDelayed(r, delay);
    }

    public static void removeSlowGroundCallback(Runnable r) {
        mSlowHandler.removeCallbacks(r);
    }
}