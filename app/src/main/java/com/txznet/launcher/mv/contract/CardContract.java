package com.txznet.launcher.mv.contract;

import android.graphics.drawable.Drawable;

import com.txznet.launcher.module.PackageManager;
import com.txznet.launcher.ui.model.UiCard;
import com.txznet.libmvp.MvpView;
import com.txznet.libmvp.RxMvpPresenter;

/**
 * Created by TXZ-METEORLUO on 2017/4/9.
 */
public interface CardContract {

    interface View extends MvpView {

        void setName(String name);

        void setDesc(String desc);

        void setAppIcon(Drawable icon);

        void setBgColor(int color);

        UiCard getCardModel();
    }

    abstract class Presenter<T extends View> extends RxMvpPresenter<T> {

        public void onClickBlank() {
            UiCard card = getMvpView().getCardModel();
            if (card != null) {
                PackageManager.getInstance().openApp(card.packageName);
            }
        }
    }
}