package com.txznet.launcher.mv.contract;

import com.txznet.libmvp.MvpView;
import com.txznet.libmvp.RxMvpPresenter;

/**
 * Created by TXZ-METEORLUO on 2017/4/24.
 */
public interface NavContract {

    interface View extends MvpView {
        void setRemainInfo(String info);

        void setDirectionIcon(int resId);

        void setDirDistance(String distance);

        void setRoadName(String roadName);
    }

    abstract class Presenter extends RxMvpPresenter<View> {

        public abstract void loadNavInfo();

    }
}