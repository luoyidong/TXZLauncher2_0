package com.txznet.launcher.mv.contract;

import com.txznet.libmvp.MvpView;
import com.txznet.libmvp.RxMvpPresenter;

/**
 * Created by TXZ-METEORLUO on 2017/4/25.
 */
public interface AdasContract {
    interface View extends MvpView {
        void showSpeed(String speed);

        void showDirection(int dirIcon);

        void showCurrentTraffic(String path);

        void moveCarDirection(float degree);

        void setTimeWeek(String week, String time);
    }

    abstract class Presenter extends RxMvpPresenter<View> {
        public abstract void loadAdasInfo();
    }
}
