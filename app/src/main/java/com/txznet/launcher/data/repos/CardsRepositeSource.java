package com.txznet.launcher.data.repos;

import android.support.annotation.NonNull;

import com.txznet.launcher.data.LocalCardCreator;
import com.txznet.launcher.data.api.CardsRepoApi;
import com.txznet.launcher.data.api.CardsSourceApi;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.di.Db;
import com.txznet.launcher.di.Pm;
import com.txznet.launcher.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by TXZ-METEORLUO on 2017/3/30.
 * 操作的记录都保存到数据库中
 */
@Singleton
public class CardsRepositeSource implements CardsRepoApi<BaseModel> {
    private CardsRepoApi mDbSource;
    private CardsSourceApi mPmSource;

    @Inject
    public CardsRepositeSource(@Pm @NonNull CardsSourceApi pm, @Db @NonNull CardsSourceApi db) {
        this.mPmSource = pm;
        this.mDbSource = (CardsRepoApi) db;
    }

    @Override
    public Observable<List<BaseModel>> loadCards() {
        boolean isFirst = SpUtil.getInstance().isFirstEnter();
        if (!isFirst) {
            return mDbSource.loadCards();
        }
        SpUtil.getInstance().setFirstEnterFlag(false);

        Observable<List<String>> datas = mPmSource.loadCards();
        return datas.map(new Func1<List<String>, List<BaseModel>>() {

            @Override
            public List<BaseModel> call(List<String> strings) {
                List<BaseModel> bms = new ArrayList<>();
                for (String p : strings) {
                    BaseModel bm = convertPackageToBm(p);
                    if (bm == null) {
                        continue;
                    }
                    bms.add(bm);
                }
                return bms;
            }
        }).doOnNext(new Action1<List<BaseModel>>() {

            @Override
            public void call(List<BaseModel> baseModels) {
                for (BaseModel bm : baseModels) {
                    mDbSource.addCard(bm);
                }
            }
        });
    }

    private BaseModel convertPackageToBm(String packageName) {
        return LocalCardCreator.getInstance().createCard(packageName);
    }

    @Override
    public void swapCards(int before, int after) {
        mDbSource.swapCards(before, after);
    }

    @Override
    public void closeCard(@NonNull BaseModel bm) {
        mDbSource.closeCard(bm);
    }

    @Override
    public void closeCard(int pos) {
        mDbSource.closeCard(pos);
    }

    @Override
    public void addCard(BaseModel bm) {
        mDbSource.addCard(bm);
    }

    @Override
    public void saveCard(BaseModel bm) {
        mDbSource.saveCard(bm);
    }

    @Override
    public boolean isDbReady() {
        return mDbSource.isDbReady();
    }
}