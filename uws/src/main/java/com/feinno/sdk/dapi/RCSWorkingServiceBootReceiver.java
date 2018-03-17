package com.feinno.sdk.dapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RCSWorkingServiceBootReceiver extends BroadcastReceiver {
    public static final String BOOT_ACTION = "com.interrcs.sdk.broadcast.BOOT_RCSWORKINGSERVICE";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootReceiver", "RCSWorkingServiceBootReceiver onReceive:" + intent.getAction());
        Intent rcsIntent = new Intent(context, RCSWorkingService.class);
        context.startService(rcsIntent);
    }
}
