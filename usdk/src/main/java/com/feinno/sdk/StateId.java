package com.feinno.sdk;

import android.content.Context;
import android.content.SharedPreferences;

class StateId {

	private static final int SAVE_POINT = 100;

	private static int lastSateId = 0;
	private static int lastSaveStateId = 0;

	private Context mContext;

	public StateId(Context context) {
		mContext = context;
		if(lastSateId == 0) {
			SharedPreferences preference = getPerference();
			lastSaveStateId = lastSateId = preference.getInt("id", 1000) + SAVE_POINT;
		}
	}

	public synchronized int getId() {
		lastSateId ++;
		if(lastSateId - lastSaveStateId >= SAVE_POINT) {
			SharedPreferences preference = getPerference();
			preference.edit().putInt("id", lastSateId).commit();
			lastSaveStateId = lastSateId;
		}
		return lastSateId;
	}

	private SharedPreferences getPerference(){
		return mContext.getSharedPreferences("stateid", Context.MODE_PRIVATE);
	}
}
