package com.txznet.launcher.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.txznet.launcher.LauncherApp;

/**
 * Created by TXZ-METEORLUO on 2017/1/18.
 */
public class SlideRecyclerView extends RecyclerView {
    public static final int DEFAULT_SLIDE_FRAME = 50;
    public static final String TAG = SlideRecyclerView.class.getSimpleName();

    private float mDownX, mDownY;
    private boolean mIsCurItemSwipeOpen = false;

    /**
     * 支持侧滑的接口
     */
    public interface SwipeLayout {
        boolean isOpen();

        void smoothOpen();

        void smoothClose();
    }


    public SlideRecyclerView(Context context) {
        super(context);
        initial();
    }

    public SlideRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    public SlideRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initial();
    }

    private float mTouchSlop;

    private void initial() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                View child = findChildViewUnder(mDownX, mDownY);
                int pos = getChildAdapterPosition(child);

                int childCount = getLayoutManager().getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View v = getChildAt(i);
                    int lastPos = getChildAdapterPosition(v);
                    if (v != null && v instanceof SwipeLayout) {
                        if (((SwipeLayout) v).isOpen() && lastPos != pos) {
                            ((SwipeLayout) v).smoothClose();
                            break;
                        }
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean intercept = super.onInterceptTouchEvent(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float disX = mDownX - e.getX();
                float disY = mDownY - e.getY();
                if (!mIsCurItemSwipeOpen && Math.abs(disX) > mTouchSlop && Math.abs(disX) > Math.abs(disY)) { // 横向滑动自己处理
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        return intercept;
    }
}