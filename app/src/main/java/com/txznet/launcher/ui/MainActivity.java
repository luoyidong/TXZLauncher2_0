package com.txznet.launcher.ui;

import android.os.Bundle;
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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

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

        DaggerLauncherComponent.builder()
                .launcherRepositeComponent(((LauncherApp) getApplication()).getLauncherRespositeComponent())
                .build()
                .inject(this);
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
                    return null;
                }
            };

            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.replaceData(cards);
        }
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