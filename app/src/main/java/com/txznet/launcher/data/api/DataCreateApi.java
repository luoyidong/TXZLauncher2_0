package com.txznet.launcher.data.api;

import com.txznet.launcher.data.model.AppInfo;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.db.CardBean;

/**
 * Created by TXZ-METEORLUO on 2017/4/9.
 */
public interface DataCreateApi {

    /**
     * 通过包名创建卡片
     * @param packageName
     * @return
     */
    BaseModel createCard(String packageName);

    /**
     * @param type
     * @param isCard
     * @return
     */
    AppInfo createFromType(int type, boolean isCard);
}
