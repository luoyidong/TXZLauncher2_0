package com.txznet.launcher.data.api;

import com.txznet.launcher.data.model.BaseModel;

/**
 * Created by TXZ-METEORLUO on 2017/4/9.
 */
public interface CardCreateApi {

    BaseModel createCard(String packageName);

    BaseModel createCard(int type);
}
