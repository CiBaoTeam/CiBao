package com.example.cibao.cibao.Helpers;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * 屈彬
 * 2017/3/16
 * 数据库辅助类
 */
public class DBHelper  extends OrmLiteSqliteOpenHelper {
    /**
     * @show 数据库名
     */
    public static final String DATA_BASE_NAME = "db_CiBao";
    public static final int DATA_BASE_VERSION = 1;

    /**
     * @show 存储类型
     */
    protected Class DBClass;

    /**
     * @show 设置存储类型
     * @param DBClass 存储类型
     */
    public void setDBClass(Class DBClass){
        this.DBClass = DBClass;
    }

    /**
     * @show 构造函数
     * @param context 上下文
     * @param DBClass 存储类型
     */
    public DBHelper(Context context, Class DBClass) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        setDBClass(DBClass);
    }

    /**
     * @show 表创建
     * @param sqLiteDatabase 数据库
     * @param connectionSource 链接源
     */
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            Log.i(DBHelper.class.getName(), "onCreate");
            //通过创建与存储类型对应的表
            TableUtils.createTable(connectionSource, DBClass);
        } catch (SQLException e) {
            Log.e(DBHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            Log.e(DBHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }


    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i1, int i2) {
        try {
            Log.i(DBHelper.class.getName(), "onUpgrade");
            //通过存储类型删除对应的表
            TableUtils.dropTable(connectionSource, DBClass, true);
            // after we drop the old databases, we create the new ones
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DBHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            Log.e(DBHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
}
