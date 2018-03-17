package com.interrcs.uwsdemo.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.dapi.GroupManager;
import com.feinno.sdk.enums.ErrorCode;
import com.feinno.sdk.result.GroupOpResult;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;
import com.interrcs.uwsdemo.adapter.GroupAdapter;
import com.interrcs.uwsdemo.adapter.GroupEvtAdapter;
import com.interrcs.uwsdemo.data.GroupContentProvider;
import com.interrcs.uwsdemo.data.GroupEvtContentProvider;

/**
 * 群组
 */
public class GroupActivity extends ListActivity{

    public static final String TAG = "GroupActivity";

    Button btnCreate;
    ContentObserver mObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group);
        btnCreate = (Button) findViewById(R.id.create);
        mObserver = new ContentObserver(new Handler(getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                // 数据库变化，刷新UI
                beginLoadData();
            }
        };

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "com.intercs.uwsdemo.group_create";
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(GroupActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                final BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        context.unregisterReceiver(this);
                        GroupOpResult result = BroadcastActions.getResult(intent);
                        if(result.errorCode == ErrorCode.OK.value()) {
                            Toast.makeText(GroupActivity.this, "群组创建成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GroupActivity.this, "群组创建失败:" + result.errorCode + "," + result.errorExtra, Toast.LENGTH_SHORT).show();
                        }

                        context.unregisterReceiver(this);
                    }
                };

                GroupActivity.this.registerReceiver(receiver, new IntentFilter(action));

                try {
                    GroupManager.createGroup(LoginState.getStartUser(GroupActivity.this),
                            new String[] { "+8615901435218", "+8615901435219" },
                            "group name", null );
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        });

        setListAdapter(new GroupAdapter(this, null, true));
    }

    @Override
    public void onStart() {
        super.onStart();
        // 注册数据库变化的观察者
        getContentResolver().registerContentObserver(GroupContentProvider.CONTENT_URI, true, mObserver);
        beginLoadData();
    }

    private void beginLoadData() {
        AsyncTask<Object, Object, Object> mytask = new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] params) {
                Log.d(TAG, "start load db");
                return getContentResolver().query(GroupContentProvider.CONTENT_URI,
                        null, null, null, "_id desc" );
            }

            @Override
            protected void onPostExecute(Object o) {
                Cursor cursor = (Cursor) o;
                ((GroupAdapter)getListAdapter()).changeCursor(cursor);

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

