package com.txznet.launcher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by TXZ-METEORLUO on 2017/1/9.
 */
public class SQLiteHelper extends OrmLiteSqliteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "db_launcher.db";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CardBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CardBean.class, true);
            TableUtils.createTable(connectionSource, CardBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}