package com.txznet.launcher.mv;

import com.txznet.launcher.data.model.BaseModel;
import com.txznet.libmvp.LoadMvpView;
import com.txznet.libmvp.RxMvpPresenter;

import java.util.List;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public interface LauncherContract {
    interface View extends LoadMvpView {
        void showCards(List<BaseModel> cards);
    }

    abstract class Presenter extends RxMvpPresenter<View> {

        abstract void loadCards();

        abstract void addCard(BaseModel card);

        abstract void removeCard(int pos);

        abstract void swapCards(int before, int after);
    }
}