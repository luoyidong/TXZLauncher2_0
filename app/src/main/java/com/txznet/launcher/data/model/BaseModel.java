package com.txznet.launcher.data.model;

import android.support.annotation.NonNull;

import org.json.JSONObject;

/**
 * Created by TXZ-METEORLUO on 2017/3/22.
 */
public class BaseModel extends AppInfo {
    /**
     * 界面元素
     **/
    public String desc;
    public int bgColor;
    public int btnType;

    public BaseModel(int type) {
        this.type = type;
    }

    public BaseModel(@NonNull String packageName, boolean isSystemApp) {
        super(packageName, isSystemApp);

        if (isSystemApp) {
            type = TYPE_SYSTEM_APP;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public JSONObject toJsonObject() {
        return super.toJsonObject();
    }
}