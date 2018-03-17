package com.feinno.sdk.utils;

import android.util.Log;

public class LogUtil {
	public  interface  ILogger {
		public void e(String tag, String v);
		public void e(String tag, String v, Throwable e);
		public void w(String tag, String v);
		public void i(String tag, String v);
		public void d(String tag, String v);
		public void v(String tag, String v);
		public int getLogLevel();
	}

	public static class MyLogger implements ILogger {
		public static int LEVEL = Log.VERBOSE;

		@Override
		public void e(String tag, String v) {
			if(LEVEL <= Log.ERROR){
				Log.e(tag, v );
			}
		}

		@Override
		public void e(String tag, String v, Throwable e)
		{
			if(LEVEL <= Log.ERROR) {
				Log.e(tag, v, e);
			}
		}

		@Override
		public void w(String tag, String v) {
			if(LEVEL <= Log.WARN) {
				Log.w(tag, v);
			}
		}

		@Override
		public void i(String tag, String v) {
			if(LEVEL <= Log.INFO) {
				Log.i(tag, v);
			}
		}

		@Override
		public void d(String tag, String v) {
			if(LEVEL <= Log.DEBUG) {
				Log.d(tag, v);
			}
		}

		@Override
		public void v(String tag, String v) {
			if(LEVEL <= Log.VERBOSE) {
				Log.v(tag, v);
			}
		}

		@Override
		public int getLogLevel() {
			return LEVEL;
		}
	}

	public static ILogger Logger = new MyLogger();

	public static void v(String tag, String v) {
		Logger.v(tag, v);
	}

	public static void d(String tag, String v) {
		Logger.d(tag, v);
	}

	public static void i(String tag, String v) {
		Logger.i(tag, v);
	}

	public static void w(String tag, String v) {
		Logger.w(tag, v);
	}

	public static void e(String tag, String v) {
		Logger.e(tag, v);
	}

	public static void e(String tag, Throwable e) {
		Logger.e(tag, "", e);
	}

	public static void e(String tag, String v, Throwable e) {
		Logger.e(tag, v, e);
	}

	public static int getLogLevel(){
		return Logger.getLogLevel();
	}
}