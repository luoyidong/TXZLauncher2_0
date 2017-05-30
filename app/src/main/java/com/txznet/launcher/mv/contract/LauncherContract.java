package com.txznet.launcher.mv.contract;

import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.ui.model.UiCard;
import com.txznet.libmvp.LoadMvpView;
import com.txznet.libmvp.RxMvpPresenter;

import java.util.List;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public interface LauncherContract {
    interface View extends LoadMvpView {

        void showCards(List<UiCard> cards);

        void notifyRemove(int pos);

        void notifyAdd(UiCard card, int pos);

        void swapList(int before, int after);
    }

    abstract class Presenter extends RxMvpPresenter<View> {

        public abstract void loadCards();

        public abstract void addCard(BaseModel card, int pos);

        public abstract void removeCard(int pos);

        public abstract void swapCards(int before, int after);
    }
}