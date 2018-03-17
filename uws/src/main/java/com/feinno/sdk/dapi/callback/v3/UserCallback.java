package com.feinno.sdk.dapi.callback.v3;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.feinno.sdk.Callback;
import com.feinno.sdk.result.v3.UserResult;
import com.feinno.sdk.utils.LogUtil;

public class UserCallback implements Callback<UserResult> {

    public static final String TAG = "UserCallback";

    public Context mContext;
    public PendingIntent mIntent;

    public UserCallback(Context ctx, PendingIntent resultIntent) {
        mContext = ctx;
        mIntent = resultIntent;
    }

    @Override
    public void run(UserResult result) {
        LogUtil.i(TAG, "run");
        if (result == null) {
            LogUtil.i(TAG, "message result is null, return now");
            return;
        } else {
            LogUtil.i(TAG, "result id = " + result.id);
        }

        LogUtil.i(TAG, "message sent call back");
        sendIntent(result, mIntent, com.feinno.sdk.BroadcastActions.ACTION_USERINFO_OP);
    }


    private void sendIntent(UserResult result, PendingIntent pendingIntent, String action) {
        if (pendingIntent != null) {
            LogUtil.i(TAG, "pendingIntent is not null");
            try {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable(com.feinno.sdk.BroadcastActions.EXTRA_RESULT, result);
                intent.putExtras(bundle);
                pendingIntent.send(mContext, 0, intent);
            } catch (Exception e) {
                LogUtil.e(TAG, e);
            }
        } else {
            LogUtil.i(TAG, "pendingIntent is null, send broadcast with action " + action);
            Intent intent = new Intent(action);
            Bundle bundle = new Bundle();
            bundle.putParcelable(com.feinno.sdk.BroadcastActions.EXTRA_RESULT, result);
            intent.putExtras(bundle);
            mContext.sendBroadcast(intent);
        }
    }
}
