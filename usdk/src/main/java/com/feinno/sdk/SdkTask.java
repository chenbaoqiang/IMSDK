package com.feinno.sdk;


import android.text.TextUtils;
import com.feinno.sdk.utils.LogUtil;

import org.keplerproject.luajava.Helper;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

class SdkTask {
    private static String TAG = "SdkTask";

    public int id;
    public String s;
    public ISdkCallback cb;
    public Object cookie;
    public LuaState L;
    public Object[] params;
    public String moduleName;
    public String funcName;

    public boolean isCompleted;
    public String result;

	protected interface ISdkCallback {
		void run(String lo);
	}

    public SdkTask(LuaState L, String s) {
        this(L, 0, s, null);
    }

	public SdkTask(LuaState L, int id, String s) {
        this(L, id, s, null);
	}

    public SdkTask(LuaState L, int id, String s, Object cookie) {
        this.L = L;
        this.id = id;
        this.s = s;
        this.cookie = cookie;
    }

    public SdkTask(LuaState L, String moduleName, String funcName, int id, Object cookie, Object... params) {
        this.L = L;
        this.id = id;
        this.s = moduleName + ".call";
        this.cookie = cookie;
        this.params = params;
        this.moduleName = moduleName;
        this.funcName = funcName;
    }

    public void run(){
        try {
            if (TextUtils.isEmpty(moduleName) || TextUtils.isEmpty(funcName)) {
                result = Helper.evalLuaFunc(L, s);
            } else {
                result = Helper.evalLuaFunc(L, moduleName, funcName, params);
            }
        } catch (LuaException e) {
            LogUtil.e(TAG, "evalLuaFunc:" + this.funcName, e);
        }

        synchronized (this){
            isCompleted = true;
            this.notify();
        }

        if(cb != null){
            try {
                cb.run(result);
            }catch (Exception ex){
                LogUtil.e(TAG, ex);
            }
        }
    }
}
