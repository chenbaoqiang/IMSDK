package com.feinno.sdk.dapi;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.feinno.sdk.utils.LogUtil;

public class RemoveNotificationService extends Service {
    private static String TAG = RemoveNotificationService.class.getSimpleName();

    public static String KEY_NOTIFICATION_ID = "KEY_NOTIFICATION_ID";

	@Override
	public IBinder onBind(Intent intent) {
        LogUtil.i(TAG, "onBind()");
		return null;
	}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "onStartCommand()");
        if (intent != null) {
            Notification notify = RCSWorkingService.createNotification(getApplicationContext());
            notify.setLatestEventInfo(this, null, null,
                    PendingIntent.getActivity(this, 0, new Intent(), 0));
            int notificationId = intent.getIntExtra(KEY_NOTIFICATION_ID, 0);
            startForeground(notificationId, notify);
            stopForeground(true);
            LogUtil.i(TAG, "onStartCommand() remove notification, notiId:" + notificationId);
        }
        this.stopSelf(startId);
        // 返回后服务可以退出了
        return Service.START_NOT_STICKY;
    }

    @Override
	public void onCreate() {
        LogUtil.i(TAG, "onCreate()");
		super.onCreate();
	}

    @Override
    public void onDestroy() {
        LogUtil.i(TAG, "onDestroy()");
        super.onDestroy();
    }
}
