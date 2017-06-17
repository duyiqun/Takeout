package com.qun.takeout.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by Qun on 2017/6/17.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "takeout.db";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper sDBHelper;

    public static DBHelper getInstance(Context context) {
        if (sDBHelper == null) {
            return new DBHelper(context);
        }
        return sDBHelper;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //表的创建
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    //表的更新
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
