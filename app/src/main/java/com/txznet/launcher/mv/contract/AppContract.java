package com.txznet.launcher.mv.contract;

/**
 * Created by TXZ-METEORLUO on 2017/5/3.
 */
public interface AppContract {
    interface View extends CardContract.View {

    }

    abstract class Presenter extends CardContract.Presenter<View> {
    }
}
