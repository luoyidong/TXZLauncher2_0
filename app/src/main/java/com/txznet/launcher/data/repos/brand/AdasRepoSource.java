package com.txznet.launcher.data.repos.brand;

import com.txznet.launcher.data.repos.BaseDataRepo;

import rx.Observable;

/**
 * Created by UPC on 2017/4/15.
 */

public class AdasRepoSource<T, F extends Integer> extends BaseDataRepo<T, F> {

    @Override
    public Observable<T> getNewData() {
        return null;
    }

    @Override
    public void initialize(OnInitListener listener) {

    }
}
