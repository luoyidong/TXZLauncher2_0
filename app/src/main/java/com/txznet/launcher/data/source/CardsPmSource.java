package com.txznet.launcher.data.source;

import android.content.Context;
import android.content.pm.PackageManager;

import com.txznet.launcher.data.api.CardsSourceApi;
import com.txznet.launcher.data.model.BaseModel;

import java.util.List;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public class CardsPmSource implements CardsSourceApi {
    private Context mContext;

    public CardsPmSource(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<BaseModel>> loadCards() {
        return null;
    }

    private void getAppsFromPhone() {
        PackageManager pm = mContext.getPackageManager();
    }
}