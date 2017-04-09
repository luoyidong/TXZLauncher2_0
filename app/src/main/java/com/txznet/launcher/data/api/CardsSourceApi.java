package com.txznet.launcher.data.api;

import java.util.List;

import rx.Observable;

/**
 * Created by TXZ-METEORLUO on 2017/3/18.
 */
public interface CardsSourceApi<T> {

    Observable<List<T>> loadCards();

}