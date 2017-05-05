package com.txznet.launcher.data.api;

import com.txznet.launcher.data.model.AppInfo;
import com.txznet.launcher.db.CardBean;

/**
 * Created by UPC on 2017/5/4.
 */

public interface DataConvertApi {

    AppInfo convertFromCardBean(CardBean cb);

    CardBean convertFromModel(AppInfo info);
}
