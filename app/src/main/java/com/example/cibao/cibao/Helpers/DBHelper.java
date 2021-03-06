package com.example.cibao.cibao.Helpers;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

import com.j256.ormlite.dao.Dao;
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
    /**
     * @show 数据库版本
     */
    public static final int DATA_BASE_VERSION = 1;
    /**
     * @show 单词表名
     */
    public static final String TABLE_WORD = "TABLE_WORD";
    /**
     * @show 词库表名
     */
    public static final String TABLE_LEXICON = "TABLE_LEXICON";
    /**
     * @show 选词表名
     */
    public static final String TABLE_WORD_SELECT = "TABLE_WORD_SELECT";
    // 词库名
    /**
     * @show 默认词库名
     */
    public static final String DEFAULT_LEXICON_TABLE_NAME = "我的词库";
    /**
     * @show 默认词库描述
     */
    public static final String DEFAULT_LEXICON_DESCRIPTION = "这是一个默认词库";
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
     * @show 获取数据库存储类型
     * @return 存储类型
     */
    public Class getDBClass(){return DBClass;}

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

    /**
     * @show 表更新
     * @param sqLiteDatabase 数据库
     * @param connectionSource 链接源
     * @param i1
     * @param i2
     */
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

    /**
     * @show 创建刀
     * @return 刀
     * @throws java.sql.SQLException
     */
   public Dao createDao(Class c) throws java.sql.SQLException{
       return getDao(c);
   }
}
