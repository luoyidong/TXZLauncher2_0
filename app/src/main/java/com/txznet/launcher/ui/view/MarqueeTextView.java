package com.txznet.launcher.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by TXZ-METEORLUO on 2017/2/25.
 */
public class MarqueeTextView extends TextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    boolean mFocus;

    public void setFocus(boolean focus) {
        this.mFocus = focus;
        invalidate();
    }

    @Override
    public boolean isFocused() {
        return mFocus;
    }
}