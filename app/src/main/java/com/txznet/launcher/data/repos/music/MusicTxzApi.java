package com.txznet.launcher.data.repos.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.txznet.launcher.data.Constants;
import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.api.MusicApi;
import com.txznet.launcher.data.data.MusicData;
import com.txznet.launcher.module.PackageManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by UPC on 2017/4/14.
 */
@Singleton
public class MusicTxzApi implements DataApi<MusicData>, MusicApi {
    static final String TAG = MusicTxzApi.class.getSimpleName();

    private Context mContext;
    private MusicData mCurrTmpData;
    private Subscriber<? super MusicData> mSubscriber;

    @Inject
    public MusicTxzApi(Context context) {
        mContext = context;
    }

    @Override
    public void initialize(OnInitListener listener) {
        if (!PackageManager.getInstance().checkAppExist(Constants.PACKAGE_NAME.MUSIC_PACKAGE_NAME)) {
            if (listener != null) {
                listener.onInit(false);
            }
            return;
        }
        initConnect();
        if (listener != null) {
            listener.onInit(true);
        }
    }

    private void initConnect() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.KeyValueConstants.KeyValueMusic.TXZ_STATUS_CHANGE_ACTION);
        filter.addAction(Constants.KeyValueConstants.KeyValueMusic.TXZ_MUSIC_INFO_ACTION);
        filter.addAction(Constants.KeyValueConstants.KeyValueMusic.TXZ_MUSIC_PROPRESS_ACTION);
        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d(TAG, "onReceive action:" + action);
                try {
                    if (mSubscriber != null) {
                        mSubscriber.onStart();
                    }

                    if (mCurrTmpData == null) {
                        mCurrTmpData = new MusicData();
                    }

                    if (Constants.KeyValueConstants.KeyValueMusic.TXZ_STATUS_CHANGE_ACTION.equals(action)) {
                        /**
                         1：缓冲
                         2：播放
                         3：暂停
                         4：退出
                         */
                        int status = intent.getIntExtra("status", 0);
                        mCurrTmpData.mediaState = status;
                        if (mSubscriber != null) {
                            mSubscriber.onNext(mCurrTmpData);
                        }
                    } else if (Constants.KeyValueConstants.KeyValueMusic.TXZ_MUSIC_INFO_ACTION.equals(action)) {
                        String arts = intent.getStringExtra("artists");
                        String title = intent.getStringExtra("title");
                        String logo = intent.getStringExtra("logo");
                        int status = intent.getIntExtra("status", 0);
                        Log.d(TAG, "status:" + status + ",arts:" + arts + ",title:" + title + ",albumPic:" + logo);
                        if (status != 0) {
                            mCurrTmpData.mediaState = status;
                        }

                        mCurrTmpData.songTitle = title;
                        mCurrTmpData.artistName = arts;
                        mCurrTmpData.albumPic = logo;
                        if (mSubscriber != null) {
                            mSubscriber.onNext(mCurrTmpData);
                        }
                    }
                } catch (Exception e) {
                    if (mSubscriber != null) {
                        e.printStackTrace();
                        mSubscriber.onError(e);
                    }
                } finally {
                    if (mSubscriber != null) {
                        mSubscriber.onCompleted();
                    }
                }
            }
        };
        mContext.registerReceiver(mReceiver, filter);
    }

    @Override
    public Observable<MusicData> reqData(boolean forceReq) {
        return Observable.create(new Observable.OnSubscribe<MusicData>() {
            @Override
            public void call(Subscriber<? super MusicData> subscriber) {
                mSubscriber = subscriber;
            }
        });
    }

    @Override
    public int getInterfacePriority() {
        return Constants.Repo_Level.Music_Level.LEVEL_TT;
    }

    @Override
    public void startApp() {
        PackageManager.getInstance().openApp(Constants.PACKAGE_NAME.MUSIC_PACKAGE_NAME);
    }

    @Override
    public void stopApp() {
        PackageManager.getInstance().closeApp(Constants.PACKAGE_NAME.MUSIC_PACKAGE_NAME);
    }

    @Override
    public void play() {
        dispatchAction(Constants.KeyValueConstants.KeyValueMusic.TXZ_MUSIC_PLAY_ACTION);
    }

    @Override
    public void pause() {
        dispatchAction(Constants.KeyValueConstants.KeyValueMusic.TXZ_MUSIC_PAUSE_ACTION);
    }

    @Override
    public void playNext() {
        dispatchAction(Constants.KeyValueConstants.KeyValueMusic.TXZ_MUSIC_NEXT_ACTION);
    }

    @Override
    public void playPre() {
    }

    @Override
    public boolean isReady() {
        return PackageManager.getInstance().checkAppExist(Constants.PACKAGE_NAME.MUSIC_PACKAGE_NAME);
    }

    @Override
    public void reqState() {
        dispatchAction(Constants.KeyValueConstants.KeyValueMusic.TXZ_MUSIC_REQUEST_ACTION);
    }

    private void dispatchAction(String action) {
        Log.d(TAG, "dispatchAction:" + action);
        Intent intent = new Intent(action);
        mContext.startActivity(intent);
    }
}