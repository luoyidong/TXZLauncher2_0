package com.txznet.launcher.ui.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.txznet.launcher.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TXZ-METEORLUO on 2017/1/9.
 */
public class CardButton extends FrameLayout {
    private static final String TAG = CardButton.class.getSimpleName();

    public static final int STATE_PLAY = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_STOP = 3;
    public static final int STATE_VOICE = 4;
    public static final int STATE_MORE = 5;
    public static final int STATE_RECORDING = 6;
    public static final int STATE_TEXT = 7;
    public static final int STATE_NAVI = 8;

    private ImageView mIv;
    private TextView mTv;
    private View mBtnBgLy;

    private int mCurState = -1;
    private CharSequence mBtnText;
    private OnClickListener mClickListener;

    private static final Map<Integer, Integer> sIconResMap = new HashMap<>();

    static {
        sIconResMap.put(STATE_PLAY, R.drawable.icon_music_play);
        sIconResMap.put(STATE_PAUSE, R.drawable.icon_real_record);
        sIconResMap.put(STATE_STOP, 0);
        sIconResMap.put(STATE_VOICE, 0);
        sIconResMap.put(STATE_MORE, R.drawable.icon_more_app);
        sIconResMap.put(STATE_RECORDING, R.drawable.icon_real_record);
        sIconResMap.put(STATE_NAVI, R.drawable.icon_nav_nav);
    }

    public CardButton(Context context) {
        super(context);
        setUpView();
    }

    public CardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public CardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }

    public void setState(int state) {
        if (mCurState == state) {
            Log.e(TAG, "CardButton State:" + state);
            return;
        }
        Log.d(TAG, "CardButton setState:" + state);

        mCurState = state;
        if (mCurState == STATE_TEXT) {
            mIv.setVisibility(GONE);
            mTv.setVisibility(VISIBLE);
            return;
        }

        mTv.setVisibility(INVISIBLE);
        mIv.setVisibility(VISIBLE);
        int resId = sIconResMap.get(mCurState);
        if (mIv.getVisibility() == VISIBLE && resId != 0) {
            mIv.setImageResource(resId);
        }

        if (mCurState == STATE_MORE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mBtnBgLy.setBackground(null);
            } else {
                mBtnBgLy.setBackgroundDrawable(null);
            }
        }
    }

    public void setText(CharSequence cs) {
        mTv.setText(cs);
    }

    public void setOnMainBtnClickListener(OnClickListener listener) {
        mClickListener = listener;
    }

    private void setUpView() {
        removeAllViews();
        View view = View.inflate(getContext(), R.layout.card_btn_ly, null);
        mBtnBgLy = view.findViewById(R.id.card_btn_ly);
        mIv = (ImageView) view.findViewById(R.id.icon_btn_iv);
        mTv = (TextView) view.findViewById(R.id.text_btn_tv);
        mTv.setVisibility(GONE);

        int w = getResources().getDimensionPixelSize(R.dimen.dimen_card_btn_width);
        int h = getResources().getDimensionPixelSize(R.dimen.dimen_card_btn_height);
        LayoutParams lp = new LayoutParams(w, h);
        addView(view, lp);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClick(CardButton.this);
                }
            }
        });
    }
}