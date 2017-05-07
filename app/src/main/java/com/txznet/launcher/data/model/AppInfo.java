package com.txznet.launcher.data.model;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import javax.inject.Inject;

/**
 * Created by TXZ-METEORLUO on 2017/3/31.
 */
public class AppInfo {
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
    public int type = TYPE_THIRD_APP;

    public String packageName;

    public boolean isSystemApp;

    @Inject
    public AppInfo() {
    }

    public AppInfo(int type) {
        this.type = type;
    }

    public AppInfo(@NonNull String packageName, boolean isSystemApp) {
        this.packageName = packageName;
        this.isSystemApp = isSystemApp;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                jsonObject.put(field.getName(), field.get(this));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public String toString() {
        return toJsonObject().toString();
    }

    public AppInfo fromJson(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.set(this, jo.opt(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public static AppInfo createByJson(String json) {
        return new AppInfo().fromJson(json);
    }
}