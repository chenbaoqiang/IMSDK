package com.feinno.sdk.dapi;

import android.app.PendingIntent;

import android.os.RemoteException;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.result.CapsResult;
import com.feinno.sdk.utils.LogUtil;

/**
 * 能力查询接口
 */
public class CapsManager {
	private static final String TAG = "CapsManager";

	/**
	 * 获取用户能力
	 * @param owner 操作用户
	 * @param number 目标被查询号码
	 * @param pendingIntent 用于处理回调结果的PendingIntent
	 *               		通过intent.getExtras().getParcelable({@link BroadcastActions#EXTRA_RESULT})可以获取{@link CapsResult}对象。
	 *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_CAPS_RESULT}的广播，
	 *                      通过intent.getExtras().getParcelable({@link BroadcastActions#EXTRA_RESULT})可以获取{@link CapsResult}对象。
	 * @throws Exception
	 */
	public static void getCap(String owner, String number, PendingIntent pendingIntent) throws RemoteException{
		LogUtil.i(TAG, "getCap, owner = " + owner + ", number = " + number);
		WorkingServiceProxy.instance().getCap(owner, number, pendingIntent);
	}
}
