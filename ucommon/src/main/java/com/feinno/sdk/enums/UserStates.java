package com.feinno.sdk.enums;


public enum UserStates {
    /**
     * sdk 未初始化 Android 无此状态
     */
//    NOINIT(1),
    /**
     * 未调用provision接口
     */
    NOPROVISION(2),
    /**
     * 未调用login接口
     */
    NOLOGIN(3),
    /**
     * sdk 断链,等待重连中
     */
    DISCONNECTED(4),
    /**
     * sdk 连接正常
     */
    CONNECTED(5);

    private int nCode;
    private UserStates(int code) {
        this.nCode = code;
    }
    public int value() { return this.nCode; }

    public static UserStates fromInt(int intValue){
        switch (intValue){

            case 2:
                return NOPROVISION;
            case 3:
                return NOLOGIN;
            case 4:
                return DISCONNECTED;
            case 5:
                return CONNECTED;
            default:
                return null;
        }
    }
}
