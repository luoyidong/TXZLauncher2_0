package com.txznet.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Observable;
import android.util.Log;

public class InstallReceiver extends BroadcastReceiver {
    public static final String TAG = InstallReceiver.class.getSimpleName();

    public static final InstallObservable SINSTALL_OBSERVABLE = new InstallObservable();

    public static class InstallObservable extends Observable<InstallObservable.InstallObserver> {

        public interface InstallObserver {
            void onApkInstall(String packageName);

            void onApkUnInstall(String packageName);
        }

        public void notifyApkInstall(String packageName) {
            synchronized (mObservers) {
                for (int i = mObservers.size() - 1; i >= 0; i--) {
                    mObservers.get(i).onApkInstall(packageName);
                }
            }
        }

        public void notifyApkUnInstall(String packageName) {
            synchronized (mObservers) {
                for (int i = mObservers.size() - 1; i >= 0; i--) {
                    mObservers.get(i).onApkUnInstall(packageName);
                }
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 接收安装广播
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getDataString();
            if (packageName.startsWith("package:")) {
                packageName = packageName.substring(packageName.indexOf("package:") + 8);
            }
            SINSTALL_OBSERVABLE.notifyApkInstall(packageName);
        }
        // 接受更新事件
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_CHANGED) || intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getDataString();
            if (packageName.startsWith("package:")) {
                packageName = packageName.substring(packageName.indexOf("package:") + 8);
            }
        }
        // 接收卸载广播
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getDataString();
            if (packageName.startsWith("package:")) {
                packageName = packageName.substring(packageName.indexOf("package:") + 8);
            }
            SINSTALL_OBSERVABLE.notifyApkUnInstall(packageName);

            Log.d(TAG, "removed:" + packageName);
        }
    }
}
