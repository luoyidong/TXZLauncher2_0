package com.txznet.launcher.data.repos.music;

import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.api.MusicApi;
import com.txznet.launcher.data.data.MusicData;

import rx.Observable;

/**
 * Created by UPC on 2017/4/16.
 */

public class MusicRemoteApi implements DataApi<MusicData>, MusicApi {

    @Override
    public void startApp() {

    }

    @Override
    public void stopApp() {

    }

    @Override
    public void reqState() {

    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void initialize(OnInitListener listener) {

    }

    @Override
    public Observable<MusicData> reqData(boolean forceReq) {
        return null;
    }

    @Override
    public int getInterfacePriority() {
        return 0;
    }

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void playNext() {

    }

    @Override
    public void playPre() {

    }
}
