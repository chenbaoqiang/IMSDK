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
import android.widget.Toast;
import com.interrcs.uwsdemo.LoginState;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.session.*;
import com.feinno.sdk.dapi.LoginManager;
import com.feinno.sdk.enums.ErrorCode;
import com.feinno.sdk.result.v3.UserStateResult;
import com.interrcs.uwsdemo.R;

public class MainActivity extends Activity{

    Button btnBuddy;
    Button btnBuddyNtf;
    Button btnUser;
    Button btnSetting;
    Button btnFetchFile;
    Button btnGroupEvt;
    Button btnGroupList;
    Button btnCustomMessage;
    Button btnPresence;
    Button btnDevice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!LoginState.isRegistered(this)) {
            // 没有注册，进入注册界面
            startActivity(new Intent(this, ProvisionActivity.class));
            finish();
            return;
        }

        setContentView(com.interrcs.uwsdemo.R.layout.activity_main);

        btnBuddy = (Button) findViewById(R.id.btn_buddy);
        btnBuddyNtf = (Button) findViewById(R.id.btn_buddy_ntf);
        btnUser = (Button) findViewById(R.id.btn_user);
        btnSetting = (Button) findViewById(R.id.btn_setting_profile);
        btnFetchFile = (Button) findViewById(R.id.btn_fetch_file);
        btnGroupEvt = (Button)  findViewById(R.id.btn_group_evt);
        btnGroupList = (Button)  findViewById(R.id.btn_group_list);
        btnCustomMessage = (Button)  findViewById(R.id.btn_custom_message);
        btnPresence = (Button)  findViewById(R.id.btn_presence);
        btnDevice = (Button)  findViewById(R.id.btn_device);

        btnBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入好友列表界面
                startActivity(new Intent(MainActivity.this, BuddyActivity.class));
            }
        });

        btnBuddyNtf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入好友事件通知界面
                startActivity(new Intent(MainActivity.this, BuddyEventActivity.class));
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入个人资料
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        btnFetchFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FetchFileActivity.class));
            }
        });

        btnGroupEvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GroupEventActivity.class));
            }
        });

        btnGroupList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GroupActivity.class));
            }
        });

        btnCustomMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                intent.putExtra("chattype", 128);
                startActivity(intent);
            }
        });

        btnDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入设备列表界面
                startActivity(new Intent(MainActivity.this, DeviceActivity.class));
            }
        });


        btnPresence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "interrcs.sub-presence";
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                try {
                    LoginManager.getUserStates(LoginState.getStartUser(getApplicationContext()), pi);
                } catch (Exception e) {
                    Log.e("presence", "sub presence error", e);
                    Toast.makeText(getApplicationContext(), "订阅好友状态失败", Toast.LENGTH_SHORT).show();
                }

                BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        UserStateResult result = BroadcastActions.getResult(intent);
                        if (result.errorCode == ErrorCode.OK.value()) {
                            Toast.makeText(getApplicationContext(), "订阅好友状态成功发送", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "订阅好友状态失败：" + result.errorExtra,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                getApplicationContext().registerReceiver(receiver, new IntentFilter(action));
            }
        });

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LoginStateSession session = (LoginStateSession)BroadcastActions.getSession(intent);
                if(session!=null){
                    if(session.type == 7){
                        Toast.makeText(getApplicationContext(), "SDK重连成功", Toast.LENGTH_SHORT).show();
                    }else if(session.type == 6){
                        Toast.makeText(getApplicationContext(), "SDK断开连接", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        getApplicationContext().registerReceiver(receiver, new IntentFilter(BroadcastActions.ACTION_LOGIN_STATE_SESSION));
    }


}
