package com.interrcs.uwsdemo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * 好友数据的 ContentProvider
 */
public class BuddyContentProvider extends ContentProvider{

    public static final String TAG = "DATA/Buddy";

    public static final String AUTHORITY = "com.interrcs.demo.buddy.provider";
    public static final String BASE_PATH = "defaultBasePath";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final UriMatcher sURIMatcher;

    private static final int MESSAGE_DIR = 0;
    private static final String TABLE = "Buddy";

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
        return MyDbHelper.instance(getContext()).getReadableDatabase().query(
                TABLE, projection,selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long n = MyDbHelper.instance(getContext()).getWritableDatabase()
                .insert(TABLE, null, values);
        Log.e(TAG, "inserted " + n);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int n = MyDbHelper.instance(getContext()).getWritableDatabase()
                .delete(TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return n;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int n = MyDbHelper.instance(getContext()).getWritableDatabase()
                .update(TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return n;
    }
}
