//Generated. DO NOT modify.
package com.feinno.sdk.enums;

/**
 * 客户端类型
 */
public enum ClientType {
    /**
     * Unknown
     */
    UNKNOWN(0),
    /**
     * Android
     */
    ANDROID(1),
    /**
     * iPhone
     */
    IPHONE(2),
    /**
     * PC
     */
    PC(3),
    /**
     * WEB
     */
    WEB(4),
    /**
     * IPad
     */
    IPAD(5),
    /**
     * Mac
     */
    MAC(6),
    /**
     * OpenApi
     */
    OPENAPI(100);

    private int nCode;

    private ClientType(int code) {
        this.nCode = code;
    }

    public int value() {
        return this.nCode;
    }

    /**
     * 将整形值转换为枚举值
     *
     * @param v 整型值
     * @return ChatType枚举值
     */
    public static ClientType fromInt(int v) {
        switch (v) {
            case 0:
                return UNKNOWN;
            case 1:
                return ANDROID;
            case 2:
                return IPHONE;
            case 3:
                return PC;
            case 4:
                return WEB;
            case 5:
                return IPAD;
            case 6:
                return MAC;
            case 100:
                return OPENAPI;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.nCode);
    }
}
