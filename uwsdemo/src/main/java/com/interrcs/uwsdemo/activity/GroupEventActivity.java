package com.interrcs.uwsdemo.activity;

import android.app.ListActivity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.interrcs.uwsdemo.R;
import com.interrcs.uwsdemo.adapter.GroupEvtAdapter;
import com.interrcs.uwsdemo.data.GroupEvtContentProvider;


/**
 * 群组事件通知
 */
public class GroupEventActivity extends ListActivity{

    public static final String TAG = "GroupEventActivity";

    ContentObserver mObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_evt);
        mObserver = new ContentObserver(new Handler(getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                // 数据库变化，刷新UI
                beginLoadData();
            }
        };

        setListAdapter(new GroupEvtAdapter(this, null, true));
    }

    @Override
    public void onStart() {
        super.onStart();
        // 注册数据库变化的观察者
        getContentResolver().registerContentObserver(GroupEvtContentProvider.CONTENT_URI, true, mObserver);
        beginLoadData();
    }

    private void beginLoadData() {
        AsyncTask<Object, Object, Object> mytask = new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] params) {
                Log.d(TAG, "start load db");
                return getContentResolver().query(GroupEvtContentProvider.CONTENT_URI,
                        null, null, null, "_id desc" );
            }

            @Override
            protected void onPostExecute(Object o) {
                Cursor cursor = (Cursor) o;
                ((GroupEvtAdapter)getListAdapter()).changeCursor(cursor);

            }
        };
        mytask.execute();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        getContentResolver().unregisterContentObserver(mObserver);
    }


}
