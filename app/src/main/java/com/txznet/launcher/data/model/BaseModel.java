package com.txznet.launcher.data.model;

import android.support.annotation.NonNull;

import org.json.JSONObject;

/**
 * Created by TXZ-METEORLUO on 2017/3/22.
 */
public class BaseModel extends AppInfo {
    // 通知（仅仅用于展示）
    public static final int TYPE_NOTIFY = 1;
    // 第三方应用
    public static final int TYPE_THIRD_APP = 2;
    // 系统应用
    public static final int TYPE_SYSTEM_APP = 3;
    // 交互（如：音乐控制器）
    public static final int TYPE_MUSIC = 4;
    // 实时 （行车记录仪）
    public static final int TYPE_REAL = 5;
    // 用于显示更多APP
    public static final int TYPE_MORE_APP = 6;
    // 展开（天气仅可以展开）
    public static final int TYPE_WEATHER = 7;
    // LOGO
    public static final int TYPE_BRAND = 8;
    // 导航
    public static final int TYPE_NAV = 9;
    // 虚拟卡片（用于占位）
    public static final int TYPE_VIRTUAL = 10;

    // 对应的类型
    public int type;
    /**
     * 界面元素
     **/
    public String name;
    public String desc;
    public int bgColor;
    public int btnType;

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