package com.txznet.launcher.data.api;

import com.txznet.launcher.data.model.AppInfo;
import com.txznet.launcher.data.model.BaseModel;
import com.txznet.launcher.db.CardBean;

/**
 * Created by TXZ-METEORLUO on 2017/4/9.
 */
public interface CardCreateApi {

    BaseModel createCard(String packageName);

    AppInfo createFromCardBean(CardBean cb);
}
