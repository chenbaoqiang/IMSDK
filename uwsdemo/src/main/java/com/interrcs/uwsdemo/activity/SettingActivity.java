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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.args.v3.UserInfoArg;
import com.feinno.sdk.dapi.v3.UserInfoManager;
import com.feinno.sdk.result.v3.UserInfo;
import com.feinno.sdk.result.v3.UserPortraitResult;
import com.feinno.sdk.result.v3.UserProfileResult;
import com.feinno.sdk.utils.JsonUtils;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;

import java.io.File;

public class SettingActivity extends Activity implements View.OnClickListener {

    public static String TAG = "SettingActivity";

    public final static String ACTION_SET_PROFILE = "com.interrcs.uwsdemo.activity.ACTION_SET_PROFILE";
    public final static String ACTION_SET_PORTRAIT = "com.interrcs.uwsdemo.activity.ACTION_SET_PORTRAIT";

    public final static String PORTRAIT_FILE_PATH = "/sdcard/portrait.png";

    TextView tvResult;
    Button setProfile;
    Button setPortrait;

    BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tvResult = (TextView)findViewById(R.id.tv_result);
        setProfile = (Button)findViewById(R.id.btn_set_profile);
        setPortrait = (Button)findViewById(R.id.btn_set_portrait);

        setProfile.setOnClickListener(this);
        setPortrait.setOnClickListener(this);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (ACTION_SET_PROFILE.equals(action)) {
                    UserProfileResult result = BroadcastActions.getResult(intent);
                    tvResult.setText(JsonUtils.toJson(result));
                    Log.d(TAG, "UserPortraitResult:" + JsonUtils.toJson(result));
                } else if (ACTION_SET_PORTRAIT.equals(action)) {
                    UserPortraitResult result = BroadcastActions.getResult(intent);
                    tvResult.setText(JsonUtils.toJson(result));
                    Log.d(TAG, "UserPortraitResult:" + JsonUtils.toJson(result));
                }
            }
        };
        IntentFilter filter = new IntentFilter(ACTION_SET_PORTRAIT);
        filter.addAction(ACTION_SET_PROFILE);
        registerReceiver(receiver, filter);
    }


    @Override
    public void onStop() {
        super.onStop();
        try {
            unregisterReceiver(receiver);
        }catch (Exception e) {}
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_set_profile) {
            Intent intent = new Intent(ACTION_SET_PROFILE);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            try {
                UserInfoArg arg = new UserInfoArg();
                arg.nickName = "融聚世界";
                arg.impresa = "为终端厂商提供性能最佳的融合通信RCS终端解决方案";
                arg.firstName = "融聚";
                arg.lastName = "新媒";
                arg.gender = 1;
                arg.email = "boss@interrcs.com";
                arg.birthday = "1989-1-1";
                UserInfoManager.setProfile(LoginState.getStartUser(this), arg, pi);
            } catch (RemoteException e) {
                Log.e(TAG, "set profile error", e);
            }
        } else if (id == R.id.btn_set_portrait) {
            if (!new File(PORTRAIT_FILE_PATH).exists()) {
                Toast.makeText(this, PORTRAIT_FILE_PATH + " 文件不存在", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(ACTION_SET_PORTRAIT);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            try {
                UserInfoManager.setPortrait(LoginState.getStartUser(this), PORTRAIT_FILE_PATH, pi);
            } catch (RemoteException e) {
                Log.e(TAG, "set portrait error", e);
            }
        }
    }
}
