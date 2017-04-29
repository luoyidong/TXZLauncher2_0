package com.txznet.launcher.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ViewAware;
import com.txznet.launcher.R;
import com.txznet.launcher.data.data.MusicData;
import com.txznet.launcher.mv.contract.MusicContract;
import com.txznet.launcher.mv.MusicPresenter;
import com.txznet.launcher.ui.view.MarqueeTextView;

/**
 * Created by UPC on 2017/4/15.
 */

public class MusicCardView extends BaseCardView<MusicPresenter> implements MusicContract.View {
    private static final int STATE_PLAY = 1;
    private static final int STATE_PAUSE = 2;

    private int mPPState = STATE_PLAY;

    private View mLoadingIv;
    private ImageView mRingIv;
    private Button mPlayBtn;
    private Button mNextBtn;

    private RotateAnimation rotateAnim = null;

    public MusicCardView(Context context) {
        super(context);
    }

    public MusicCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.card_music_ly;
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);

        mLoadingIv = view.findViewById(R.id.loading_iv);
        mRingIv = (ImageView) view.findViewById(R.id.ring_iv);
        mPlayBtn = (Button) view.findViewById(R.id.play_pause_btn);
        mNextBtn = (Button) view.findViewById(R.id.next_btn);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mPlayBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mPPState) {
                    case STATE_PAUSE:
                        mPresenter.pause();
                        break;
                    case STATE_PLAY:
                        mPresenter.play();
                        break;
                }
            }
        });
        mNextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.playNext();
            }
        });
        mPlayBtn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mPlayBtn.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Rect touchRect = new Rect();
                touchRect.left = mPlayBtn.getLeft() - mPlayBtn.getWidth();
                touchRect.top = mPlayBtn.getTop() - mPlayBtn.getHeight();
                touchRect.right = mPlayBtn.getRight() + mPlayBtn.getWidth() * 1 / 3;
                touchRect.bottom = mPlayBtn.getBottom() + mPlayBtn.getHeight();
                findViewById(R.id.btn_ly).setTouchDelegate(new TouchDelegate(touchRect, mPlayBtn));
            }
        });
        mNextBtn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mNextBtn.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Rect touchRect = new Rect();
                touchRect.left = mNextBtn.getLeft() - mNextBtn.getWidth() * 1 / 3;
                touchRect.top = mNextBtn.getTop() - mNextBtn.getHeight();
                touchRect.right = mNextBtn.getRight() + mNextBtn.getWidth();
                touchRect.bottom = mNextBtn.getBottom() + mNextBtn.getHeight();
                findViewById(R.id.btn_ly).setTouchDelegate(new TouchDelegate(touchRect, mNextBtn));
            }
        });
        mLoadingIv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mLoadingIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Rect touchRect = new Rect();
                touchRect.left = mLoadingIv.getLeft() - mLoadingIv.getWidth();
                touchRect.top = mLoadingIv.getTop() - mLoadingIv.getHeight();
                touchRect.right = mLoadingIv.getRight() + mLoadingIv.getWidth() * 1 / 3;
                touchRect.bottom = mLoadingIv.getBottom() + mLoadingIv.getHeight();
                findViewById(R.id.btn_ly).setTouchDelegate(new TouchDelegate(touchRect, mLoadingIv));
            }
        });
    }

    @Override
    public void setMediaState(int state) {
        switch (state) {
            case MusicData.STATE_EXIT:
                break;
            case MusicData.STATE_LOADING:
                setLoadingView();
                break;
            case MusicData.STATE_PAUSE:
                mPPState = STATE_PLAY;
                mPlayBtn.setBackgroundResource(R.drawable.card_play_btn);
                setPlayView();
                stopMarquee();
                break;
            case MusicData.STATE_PLAY:
                mPPState = STATE_PAUSE;
                mPlayBtn.setBackgroundResource(R.drawable.card_pause_btn);
                setPlayView();
                startMarquee();
                break;
            case MusicData.STATE_STOP:
                break;
        }
    }

    private void setPlayView() {
        mPlayBtn.setVisibility(VISIBLE);
        mLoadingIv.setVisibility(GONE);
        mLoadingIv.clearAnimation();
    }

    private void setLoadingView() {
        mPlayBtn.setVisibility(GONE);
        mLoadingIv.setVisibility(VISIBLE);
        if (rotateAnim == null) {
            rotateAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            rotateAnim.setRepeatCount(Animation.INFINITE);
            rotateAnim.setRepeatMode(Animation.RESTART);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setDuration(1000);
        }

        mRingIv.startAnimation(rotateAnim);
    }

    private void startMarquee() {
        MarqueeTextView mtv = (MarqueeTextView) findViewById(R.id.card_name_tv);
        mtv.setFocus(true);
        mtv.requestFocus();
        MarqueeTextView mtv1 = (MarqueeTextView) findViewById(R.id.card_des_tv);
        mtv1.setFocus(true);
        mtv1.requestFocus();
    }

    private void stopMarquee() {
        MarqueeTextView mtv = (MarqueeTextView) findViewById(R.id.card_name_tv);
        mtv.setFocus(false);
        mtv = (MarqueeTextView) findViewById(R.id.card_des_tv);
        mtv.setFocus(false);
    }

    @Override
    public void setSongTilte(String songTitle) {
        setName(songTitle);
    }

    @Override
    public void setArtistName(String artistName) {
        setDesc(artistName);
    }

    @Override
    public void setAlbumPic(String uri) {
        if (TextUtils.isEmpty(uri)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mIconLy.setBackground(null);
                mIconLy.setBackgroundColor(getResources().getColor(R.color.color_top_card_bg));
            } else {
                mIconLy.setBackgroundDrawable(null);
                mIconLy.setBackgroundColor(getResources().getColor(R.color.color_top_card_bg));
            }
            mIconIv.setVisibility(VISIBLE);
            return;
        }
        mIconIv.setVisibility(GONE);
        if (!uri.contains("http")) {
            uri = "file://" + uri;
        }

        ImageLoader.getInstance().displayImage(uri, new ViewAware(mIconLy) {

            @Override
            protected void setImageDrawableInto(Drawable drawable, View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(drawable);
                } else {
                    view.setBackgroundDrawable(drawable);
                }
            }

            @Override
            protected void setImageBitmapInto(Bitmap bitmap, View view) {
                BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
                setImageDrawableInto(bd, view);
            }
        });
    }

    @Override
    public void setCurrProgress(int progress) {

    }

    @Override
    public void setTotalProgress(int totalTime) {

    }
}