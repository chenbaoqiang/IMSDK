
package com.feinno.uws;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.feinno.sdk.dapi.RCSWorkingService;


public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String action_boot="android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(action_boot)){
            Log.d("============boot", "receive boot");
            Intent rcsIntent = new Intent(context, RCSWorkingService.class);
            context.startService(rcsIntent);
        }
    }
}
