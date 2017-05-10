package com.txznet.launcher.data.repos.navi;

import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.api.NavApi;
import com.txznet.launcher.data.data.NavData;
import com.txznet.launcher.data.repos.LevelRepositeSource;

import javax.inject.Inject;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
public class NaviLevelRepoSource extends LevelRepositeSource<NavData, NavApi.OnNavListener> implements NavApi {

    @Inject
    public NaviLevelRepoSource(DataApi<NavData, NavApi.OnNavListener>... nds) {
        super(nds);

        initialize(new OnInitListener() {
            @Override
            public void onInit(boolean bSucc) {
            }
        });
    }

    private NavApi getCurrInstance() {
        DataApi<NavData, NavApi.OnNavListener> currInstance = getCurrInterface();
        if (currInstance instanceof NavApi) {
            return (NavApi) currInstance;
        }
        return null;
    }

    @Override
    public void navigateHome() {
        if (getCurrInstance() == null) {
            getCurrInstance().navigateHome();
        }
    }

    @Override
    public void navigateCompany() {
        if (getCurrInstance() == null) {
            getCurrInstance().navigateCompany();
        }
    }
}
