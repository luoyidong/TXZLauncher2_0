package com.txznet.launcher.data.repos.music;

import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.api.MusicApi;
import com.txznet.launcher.data.data.MusicData;
import com.txznet.launcher.data.repos.LevelRepositeSource;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
@Singleton
public class MusicLevelRepoSource extends LevelRepositeSource<MusicData, MusicApi.OnMusicStateListener> implements MusicApi {
    private static final String TAG = MusicLevelRepoSource.class.getSimpleName();

    @Inject
    public MusicLevelRepoSource(DataApi<MusicData, OnMusicStateListener>... mds) {
        super(mds);

        initialize(new OnInitListener() {
            @Override
            public void onInit(boolean bSucc) {
            }
        });
    }

    private MusicApi getCurrInstance() {
        DataApi<MusicData,OnMusicStateListener> currInstance = getCurrInterface();
        if (currInstance instanceof MusicApi) {
            return (MusicApi) currInstance;
        }
        return null;
    }

    @Override
    public void startApp() {
        if (getCurrInterface() != null) {
            getCurrInstance().startApp();
        }
    }

    @Override
    public void stopApp() {
        if (getCurrInterface() != null) {
            getCurrInstance().stopApp();
        }
    }

    @Override
    public void play() {
        if (getCurrInterface() != null) {
            getCurrInstance().play();
        }
    }

    @Override
    public void pause() {
        if (getCurrInterface() != null) {
            getCurrInstance().pause();
        }
    }

    @Override
    public void playNext() {
        if (getCurrInterface() != null) {
            getCurrInstance().playNext();
        }
    }

    @Override
    public void playPre() {
        if (getCurrInterface() != null) {
            getCurrInstance().playPre();
        }
    }

    @Override
    public void reqState() {
        if (getCurrInterface() != null) {
            getCurrInstance().reqState();
        }
    }

    @Override
    public boolean isReady() {
        if (getCurrInstance() != null) {
            return getCurrInstance().isReady();
        }
        return false;
    }

    @Override
    public void register(OnMusicStateListener listener) {
        if (getCurrInstance() != null) {
            getCurrInstance().register(listener);
        }
    }

    @Override
    public void unRegister() {
        if (getCurrInstance() != null) {
            getCurrInstance().unRegister();
        }
    }
}