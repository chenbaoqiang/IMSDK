package com.feinno.sdk.enums;

/**
 * 终端管理操作类型的枚举
 */
public enum EndpointOPEnum {
    /**获取其他终端在线状态*/
    GET_EP_STATUS(1),
    /**提其他终端下线*/
    KICK_ENDPOINT(2),
    /**扫描二维码登录PC终端*/
    GEN_PC_TOKEN(3);


    private int nCode;
    private EndpointOPEnum(int code) {
        this.nCode = code;
    }
    public int value() { return this.nCode; }

    /**
     * 根据整形值生成枚举值
     * @param v 整形值
     * @return 枚举值
     */
    public static EndpointOPEnum fromInt(int v) {
        switch(v){
            case 1:
                return GET_EP_STATUS;
            case 2:
                return KICK_ENDPOINT;
            case 3:
                return GEN_PC_TOKEN;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}
