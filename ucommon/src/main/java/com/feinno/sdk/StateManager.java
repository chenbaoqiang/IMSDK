package com.feinno.sdk;

public class StateManager {
	private static final String TAG = "StateManager";

	/**此Action用来发送有关RCSWorkingService的启动与停止状态的广播*/
	public static final String ACTION_SERVICE_STATE = "com.interrcs.sdk.yourapp.broadcast.ACTION_SERVICE_STATE";
	/**广播结果的key参数*/
	public static final String EXTRA_SERVICE_STATE = "extra_service_state";
	/**RCSWorkingService启动*/
	public static final int SERVICE_STATE_START = 1;
	/**RCSWorkingService停止*/
	public static final int SERVICE_STATE_STOP = 2;

	/**此Action用来发送有关RCS SDK的启动与停止状态的广播*/
	public static final String ACTION_SDK_STATE = "com.interrcs.sdk.yourapp.broadcast.ACTION_SDK_STATE";
	/**广播结果的key参数*/
	public static final String EXTRA_SDK_STATE = "extra_sdk_state";
	/**RCS SDK启动*/
	public static final int SDK_STATE_START = 1;
	/**RCS SDK停止*/
	public static final int SDK_STATE_STOP = 2;

	/**此Action用来发送有关RCS用户状态的广播*/
	public static final String ACTION_USER_STATE = "com.interrcs.sdk.yourapp.broadcast.ACTION_USER_STATE";
	/**广播结果用户状态的key参数*/
	public static final String EXTRA_USER_NUMBER = "extra_user_state";
	/**广播结果用户号码的key参数*/
	public static final String EXTRA_USER_STATE = "extra_user_number";
	/**用户已添加到RCSWorkingService中，用户登出以后亦为此状态*/
	public static final int USER_STATE_STARTED = 1;
	/**该用户已经不再存在于RCSWorkingService中*/
	public static final int USER_STATE_NOT_STOPPED = 3;

	/**此Action用来发送与服务平台之间连接状态的广播*/
	public static final String ACTION_CONNECTION_STATE = "com.interrcs.sdk.yourapp.broadcast.ACTION_CONNECTION_STATE";
	/**广播结果连接状态的key参数*/
	public static final String EXTRA_CONNECTION_STATE = "extra_connection_state";
	/**广播结果的连接主机key参数*/
	public static final String EXTRA_CONNECTION_HOST = "extra_connection_host";
	/**广播结果的连接端口key参数*/
	public static final String EXTRA_CONNECTION_PORT = "extra_connection_port";
	/**已与服务平台断开连接*/
	public static final int CONNECTION_STATE_DISCONNECTED = 1;
	/**已连接到服务平台*/
	public static final int CONNECTION_STATE_CONNECTED = 2;

}