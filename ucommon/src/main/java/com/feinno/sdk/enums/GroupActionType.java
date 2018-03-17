package com.feinno.sdk.enums;

/**
 * 群信息类型
 */
public enum GroupActionType {
    /**通知*/
    NOTIFY(1),
    /**被踢出群*/
    BOOTED(2),
    /**群被解散*/
    DISMISSED(3),
    /**被邀请入群*/
    INVITED(4);

    private int nCode;

    private GroupActionType(int code) {
        this.nCode = code;
    }

    public int value() { return this.nCode; }

    /**
     * 将整形值转换为枚举值
     * @param v 整型值
     * @return GroupOpEnum枚举值
     */
    public static GroupActionType fromInt(int v) {
        switch(v){
            case 1:
                return NOTIFY;
            case 2:
                return BOOTED;
            case 3:
                return DISMISSED;
            case 4:
                return INVITED;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}
