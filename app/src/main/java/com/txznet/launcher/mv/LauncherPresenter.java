package com.txznet.launcher.mv;

import com.txznet.launcher.data.CardsRepositeSource;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.ui.model.UiCard;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public class LauncherPresenter extends LauncherContract.Presenter {
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
                .flatMap(new Func1<List<BaseModel>, Observable<UiCard>>() {
                    @Override
                    public Observable<UiCard> call(List<BaseModel> baseModels) {
                        return Observable.from(baseModels).flatMap(new Func1<BaseModel, Observable<UiCard>>() {
                            @Override
                            public Observable<UiCard> call(BaseModel model) {
                                return Observable.just(convertToUiCard(model));
                            }
                        });
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                    }

                    @Override
                    public void onNext(List<UiCard> baseModels) {
                        if (isViewActive()) {
                            getMvpView().showCards(baseModels);
                        }
                    }
                }));
    }

    UiCard convertToUiCard(BaseModel bm) {
        UiCard card = new UiCard();
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