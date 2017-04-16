package com.txznet.launcher.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import com.txznet.launcher.mv.SimplePresenter;

/**
 * Created by UPC on 2017/4/16.
 */

public class ThirdCardView extends BaseCardView<SimplePresenter> {

    public ThirdCardView(Context context) {
        super(context);
    }

    public ThirdCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThirdCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initWidget() {
        super.initWidget();

        clearIconLyDrawable();
    }

    private void clearIconLyDrawable(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mIconLy.setBackground(null);
        } else {
            mIconLy.setBackgroundDrawable(null);
        }

        mIconLy.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void setAppIcon(Drawable icon) {
        super.setAppIcon(icon);
        paletteIconColor(icon);
    }
}