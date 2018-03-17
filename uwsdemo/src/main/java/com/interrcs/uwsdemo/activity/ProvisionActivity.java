package com.interrcs.uwsdemo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.dapi.LoginManager;
import com.feinno.sdk.dapi.ProvisionManager;
import com.feinno.sdk.dapi.RCSManager;
import com.feinno.sdk.dapi.RcsConfig;
import com.feinno.sdk.result.GetSmsResult;
import com.feinno.sdk.result.LoginResult;
import com.feinno.sdk.result.ProvisionResult;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.MyApp;
import com.interrcs.uwsdemo.R;

import java.util.UUID;

/**
 * 开通服务，流程：
 * 1. RCSManager.startUser()，启动一个SDK的用户。
 * 2. 获取短信验证码。
 * 3. 注册开通服务。
 * 4. 登陆服务器。
 * 5. 启动MainActivity。
 */
public class ProvisionActivity extends Activity {

    public static final String TAG = "ProvisionActivity";
    EditText mEdNumber;
    EditText mCode;
    Button mBtnProv;
    Button mBtnGetSms;
    BroadcastReceiver mReceiver;
    String mCurrentNumber;
    String mOtp;
    String mSessionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_provision);

        mBtnProv = (Button)findViewById(R.id.btn_prov);
        mCode = (EditText)findViewById(R.id.ed_smscode);
        mEdNumber= (EditText)findViewById(R.id.ed_number);
        mBtnGetSms = (Button)findViewById(R.id.btn_getsms);

        mBtnGetSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mEdNumber.getText().toString();
                mCurrentNumber = number;
                try {
                    String storagePath = MyApp.getStorageFilePath(mCurrentNumber);
                    String sysPath = MyApp.getSysPath(mCurrentNumber);
                    // 0. 环境配置
                    RcsConfig config = new RcsConfig();
                    
                    config.setMqttNav("http://117.107.139.42:8080"); // 环境导航地址
                    config.setCryptoType(""); // 关闭协议加密
                    RCSManager.initConfig(sysPath, config);
                    
                    // 1. RCSManager.startUser()
                    // userid 未知 使用 UnRegisteredStartUser 启动一个未注册SDK的用户。
                    boolean succ =  RCSManager.startUser(LoginState.UnRegisteredStartUser, "", storagePath, "Feinno", "1.0", sysPath, "0");

                    if (!succ) {
                        Log.e(TAG, "start user failed");
                        Toast.makeText(ProvisionActivity.this, "start user 失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "start user" + ":", e);
                    Toast.makeText(ProvisionActivity.this, "start user 失败", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, "start user" + ":", e);
                    Toast.makeText(ProvisionActivity.this, "start user 失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnProv.setEnabled(false);

        mBtnProv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3. 注册开通服务。
                try {
                    //生成一次性密码, 密码仅供当前设备后续登录使用
                    mOtp = UUID.randomUUID().toString();
                    ProvisionManager.provisionOtp(LoginState.UnRegisteredStartUser, mSessionId, mCurrentNumber, mCode.getText().toString(), mOtp, null);
                } catch (RemoteException e) {
                    Log.e(TAG, "getsmscode error:", e);
                    Toast.makeText(ProvisionActivity.this, "开通错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "receiver:" + intent.getAction());
                String action = intent.getAction();
                if(action.equals(BroadcastActions.ACTION_USER_STATE)) {
                    int state = intent.getExtras().getInt(BroadcastActions.EXTRA_USER_STATE);
                    Log.d(TAG, "user state:" + state);
                    if (state == BroadcastActions.USER_STATE_STARTED) {
                        try {
                            // 2. 获取短信验证码。
                            ProvisionManager.getSMSCode(LoginState.UnRegisteredStartUser, mEdNumber.getText().toString());
                        } catch (Exception e) {
                            Log.e(TAG, "getsmserror:", e);
                        }
                    }
                }
                else if (action.equals(BroadcastActions.ACTION_GETSMSCODE_RESULT)) {
                    GetSmsResult result = BroadcastActions.getResult(intent);

                    if (result.errorCode == GetSmsResult.OK) {
                        mBtnProv.setEnabled(true);
                        mSessionId = result.sessionId;
                    }
                    else {
                        Toast.makeText(ProvisionActivity.this, "获取验证码错误:" + result.errorCode + "," + result.errorExtra,
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (action.equals(BroadcastActions.ACTION_PROVISION_RESULT)) {
                    ProvisionResult result = BroadcastActions.getResult(intent);
                    if(result.errorCode == ProvisionResult.OK) {
                        Toast.makeText(context, "注册成功，尝试登录", Toast.LENGTH_SHORT).show();
                        LoginState.setPassword(context, mOtp);
                        LoginState.setUserName(context, mCurrentNumber);
                        LoginState.setUserId(context, result.userId);
                        try {
                            // 4. 注册激活成功后，这里先把未注册SDK实例停掉 （如果provision 前userid已知，就可以不需要先stop再重新start SDK 用户了）
                            RCSManager.stopUser(LoginState.UnRegisteredStartUser);

                            // 5. 启动SDK用户， 启动成功后登录
                            boolean succ =  RCSManager.startUser(LoginState.getStartUser(context), "", MyApp.getStorageFilePath(mCurrentNumber), "Feinno", "1.0", MyApp.getSysPath(mCurrentNumber), "0");

                            if (!succ) {
                                Log.e(TAG, "start user failed");
                                Toast.makeText(ProvisionActivity.this, "start user 失败", Toast.LENGTH_SHORT).show();
                            }else {
                                LoginManager.login(LoginState.getStartUser(context), mOtp, null);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "login error:", e);
                            Toast.makeText(context, "登录异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Log.d(TAG, "provision result:" + result.errorCode);
                } else if (action.equals(BroadcastActions.ACTION_LOGIN_RESULT)) {
                    LoginResult result = BroadcastActions.getResult(intent);
                    if(result.errorCode == LoginResult.OK) {
                        Toast.makeText(context, "登录成功！", Toast.LENGTH_SHORT).show();
                        // 5. 启动MainActivity。
                        startActivity(new Intent(context, MainActivity.class));
                    } else {
                        Toast.makeText(ProvisionActivity.this, "登录错误:" + result.errorCode + "," + result.errorExtra,
                                Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "login result:" + result.errorCode);
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastActions.ACTION_USER_STATE);
        filter.addAction(BroadcastActions.ACTION_GETSMSCODE_RESULT);
        filter.addAction(BroadcastActions.ACTION_PROVISION_RESULT);
        filter.addAction(BroadcastActions.ACTION_LOGIN_RESULT);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }
}
