package com.txznet.launcher.data.api;

import android.support.annotation.NonNull;

import com.txznet.launcher.data.model.BaseModel;

/**
 * Created by TXZ-METEORLUO on 2017/3/31.
 */
public interface CardsRepoApi<T> extends CardsSourceApi<T> {

    boolean swapCards(int before, int after);

    boolean closeCard(@NonNull BaseModel bm);

    boolean closeCard(int pos);

    boolean addCard(BaseModel bm);

    boolean saveCard(BaseModel bm);

    boolean isDbReady();
}