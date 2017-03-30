package com.txznet.launcher.data.source;

import android.support.annotation.NonNull;

import com.txznet.launcher.data.api.CardsSourceApi;
import com.txznet.launcher.data.model.BaseModel;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public class CardsDbSource implements CardsSourceApi {

    @Override
    public Observable<ArrayList<BaseModel>> queryCards() {
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