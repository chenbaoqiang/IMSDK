package com.interrcs.uwsdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

/// 登录状态维护
public class LoginState {
    public static final String TAG = "LoginState";

    public static final int UNREGISTER = 1;
    public static final int REGISTERED = 2;
    public static final int LOGGED_IN = 3;
    
    // 未注册用户账号
    // 用于注册激活前UserId未知时
    public static final String UnRegisteredStartUser = "9999";

    public static void setPassword(Context context, String pwd) {
        SharedPreferences.Editor editor = context.getSharedPreferences("UWS", Context.MODE_MULTI_PROCESS).edit();
        editor.putString("pwd", pwd).commit();
    }

    public static String getPassword(Context context) {
        return context.getSharedPreferences("UWS", Context.MODE_MULTI_PROCESS).getString("pwd", null);
    }

    public static void setUserName(Context context, String user) {
        SharedPreferences.Editor editor = context.getSharedPreferences("UWS", Context.MODE_MULTI_PROCESS).edit();
        editor.putString("user", user).commit();
    }

    public static void setUserId(Context context, int userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences("UWS", Context.MODE_MULTI_PROCESS).edit();
        editor.putInt("userid", userId).commit();
    }

    public static String getUserName(Context context) {
        return context.getSharedPreferences("UWS", Context.MODE_MULTI_PROCESS).getString("user", null);
    }

    public static int getUserId(Context context) {
        return context.getSharedPreferences("UWS", Context.MODE_MULTI_PROCESS).getInt("userid", 0);
    }

    public static String getStartUser(Context context) {
        return String.valueOf(context.getSharedPreferences("UWS", Context.MODE_MULTI_PROCESS).getInt("userid", 0));
    }

    public static boolean isRegistered(Context context) {
        return !TextUtils.isEmpty(getUserName(context));
    }

    public static void logout(Context context) {
        Log.d(TAG, "logout");
        context.getSharedPreferences("UWS", Context.MODE_MULTI_PROCESS).edit().clear().commit();
    }

}

