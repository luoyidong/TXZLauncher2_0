package com.txznet.launcher.data.repos.navi;

import com.txznet.launcher.data.api.DataInterface;
import com.txznet.launcher.data.data.NavData;
import com.txznet.launcher.data.repos.LevelRepositeSource;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
public class NaviLevelRepoSource extends LevelRepositeSource<NavData> {
    public NaviLevelRepoSource(DataInterface<NavData>... nds) {
        super(nds);
    }
}
