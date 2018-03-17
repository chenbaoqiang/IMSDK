//Generated. DO NOT modify.
package com.feinno.sdk.enums;

/**
 * 终端管理接口调用的结果枚举
 */
public enum EPManagerStates {
    OK(200),
    REQUEST_ERROR(400),
    INVALID_IDENTITY(401),
    INVALID_TOKEN(410),
    INVALID_SESSION_ID(420),
    SERVER_ERROR(500);

    private int nCode;
    private EPManagerStates(int code) {
        this.nCode = code;
    }
    public int value() { return this.nCode; }

    /**
     * 根据整形值生成枚举值
     * @param v 整形值
     * @return 枚举值
     */
    public static EPManagerStates fromInt(int v) {
        switch(v){
            case 200:
                return OK;
            case 400:
                return REQUEST_ERROR;
            case 401:
                return INVALID_IDENTITY;
            case 410:
                return INVALID_TOKEN;
            case 420:
                return INVALID_SESSION_ID;
            case 500:
                return SERVER_ERROR;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}
