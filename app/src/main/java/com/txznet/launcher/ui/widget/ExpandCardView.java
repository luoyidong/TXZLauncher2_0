package com.txznet.launcher.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.txznet.launcher.mv.contract.CardContract;

/**
 * Created by UPC on 2017/4/16.
 */

public abstract class ExpandCardView<T extends CardContract.Presenter> extends BaseCardView<T> {

    private boolean mIsExpend;

    public ExpandCardView(Context context) {
        super(context);
    }

    public ExpandCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract View getExpandableView();

    /**
     * 展开卡片
     */
    public void expandView() {
        if (getExpandableView() != null) {
            mIsExpend = true;
            getExpandableView().setVisibility(VISIBLE);
        }
    }

    /**
     * 收缩卡片
     */
    public void retractView() {
        if (getExpandableView() != null) {
            mIsExpend = false;
            getExpandableView().setVisibility(GONE);
        }
    }

    public boolean isExpand() {
        return mIsExpend;
    }
}