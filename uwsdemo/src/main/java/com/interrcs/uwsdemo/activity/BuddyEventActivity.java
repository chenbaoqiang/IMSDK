package com.interrcs.uwsdemo.activity;

import android.app.ListActivity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.interrcs.uwsdemo.R;
import com.interrcs.uwsdemo.adapter.BuddyEvtAdapter;
import com.interrcs.uwsdemo.data.BuddyEvtContentProvider;

// 好友事件通知的列表
public class BuddyEventActivity extends ListActivity{
    public static final String TAG = "BuddyNtfActivity";

    ContentObserver mObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buddy_evt);
        mObserver = new ContentObserver(new Handler(getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                // 数据库变化，刷新UI
                beginLoadData();
            }
        };

        setListAdapter(new BuddyEvtAdapter(this, null, true));
    }

    @Override
    public void onStart() {
        super.onStart();
        // 注册数据库变化的观察者
        getContentResolver().registerContentObserver(BuddyEvtContentProvider.CONTENT_URI, true, mObserver);
        beginLoadData();
    }

    private void beginLoadData() {
        AsyncTask<Object, Object, Object> mytask = new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] params) {
                Log.d(TAG, "start load db");
                return getContentResolver().query(BuddyEvtContentProvider.CONTENT_URI,
                        null, null, null, "_id desc" );
            }

            @Override
            protected void onPostExecute(Object o) {
                Cursor cursor = (Cursor) o;
                ((BuddyEvtAdapter)getListAdapter()).changeCursor(cursor);

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
