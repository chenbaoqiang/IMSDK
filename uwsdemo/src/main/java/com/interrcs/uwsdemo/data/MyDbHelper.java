package com.interrcs.uwsdemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {

    public static final String TAG = "MyDbHelper";
    
    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTables(SQLiteDatabase db) {
        Log.e(TAG, "create tables");
        // 存储消息的表
        String sql = "CREATE TABLE 'CHAT_MESSAGE' ( " +
                "'_id' INTEGER PRIMARY KEY ," +
                "'IMDN_ID' TEXT NOT NULL ," +
                "'MSG_FROM' TEXT," +
                "'MSG_TO' TEXT," +
                "'RECEIVE_TIME' INTEGER," +
                "'CONTENT' TEXT," +
                "'CHAT_TYPE' INTEGER," +
                "'CONTENT_TYPE' INTEGER," +
                "'STATUS' INTEGER," +
                "'TRANSFER_ID' TEXT," +
                "'PROGRESS' INTEGER," +
                "'FILE_SIZE' INTEGER" +
                ");";
        db.execSQL(sql);
        Log.e(TAG, "create table message");
        // 存储好友事件的表，如：好友添加、删除、修改、同步等
        sql = "CREATE TABLE 'BUDDY_EVENT' (" +
                "'_id' INTEGER PRIMARY KEY ," +
                "'FROM_USER_ID' INTEGER ," +
                "'FROM_USER_NAME' TEXT ," +
                "'TO_USER_ID' INTEGER ," +
                "'REASON' TEXT ," +
                "'TIME' INTEGER ," +
                "'STATE' INTEGER ," +
                "'REFUSE_REASON' TEXT" +
                ");";
        db.execSQL(sql);
        Log.e(TAG, "create table buddy event");
        // 存储好友的表
        sql = "CREATE TABLE 'BUDDY' (" +
                "'_id' INTEGER PRIMARY KEY ," +
                "'USER_ID' INTEGER ," +
                "'USER_NAME' TEXT ," +
                "'LOCAL_NAME' TEXT ," +
                "'NICK_NAME' TEXT ," +
                "'PORTRAIT_PATH' TEXT ," +
                "'PORTRAIT_VERSION' INTEGER ," +
                "'IMPRESA' TEXT" +
                ");";
        db.execSQL(sql);

        //群组表
        sql = "CREATE TABLE 'GROUPS' (" +
                "'_id' INTEGER PRIMARY KEY ," +
                "'GROUP_ID' INTEGER ," +
                "'GROUP_NAME' TEXT ," +
                "'GROUP_VERSION' TEXT " +
                ");";
        db.execSQL(sql);
        Log.e(TAG, "create table group");

        //群事件通知表
        sql = "CREATE TABLE 'GROUP_EVENT' (" +
                "'_id' INTEGER PRIMARY KEY ," +
                "'SOURCE_USER' INTEGER ," +
                "'SOURCE_USER_NICKNAME' TEXT ," +
                "'TIME' INTEGER ," +
                "'EVENT' INTEGER ," +
                "'GROUP_ID' INTEGER, " +
                "'HANDLE_RESULT' INTEGER " +
                ");";
        db.execSQL(sql);
        Log.e(TAG, "create table group_event");

        //群成员列表
        sql = "CREATE TABLE 'GROUP_MEMBER' (" +
                "'_id' INTEGER PRIMARY KEY ," +
                "'GROUP_ID' INTEGER ," +
                "'USER_ID' INTEGER ," +
                "'USER_NAME' TEXT ," +
                "'LOCAL_NAME' TEXT ," +
                "'JOIN_TIME' INTEGER ," +
                "'ROLE' INTEGER " +
                ");";
        db.execSQL(sql);

        Log.e(TAG, "create table group_member");
    }

    public static MyDbHelper sHelper;
    public static MyDbHelper instance(Context context) {

        if(sHelper == null) {
            synchronized (MyDbHelper.class) {
                if(sHelper == null) {
                    sHelper = new MyDbHelper(context, "mydb", null);
                }
            }
        }

        return sHelper;
    }
}
