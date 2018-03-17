package com.interrcs.uwsdemo.activity;

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
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.dapi.v3.BuddyManager;
import com.feinno.sdk.enums.ErrorCode;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.result.v3.BklistGetResult;
import com.feinno.sdk.result.v3.BuddyResult;
import com.feinno.sdk.result.v3.GetPresenceResult;
import com.feinno.sdk.dapi.v3.*;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;
import com.interrcs.uwsdemo.adapter.BuddyAdapter;
import com.interrcs.uwsdemo.data.BuddyContentProvider;

// 好友列表界面
public class BuddyActivity extends ListActivity {
    public static final String TAG = "BuddyActivity";
    private ContentObserver mObserver;
    Button btnAddBuddy;
    Button btnGetPresence;
    Button btnBlackList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy);
        mObserver = new ContentObserver(new Handler(getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                // 数据库变化，刷新UI
                beginLoadData();
            }
        };

        String action = "interrcs.add-buddy";
        setListAdapter(new BuddyAdapter(this, null));
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                BuddyResult result = BroadcastActions.getResult(intent);
                if(result.errorCode == ErrorCode.OK.value()) {
                    Toast.makeText(getApplicationContext(), "添加请求成功发送", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "添加好友失败："+result.errorExtra,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        getApplicationContext().registerReceiver(receiver, new IntentFilter(action));

        // 添加好友按钮
        btnAddBuddy = (Button) findViewById(R.id.btn_add);
        btnAddBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "interrcs.add-buddy";
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                try {
                    BuddyManager.addBuddy(LoginState.getStartUser(getApplicationContext()), "+8615901435217", "HI", pi);
                } catch (RemoteException e) {
                    Log.e(TAG, "add buddy error", e);
                    Toast.makeText(getApplicationContext(), "添加好友错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGetPresence= (Button) findViewById(R.id.btn_getpre);
        btnGetPresence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action1 = "interrcs.get-presence";
                Intent intent = new Intent(action1);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                try {
                    PresenceManager.getPresence(LoginState.getStartUser(getApplicationContext()), "849183", pi);
                } catch (RemoteException e) {
                    Log.e(TAG, "get presence", e);
                    Toast.makeText(getApplicationContext(), "添加好友错误", Toast.LENGTH_SHORT).show();
                }
//
                BroadcastReceiver receiver1 = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        GetPresenceResult result = (GetPresenceResult) BroadcastActions.getResult(intent);
                        if(result.errorCode == ErrorCode.OK.value()) {
                            Toast.makeText(getApplicationContext(), "请求成功发送,presence:"+ result.presence+"presenceDes: "+result.presenceDes+"caps:"+result.caps, Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "error："+result.errorExtra,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                getApplicationContext().registerReceiver(receiver1, new IntentFilter(action1));
            }
        });

        btnBlackList = (Button) findViewById(R.id.btn_blacklist);
        btnBlackList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String action1 = "interrcs.get-blacklist";
                Intent intent = new Intent(action1);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                try {
                    BuddyManager.getBlacklist(LoginState.getStartUser(getApplicationContext()), pi);
                } catch (RemoteException e) {
                    Log.e(TAG, "get black", e);
                    Toast.makeText(getApplicationContext(), "获取黑名单错误", Toast.LENGTH_SHORT).show();
                }
//
                BroadcastReceiver receiver1 = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        BklistGetResult result = (BklistGetResult) BroadcastActions.getResult(intent);
                        if(result.errorCode == ErrorCode.OK.value()) {
                            Toast.makeText(getApplicationContext(), "请求成功发送,blacklist: "+ result.id + " " + result.blacklist.length, Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "error："+result.errorExtra,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                getApplicationContext().registerReceiver(receiver1, new IntentFilter(action1));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // 监听数据库变化
        getContentResolver().registerContentObserver(BuddyContentProvider.CONTENT_URI, true, mObserver);
        beginLoadData();
    }

    // 刷新好友列表
    private void beginLoadData() {
        AsyncTask<Object, Object, Object> mytask = new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] params) {
                Log.d(TAG, "start load db");
                return getContentResolver().query(BuddyContentProvider.CONTENT_URI,
                        null, null, null, "_id desc" );
            }

            @Override
            protected void onPostExecute(Object o) {
                Cursor cursor = (Cursor) o;
                ((BuddyAdapter)getListAdapter()).changeCursor(cursor);

            }
        };
        mytask.execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        getContentResolver().unregisterContentObserver(mObserver);
    }
}
