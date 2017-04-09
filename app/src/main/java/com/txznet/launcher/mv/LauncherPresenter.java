package com.txznet.launcher.mv;

import android.text.TextUtils;
import android.util.Log;

import com.txznet.launcher.data.CardsRepositeSource;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.module.PackageManager;
import com.txznet.launcher.ui.model.UiCard;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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
    void loadCards() {
        loadCards(true);
    }

    private void loadCards(final boolean showingLoading) {
        if (showingLoading) {
            getMvpView().showLoading();
        }

        mCompositeSubscription.clear();
        mCompositeSubscription.add(mRepoSource.loadCards()
//                .flatMap(new Func1<List<BaseModel>, Observable<UiCard>>() {
//                    @Override
//                    public Observable<UiCard> call(List<BaseModel> baseModels) {
//                        return Observable.from(baseModels).flatMap(new Func1<BaseModel, Observable<UiCard>>() {
//                            @Override
//                            public Observable<UiCard> call(BaseModel model) {
//                                return Observable.just(convertToUiCard(model));
//                            }
//                        });
//                    }
//                })
//                .toList()
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
                .subscribe(new Subscriber<List<UiCard>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                        if (showingLoading) {
                            getMvpView().dismissLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                        getMvpView().showError(e);
                    }

                    @Override
                    public void onNext(List<UiCard> uicards) {
                        Log.d(TAG, "onNext baseModels:" + uicards + ",size:" + (uicards != null ? uicards.size() : 0));
                        if (isViewActive()) {
                            getMvpView().showCards(uicards);
                        }
                    }
                }));
    }

    UiCard convertToUiCard(BaseModel bm) {
        UiCard card = new UiCard();
        card.type = bm.type;
        // TODO 是否虚拟
        card.packageName = bm.packageName;
        card.backgroundColor = bm.bgColor;
        card.desc = bm.desc;
        if (TextUtils.isEmpty(bm.name)) {
            // 通过包名尝试获取，不用在生成BaseModel的时候就获取名字
            PackageManager.AppInfo appInfo = PackageManager.getInstance().getAppInfo(bm.packageName);
            card.name = appInfo.appName;
            card.iconDrawable = appInfo.appIcon;
        }
        return card;
    }

    @Override
    void addCard(BaseModel card) {
        mRepoSource.addCard(card);
    }

    @Override
    void removeCard(int pos) {
        mRepoSource.closeCard(pos);
    }

    @Override
    void swapCards(int before, int after) {
        mRepoSource.swapCards(before, after);
    }
}