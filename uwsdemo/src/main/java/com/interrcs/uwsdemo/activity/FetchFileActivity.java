package com.interrcs.uwsdemo.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.args.FetchFileMessageArg;
import com.feinno.sdk.dapi.RCSMessageManager;
import com.feinno.sdk.enums.ErrorCode;
import com.feinno.sdk.result.FetchFileResult;
import com.feinno.sdk.result.MessageResult;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;

import java.util.UUID;


public class FetchFileActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchfile);

        String msgId = UUID.randomUUID().toString();

        String transferId = "从收听到的FTMessageSession中取得的transferId";

        String filePath = "要存储到的本地路径, 包含文件名";

        int start = 0; // 开始下载的起始位置, 单位byte
        int size = 2048; // 文件的总大小, 单位byte

        FetchFileMessageArg args = new FetchFileMessageArg(msgId, transferId, filePath, start, size);



        String action = "interrcs.fetch-file";
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        // 开始下载文件
        try {
            RCSMessageManager.fetchFile(LoginState.getStartUser(getApplicationContext()), args, pi);
        } catch (Exception e) {
            Log.e("fetchfile", "fetch file error", e);
            Toast.makeText(getApplicationContext(), "获取文件错误", Toast.LENGTH_SHORT).show();
        }

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                MessageResult result = BroadcastActions.getResult(intent);
                if(result.errorCode == ErrorCode.OK.value()) {
                    Toast.makeText(getApplicationContext(), "成功下载文件", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "下载失败："+result.errorExtra,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        getApplicationContext().registerReceiver(receiver, new IntentFilter(action));
    }

    @Override
    public void onStart() {
        super.onStart();
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
    }
}
