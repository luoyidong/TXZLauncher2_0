package com.txznet.launcher.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.txznet.launcher.R;
import com.txznet.launcher.mv.AppPresenter;
import com.txznet.launcher.ui.MainActivity;

/**
 * Created by TXZ-METEORLUO on 2017/5/3.
 */
public class AppCardView extends ExpandCardView<AppPresenter> {

    public AppCardView(Context context) {
        super(context);
    }

    public AppCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void inject(MainActivity ma) {
        ma.getComponent().inject(this);
    }

    @Override
    public View getExpandableView() {
        return findViewById(R.id.all_app_rv);
    }

    @Override
    public int getLayoutId() {
        return R.layout.card_apps_ly;
    }
}