package com.feinno.sdk.dapi;

import android.app.Application;
import android.text.TextUtils;
import com.feinno.sdk.utils.LogUtil;

import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;

/**
 * 该类用于初始化 RCS Working Service 以及进行 SDK 管理的任务
 */
public class RCSManager {
	private static final String TAG = "RCSManager";

    /**
     * 设置SDK环境配置
     * @param sysPath SDK系统文件路径
     * @param conf 配置信息
     */
    public static void initConfig(String sysPath, RcsConfig conf) throws Exception {
        if(!sysPath.endsWith("/")){
            sysPath = sysPath + "/";
        }
        File f = new File(sysPath);
		if(!f.exists() || !f.isDirectory()) {
			throw  new IllegalArgumentException("path: " + sysPath + " does not exist");
		}

        String confStr = conf.toJsonString();
        String fpath = sysPath + "nav.conf";
        
        f = new File(fpath);
        FileWriter fw = new FileWriter(f);
        fw.write(confStr);
        fw.close();
        LogUtil.d(TAG, "initConfig " + fpath + " value:" + confStr);
    }
    
	/**
	 * 启动 RCSWorkingService
	 * @param application Application 上下文
	 * @param dmUrl [可选]，自动配置服务器地址，默认选择中国移动商用局
	 * @param autoAddUser 是否根据SIM卡情况自动添加 RCS 用户，默认为 false
	 */
	public static boolean startSvc(Application application, String dmUrl, boolean autoAddUser) {
		LogUtil.i(TAG, "startSvc");
		return WorkingServiceProxy.instance().startSvc(application, dmUrl, autoAddUser);
	}

    /**
     * 后台进程是否连接
     * @return
     */
    public static boolean isConnected() {
        LogUtil.i(TAG, "isConnected()");
        return WorkingServiceProxy.instance().isConnected();
    }

	/**
	 * 关闭 RCSWorkingService
	 * @param application Application 上下文
	 */
	public static void stopSvc(Application application) {
		LogUtil.i(TAG, "stopSvc");
		WorkingServiceProxy.instance().stopSvc(application);
	}

	/**
	 * 添加用户
	 * @param number 号码
	 * @param imsi IMSI, http://baike.baidu.com/item/imsi
	 * @param storage 富文本文件下载保存的绝对路径(注意: 不同用户使用不同的目录)
	 * @param clientVendor 客户端厂商
	 * @param clientVersion 客户端版本
     * @param sysPath SDK配置文件隐私数据保存的绝对路径(注意: 不同用户使用不同的目录)
     * @param appId 应用id，如果不区分应用可以使用 默认值: "0"
	 * @return 是否启动成功, 请确保启动成功再调用其他API
     * 
     * @throws Exception
     */
	public static boolean startUser(String number, String imsi, String storage, String clientVendor, String clientVersion, String sysPath, String appId) throws Exception {
		LogUtil.i(TAG, "startUser, " + TextUtils.join(",", new String[] {number, imsi, storage, clientVendor, clientVersion, sysPath, appId}));
		if(TextUtils.isEmpty(number) || TextUtils.isEmpty(storage) || TextUtils.isEmpty(clientVendor) || TextUtils.isEmpty(clientVersion)|| TextUtils.isEmpty(sysPath)) {
			throw new IllegalArgumentException("number, storage, clientVendor, clientVersion, sysPath cannot be empty");
		}

		if(TextUtils.isEmpty(appId)){
			appId = "0";
		}
		if(!storage.endsWith("/")) {
			storage = storage + "/";
		}

		if(!sysPath.endsWith("/")) {
			sysPath = sysPath + "/";
		}
		File f = new File(storage);
		if(!f.exists() || !f.isDirectory()) {
			throw  new IllegalArgumentException("path: " + storage + " does not exist");
		}

		f = new File(sysPath);
		if(!f.exists() || !f.isDirectory()) {
			throw  new IllegalArgumentException("path: " + sysPath + " does not exist");
		}


		return WorkingServiceProxy.instance().addUser(number, imsi, storage, clientVendor, clientVersion, sysPath, appId) > 0;
	}

	/**
	 * 删除用户
	 * @param number 要删除的用户的电话号码
	 * @return 是否删除成功
	 */
	public static boolean stopUser(String number) throws Exception {
		LogUtil.i(TAG, "stopUser, number = " + number);
		WorkingServiceProxy.instance().stop(number);
		return WorkingServiceProxy.instance().removeUser(number);
	}

	/**
	 * 获取当前正在运行的用户
	 * @return 前正在运行的用户的电话号码
	 */
	public static String[] getUsers() throws Exception {
		LogUtil.i(TAG, "getUsers");
		return WorkingServiceProxy.instance().getAllUsers();
	}

	/**
	 * 应用回到前台调用，确保remote service可用
	 * @param owner
	 * @throws Exception
     */
	public  static void keepAlive(String owner) throws Exception{
		LogUtil.i(TAG, "keepalvie");
		WorkingServiceProxy.instance().keepAlive(owner);
	}
}
