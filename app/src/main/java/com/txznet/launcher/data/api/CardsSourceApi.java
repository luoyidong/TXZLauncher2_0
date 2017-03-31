package com.txznet.launcher.data.api;

import android.support.annotation.NonNull;

import com.txznet.launcher.data.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public interface CardsSourceApi {

    Observable<List<BaseModel>> loadCards();

}