package com.feinno.sdk.dapi;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.RemoteException;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.result.LoginResult;
import com.feinno.sdk.result.LogoutResult;
import com.feinno.sdk.result.v3.UserStateResult;
import com.feinno.sdk.utils.LogUtil;

/**
 * 登录接口
 */
public class LoginManager {
	private static final String TAG = "LoginManager";


	/**
	 * 用户登录
	 * @param owner 当前用户
	 * @param password 密码
	 * @param intent 用于处理回调结果的PendingIntent
	 *               通过{@link BroadcastActions#getResult(Intent)}可以获取{@link LoginResult}对象。
	 *               如果为null，则会发送一条action为{@link BroadcastActions#ACTION_LOGIN_RESULT}的广播，
	 *               通过{@link BroadcastActions#getResult(Intent)}可以获取{@link LoginResult}对象。
	 * @throws Exception
	 */
	public static void login(String owner, String password, PendingIntent intent) throws Exception {
		login(owner, password, null, intent) ;
	}

	/**
	 * 用户登录
	 * @param owner 操作用户
	 * @param dmToken 非PS域下DM认证Token
	 * @param password 密码
	 * @param pendingIntent 用于处理回调结果的PendingIntent
	 *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link LoginResult}对象。
	 *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_LOGIN_RESULT}的广播，
	 *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link LoginResult}对象。
	 * @throws Exception
	 */
	public static void login(String owner, String password, String dmToken, PendingIntent pendingIntent) throws Exception {
		LogUtil.i(TAG, "login, owner = " + owner);
		WorkingServiceProxy.instance().login(owner, password, dmToken, pendingIntent);
	}

	/**
	 * 用户注销
	 * @param owner 操作用户
	 * @param pendingIntent 用于处理回调结果的PendingIntent
	 *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link LogoutResult}对象。
	 *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_LOGOUT_RESULT}的广播，
	 *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link LogoutResult}对象。
	 * @throws Exception
	 */
	public static void logout(String owner, PendingIntent pendingIntent) throws Exception {
		LogUtil.i(TAG, "logout, owner = " + owner);
		WorkingServiceProxy.instance().logout(owner, pendingIntent);
	}

	public static void stop(String owner) throws Exception {
		LogUtil.i(TAG, "stop, owner = " + owner);
		WorkingServiceProxy.instance().stop(owner);
	}

	/**
	 * 应用到前端,调用此接口
	 * 后台检查连接状态,并自动发起重连
	 * @param owner 操作用户
	 *
	 * 通过{@link BroadcastActions#getSession(Intent)}}可以获取{@link com.feinno.sdk.session.LoginStateSession}对象
	 *
	 * @throws Exception
	 */
	public static void doConnect(String owner) throws Exception{
		LogUtil.i(TAG, "doConnect, owner = " + owner);
		WorkingServiceProxy.instance().doConnect(owner);
	}

	/**
	 * 应用到前端,调用此接口
	 * 查询User sdk后台状态
	 * @param owner 操作用户
	 *@param pendingIntent 用于处理回调结果的PendingIntent
	 * 如果为null，则会发送一条action为{@link BroadcastActions#ACTION_GET_USERSTATES_RESULT}的广播，
	 * 通过{@link BroadcastActions#getResult(Intent)} 可以获取{@link UserStateResult}对象
	 *
	 * @throws Exception
	 */
	public static void getUserStates(String owner, PendingIntent pendingIntent) throws Exception{
		LogUtil.i(TAG, "getUserStates, owner = " + owner);
		WorkingServiceProxy.instance().getUserStates(owner, pendingIntent);
	}

	public static boolean setDm(String owner,String ip, String port, String sslPort) {
		LogUtil.i(TAG, "setDm, owner = " + owner + ", ip:" + ip + ", port:" + port + ", sslport:" + sslPort);
		try {
			WorkingServiceProxy.instance().setDm(owner, ip, port, sslPort);
		} catch (RemoteException e) {
			LogUtil.e(TAG, e);
			return false;
		}
		return true;
	}
}
