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
import com.feinno.sdk.args.ChatUri;
import com.feinno.sdk.args.TextMessageArg;
import com.feinno.sdk.dapi.RCSMessageManager;
import com.feinno.sdk.dapi.v3.BuddyManager;
import com.feinno.sdk.dapi.v3.PresenceManager;
import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.ContentType;
import com.feinno.sdk.enums.ErrorCode;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.result.MessageResult;
import com.feinno.sdk.result.v3.BklistGetResult;
import com.feinno.sdk.result.v3.BuddyResult;
import com.feinno.sdk.result.v3.DeviceListGetResult;
import com.feinno.sdk.result.v3.DeviceStatusGetResult;
import com.feinno.sdk.result.v3.GetPresenceResult;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;
import com.interrcs.uwsdemo.adapter.BuddyAdapter;
import com.interrcs.uwsdemo.data.BuddyContentProvider;
import com.google.gson.Gson;

import java.util.UUID;

// 好友列表界面
public class DeviceActivity extends ListActivity {
    public static final String TAG = "DeviceActivity";
    Button btnAddDevice;
    Button btnRemoveDevice;
    Button btnDeviceList;
    Button btnDeviceStatus;
    Button btnSendMsg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        String action = "interrcs.add-device";
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ActionResult result = BroadcastActions.getResult(intent);
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
        btnAddDevice = (Button) findViewById(R.id.btn_adddevice);
        btnAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "interrcs.add-device";
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                try {
                    BuddyManager.addDevice(LoginState.getStartUser(getApplicationContext()), "IOT:aabbccdd", "123456", pi);
                } catch (RemoteException e) {
                    Log.e(TAG, "add device error", e);
                    Toast.makeText(getApplicationContext(), "添加设备错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 添加好友按钮
        btnRemoveDevice = (Button) findViewById(R.id.btn_removedevice);
        btnRemoveDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "interrcs.remove-device";
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                try {
                    BuddyManager.removeDevice(LoginState.getStartUser(getApplicationContext()), "IOT:aabbccdd", pi);
                } catch (RemoteException e) {
                    Log.e(TAG, "remove device error", e);
                    Toast.makeText(getApplicationContext(), "添加设备错误", Toast.LENGTH_SHORT).show();
                }

                BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        ActionResult result = BroadcastActions.getResult(intent);
                        if(result.errorCode == ErrorCode.OK.value()) {
                            Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "删除失败："+result.errorExtra,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                getApplicationContext().registerReceiver(receiver, new IntentFilter(action));
            }
        });



        btnDeviceList = (Button) findViewById(R.id.btn_devicelist);
        btnDeviceList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String action1 = "interrcs.get-devicelist";
                Intent intent = new Intent(action1);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                try {
                    BuddyManager.getDeviceList(LoginState.getStartUser(getApplicationContext()), pi);
                } catch (RemoteException e) {
                    Log.e(TAG, "get device", e);
                    Toast.makeText(getApplicationContext(), "获取黑名单错误", Toast.LENGTH_SHORT).show();
                }
//
                BroadcastReceiver receiver1 = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        DeviceListGetResult result = (DeviceListGetResult) BroadcastActions.getResult(intent);
                        if(result.errorCode == ErrorCode.OK.value()) {
                            Toast.makeText(getApplicationContext(), "请求成功发送,devicelist: "+ result.id + " " + result.deviceList.length, Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "error："+result.errorExtra,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                getApplicationContext().registerReceiver(receiver1, new IntentFilter(action1));
            }
        });

        btnDeviceStatus = (Button) findViewById(R.id.btn_devicestatus);
        btnDeviceStatus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String action1 = "interrcs.get-devicestatus";
                Intent intent = new Intent(action1);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                try {
                    BuddyManager.getDeviceStatus(LoginState.getStartUser(getApplicationContext()),"IOT:AABBCCDD", pi);
                } catch (RemoteException e) {
                    Log.e(TAG, "get device", e);
                    Toast.makeText(getApplicationContext(), "获取黑名单错误", Toast.LENGTH_SHORT).show();
                }
//
                BroadcastReceiver receiver1 = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        DeviceStatusGetResult result = (DeviceStatusGetResult) BroadcastActions.getResult(intent);
                        if(result.errorCode == ErrorCode.OK.value()) {
                            Toast.makeText(getApplicationContext(), "请求成功发送,devicelist: "+ result.id + " " + result.relationList.length, Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "error："+result.errorExtra,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                getApplicationContext().registerReceiver(receiver1, new IntentFilter(action1));
            }
        });

        btnSendMsg = (Button) findViewById(R.id.btn_sendmsg);
        btnSendMsg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String action1 = "interrcs.sendmsg";
                Intent intent = new Intent(action1);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                try {
                    int chattype = ChatType.SINGLE.value();
                    ChatUri uri =  new ChatUri(ChatType.fromInt(chattype), "IOT:AABBCCDD" + "");
                    String msgId = UUID.randomUUID().toString();
                    TextMessageArg arg = new TextMessageArg(msgId, uri, "", "1",true);
                    RCSMessageManager.sendMessage(LoginState.getStartUser(getApplicationContext()), arg, pi);


                } catch (RemoteException e) {
                    Log.e(TAG, "sendmsg", e);
                    Toast.makeText(getApplicationContext(), "sendmsg", Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Log.e(TAG, "sendmsg", e);
                    Toast.makeText(getApplicationContext(), "sendmsg", Toast.LENGTH_SHORT).show();
                }
//
                BroadcastReceiver receiver1 = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        MessageResult result = (MessageResult) BroadcastActions.getResult(intent);
                        if(result.errorCode == ErrorCode.OK.value()) {
                            Toast.makeText(getApplicationContext(), "请求成功发送,: "+ result.id + " " + result.errorCode, Toast.LENGTH_SHORT).show();
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
    }



    @Override
    public void onStop() {
        super.onStop();
    }
}
