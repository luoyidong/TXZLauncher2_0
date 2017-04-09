package com.txznet.launcher.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.view.ViewGroup;

import com.txznet.launcher.LauncherApp;
import com.txznet.launcher.R;
import com.txznet.launcher.adapter.CardAdapter;
import com.txznet.launcher.di.component.DaggerLauncherComponent;
import com.txznet.launcher.mv.LauncherContract;
import com.txznet.launcher.mv.LauncherPresenter;
import com.txznet.launcher.ui.model.UiCard;
import com.txznet.launcher.ui.view.SlideRecyclerView;
import com.txznet.launcher.ui.widget.BaseCardView;

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

    private CardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_main);
        ButterKnife.bind(this);

        DaggerLauncherComponent.builder()
                .launcherRepositeComponent(((LauncherApp) getApplication()).getLauncherRespositeComponent())
                .build()
                .inject(this);

        LinearLayoutManager mLyManager = new LinearLayoutManager(MainActivity.this, OrientationHelper.HORIZONTAL, false);
        mLyManager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLyManager);
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
            mAdapter = new CardAdapter(this, cards) {
                @Override
                public View createViewByType(ViewGroup parent, int viewType) {
                    return createView(parent, viewType);
                }
            };

            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.replaceData(cards);
        }
    }

    private View createView(ViewGroup parent, int viewType) {
        // TODO
        return new BaseCardView(MainActivity.this);
    }

    @Override
    public void notifyRemove(int pos) {
        mAdapter.notifyItemRemoved(pos);
    }

    @Override
    public void notifyAdd(int pos) {
        mAdapter.notifyItemInserted(pos);
    }
}