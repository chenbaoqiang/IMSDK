package com.interrcs.sdk.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.interrcs.sdk.RcsApi;
import com.interrcs.sdk.RcsState;

import java.io.File;
import java.util.UUID;

public class RcsWorkingService extends Service {
    private static final String TAG = "RCSWorkingService";
    private Handler mHandler = new Handler();
    
    private RcsState mState;

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mState = startSdk();
                    loopSendMessage();
                }catch (Exception ex){
                    Log.e(TAG, "", ex);
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind");

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    private RcsState startSdk() throws Exception {
        RcsState rcsState1 = RcsApi.newState(this, "+8613800010002", "pc-imei-value", "", "", "", "windows NT", "5.0", "feinno",
                "1.0", getOutputFilePath() + "/", "0", this.getFilesDir() + "/");
        Log.d(TAG, "onCreate: "+String.valueOf(rcsState1));
        RcsApi.getsmscode(rcsState1, "+8613800010002", null);
        Thread.sleep(500);
        RcsApi.provisionotp(rcsState1, "777777", "+8613800010002", "123456", "xxx", null);
        Thread.sleep(500);
        Log.d(TAG, "provision");
        RcsApi.login(rcsState1, "+8613800010002", "123456", null);
        Log.d(TAG, "login");
        return rcsState1;
    }

    public static String getOutputFilePath() throws Exception {
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File sdcard = Environment.getExternalStorageDirectory();
                if (sdcard == null) {
                    // TODO: get another directory
                    throw new Exception("There is no external storage in this phone");
                }
                File storageDirectory = new File(sdcard, "URCS");
                if (!storageDirectory.exists()) {
                    if (!storageDirectory.mkdir()) {
                        throw new Exception("failed to create directory");
                    }
                }
                return storageDirectory.getAbsolutePath();
            } else {
                throw new Exception("External storage is not mounted READ/WRITE.");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void loopSendMessage() {
        // int needReport, int isBurn, int directedType, int needReadReport, String extension, int contentType, 
        RcsApi.msgsendtext(mState, "+8610000", UUID.randomUUID().toString(), "hello", 0, 0, 0, 0, "", 1, null); 
        
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //loopSendMessage();
            }
        }, 1000);
    }
}