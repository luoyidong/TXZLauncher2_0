package com.txznet.launcher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.txznet.launcher.ui.model.UiCard;
import com.txznet.launcher.ui.widget.BaseCardView;
import com.txznet.launcher.ui.widget.swipe.SwipeFrameLayout;

import java.util.List;

/**
 * Created by TXZ-METEORLUO on 2017/3/31.
 */
public abstract class CardAdapter extends BaseSwipeAdapter<CardAdapter.CardViewHolder> {
    private Context mContext;
    private List<UiCard> mCards;

    public CardAdapter(Context context, List<UiCard> cards) {
        super(context);
        this.mContext = context;
        this.mCards = cards;
    }

    public void replaceData(List<UiCard> cards) {
        this.mCards = cards;
        this.notifyDataSetChanged();
    }

    @Override
    public CardViewHolder onCompatCreateViewHolder(View contentView, int viewType) {
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(200, RecyclerView.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(lp);
        return new CardViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Log.d("onBindViewHolder","onBindViewHolder");
        UiCard model = mCards.get(position);
        holder.attachModel(model);
    }

    @Override
    public int getItemViewType(int position) {
        if (mCards != null) {
            UiCard bm = mCards.get(position);
            return bm.type;
        }
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mCards != null ? mCards.size() : 0;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        public CardViewHolder(View itemView) {
            super(itemView);
        }

        public void attachModel(UiCard model) {
            if (itemView instanceof SwipeFrameLayout) {
                SwipeFrameLayout sfly = (SwipeFrameLayout) itemView;
                View view = sfly.getContentView();
                if (view instanceof BaseCardView) {
                    ((BaseCardView) view).bindModel(model);
                }
            }
        }
    }
}