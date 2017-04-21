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

    public String packageName;

    public boolean isSystemApp;

    @Inject
    public AppInfo() {
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