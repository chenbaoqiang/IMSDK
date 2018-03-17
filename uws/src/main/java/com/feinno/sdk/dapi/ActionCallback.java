package com.feinno.sdk.dapi;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.feinno.sdk.Callback;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.LogUtil;

public class ActionCallback<T extends ActionResult> implements Callback<T> {
    Context mContext;
    PendingIntent mIntent;
    String tag;
    String action;

    public ActionCallback(Context ctx, PendingIntent resultIntent, String action, String tag) {
        mContext = ctx;
        mIntent = resultIntent;
        this.tag = tag;
        this.action = action;
    }


    @Override
    public void run(T result) {
        LogUtil.i(tag, "run");
        if (result == null) {
            LogUtil.i(tag, "result is null, return now");
            return;
        } else {
            LogUtil.i(tag, "result id = " + result.id);
        }

        LogUtil.i(tag, "send call back intent");
        sendIntent(result, mIntent, action);
    }


    private void sendIntent(T result, PendingIntent pendingIntent, String action) {
        if (pendingIntent != null) {
            LogUtil.i(tag, "pendingIntent is not null");
            try {
                //MUCH Thanks to:https://code.google.com/p/android/issues/detail?id=6822#c5
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.setClassLoader(getClass().getClassLoader());
                bundle.putParcelable(com.feinno.sdk.BroadcastActions.EXTRA_RESULT, result);
                intent.putExtra("_bundle", bundle);
                pendingIntent.send(mContext, 0, intent);
            } catch (Exception e) {
                LogUtil.e(tag, e);
            }
        } else {
            LogUtil.i(tag, "pendingIntent is null, send broadcast with action " + action);
            Intent intent = new Intent(action);
            Bundle bundle = new Bundle();
            bundle.setClassLoader(result.getClass().getClassLoader());
            bundle.putParcelable(com.feinno.sdk.BroadcastActions.EXTRA_RESULT, result);
            intent.putExtra("_bundle", bundle);
            mContext.sendBroadcast(intent);
        }
    }
}
