package com.txznet.launcher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.txznet.launcher.R;
import com.txznet.launcher.ui.widget.swipe.SwipeFrameLayout;

/**
 * Created by TXZ-METEORLUO on 2017/3/31.
 */
@Deprecated
public abstract class BaseSwipeAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context mContext;

    public BaseSwipeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = createViewByType(parent, viewType);
        if (contentView != null) {
            SwipeFrameLayout sfly = (SwipeFrameLayout) View.inflate(mContext, R.layout.swipe_layout, null);
            sfly.setContentView(contentView);
            contentView = sfly;
        }
        return onCompatCreateViewHolder(contentView, viewType);
    }

    public abstract VH onCompatCreateViewHolder(View contentView, int viewType);

    public abstract View createViewByType(ViewGroup parent, int viewType);
}