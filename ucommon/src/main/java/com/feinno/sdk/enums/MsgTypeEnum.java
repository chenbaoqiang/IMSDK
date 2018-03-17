package com.feinno.sdk.enums;

/**
 * 消息类型
 */
public enum  MsgTypeEnum {
    Text(1),
    Image(2),
    Audio(3);

    private int nCode;

    private MsgTypeEnum(int code) {
        this.nCode = code;
    }

    public int getCode() {
        return nCode;
    }
}
