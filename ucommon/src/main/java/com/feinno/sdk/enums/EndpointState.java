package com.feinno.sdk.enums;

/**
 * 当前终端的在线状态表示
 */
public enum EndpointState {
    /**与服务器连接失败*/
    DISCONNECTED(1),
    /**已重新连接服务器*/
    CONNECTED(2),
    /**被其他终端踢下线*/
    BOOTED(3);


    private int nCode;

    private EndpointState(int code) {
        this.nCode = code;
    }

    public int value() { return this.nCode; }

    /**
     * 将整形值转换为枚举值
     * @param v 整型值
     * @return EndpointState枚举值
     */
    public static EndpointState fromInt(int v) {
        switch(v){
            case 1:
                return DISCONNECTED;
            case 2:
                return CONNECTED;
            case 3:
                return BOOTED;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}
