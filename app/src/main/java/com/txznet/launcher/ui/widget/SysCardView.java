package com.txznet.launcher.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.txznet.launcher.mv.SimplePresenter;
import com.txznet.launcher.ui.MainActivity;

/**
 * Created by UPC on 2017/4/16.
 */

public class SysCardView extends BaseCardView<SimplePresenter>{
    public SysCardView(Context context) {
        super(context);
    }

    public SysCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SysCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void inject(MainActivity ma) {
        ma.getComponent().inject(this);
    }
}