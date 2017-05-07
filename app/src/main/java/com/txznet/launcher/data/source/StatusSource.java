package com.txznet.launcher.data.source;

import com.txznet.launcher.data.DataConvertor;
import com.txznet.launcher.data.api.CardsSourceApi;
import com.txznet.launcher.data.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by UPC on 2017/5/7.
 */

public class StatusSource implements CardsSourceApi<BaseModel> {

    @Override
    public Observable<List<BaseModel>> loadCards() {
        return Observable.create(new Observable.OnSubscribe<List<BaseModel>>() {
            @Override
            public void call(Subscriber<? super List<BaseModel>> subscriber) {
                // 天气、更多静态卡片，不需要依赖包名
                List<BaseModel> statusModel = new ArrayList<>();
                statusModel.add((BaseModel) DataConvertor.getInstance().createFromType(BaseModel.TYPE_WEATHER, true));
                statusModel.add((BaseModel) DataConvertor.getInstance().createFromType(BaseModel.TYPE_MORE_APP, true));
                subscriber.onNext(statusModel);
                subscriber.onCompleted();
            }
        });
    }
}