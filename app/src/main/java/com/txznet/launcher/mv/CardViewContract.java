package com.txznet.launcher.mv;

import android.graphics.drawable.Drawable;

import com.txznet.libmvp.MvpView;

/**
 * Created by TXZ-METEORLUO on 2017/4/9.
 */
public interface CardViewContract {

    interface View extends MvpView {

        void setName(String name);

        void setDesc(String desc);

        void setAppIcon(Drawable icon);

        void setBgColor(int color);
    }
}