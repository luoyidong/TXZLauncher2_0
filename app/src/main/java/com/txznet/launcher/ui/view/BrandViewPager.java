package com.txznet.launcher.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by TXZ-METEORLUO on 2017/2/8.
 */
public class BrandViewPager extends ViewPager {
    public interface OnEditChangeListener {
        void onEditChange(View child, boolean isEditMode);
    }

    private int mTouchSlop;

    private int mDownX;
    private boolean mIsEditMode;
    private boolean mHasMove;
    private boolean mResetUp;

    private GestureDetector mGd;
    private OnEditChangeListener mEditListener;

    public BrandViewPager(Context context) {
        this(context, null);
    }

    public BrandViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getX();
                if (mIsEditMode) {
                    mResetUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mResetUp = false;
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    public boolean isEditMode() {
        return mIsEditMode;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mGd == null) {
            mGd = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                    handleLongPress();
                }
            });
        }
        mGd.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int curX = (int) ev.getX();
                if (Math.abs(curX - mDownX) > mTouchSlop) {
                    mHasMove = true;
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mResetUp && !mHasMove) {
                    // 退出编辑
                    exitEditMode();
                }
                mHasMove = false;
                mResetUp = false;
                break;
        }
        if (!isEditMode() && mHasMove) {
            getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        }

        return super.onTouchEvent(ev);
    }

    private void handleLongPress() {
        if (!mIsEditMode) {
            mIsEditMode = true;
            if (mEditListener != null) {
                mEditListener.onEditChange(this, true);
            }
        }
    }

    private void exitEditMode() {
        if (mIsEditMode) {
            mIsEditMode = false;
            if (mEditListener != null) {
                mEditListener.onEditChange(this, false);
            }
        }
    }

    public void setOnEditChangeListener(OnEditChangeListener listener) {
        mEditListener = listener;
    }
}