package com.txznet.launcher.data.repos.music;

import com.txznet.launcher.data.api.DataInterface;
import com.txznet.launcher.data.data.MusicData;
import com.txznet.launcher.data.repos.LevelRepositeSource;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
public class MusicLevelRepoSource extends LevelRepositeSource<MusicData> {

    public MusicLevelRepoSource(DataInterface<MusicData>... mds) {
        super(mds);
    }
}