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
    private int mLastSwipeOpenPosition = -1;
    private SwipeLayout mLastSwipeItem;

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

        setOnDragListener(new View.OnDragListener() {
            private float mLastX;

            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_EXITED:
                        if (mLastX < 1) { // 向左移动
                            slideLeft(event);
                        } else { // 向右移动
                            slideRight(event);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        return false;
                    case DragEvent.ACTION_DROP:
                        return false;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        removeCallback();
                        break;
                }
                mLastX = event.getX();
                return true;
            }
        });
    }

    private void slideLeft(DragEvent event) {
        mSlideRunnable.update(true);
        removeCallback();
        postDelayed(mSlideRunnable, 0);
    }

    private void slideRight(DragEvent event) {
        mSlideRunnable.update(false);
        removeCallback();
        postDelayed(mSlideRunnable, 0);
    }

    LauncherApp.Runnable1<Boolean> mSlideRunnable = new LauncherApp.Runnable1<Boolean>(null) {
        @Override
        public void run() {
            if (mP1 == null) {
                removeCallback();
                return;
            }
            LinearLayoutManager mLyManager = (LinearLayoutManager) getLayoutManager();
            if (mP1) {
                if (mLyManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    removeCallback();
                    return;
                }
            } else {
                if (mLyManager.findLastCompletelyVisibleItemPosition() == getAdapter().getItemCount() - 1) {
                    removeCallback();
                    return;
                }
            }

            int ratio = mP1 ? -1 : 1;
            smoothScrollBy(ratio * DEFAULT_SLIDE_FRAME, 0);
            removeCallback();
            postDelayed(this, 200);
        }
    };

    public void removeCallback() {
        removeCallbacks(mSlideRunnable);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                int touchingPosition = getChildAdapterPosition(findChildViewUnder(ev.getX(), ev.getY())); // 当前触摸的item
                ViewHolder vh = findViewHolderForAdapterPosition(touchingPosition);
                if (vh == null || vh.itemView == null) {
                    break;
                }

                if (vh.itemView instanceof SwipeLayout) {
                    if (mLastSwipeItem != null && mLastSwipeItem.isOpen() && touchingPosition != mLastSwipeOpenPosition) {
                        mLastSwipeItem.smoothClose();
                        mLastSwipeOpenPosition = -1;
                    }

                    mIsCurItemSwipeOpen = ((SwipeLayout) vh.itemView).isOpen(); // 检测当前按下的item是否已经展开
                    if (mIsCurItemSwipeOpen) {
                        mLastSwipeItem = (SwipeLayout) vh.itemView;
                        mLastSwipeOpenPosition = touchingPosition; // 记录当前展开item的索引
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