package com.interrcs.sdk;

import android.util.Log;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class RcsState {
    private static String TAG = "RcsState";
    private static ConcurrentHashMap<Long, RcsState> mStates = new ConcurrentHashMap<>();
    private long _statePtr;
    
    private String mNumber;
    private HashMap<Long, Callback> mCallbacks;
    private HashMap<Long, String> mUnmatchedCallback;
    private SdkAlarm sdkAlarm;

    public RcsState(long statePtr, String number){
        if(number == null){
            throw new IllegalArgumentException("number cannot be null.");
        }
        _statePtr = statePtr;
        mNumber = number;
        mCallbacks = new HashMap<>();
        mUnmatchedCallback = new HashMap<>();
        mStates.put(statePtr, this);
    }
    
    public static RcsState getState(long statePtr){
        return mStates.get(statePtr);
    }
    
    public long statePtr(){
        return _statePtr;
    }
    
    public String getNumber(){
        return mNumber;
    }

    public void setSdkAlarm(SdkAlarm obj){
        sdkAlarm=obj;
    }
    //public void init(Context context, SdkConf conf, IListenerProvider cp){
//        
//    }

    public long startState(){
        return RcsApi.startState(this);
    }

    public synchronized void addCallback(long sid, Callback callback){
        // 可能应答已经回来了
        String result = mUnmatchedCallback.remove(sid);
        if(result != null){
            processCallback(result, callback);
        }else{
            mCallbacks.put(sid, callback);
        }
    }

    public synchronized void dispatchCallback(String funcname, String result){
        Log.d(TAG, "func:" + funcname + " result:" + result);
        try {
            // TODO: 临时， 应该根据funcname 解析json
            JSONObject json = new JSONObject(result);
            long sid = json.getLong("id");

            Callback callback = mCallbacks.remove(sid);
            if(callback == null){
                mUnmatchedCallback.put(sid, result);
            }else{
                processCallback(result, callback);
            }
        }catch (Exception ex){
            //Log.d(TAG, "callback failed", ex);
            ex.printStackTrace();
        }
    }

    public synchronized void dispatchAlarm(String funcname, String json){
        Log.d(TAG, "alarm func:" + funcname + " json:" + json);
        switch (funcname){
            case "set":
                if(sdkAlarm !=null){
                    sdkAlarm.set(json);
                }
                break;
            case "setRepeating":
                if(sdkAlarm !=null){
                    sdkAlarm.setRepeating(json);
                }
                break;
            case "cancel":
                if(sdkAlarm !=null){
                    sdkAlarm.cancel(json);
                }
                break;
            default:
                break;
        }
    }
    
    
    public void dispatchListener(String listenerName, String json){
        try{
            doDispatchListener(listenerName, json);
        }catch (Exception ex){
            Log.e(TAG, "dispatch error", ex);
        }
    }
    public void processCallback(String result, Callback callback){

    }
    
    public void doDispatchListener(String listenerName, String json){
        Log.d(TAG, "number = " + getNumber() + "name = " + listenerName + " json = " + json);

        // TODO set LinstenerProvider
    }
    
    @Override
    public String toString() {
        return "sdkstate number:=" + mNumber;
    }

    @Override
    protected void finalize() throws Throwable {
        mStates.remove(_statePtr);
        super.finalize();
    }
}
