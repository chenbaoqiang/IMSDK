package com.feinno.sdk.enums;

/**
 * 会话类型
 */
public enum ConvType {
    /**二人*/
    SINGLE(1),
    /**群组*/
    GROUP(2),
    /**通知*/
    NOTIFICATION(3);

    private int nCode;
    private ConvType(int code) {
        this.nCode = code;
    }
    public int value() { return this.nCode; }

    /**
     * 根据整形值生成枚举值
     * @param v 整形值
     * @return 枚举值
     */
    public static ConvType fromInt(int v) {
        switch(v){
            case 1:
                return SINGLE;
            case 2:
                return GROUP;
            case 3:
                return NOTIFICATION;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}
