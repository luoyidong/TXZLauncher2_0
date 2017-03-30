package com.txznet.launcher.ui.widget;

import android.app.AlertDialog;
import android.content.Context;

import dmax.dialog.SpotsDialog;

public class LoadingView {

    private AlertDialog mLoadingDialog;

    public LoadingView(Context context, String message) {
        mLoadingDialog = new SpotsDialog(context, message);
    }

    public void show() {
        mLoadingDialog.show();
    }

    public void dismiss() {
        mLoadingDialog.dismiss();
    }
}