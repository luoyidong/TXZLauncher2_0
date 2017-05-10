package com.txznet.launcher.mv.contract;

import com.txznet.launcher.ui.model.UiApp;

import java.util.List;

/**
 * Created by TXZ-METEORLUO on 2017/5/3.
 */
public interface AppContract {
    interface View extends CardContract.View {
        void showQuickApps(List<UiApp> appInfos);

        List<UiApp> getQuickApps();

        void showAllApps(List<UiApp> apps);

        List<UiApp> getAllApps();

        void hideApps();

        void visibleApps();

        boolean isAppsVisible();
    }

    abstract class Presenter extends CardContract.Presenter<View> {

        public abstract void closePos(int pos);

        public abstract void clickPos(int pos);

        public abstract void onClickBlank();

    }
}