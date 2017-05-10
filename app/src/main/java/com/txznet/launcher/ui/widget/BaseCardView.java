package com.txznet.launcher.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.txznet.launcher.R;
import com.txznet.launcher.mv.contract.CardContract;
import com.txznet.launcher.ui.MainActivity;
import com.txznet.launcher.ui.model.UiCard;
import com.txznet.launcher.ui.widget.swipe.SwipeFrameLayout;

import javax.inject.Inject;

/**
 * Created by TXZ-METEORLUO on 2017/3/31.
 */
public abstract class BaseCardView<T extends CardContract.Presenter> extends SwipeFrameLayout implements CardContract.View {
    protected TextView mNameTv;
    protected TextView mDescTv;
    protected ImageView mIconIv;

    protected CardView mCardView;
    protected View mCardLy;
    protected View mIconLy;

    protected UiCard mUseModel;

    @Inject
    protected T mPresenter;

    public BaseCardView(Context context) {
        this(context, null);
    }

    public BaseCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inject((MainActivity) context);
        initView();
    }

    public abstract void inject(MainActivity ma);

    public void initView() {
        int layoutId = getLayoutId();
        View view = View.inflate(getContext(), layoutId, null);
        setContentView(view);
        findViewById(view);
        initWidget();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        smoothClose();
    }

    @Override
    public UiCard getCardModel() {
        return mUseModel;
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

        if (mCardLy != null) {
            mCardLy.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBlank();
                }
            });
        }
    }

    public void onClickBlank() {
        if (mPresenter != null) {
            mPresenter.onClickBlank();
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
        if (mNameTv != null) {
            mNameTv.setText(name);
        }
    }

    @Override
    public void setDesc(String desc) {
        if (desc == null) {
            desc = "";
        }
        if (mDescTv != null) {
            mDescTv.setText(desc);
        }
    }

    @Override
    public void setAppIcon(Drawable icon) {
        if (mIconIv != null) {
            mIconIv.setImageDrawable(icon);
        }
    }

    /**
     * 将背景色设置为图片主色调
     *
     * @param icon
     */
    public void paletteIconColor(Drawable icon) {
        // 提取主色调
        if (icon != null) {
            Palette.from(drawableToBitamp(icon)).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    Palette.Swatch swatch = palette.getVibrantSwatch();
                    if (swatch != null) {
                        mUseModel.backgroundColor = swatch.getRgb();
                        setBgColor(swatch.getRgb());
                    }
                }
            });
        }
    }

    /**
     * Drawable转为Bitmap
     *
     * @param drawable
     * @return
     */
    protected Bitmap drawableToBitamp(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 :
                Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void setBgColor(int color) {
        if (color != 0 && mCardLy != null) {
            mCardLy.setBackgroundColor(color);
        }
    }
}