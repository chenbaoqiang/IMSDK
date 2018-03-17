package com.feinno.sdk.dapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.feinno.sdk.Listener;
import com.feinno.sdk.utils.JsonUtils;
import com.feinno.sdk.utils.LogUtil;

public class EventListener<T1 extends Parcelable> implements Listener<T1>{

    private String tag;
    private String action;

    private Context mContext;

    public EventListener(Context context, String action, String tag) {
        this.tag = tag;
        this.action = action;
        mContext = context;
    }

    @Override
    public void run(T1 session) {
        LogUtil.i(tag, "run");
        if (session == null) {
            LogUtil.i(tag, "session is null, return now");
            return;
        }

        sendIntent(session, action);
    }

    private void sendIntent(T1 session, String action) {
        LogUtil.i(tag, "send broadcast with action " + action);
        Intent intent = new Intent(action);
        Bundle bundle = new Bundle();
        LogUtil.i(tag, JsonUtils.toJson(session));
        bundle.putParcelable(com.feinno.sdk.BroadcastActions.EXTRA_SESSION, session);
        intent.putExtras(bundle);
        mContext.sendBroadcast(intent);
    }
}
