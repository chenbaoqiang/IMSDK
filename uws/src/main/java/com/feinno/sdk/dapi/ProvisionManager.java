package com.feinno.sdk.dapi;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.feinno.sdk.utils.LogUtil;

/**
 * 注册接口
 */
public class ProvisionManager {
    public static final String TAG = "ProvisionManager";

//	/**
//	 * 设置注册使用的url地址
//	 * @param getSMSCodeUrl 获取短信验证码使用的地址
//	 * @param provisionUrl 注册使用的地址
//	 */
//	public static void setProvisionUrl(String owner, String getSMSCodeUrl, String provisionUrl) throws Exception {
//		LogUtil.i(TAG, "setProvisionUrl, getSMSCodeUrl = " + getSMSCodeUrl + " provisionUrl = " + provisionUrl);
//		WorkingServiceProxy.instance().setProvisionUrl(owner, getSMSCodeUrl, provisionUrl);
//	}

    /**
     * 获取短信验证码
     * @Deprecated 请使用 getSMSCode(String owner, String number)
     *
     * @param owner 当前用户
     * @throws Exception 请使用 getSMSCode(String owner, String number) 
     */
    @Deprecated
    public static void getSMSCode(String owner) throws Exception {
        LogUtil.i(TAG, "getSMSCode, owner = " + owner);
        WorkingServiceProxy.instance().getSMSCode(owner);
    }

    /**
     * 获取短信验证码
     *
     * @param owner 当前用户
     * @param number 手机号码
     * @throws Exception
     */
    public static void getSMSCode(String owner, String number) throws Exception {
        LogUtil.i(TAG, "getSMSCode, owner = " + owner);
        WorkingServiceProxy.instance().getSMSCode(owner, number);
    }
    
//	/**
//	 * 获取短信验证码(目前Udialer在使用)
//	 * @param owner 用户号码
//	 * @throws Exception
//	 */
//	public static void getSMSCode2(String owner) throws Exception {
//		LogUtil.i(TAG, "getSMSCode, owner = " + owner);
//		WorkingServiceProxy.instance().getSMSCode2(owner);
//	}
//
//	/**
//	 * 注册(目前Udialer在使用)
//	 * @param owner 用户号码
//	 * @param sessionId session id
//	 * @param password 密码
//	 * @param smsCode 短信验证码
//	 * @param imsi imsi
//	 * @param imei imei
//	 * @param deviceModel 设备型号
//	 * @param deviceType 设备类型
//	 * @param deviceOS 设备操作系统
//	 * @param subHost 应用名
//	 * @throws Exception
//	 */
//	public static void provision2(String owner, int sessionId, String password, String smsCode, String imsi, String imei, String deviceModel, String deviceType, String deviceOS, String subHost) throws Exception {
//		LogUtil.i(TAG, "provision, owner = " + owner + ", smsCode = " + smsCode);
//		WorkingServiceProxy.instance().provision2(owner, sessionId, password, smsCode, imsi, imei, deviceModel, deviceType, deviceOS, subHost);
//	}


    /**
     * 使用短信验证码开通服务
     *
     * @param owner   当前用户
     * @param sessId  session id, 通过 getSmsCode 得到的
     * @param number  手机号码
     * @param smsCode 短信验证码
     * @param otp     当前设备一次性密码
     * @param intent  结果 Intent
     * @throws RemoteException
     */
    public static void provisionOtp(String owner, String sessId, String number, String smsCode, String otp, PendingIntent intent) throws RemoteException {
        LogUtil.i(TAG, "provisionOtp");
        WorkingServiceProxy.instance().provisionOtp(owner, sessId, number, smsCode, otp, intent);
    }

    /**
     * iot注册
     *
     * @param owner     用户名
     * @param password  密码
     * @param intent  结果 Intent
     * @throws Exception
     */
    public static void provisionIot(String owner, String password, PendingIntent intent) throws Exception {
        LogUtil.i(TAG, "provision");
        WorkingServiceProxy.instance().provisionIot(owner, password, intent);
    }

    /**
     * 注册
     * @Deprecated 请使用 provision(String owner, String number,  String password, PendingIntent intent) 
     *
     * @param owner     用户号码
     * @param password  密码
     * @param intent  结果 Intent
     * @throws Exception
     */
    @Deprecated
    public static void provision(String owner, String password, PendingIntent intent) throws Exception {
        LogUtil.i(TAG, "provision");
        WorkingServiceProxy.instance().provision(owner, password, intent);
    }

    /**
     * 注册
     *
     * @param owner     启动用户号码
     * @param number    激活者手机号码
     * @param password  密码
     * @param intent  结果 Intent
     * @throws Exception
     */
    public static void provision(String owner, String number,  String password, PendingIntent intent) throws Exception {
        LogUtil.i(TAG, "provision number " + number);
        WorkingServiceProxy.instance().provision(owner, number, password, intent);
    }
}
