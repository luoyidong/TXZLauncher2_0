package com.txznet.launcher.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by UPC on 2017/4/16.
 */
@DatabaseTable
public class CardBean implements Serializable {
    public static final int TYPE_CARD = 1;
    public static final int TYPE_APP = 2;
    public static final int DELETE_TRUE = 1;
    public static final int CARD_TRUE = 1;

    @DatabaseField(generatedId = true)
    public Long _id;
    @DatabaseField // 数组位置
    public int pos = -1;
    @DatabaseField // 数据对应类型
    public int type = -1;
    @DatabaseField // 表示是否属于卡片（被删除的卡片）
    public int card = -1;
    @DatabaseField // 表明是否已经被卸载
    public int delete = -1;
    @DatabaseField // 卡片对应的包名
    public String packageName;
    @DatabaseField // 携带的数据信息
    public String jsonData;
}