package com.interrcs.uwsdemo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * GroupMember 数据操作
 */

public class GroupMemberContentProvider extends ContentProvider{
    public static final String TAG = "DATA/GroupMember";

    public static final String AUTHORITY = "com.interrcs.demo.group_member.provider";
    public static final String BASE_PATH = "defaultBasePath";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final String TABLE = "group_member";
    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return MyDbHelper.instance(getContext()).getReadableDatabase().query(TABLE, projection,selection,selectionArgs,null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long n = MyDbHelper.instance(getContext()).getWritableDatabase().insert(TABLE, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        MyDbHelper.instance(getContext()).getWritableDatabase().delete(TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        MyDbHelper.instance(getContext()).getWritableDatabase().update(TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
