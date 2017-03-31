package com.txznet.launcher.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.txznet.launcher.ui.model.UiCard;

/**
 * Created by TXZ-METEORLUO on 2017/3/31.
 */
public class BaseCardView extends FrameLayout {
    private UiCard mUseModel;

    public BaseCardView(Context context) {
        this(context, null);
    }

    public BaseCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bindModel(UiCard model) {
        mUseModel = model;
    }
}