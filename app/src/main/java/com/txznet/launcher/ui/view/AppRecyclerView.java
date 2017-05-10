package com.txznet.launcher.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TXZ-METEORLUO on 2017/1/25.
 * 到底左右端的时候给出回调
 */
public class AppRecyclerView extends RecyclerView {
    private static final String TAG = AppRecyclerView.class.getSimpleName();
    public static final int MODE_NORMAL = 0;
    public static final int MODE_LEFT = 1;
    public static final int MODE_RIGHT = 2;

    private int mCurrMoveMode;
    private View mTouchChild;
    private ViewHolder mTouchViewHolder;

    public interface OnLeftRightListener {
        void onItemOffset(View child, boolean isLeft, int offset);

        void onItemMove(View child, int leftOffset);

        void onChildLeft(ViewHolder holder);

        void onChildRight(ViewHolder holder);
    }

    public interface OnMoveSwapListener {
        boolean onMove(int before, int after);
    }

    public interface OnSpaceClickListener {
        void onClick();
    }

    public AppRecyclerView(Context context) {
        this(context, null);
    }

    public AppRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initItemTouchHelper();
    }

    private void initItemTouchHelper() {
        ItemTouchHelper appTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int dragFlags = 0;
                int swipeFlags = 0;
                return makeMovementFlags(dragFlags,
                        swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int before = viewHolder.getAdapterPosition();
                int after = target.getAdapterPosition();

//                if (mOnMoveSwapListener != null) {
//                    boolean swap = mOnMoveSwapListener.onMove(before, after);
//                    if (swap) {
//                        getAdapter().notifyItemMoved(before, after);
//                    }
//                }
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                if (actionState != ItemTouchHelper.ACTION_STATE_DRAG) {
//                    return;
//                }
//                Boolean hasScale = (Boolean) viewHolder.itemView.getTag(R.id.flag_hasScale);
//                if (isCurrentlyActive) {
//                    if (hasScale != null && hasScale) {
//                        return;
//                    }
//                    viewHolder.itemView.setTag(R.id.flag_hasScale, true);
//                    viewHolder.itemView.setScaleX(0.95f);
//                    viewHolder.itemView.setScaleY(0.95f);
//                    viewHolder.itemView.setAlpha(0.9f);
//                } else {
//                    viewHolder.itemView.setScaleX(1.0f);
//                    viewHolder.itemView.setScaleY(1.0f);
//                    viewHolder.itemView.setAlpha(1f);
//                    viewHolder.itemView.setTag(R.id.flag_hasScale, false);
//                }

                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        appTouchHelper.attachToRecyclerView(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchChild = this.findChildViewUnder(e.getX(), e.getY());
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
//                handleTouchMove();
                break;
            case MotionEvent.ACTION_UP:
                if (mTouchChild == null) {
                    if (mOnSpaceClickListener != null) {
                        mOnSpaceClickListener.onClick();
                    }
                    break;
                }
//                if (mTouchChild != null) {
//                    mTouchViewHolder = findContainingViewHolder(mTouchChild);
//                }
//                switch (mCurrMoveMode) {
//                    case MODE_LEFT:
//                        if (mLeftRightListener != null) {
//                            mLeftRightListener.onChildLeft(mTouchViewHolder);
//                        }
//                        break;
//                    case MODE_RIGHT:
//                        if (mLeftRightListener != null) {
//                            mLeftRightListener.onChildRight(mTouchViewHolder);
//                        }
//                        break;
//                    case MODE_NORMAL:
//                        break;
//                }
            case MotionEvent.ACTION_CANCEL:
                mTouchChild = null;
                break;
        }
        return super.onTouchEvent(e);
    }

//    private void handleTouchMove() {
//        if (mTouchChild != null) {
//            Rect hintRect = new Rect();
//            mTouchChild.getHitRect(hintRect);
//            if (hintRect.left < 0) {
//                // 移动到最左端
//                if (mCurrMoveMode != MODE_LEFT) {
//                    mCurrMoveMode = MODE_LEFT;
//                }
//                if (mLeftRightListener != null) {
//                    mLeftRightListener.onItemOffset(mTouchChild, true, Math.abs(hintRect.left));
//                }
//            } else if (hintRect.right > getWidth()) {
//                // 移动到最右端
//                if (mCurrMoveMode != MODE_RIGHT) {
//                    mCurrMoveMode = MODE_RIGHT;
//                }
//                if (mLeftRightListener != null) {
//                    mLeftRightListener.onItemOffset(mTouchChild, false, hintRect.right - getWidth());
//                }
//            } else {
//                if (mCurrMoveMode != MODE_NORMAL) {
//                    mCurrMoveMode = MODE_NORMAL;
//                }
//                if (mLeftRightListener != null) {
//                    mLeftRightListener.onItemMove(mTouchChild, hintRect.left);
//                }
//            }
//        }
//    }

    private OnLeftRightListener mLeftRightListener;

    public void setLeftRightListener(OnLeftRightListener listener) {
        mLeftRightListener = listener;
    }

    private OnMoveSwapListener mOnMoveSwapListener;

    public void setMoveSwapListener(OnMoveSwapListener listener) {
        mOnMoveSwapListener = listener;
    }

    private OnSpaceClickListener mOnSpaceClickListener;

    public void setSpaceClickListener(OnSpaceClickListener listener) {
        mOnSpaceClickListener = listener;
    }
}