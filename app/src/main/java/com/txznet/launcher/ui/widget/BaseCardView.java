package com.txznet.launcher.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.txznet.launcher.R;
import com.txznet.launcher.mv.CardViewContract;
import com.txznet.launcher.ui.model.UiCard;
import com.txznet.launcher.ui.widget.swipe.SwipeFrameLayout;

/**
 * Created by TXZ-METEORLUO on 2017/3/31.
 */
public class BaseCardView extends SwipeFrameLayout implements CardViewContract.View {
    private TextView mNameTv;
    private TextView mDescTv;
    private ImageView mIconIv;

    private CardView mCardView;
    private View mCardLy;
    private View mIconLy;

    private UiCard mUseModel;

    public BaseCardView(Context context) {
        this(context, null);
    }

    public BaseCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        int layoutId = getLayoutId();
        View view = View.inflate(getContext(), layoutId, null);
        findViewById(view);
        initWidget();
        setContentView(view);
    }

    // 获取所属的布局文件Id
    public int getLayoutId() {
        return R.layout.card_default_ly;
    }

    public void findViewById(View view) {
        mNameTv = (TextView) view.findViewById(R.id.card_name_tv);
        mDescTv = (TextView) view.findViewById(R.id.card_des_tv);
        mIconIv = (ImageView) view.findViewById(R.id.icon_iv);
        mCardView = (CardView) view.findViewById(R.id.card_view);
        mCardLy = view.findViewById(R.id.card_ly);
        mIconLy = view.findViewById(R.id.top_icon_bg);
    }

    public void initWidget() {
        if (mCardView != null) {
            mCardView.setCardBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void bindModel(UiCard model) {
        mUseModel = model;
        refreshUi();
    }

    public void refreshUi() {
        if (mUseModel == null) {
            return;
        }

        setName(mUseModel.name);
        setDesc(mUseModel.desc);
        setAppIcon(mUseModel.iconDrawable);
        setBgColor(mUseModel.backgroundColor);
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            name = "";
        }
        mNameTv.setText(name);
    }

    @Override
    public void setDesc(String desc) {
        if (desc == null) {
            desc = "";
        }
        mDescTv.setText(desc);
    }

    @Override
    public void setAppIcon(Drawable icon) {
        mIconIv.setImageDrawable(icon);
    }

    @Override
    public void setBgColor(int color) {
        if (color != 0) {
            mCardLy.setBackgroundColor(color);
        }
    }
}