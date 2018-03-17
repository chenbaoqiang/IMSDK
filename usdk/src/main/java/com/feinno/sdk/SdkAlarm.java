package com.feinno.sdk;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;

import com.feinno.sdk.utils.JsonUtils;
import com.feinno.sdk.utils.LogUtil;

import org.json.JSONObject;
import org.keplerproject.luajava.Helper;
import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SdkAlarm {
    private static String TAG = "SdkAlarm";
    private static String ACTION_PREFIX = "com.feinno.sdk.SdkAlarm.";
    private static String ALARM_MODULE_NAME = "nalarm";

    private Context context;
    private Sdk.SdkState state;
    private AlarmManager alarmMgr;

    private HashMap<String, BroadcastReceiver> receivers;
    private HashMap<String, SdkAlarmIntent> alarmIntents;

    public SdkAlarm(Sdk.SdkState state, Context context){
        this.state = state;
        this.context = context;
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmIntents = new HashMap<>();
        receivers = new HashMap<>();
    }

    public void registerFunctions() throws LuaException {
        Helper.evalLua(state.getLuaState(), ALARM_MODULE_NAME + " = " + ALARM_MODULE_NAME + " or {}");
        JavaFunction setFunc = new JavaFunction(state.getLuaState()) {
            @Override
            public int execute() throws LuaException {
                try {
                    JSONObject json = JsonUtils.fromJson(params());
                    set(json.getString("name"), json.getLong("delay"));
                }catch(Exception ex){
                    LogUtil.e(TAG, ex);
                }
                return 0;
            }
        };
        setFunc.register(ALARM_MODULE_NAME + ".set");

        JavaFunction setRepeatingFunc = new JavaFunction(state.getLuaState()) {
            @Override
            public int execute() throws LuaException {
                try {
                    JSONObject json = JsonUtils.fromJson(params());
                    setRepeating(json.getString("name"), json.getLong("interval"));
                }catch(Exception ex){
                    LogUtil.e(TAG, ex);
                }
                return 0;
            }
        };
        setRepeatingFunc.register(ALARM_MODULE_NAME + ".setRepeating");

        JavaFunction cancelFunc = new JavaFunction(state.getLuaState()) {
            @Override
            public int execute() throws LuaException {
                try {
                    cancel(params());
                }catch(Exception ex){
                    LogUtil.e(TAG, ex);
                }
                return 0;
            }
        };
        cancelFunc.register(ALARM_MODULE_NAME + ".cancel");
    }

    public synchronized void stop() {
        Iterator  iter = alarmIntents.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            cancel((String)entry.getKey());
        }

        iter = receivers.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            context.unregisterReceiver((BroadcastReceiver) entry.getValue());
        }
    }

    public synchronized void set(String alarmName, long delayMs){
        set(alarmName, delayMs, false);
    }

    public synchronized  void setRepeating(String alarmName, long IntervalMs){
        set(alarmName, IntervalMs, true);
    }

    public synchronized void cancel(String alarmName){
        SdkAlarmIntent intent = alarmIntents.get(alarmName);
        if (intent != null) {
            alarmMgr.cancel(intent.intent);
            alarmIntents.remove(alarmName);
        }
    }

    @SuppressLint("NewApi")
    private void set(String alarmName, long nextDelay, boolean repeat){
        registerReceiver(alarmName);
        cancel(alarmName);

        Intent intent = new Intent(ACTION_PREFIX.concat(alarmName));
        intent.putExtra("name", alarmName);

        long nextRealTime = SystemClock.elapsedRealtime() + nextDelay;
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmIntents.put(alarmName, new SdkAlarmIntent(alarmIntent, repeat, nextDelay, nextRealTime));

        if(Build.VERSION.SDK_INT >= 19){
            alarmMgr.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, nextRealTime, alarmIntent);
        }
        else {
            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, nextRealTime, alarmIntent);
        }
    }

    // If there is already an alarm for this Intent scheduled (with the equality of two intents
    // being defined by filterEquals(Intent)), then it will be removed and replaced by this one
    // if their action, data, type, class, and categories are the same. This does not compare
    // any extra data included in the intents
    private void registerReceiver(String name){
        if(receivers.get(name) == null) {
            IntentFilter intent = new IntentFilter(ACTION_PREFIX.concat(name));
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    try {
                        String name = intent.getStringExtra("name");

                        SdkAlarmIntent lastIntent = removeIntent(name);
                        if(lastIntent == null) {
                            LogUtil.d(TAG, String.format("unknow alarm:%s", intent.getAction()));
                            return;
                        }

                        if (lastIntent.repeat && lastIntent.delay > 1000) {
                            setRepeating(name, lastIntent.delay);
                        }
                        long delta = SystemClock.elapsedRealtime() - lastIntent.tickTime;
                        LogUtil.d(TAG, String.format("receive Alarm:%s,action:%s, delay:%dms(delta:%d)",
                                name, intent.getAction(), lastIntent.delay, delta ));

                        SdkApi.onbell(state, name);
                        //BroadcastReceiver.onRecive 持有Wakelock保证不会休眠, 但执行完可能立即进入休眠
                        //SdkApi.onbell 只保证写到socket buffer, 不确保发出;嗯.. 让子弹再飞一会儿?
                        Thread.sleep(10);
                    }
                    catch(Exception ex){
                        LogUtil.e(TAG, ex);
                    }
                }
            };
            context.registerReceiver(receiver, intent);
            receivers.put(name, receiver);
        }
    }


    private synchronized SdkAlarmIntent removeIntent(String name){
        return alarmIntents.remove(name);
    }

    class SdkAlarmIntent {
        public SdkAlarmIntent(PendingIntent intent, boolean repeat, long delay, long tickTime){
            this.intent = intent;
            this.repeat = repeat;
            this.delay = delay;
            this.tickTime = tickTime;
        }
        public PendingIntent intent;
        public boolean repeat;
        public long delay;
        public long tickTime;
    }
}
