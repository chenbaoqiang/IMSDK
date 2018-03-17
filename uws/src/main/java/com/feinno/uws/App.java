
package com.feinno.uws;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.feinno.sdk.utils.NgccTextUtils;

public class App extends Application {
    public static final String LOG_TAG = "UWS/App";
    public static final boolean PRINT_LOG = true;

    public static App sApp = null;

    public static App app() {
        return sApp;
    }

    @Override
    public void onCreate() {
        sApp = this;
        super.onCreate();
    }

}
