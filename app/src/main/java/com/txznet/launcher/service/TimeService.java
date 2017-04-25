package com.txznet.launcher.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Observable;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by TXZ-METEORLUO on 2017/2/27.
 */
public class TimeService extends Service {

    private TimeReceiver mReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mReceiver = new TimeReceiver();
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public static class TimeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            TIME_OBSERVABLE.notifyTimeChange();
        }
    }

    public static class TimeObservable extends Observable<TimeObservable.TimeObserver> {
        public interface TimeObserver {
            void onTimeChange();
        }

        public void notifyTimeChange() {
            for (int i = 0; i < mObservers.size(); i++) {
                mObservers.get(i).onTimeChange();
            }
        }
    }

    public static TimeObservable TIME_OBSERVABLE = new TimeObservable();
}