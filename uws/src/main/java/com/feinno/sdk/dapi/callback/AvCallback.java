package com.feinno.sdk.dapi.callback;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.Callback;
import com.feinno.sdk.result.AVResult;
import com.feinno.sdk.session.AvSession;
import com.feinno.sdk.utils.LogUtil;

public class AvCallback implements Callback<AVResult> {
	public static final String TAG = "AvCallback";

	private Context context;
	private PendingIntent pendingIntent;

	public AvCallback(Context context, PendingIntent pendingIntent) {
		this.context = context;
		this.pendingIntent = pendingIntent;
	}

	@Override
	public void run(AVResult s) {
		LogUtil.i(TAG, "run");
		if (s == null) {
			LogUtil.i(TAG, "av result is null, return now");
			return;
		} else {
			LogUtil.i(TAG, "result: sessionId:" + s.sessionId + ", op:" + s.op + ", errorCode:" + s.errorCode);
		}
		if (pendingIntent == null) {
			LogUtil.i(TAG, "pendingIntent is null, send broadcast with action " + BroadcastActions.ACTION_AV_CALL_OUT);
			Intent intent = new Intent(BroadcastActions.ACTION_AV_CALL_OUT);
			Bundle bundle = new Bundle();
			bundle.putParcelable(BroadcastActions.EXTRA_SESSION, s);
			intent.putExtras(bundle);
			context.sendBroadcast(intent);
		} else {
			LogUtil.i(TAG, "pendingIntent is not null");
			try {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putParcelable(BroadcastActions.EXTRA_SESSION, s);
				intent.putExtras(bundle);
				pendingIntent.send(context, 0, intent);
			} catch (Exception e) {
				LogUtil.e(TAG, e);
			}
		}

	}
}
