package com.interrcs.uwsdemo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 好友通知事件的 ContentProvider，如：好友添加、删除、修改、同步等。
 */
public class BuddyEvtContentProvider extends ContentProvider {

    public static final String TAG = "DATA/BuddyEvt";

    public static final String AUTHORITY = "com.interrcs.demo.buddy_evt.provider";
    public static final String BASE_PATH = "defaultBasePath";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final UriMatcher sURIMatcher;

    private static final int MESSAGE_DIR = 0;
    private static final String TABLE = "BUDDY_EVENT";

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, MESSAGE_DIR);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = MyDbHelper.instance(getContext()).getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor =  database.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);
        }
        finally {
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = MyDbHelper.instance(getContext()).getWritableDatabase();
        try{
            database.insert(TABLE, null, values);
        } finally {
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = MyDbHelper.instance(getContext()).getWritableDatabase();
        int n = 0;
        try{
            n = database.update(TABLE, values, selection, selectionArgs);
        } finally {
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return n;
    }
}
