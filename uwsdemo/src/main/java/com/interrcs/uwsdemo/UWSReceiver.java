package com.interrcs.uwsdemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.dapi.LoginManager;
import com.feinno.sdk.dapi.RCSManager;

/**
 * 接收来自SDK的广播，并分配给UWSReceiverService处理。
 */
public class UWSReceiver extends BroadcastReceiver{
    public static String TAG = "UWSReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "UWSReceiver.onReceive() action: " + action + ", extra:" + intent.getExtras() + ", intent: " + intent.toString());
        if (BroadcastActions.ACTION_SERVICE_STATE.equals(action)) {
            // remote 进程启动
            handleServiceState(context, intent);
        } else if (BroadcastActions.ACTION_SDK_STATE.equals(action)) {

        } else if (BroadcastActions.ACTION_USER_STATE.equals(action)) {
            //handleUserState(intent);
        } else if (BroadcastActions.ACTION_CONNECTION_STATE.equals(action)) {
            // SDK 与服务器连接状态变化
            int state = intent.getExtras().getInt(BroadcastActions.EXTRA_CONNECTION_STATE);
            boolean connected = state == BroadcastActions.CONNECTION_STATE_CONNECTED;
            //String connectState = (connected ? "connected" : "disconnected");
            //Toast.makeText(context, "UWS-Connected state:" + connectState, Toast.LENGTH_SHORT).show();
            if (!connected) {
                //LoginState.setRegistered(false);
            }
        } else {
            // 其他操作都放入 UWSReceiverService 进行操作
            intent.setClass(context, UWSReceiverService.class);
            beginStartingService(context, intent);
        }
    }

    private static final Object sLockObj = new Object();
    static PowerManager.WakeLock sUWSServiceWakeLock;

    /**
     * Start the service to process the current event notifications, acquiring
     * the wake lock before returning to ensure that the service will run.
     */
    public static void beginStartingService(Context context, Intent intent) {
        synchronized (sLockObj) {
            if (sUWSServiceWakeLock == null) {
                PowerManager pm =
                        (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                sUWSServiceWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                        "StartingAlertService");
                sUWSServiceWakeLock.setReferenceCounted(false);
            }
            sUWSServiceWakeLock.acquire();
            context.startService(intent);
        }
    }

    /**
     * Called back by the service when it has finished processing notifications,
     * releasing the wake lock if the service is now stopping.
     */
    public static void finishStartingService(Service service, int startId) {
        synchronized (sLockObj) {
            if (sUWSServiceWakeLock != null) {
                Log.v(TAG, "finishStartingService() startId: " + startId + " service: " + service.getClass().getName());
                if (service.stopSelfResult(startId)) {
                    sUWSServiceWakeLock.release();
                }
            }
        }
    }

    private void handleServiceState(final Context context, final Intent intent) {


        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            @Override
            public void run() {
                int state = intent.getExtras().getInt(BroadcastActions.EXTRA_SERVICE_STATE);
                boolean started = state == BroadcastActions.SERVICE_STATE_START;
                if (started) {

                    // 如果用户已经注册过，直接启动并登陆该用户
                    if (LoginState.isRegistered(context)) {
                        boolean userStarted = false;
                        try {
                            userStarted = RCSManager.startUser(LoginState.getStartUser(context), "", MyApp.getStorageFilePath(LoginState.getUserName(context)),
                                   "Feinno", "1.0", MyApp.getSysPath(LoginState.getUserName(context)),"0");
                        } catch (Exception e) {
                            Log.e(TAG, "startUser error", e);
                        }

                        if (!userStarted) {
                            Toast.makeText(context, "Failed to start user:" + LoginState.getStartUser(context), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "start user successful!", Toast.LENGTH_SHORT).show();

                            try {
                                LoginManager.login(LoginState.getStartUser(context), LoginState.getPassword(context), null);
                            } catch (Exception e) {
                                Log.e(TAG, "", e);
                            }
                        }
                    }
                } else {
                    Log.e(TAG, "cannot start UWS");
                    Toast.makeText(context, "Failed to start UWS", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "UWS-Service state:" + (started ? "started" : "stop"));
            }
        });

    }
}
