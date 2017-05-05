package com.txznet.launcher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.ui.model.UiCard;
import com.txznet.launcher.ui.widget.AppCardView;
import com.txznet.launcher.ui.widget.BaseCardView;
import com.txznet.launcher.ui.widget.MusicCardView;
import com.txznet.launcher.ui.widget.SysCardView;
import com.txznet.launcher.ui.widget.ThirdCardView;
import com.txznet.launcher.ui.widget.WeatherCardView;

import java.util.List;

/**
 * Created by TXZ-METEORLUO on 2017/4/12.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {
    private Context mContext;
    private List<UiCard> mCards;

    public CardViewAdapter(Context context, List<UiCard> cards) {
        this.mContext = context;
        this.mCards = cards;
    }

    public void replaceData(List<UiCard> cards) {
        this.mCards = cards;
        this.notifyDataSetChanged();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case BaseModel.TYPE_MUSIC:
                view = new MusicCardView(mContext);
                break;
            case BaseModel.TYPE_WEATHER:
                view = new WeatherCardView(mContext);
                break;
            case BaseModel.TYPE_SYSTEM_APP:
                view = new SysCardView(mContext);
                break;
            case BaseModel.TYPE_MORE_APP:
                view = new AppCardView(mContext);
                break;
            case BaseModel.TYPE_THIRD_APP:
                view = new ThirdCardView(mContext);
                break;
            default:
                view = new ThirdCardView(mContext);
                break;
        }
        view = new WeatherCardView(mContext);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        UiCard card = mCards.get(position);
        holder.attachModel(card);
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
            if (itemView instanceof BaseCardView) {
                ((BaseCardView) itemView).bindModel(model);
            }
        }
    }
}