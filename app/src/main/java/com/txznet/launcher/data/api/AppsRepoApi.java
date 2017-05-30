package com.txznet.launcher.data.api;

import com.txznet.launcher.data.model.AppInfo;

/**
 * Created by TXZ-METEORLUO on 2017/4/19.
 */
public interface AppsRepoApi<T> extends AppSourceApi<T> {

    /**
     * 卸载一个APP（所有APP删掉该应用显示）
     *
     * @param pos
     */
    void remove(int pos);

    /**
     * 监听安装检测的APP，从卡片移除的APP
     *
     * @param info
     */
    void addApp(AppInfo info);
}
