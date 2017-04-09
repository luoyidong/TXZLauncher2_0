package com.txznet.launcher.ui.model;

import android.graphics.drawable.Drawable;

/**
 * Created by TXZ-METEORLUO on 2017/3/31.
 */
public class UiCard {
    public int type;
    // 是否虚拟，虚拟后将缩放，用于占位
    public boolean virtual;

    public String name;
    public String desc;
    public String packageName;
    public int backgroundColor;
    public Drawable iconDrawable;
}