package com.txznet.launcher.data.repos;

import android.support.annotation.NonNull;

import com.txznet.launcher.data.DataConvertor;
import com.txznet.launcher.data.api.CardsRepoApi;
import com.txznet.launcher.data.api.CardsSourceApi;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.data.source.DbSource;
import com.txznet.launcher.di.Db;
import com.txznet.launcher.di.Pm;
import com.txznet.launcher.di.Ss;
import com.txznet.launcher.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by TXZ-METEORLUO on 2017/3/30.
 * 操作的记录都保存到数据库中
 */
@Singleton
public class CardsRepositeSource implements CardsRepoApi<BaseModel> {
    private CardsRepoApi mDbSource;
    private CardsSourceApi mPmSource;
    private CardsSourceApi mStatusSource;

    @Inject
    public CardsRepositeSource(@Pm @NonNull CardsSourceApi pm, @Db @NonNull DbSource db, @Ss @NonNull CardsSourceApi ss) {
        this.mPmSource = pm;
        this.mDbSource = (CardsRepoApi) db;
        this.mStatusSource = ss;
    }

    @Override
    public Observable<List<BaseModel>> loadCards() {
        boolean isFirst = SpUtil.getInstance().isFirstLoadCards();
        if (!isFirst) {
            return mDbSource.loadCards();
        }
        SpUtil.getInstance().setFirstLoadFlag(false);

        Observable<List<String>> datas = mPmSource.loadCards();
        Observable<List<BaseModel>> pDatas = datas.map(new Func1<List<String>, List<BaseModel>>() {

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
        });
        Observable<List<BaseModel>> sDatas = mStatusSource.loadCards();

        return Observable.zip(pDatas, sDatas, new Func2<List<BaseModel>, List<BaseModel>, List<BaseModel>>() {
            @Override
            public List<BaseModel> call(List<BaseModel> baseModels, List<BaseModel> baseModels2) {
                List<BaseModel> models = new ArrayList<>();
                models.addAll(baseModels);
                models.addAll(baseModels2);
                return models;
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
        return DataConvertor.getInstance().createCard(packageName);
    }

    @Override
    public boolean swapCards(int before, int after) {
        return mDbSource.swapCards(before, after);
    }

    @Override
    public boolean closeCard(@NonNull BaseModel bm) {
        return mDbSource.closeCard(bm);
    }

    @Override
    public boolean closeCard(int pos) {
        return mDbSource.closeCard(pos);
    }

    @Override
    public boolean addCard(BaseModel bm) {
        return mDbSource.addCard(bm);
    }

    @Override
    public boolean saveCard(BaseModel bm) {
        return mDbSource.saveCard(bm);
    }

    @Override
    public boolean isDbReady() {
        return mDbSource.isDbReady();
    }
}