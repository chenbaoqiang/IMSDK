package com.feinno.sdk.utils;

import android.database.Cursor;

public class Closeables {
	private static final String TAG = "Closeables";

	public static void closeCursor(Cursor cursor){
		try {
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
			}
		} finally {

		}
	}
}

