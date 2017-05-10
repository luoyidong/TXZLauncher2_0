package com.txznet.launcher.data.repos;

import com.txznet.launcher.InstallReceiver;
import com.txznet.launcher.data.api.AppSourceApi;
import com.txznet.launcher.data.api.AppsRepoApi;
import com.txznet.launcher.data.model.AppInfo;
import com.txznet.launcher.data.source.DbSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/5/9.
 */
@Singleton
public class AppRepositeSource implements AppsRepoApi<AppInfo>, InstallReceiver.InstallObservable.InstallObserver {

    private AppSourceApi<AppInfo> mSourceApi;

    @Inject
    public AppRepositeSource(DbSource appApi) {
        mSourceApi = appApi;

        InstallReceiver.SINSTALL_OBSERVABLE.registerObserver(this);
    }

    @Override
    public void remove(int pos) {
        if (mSourceApi instanceof AppsRepoApi) {
            ((AppsRepoApi) mSourceApi).remove(pos);
        }
    }

    @Override
    public void addApp(AppInfo info) {
        if (mSourceApi instanceof AppsRepoApi) {
            ((AppsRepoApi) mSourceApi).addApp(info);
        }
    }

    @Override
    public Observable<List<AppInfo>> loadApps() {
        return mSourceApi.loadApps();
    }

    @Override
    public Observable<List<AppInfo>> loadQuickApps() {
        return mSourceApi.loadQuickApps();
    }

    @Override
    public void onApkInstall(String packageName) {

    }

    @Override
    public void onApkUnInstall(String packageName) {
    }
}