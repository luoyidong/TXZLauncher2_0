package com.txznet.launcher.mv;

import com.txznet.launcher.mv.contract.CardContract;

import javax.inject.Inject;

/**
 * Created by UPC on 2017/4/16.
 */

public class SimplePresenter extends CardContract.Presenter<CardContract.View> {

    @Inject
    public SimplePresenter() {
    }
}
