package com.txznet.launcher.ui.widget.swipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.txznet.launcher.ui.view.SlideRecyclerView;

/**
 * Created by TXZ-METEORLUO on 2017/3/31.
 */
public class SwipeFrameLayout extends FrameLayout implements SlideRecyclerView.SwipeLayout {
    private float mLastX, mLastY;
    private float mDownX, mDownY;
    private float mScrollOffset;
    private int mScaledTouchSlop;
    private int mSwipeMaxHeight;

    private View mContentView;

    public SwipeFrameLayout(Context context) {
        this(context, null);
    }

    public SwipeFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
        removeAllViews();
        addView(contentView);
    }

    public View getContentView() {
        return mContentView;
    }

    @Override
    public boolean isOpen() {
        return mScrollOffset > 0;
    }

    @Override
    public void smoothOpen() {
        mScrollOffset = mSwipeMaxHeight;
        mContentView.scrollTo(0, (int) mScrollOffset);
    }

    @Override
    public void smoothClose() {
        mScrollOffset = 0;
        mContentView.scrollTo(0, (int) mScrollOffset);
    }

    public boolean isEnableMoveUp() {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean intercept = super.onInterceptTouchEvent(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = e.getX();
                mDownY = e.getY();
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isEnableMoveUp()) {
                    break;
                }
                float disX = mDownX - e.getX();
                float disY = mDownY - e.getY();
                if (Math.abs(disY) > mScaledTouchSlop && Math.abs(disY) > Math.abs(disX)) { // 纵向滑动自己处理
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                intercept = false;
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = e.getX();
                mLastY = e.getY();
            case MotionEvent.ACTION_MOVE:
                if (!isEnableMoveUp()) {
                    break;
                }
                if (mScrollOffset >= 0) { // 向上
                    mScrollOffset += mLastY - e.getY();
                    if (mScrollOffset <= 0) {
                        mScrollOffset = 0;
                    }
                    final float maxHeight = mSwipeMaxHeight;

                    if (mScrollOffset >= maxHeight) {
                        mScrollOffset = maxHeight;
                    }
                    mContentView.scrollTo(0, (int) mScrollOffset);
                    mLastY = e.getY();
                }
                return true;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(e);
    }
}