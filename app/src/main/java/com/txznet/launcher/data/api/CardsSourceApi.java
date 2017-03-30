package com.txznet.launcher.data.api;

import android.support.annotation.NonNull;

import com.txznet.launcher.data.model.BaseModel;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public interface CardsSourceApi {

    Observable<ArrayList<BaseModel>> queryCards();

    void swapCards(int before, int after);

    void closeCard(@NonNull BaseModel bm);

    void closeCard(int pos);

    void addCard(BaseModel bm);

    void saveCard(BaseModel bm);
}