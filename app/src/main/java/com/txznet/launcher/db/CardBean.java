package com.txznet.launcher.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by UPC on 2017/4/16.
 */
@DatabaseTable
public class CardBean implements Serializable {
    @DatabaseField(generatedId = true)
    public Long _id;
    @DatabaseField // 卡片位置
    public int pos;
    @DatabaseField // 卡片类型
    public int type;
    @DatabaseField // 卡片对应的包名
    public String packageName;
    @DatabaseField // 携带的数据信息
    public String jsonData;
}