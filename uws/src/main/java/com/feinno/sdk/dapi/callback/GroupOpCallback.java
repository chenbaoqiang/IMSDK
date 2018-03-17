package com.feinno.sdk.dapi.callback;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.Callback;
import com.feinno.sdk.session.GroupCBSession;
import com.feinno.sdk.utils.LogUtil;

public class GroupOpCallback implements Callback<GroupCBSession> {
	public static final String TAG = "GroupOpCallback";

	private Context mContext;
	private PendingIntent pendingIntent;

	public GroupOpCallback(Context ctx, PendingIntent intent) {
		this.mContext = ctx;
		this.pendingIntent = intent;
	}

	@Override
	public void run(GroupCBSession s) {
		LogUtil.i(TAG, "run");
		if (s == null) {
			LogUtil.i(TAG, "group cb session is null, return now");
			return;
		} else {
			LogUtil.i(TAG, "session id = " + s.id + ", session op = " + s.op);
		}
		if (pendingIntent != null) {
			LogUtil.i(TAG, "pendingIntent is not null");
			try {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putParcelable(BroadcastActions.EXTRA_SESSION, s);
				intent.putExtras(bundle);
				pendingIntent.send(mContext, 0, intent);
			} catch (Exception e) {
				LogUtil.e(TAG, e);
			}
		} else {
			LogUtil.i(TAG, "pendingIntent is null, send broadcast with action " + BroadcastActions.ACTION_GROUP_OP_RESULT);
			Intent intent = new Intent(BroadcastActions.ACTION_GROUP_OP_RESULT);
			Bundle bundle = new Bundle();
			bundle.putParcelable(BroadcastActions.EXTRA_SESSION, s);
			intent.putExtras(bundle);
			mContext.sendBroadcast(intent);
		}
	}
}
