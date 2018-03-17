package com.interrcs.uwsdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyFirstActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      Log.e("interrcs", "uws demo");
      TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

      Log.e("interrcs", telephonyManager.getDeviceId());
      Log.e("interrcs", Build.MANUFACTURER);
      Log.e("interrcs", Build.VERSION.RELEASE);
      Log.e("interrcs", Build.MODEL);
  }
}
