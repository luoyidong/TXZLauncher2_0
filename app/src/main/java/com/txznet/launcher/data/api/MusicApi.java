package com.txznet.launcher.data.api;

import com.txznet.launcher.data.data.MusicData;

/**
 * Created by UPC on 2017/4/15.
 */
public interface MusicApi extends AppApi<MusicApi.OnMusicStateListener> {

    void play();

    void pause();

    void playNext();

    void playPre();

    interface OnMusicStateListener {
        void onMusicUpdate(MusicData md);
    }
}