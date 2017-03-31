package com.txznet.launcher.data;

import android.support.annotation.NonNull;

import com.txznet.launcher.data.api.CardsRepoApi;
import com.txznet.launcher.data.api.CardsSourceApi;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.di.Db;
import com.txznet.launcher.di.Pm;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/3/30.
 */
@Singleton
public class CardsRepositeSource implements CardsRepoApi {
    private CardsSourceApi mPmSource;
    private CardsRepoApi mDbSource;

    @Inject
    public CardsRepositeSource(@Pm @NonNull CardsSourceApi pm, @Db @NonNull CardsSourceApi db) {
        this.mPmSource = pm;
        this.mDbSource = (CardsRepoApi) db;
    }

    @Override
    public Observable<List<BaseModel>> loadCards() {
        return null;
    }

    @Override
    public void swapCards(int before, int after) {

    }

    @Override
    public void closeCard(@NonNull BaseModel bm) {

    }

    @Override
    public void closeCard(int pos) {

    }

    @Override
    public void addCard(BaseModel bm) {

    }

    @Override
    public void saveCard(BaseModel bm) {

    }
}