package com.interrcs.sdk.demo;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.interrcs.sdk.RcsApi;
import com.interrcs.sdk.RcsState;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, RcsWorkingService.class));
        
        setContentView(R.layout.activity_main);
        
        
        try {
            //RcsState rcsState = test();

            TextView tv = new TextView(this);
           // tv.setText(String.valueOf(rcsState));
            setContentView(tv);
        }catch (Exception ex){
            ex.printStackTrace();
            Log.e(TAG, ex.getMessage());
        }
    }

    public RcsState test() throws Exception {
        RcsState rcsState1 = RcsApi.newState(this,"+8613800010002", "pc-imei-value", "", "", "", "windows NT", "5.0", "feinno",
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
}
