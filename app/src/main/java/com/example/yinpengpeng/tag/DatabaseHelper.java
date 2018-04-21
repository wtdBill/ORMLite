package com.example.yinpengpeng.tag;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by YinPengpeng on 2018/4/21/021.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    //数据库名称
    private static final String DB_NAME="db_myorm";
    //数据库版本
    private static final int DB_VERSION=1;

    private static DatabaseHelper instance;

    private DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public static DatabaseHelper getInstance(Context context){
        if (instance==null){
            synchronized (DatabaseHelper.class){
                if (instance==null){
                    instance=new DatabaseHelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            //需要新建的表
            TableUtils.createTable(connectionSource,People.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //数据库更新
        try {
            //当数据库版本发生变化时会走onUpgrade，我们这里暂时先删除原来的表，在重新创建
            TableUtils.dropTable(connectionSource,People.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Dao getDao(Class clazz) throws SQLException {

        return super.getDao(clazz);
    }
}
