package com.interrcs.uwsdemo.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.dapi.v3.UserInfoManager;
import com.feinno.sdk.utils.JsonUtils;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;
import com.feinno.sdk.result.EndPointResult;
import com.feinno.sdk.utils.LogUtil;

// 根据传入的user ID获取显示用户资料。可显示好友的资料，也可显示自己的资料。
public class UserActivity extends Activity{

    public static String TAG = "UserActivity";

    TextView tvResult;

    BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Bundle extras = getIntent().getExtras();
        String user = extras == null ? null : extras.getString("userId");
        if (user == null) {
            user = LoginState.getUserId(this) + "";
        }
        tvResult = (TextView)findViewById(R.id.tv_result);

        //获取设备节点测试代码
        Intent intent = new Intent("com.interrcs.sdk.yourapp.broadcast.endpointlist_result");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            UserInfoManager.getEndpointList(LoginState.getStartUser(this), pi);
        } catch (RemoteException e) {
            Log.e(TAG, "get list error", e);
        }

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                EndPointResult result = BroadcastActions.getResult(intent);
                tvResult.setText(JsonUtils.toJson(result));
                LogUtil.i(TAG, "result: " + intent.getExtras());
                //Toast.makeText(context, "调用结果：" +JsonUtils.toJson(result),Toast.LENGTH_LONG).show();
            }
        };
        registerReceiver(receiver, new IntentFilter("com.interrcs.sdk.yourapp.broadcast.endpointlist_result"));

//        Intent intent = new Intent("interrcs.get-profile");
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        try {
//            UserInfoManager.getProfile(LoginState.getStartUser(this), user, pi);
//        } catch (RemoteException e) {
//            Log.e(TAG, "get profile error", e);
//        }
//
//        receiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                context.unregisterReceiver(this);
//                UserProfileResult result = BroadcastActions.getResult(intent);
//                tvResult.setText(JsonUtils.toJson(result));
//                Toast.makeText(context, "调用结果：" +JsonUtils.toJson(result),Toast.LENGTH_LONG).show();
//            }
//        };
//        registerReceiver(receiver, new IntentFilter("interrcs.get-profile"));
    }


    @Override
    public void onStop() {
        super.onStop();
        try {
            unregisterReceiver(receiver);
        }catch (Exception e) {}
    }
}
