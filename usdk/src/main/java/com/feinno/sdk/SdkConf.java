package com.feinno.sdk;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.AndroidCharacter;
import android.text.TextUtils;
import android.util.Log;

/**
 * 该类描述了SDK的配置信息
 */
public class SdkConf {
    public static final String URI_SCHEME_SIP = "sip";
    public static final String URI_SCHEME_TEL = "tel";

    private String[] URI_SCHEME = {
            URI_SCHEME_SIP,
            URI_SCHEME_TEL,
    };

    /**
     * 初始化 SDK 配置
     * @param context Context
     * @param number 号码
     * @param imsi SIM 卡 IMSI
     * @param storage 文件保存路径, 绝对路径,以 '/' 结尾
     * @param vendor 软件厂商
     * @param clientVersion 软件版本
     */
    public SdkConf(Context context, String number, String imsi, String storage, String vendor, String clientVersion, String sysPath, String appId) {
        this.number = number;
        mContext = context;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //imei = telephonyManager.getDeviceId();
        // android m 之后， imei 需要弹框提示用户才能获取， 改用androidid
        // http://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        imei = androidId;
        this.imsi = imsi;
        this.storage = storage;
        this.clientVendor = vendor;
        this.clientVersion = clientVersion;
        this.sysPath = sysPath;
        this.appId = appId;
    }

    private Context mContext;
    private String countryCode = "+86";
    private String storage;
    private boolean isCM = true;
    private String number;
    private String imsi;
    private String imei;
    private String uriScheme = URI_SCHEME_TEL;
    private String userAgent;
    private String dmUrl;
    /*RCS client 厂家*/
    private String clientVendor;
    /*RCS client 版本*/
    private String clientVersion;
    /*终端厂家*/
    private String terminalVendor;
    /*终端型号*/
    private String terminalMode;
    /*终端软件版本*/
    private String terminalSwVersion;

    /*用户配置数据保存路径*/
    private String sysPath;
    /*用户所属组织编号,用来隔离用户*/
    private String appId;

    /**
     * 获得SDK使用的国家码
     * @return 国家码
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 设置SDK使用的国家码
     * @param countryCode 国家码
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 判断SDK是否使用中国移动标准的RCS协议
     * @return 是否使用中国移动标准的RCS协议
     */
    public boolean isCM() {
        return isCM;
    }

    /**
     * 设置SDK是否使用中国移动标准的RCS协议
     * @param isCM 是否使用中国移动标准的RCS协议
     */
    public void setCM(boolean isCM) {
        this.isCM = isCM;
    }

    /**
     * 获得SDK的文件存储路径
     * @return 文件存储路径
     */
    public String getStorage() {
        return storage;
    }

    /**
     * 设置SDK的文件存储路径
     * @param storage 文件存储路径
     */
    public void setStorage(String storage) {
        this.storage = storage;
    }

    /**
     * 获取要登录的电话号码
     * @return 电话号码
     */
    public String getNumber() {
        return number;
    }

    /**
     * 设置要登录的电话号码
     * @param number 电话号码
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 获取IMSI
     * @return IMSI
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * 设置IMSI
     * @param imsi IMSI
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * 获取IMEI
     * @return IMEI
     */
    public String getImei() {
        return imei;
    }

    /**
     * 设置IMEI
     * @param imei IMEI
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * 获取所使用的URI类型
     * @return URI类型
     */
    public String getUriScheme() {
        return uriScheme;
    }

    /**
     * 设置所使用的URI类型
     * @param uriScheme URI类型
     */
    public void setUriScheme(String uriScheme) {
        this.uriScheme = uriScheme;
    }

    /**
     * 获取客户端代理名称
     * @return 客户端代理名称
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 设置客户端代理名称
     * @param userAgent 客户端代理名称
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDmUrl() {
        return dmUrl;
    }

    public void setDmUrl(String dmUrl) {
        this.dmUrl = dmUrl;
    }

    public String getClientVendor() {
        return clientVendor;
    }

    public void setClientVendor(String clientVendor) {
        this.clientVendor = clientVendor;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getTerminalVendor() {
        return Build.MANUFACTURER;
    }

    public String getTerminalMode() {
        return Build.MODEL;
    }

    public String getTerminalSwVersion() {
        return Build.VERSION.RELEASE;
    }


    public String getSysPath() {
        return sysPath;
    }

    public void setSysPath(String sysPath) {
        this.sysPath = sysPath;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String sysPath) {
        this.appId = appId;
    }
}
