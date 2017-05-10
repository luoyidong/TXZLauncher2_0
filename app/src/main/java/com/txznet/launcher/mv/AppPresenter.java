package com.txznet.launcher.mv;

import android.text.TextUtils;

import com.txznet.launcher.data.model.AppInfo;
import com.txznet.launcher.data.repos.AppRepositeSource;
import com.txznet.launcher.module.PackageManager;
import com.txznet.launcher.mv.contract.AppContract;
import com.txznet.launcher.ui.model.UiApp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by TXZ-METEORLUO on 2017/5/3.
 */
public class AppPresenter extends AppContract.Presenter {

    private AppRepositeSource mRepoSource;

    @Inject
    public AppPresenter(AppRepositeSource source) {
        this.mRepoSource = source;
    }

    @Override
    public void attachView(AppContract.View view) {
        super.attachView(view);

        mCompositeSubscription.add(
                mRepoSource.loadApps()
                        .map(new Func1<List<AppInfo>, List<UiApp>>() {
                            @Override
                            public List<UiApp> call(List<AppInfo> appInfos) {
                                List<UiApp> uiApps = new ArrayList<>();
                                for (AppInfo app : appInfos) {
                                    uiApps.add(convert(app));
                                }
                                return uiApps;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<UiApp>>() {
                            @Override
                            public void call(List<UiApp> appInfos) {
                                getMvpView().showAllApps(appInfos);
                            }
                        }));

        mCompositeSubscription.add(
                mRepoSource.loadQuickApps()
                        .map(new Func1<List<AppInfo>, List<UiApp>>() {
                            @Override
                            public List<UiApp> call(List<AppInfo> appInfos) {
                                List<UiApp> uiApps = new ArrayList<>();
                                for (AppInfo app : appInfos) {
                                    uiApps.add(convert(app));
                                }
                                return uiApps;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<UiApp>>() {
                            @Override
                            public void call(List<UiApp> appInfos) {
                                getMvpView().showQuickApps(appInfos);
                            }
                        }));
    }

    private UiApp convert(AppInfo app) {
        UiApp up = new UiApp();
        PackageManager.AppInfo aif = PackageManager.getInstance().getAppInfo(app.packageName);
        if (aif != null) {
            up.iconDrawable = aif.appIcon;
        }
        if (TextUtils.isEmpty(app.name) && aif != null) {
            up.name = aif.appName;
        }

        up.isSystem = app.isSystemApp;
        up.packageName = app.packageName;
        up.isQuickApp = app.isQuickApp;
        return up;
    }

    @Override
    public void closePos(int pos) {
        // 卸载
        List<UiApp> appInfos = getMvpView().getAllApps();
        if (appInfos != null && pos != -1 && pos < appInfos.size()) {
            appInfos.remove(pos);
            getMvpView().showAllApps(appInfos);
            mRepoSource.remove(pos);
        }
    }

    @Override
    public void clickPos(int pos) {

    }

    @Override
    public void onClickBlank() {
        if (getMvpView().isAppsVisible()) {
            getMvpView().hideApps();
        } else {
            getMvpView().visibleApps();
        }
    }
}