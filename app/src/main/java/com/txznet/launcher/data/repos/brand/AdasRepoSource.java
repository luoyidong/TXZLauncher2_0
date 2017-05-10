package com.txznet.launcher.data.repos.brand;

import com.txznet.launcher.data.data.AdasData;
import com.txznet.launcher.data.repos.BaseDataRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by UPC on 2017/4/15.
 */
@Singleton
public class AdasRepoSource extends BaseDataRepo<AdasData, Integer, AdasRepoSource.OnAdasListener> {

    @Inject
    public AdasRepoSource() {
        initialize(new OnInitListener() {
            @Override
            public void onInit(boolean bSucc) {
            }
        });
    }

    @Override
    protected Observable<AdasData> getNewData() {
        return Observable.create(new Observable.OnSubscribe<AdasData>() {
            @Override
            public void call(Subscriber<? super AdasData> subscriber) {
            }
        });
    }

    @Override
    public void initialize(OnInitListener listener) {
        if (listener != null) {
            listener.onInit(true);
        }
    }

    @Override
    public void register(OnAdasListener listener) {

    }

    @Override
    public void unRegister() {

    }

    public interface OnAdasListener {
        public void onAdasUpdate(AdasData ad);
    }
}