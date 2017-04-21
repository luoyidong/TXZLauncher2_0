package com.txznet.launcher.data.source;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.txznet.launcher.data.api.CardsSourceApi;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public class PmSource implements CardsSourceApi<String> {
    private Context mContext;

    public PmSource(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<String>> loadCards() {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {

            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onStart();
                List<String> pks = new ArrayList<>();
                PackageManager pm = mContext.getPackageManager();
                List<PackageInfo> pis = pm.getInstalledPackages(0);
                for (PackageInfo info : pis) {
                    pks.add(info.packageName);
                }

                subscriber.onNext(pks);
                subscriber.onCompleted();
            }
        });
    }
}