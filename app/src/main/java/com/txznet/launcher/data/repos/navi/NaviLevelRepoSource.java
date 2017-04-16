package com.txznet.launcher.data.repos.navi;

import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.data.NavData;
import com.txznet.launcher.data.repos.LevelRepositeSource;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
public class NaviLevelRepoSource extends LevelRepositeSource<NavData> {
    public NaviLevelRepoSource(DataApi<NavData>... nds) {
        super(nds);
    }
}
