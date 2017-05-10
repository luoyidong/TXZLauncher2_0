package com.txznet.launcher.ui;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.txznet.launcher.LauncherApp;
import com.txznet.launcher.R;
import com.txznet.launcher.adapter.CardViewAdapter;
import com.txznet.launcher.di.component.DaggerLauncherComponent;
import com.txznet.launcher.di.component.LauncherComponent;
import com.txznet.launcher.mv.LauncherPresenter;
import com.txznet.launcher.mv.contract.LauncherContract;
import com.txznet.launcher.ui.model.UiCard;
import com.txznet.launcher.ui.view.SlideRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TXZ-METEORLUO on 2017/3/29.
 */
public class MainActivity extends BaseLoadingActivity implements LauncherContract.View {
    @Inject
    LauncherPresenter mPresenter;
    @BindView(R.id.card_recyclerView)
    SlideRecyclerView mRecyclerView;

    private CardViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏应用程序的标题栏，即当前activity的label
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏android系统的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.launcher_main);
        ButterKnife.bind(this);

        getComponent().inject(this);

        LinearLayoutManager mLyManager = new LinearLayoutManager(MainActivity.this, OrientationHelper.HORIZONTAL, false);
        mLyManager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLyManager);

        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);

        initViews();
    }

    private void initViews() {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                return makeMovementFlags(dragFlags,
                        swipeFlags);
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int before = viewHolder.getAdapterPosition();
                int after = target.getAdapterPosition();
//                if (!PropertUtil.FIX_BRAND_VIEW) {
//                    if (before == 0 || after == 0) {
//                        return false;
//                    }
//                }

                int childCount = mAdapter.getItemCount();
                if (before == childCount - 1 || after == childCount - 1) {
                    return false;
                }

                mPresenter.swapCards(before, after);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState != ItemTouchHelper.ACTION_STATE_DRAG) {
                    return;
                }
                int pos = viewHolder.getAdapterPosition();
                int childCount = mAdapter.getItemCount();
                if (pos != childCount - 1) {
                    Boolean hasScale = (Boolean) viewHolder.itemView.getTag(R.id.flag_hasScale);
                    if (isCurrentlyActive) {
                        if (hasScale != null && hasScale) {
                            return;
                        }
                        viewHolder.itemView.setTag(R.id.flag_hasScale, true);
                        viewHolder.itemView.setScaleX(0.95f);
                        viewHolder.itemView.setScaleY(0.95f);
                        viewHolder.itemView.setAlpha(0.9f);
                    } else {
                        viewHolder.itemView.setScaleX(1.0f);
                        viewHolder.itemView.setScaleY(1.0f);
                        viewHolder.itemView.setAlpha(1f);
                        viewHolder.itemView.setTag(R.id.flag_hasScale, false);
                    }
                }

                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onStart() {
        mPresenter.attachView(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.detachView();
        super.onStop();
    }

    @Override
    public String getLoadingMessage() {
        return null;
    }

    @Override
    public void showCards(List<UiCard> cards) {
        if (mAdapter == null) {
            mAdapter = new CardViewAdapter(this, cards);
            mAdapter.setOnDeleteListener(new CardViewAdapter.OnCardDeleteListener() {
                @Override
                public void onDelete(RecyclerView.ViewHolder v) {
                    int pos = v.getAdapterPosition();
                    if (v == null || pos == -1) {
                        return;
                    }

                    mPresenter.removeCard(pos);
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.replaceData(cards);
        }
    }

    @Override
    public void notifyRemove(int pos) {
        mAdapter.notifyItemRemove(pos);
    }

    @Override
    public void notifyAdd(UiCard card, int pos) {
        mAdapter.notifyItemInsert(card, pos);
    }

    @Override
    public void swapList(int before, int after) {
        mAdapter.notifyItemSwap(before, after);
    }

    @Override
    public void onBackPressed() {
    }

    public LauncherComponent getComponent() {
        return DaggerLauncherComponent.builder()
                .launcherRepositeComponent(((LauncherApp) getApplication()).getLauncherRespositeComponent())
                .build();
    }
}