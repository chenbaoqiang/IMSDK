package com.feinno.sdk.enums;

/**
 * 用于描述自己其他终端的类型信息
 */
public enum DirectedType {
    /**非定向消息*/
    NONE(0),
    /**其他终端*/
    OTHER(1),
    /**PC终端*/
    PC(2);


    private int nCode;
    private DirectedType(int code) {
        this.nCode = code;
    }
    public int value() { return this.nCode; }

    /**
     * 根据整形值生成枚举值
     * @param v 整形值
     * @return 枚举值
     */
    public static DirectedType fromInt(int v) {
        switch(v){
            case 0:
                return NONE;
            case 1:
                return OTHER;
            case 2:
                return PC;
            default:
                return NONE;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}
