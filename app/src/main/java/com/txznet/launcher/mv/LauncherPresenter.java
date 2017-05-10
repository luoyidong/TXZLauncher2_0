package com.txznet.launcher.mv;

import android.text.TextUtils;
import android.util.Log;

import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.data.repos.CardsRepositeSource;
import com.txznet.launcher.module.PackageManager;
import com.txznet.launcher.mv.contract.LauncherContract;
import com.txznet.launcher.ui.model.UiCard;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public class LauncherPresenter extends LauncherContract.Presenter {
    private static final String TAG = LauncherPresenter.class.getSimpleName();

    private CardsRepositeSource mRepoSource;

    @Inject
    public LauncherPresenter(CardsRepositeSource crs) {
        mRepoSource = crs;
    }

    @Override
    public void attachView(LauncherContract.View view) {
        super.attachView(view);
        loadCards();
    }

    @Override
    public void loadCards() {
        loadCards(true);
    }

    private void loadCards(final boolean showingLoading) {
        if (showingLoading) {
            getMvpView().showLoading();
        }

        mCompositeSubscription.add(mRepoSource.loadCards()
                .map(new Func1<List<BaseModel>, List<UiCard>>() {
                    @Override
                    public List<UiCard> call(List<BaseModel> baseModels) {
                        List<UiCard> uiCards = new ArrayList<>();
                        for (BaseModel bm : baseModels) {
                            uiCards.add(convertToUiCard(bm));
                        }
                        return uiCards;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (showingLoading) {
                            getMvpView().dismissLoading();
                        }
                    }
                })
                .subscribe(new Subscriber<List<UiCard>>() {
                    @Override
                    public void onCompleted() {
                        if (showingLoading) {
                            getMvpView().dismissLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                        if (showingLoading) {
                            getMvpView().dismissLoading();
                        }
                    }

                    @Override
                    public void onNext(List<UiCard> uicards) {
                        Log.d(TAG, "onNext:" + uicards);
                        if (isViewActive()) {
                            getMvpView().showCards(uicards);
                        }
                    }
                }));
    }

    UiCard convertToUiCard(BaseModel bm) {
        UiCard card = new UiCard();
        card.type = bm.type;
        card.packageName = bm.packageName;
        card.backgroundColor = bm.bgColor;
        card.desc = bm.desc;
        PackageManager.AppInfo appInfo = PackageManager.getInstance().getAppInfo(bm.packageName);
        if (TextUtils.isEmpty(bm.name) && appInfo != null) {
            card.name = appInfo.appName;
        } else {
            card.name = bm.name;
        }
        if (appInfo != null) {
            card.iconDrawable = appInfo.appIcon;
        }

        return card;
    }

    @Override
    public void addCard(BaseModel card) {
        mRepoSource.addCard(card);
    }

    @Override
    public void removeCard(int pos) {
        if (mRepoSource.closeCard(pos)) {
            getMvpView().notifyRemove(pos);
        }
    }

    @Override
    public void swapCards(int before, int after) {
        if (mRepoSource.swapCards(before, after)) {
            getMvpView().swapList(before, after);
        }
    }
}