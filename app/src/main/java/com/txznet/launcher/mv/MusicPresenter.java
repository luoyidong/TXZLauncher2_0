package com.txznet.launcher.mv;

import android.text.TextUtils;

import com.txznet.launcher.data.data.MusicData;
import com.txznet.launcher.data.repos.music.MusicLevelRepoSource;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by UPC on 2017/4/15.
 */

public class MusicPresenter extends MusicContract.Presenter {
    private static final String TAG = MusicPresenter.class.getSimpleName();

    private boolean mIsFirstReq = true;
    private MusicLevelRepoSource mMusicRepoSource;

    @Inject
    public MusicPresenter(MusicLevelRepoSource repoSource) {
        mMusicRepoSource = repoSource;
    }

    @Override
    public void attachView(MusicContract.View view) {
        super.attachView(view);

        Observable<MusicData> observable = mMusicRepoSource.reqData(mIsFirstReq);
        if (observable != null) {
            mCompositeSubscription.add(
                    observable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<MusicData>() {
                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onCompleted() {
                                    mIsFirstReq = false;
                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onNext(MusicData musicData) {
                                    onDataNext(musicData);
                                }
                            }));
        }

        // 更新
        mMusicRepoSource.reqState();
    }

    private void onDataNext(MusicData musicData) {
        if (mCacheData == null) {
            mCacheData = musicData;
        }

        if ((!TextUtils.isEmpty(mCacheData.albumPic) && !mCacheData.albumPic.equals(musicData.albumPic))
                || TextUtils.isEmpty(mCacheData.albumPic)) {
            mCacheData.albumPic = musicData.albumPic;
            getMvpView().setAlbumPic(mCacheData.albumPic);
        }

        if ((!TextUtils.isEmpty(mCacheData.artistName) && !mCacheData.artistName.equals(musicData.artistName))
                || TextUtils.isEmpty(mCacheData.artistName)) {
            mCacheData.artistName = musicData.artistName;
            getMvpView().setArtistName(mCacheData.artistName);
        }

        if ((!TextUtils.isEmpty(mCacheData.songTitle) && !mCacheData.songTitle.equals(musicData.songTitle))
                || TextUtils.isEmpty(mCacheData.songTitle)) {
            mCacheData.songTitle = musicData.songTitle;
            getMvpView().setArtistName(mCacheData.songTitle);
        }

        if (mCacheData.mediaState != musicData.mediaState) {
            mCacheData.mediaState = musicData.mediaState;
            getMvpView().setMediaState(mCacheData.mediaState);
        }

        if (mCacheData.currProgress != musicData.currProgress) {
            mCacheData.currProgress = musicData.currProgress;
            getMvpView().setCurrProgress(mCacheData.currProgress);
        }

        if (mCacheData.totalProgress != musicData.totalProgress) {
            mCacheData.totalProgress = musicData.totalProgress;
            getMvpView().setTotalProgress(mCacheData.totalProgress);
        }
    }

    private MusicData mCacheData;

    @Override
    public void play() {
        mMusicRepoSource.play();
    }

    @Override
    public void pause() {
        mMusicRepoSource.pause();
    }

    @Override
    public void playPre() {
        mMusicRepoSource.playPre();
    }

    @Override
    public void playNext() {
        mMusicRepoSource.playNext();
    }

    @Override
    public void onClickBlank() {
        mMusicRepoSource.startApp();
    }
}
