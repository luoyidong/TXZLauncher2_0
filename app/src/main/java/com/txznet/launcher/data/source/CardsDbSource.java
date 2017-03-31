package com.txznet.launcher.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.txznet.launcher.data.api.CardsRepoApi;
import com.txznet.launcher.data.api.CardsSourceApi;
import com.txznet.launcher.data.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
@Singleton
public class CardsDbSource implements CardsRepoApi {

    private Context mContext;

    public CardsDbSource(Context context) {
        mContext = context;
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

    @Override
    public Observable<List<BaseModel>> loadCards() {
        return null;
    }
}