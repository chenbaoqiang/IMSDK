package com.interrcs.uwsdemo.activity;

import android.app.ListActivity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.interrcs.uwsdemo.R;
import com.interrcs.uwsdemo.adapter.GroupAdapter;
import com.interrcs.uwsdemo.adapter.GroupMemberAdapter;
import com.interrcs.uwsdemo.data.GroupContentProvider;
import com.interrcs.uwsdemo.data.GroupEvtContentProvider;
import com.interrcs.uwsdemo.data.GroupMemberContentProvider;

/**
 * 群成员
 */
public class GroupMemberActivity extends ListActivity{

    public static final String TAG = "GroupActivity";

    ContentObserver mObserver;
    int groupId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_member);
        mObserver = new ContentObserver(new Handler(getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                // 数据库变化，刷新UI
                beginLoadData();
            }
        };

        groupId = getIntent().getIntExtra("groupId", 0);
        Log.d(TAG, "groupid:" + groupId);
        setListAdapter(new GroupMemberAdapter(this, null, true));
    }

    @Override
    public void onStart() {
        super.onStart();
        // 注册数据库变化的观察者
        getContentResolver().registerContentObserver(GroupMemberContentProvider.CONTENT_URI, true, mObserver);
        beginLoadData();
    }

    private void beginLoadData() {
        AsyncTask<Object, Object, Object> mytask = new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] params) {
                Log.d(TAG, "start load db");
                return getContentResolver().query(GroupMemberContentProvider.CONTENT_URI,
                        null, "GROUP_ID=?", new String[] {groupId+""}, "_id desc" );
            }

            @Override
            protected void onPostExecute(Object o) {
                Cursor cursor = (Cursor) o;
                ((GroupMemberAdapter)getListAdapter()).changeCursor(cursor);

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
