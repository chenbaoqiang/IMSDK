package com.interrcs.uwsdemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.feinno.sdk.SdkException;
import com.feinno.sdk.dapi.RCSManager;
import com.interrcs.uwsdemo.data.MyDbHelper;

import java.io.File;
import java.util.List;

public class MyApp extends Application{

    public static String TAG = "MyApp";
    public static final String NUMBER_SEPARATOR = ",";

    public static Application sApp;
    private static Handler sUIHandler;
    private static Handler sAsyncHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App.onCreate()");
        String processName = getProcessName();
        if(processName.equals(getPackageName())) {
            //启动 AIDL 服务
            RCSManager.startSvc(this, null, false);
            Log.d(TAG, "rcs service started!");

            //初始化数据库
            MyDbHelper.instance(this);
        }

        sApp = this;
        sUIHandler = new Handler(getMainLooper());
        HandlerThread th = new HandlerThread("AsyncHandler");
        th.start();
        sAsyncHandler = new Handler(th.getLooper());
    }

    public static String getStorageFilePath(String subdir) throws Exception {
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File sdcard = Environment.getExternalStorageDirectory();
                if (sdcard == null) {
                    // TODO: get another directory
                    throw new Exception("There is no external storage in this phone");
                }
                File storageDirectory = new File(sdcard, "URCS/" + subdir);
                if (!storageDirectory.exists()) {
                    if (!storageDirectory.mkdirs()) {
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

    public static String getSysPath(String subdir) throws Exception {
        try {
            String dir = getApp().getApplicationInfo().dataDir;
            File fileDir = new File(dir, "URCS/sys/" + subdir);
            fileDir.mkdirs();
            return fileDir.toString();
        } catch(Exception ex) {
              throw ex;
        }
    }


    public static Application getApp() {
        return sApp;
    }

    public static Handler uiHandler() {
        return sUIHandler;
    }

    public static Handler asyncHandler() {
        return sAsyncHandler;
    }

    private String _processName;
    public String getProcessName() {
        if(_processName == null) {
            int pid = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appInfos = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : appInfos) {
                if (appProcess.pid == pid) {
                    _processName = appProcess.processName;
                }
            }
            if(_processName == null){
                _processName = "";
            }
        }

        return _processName;
    }
}
