package com.feinno.sdk.enums;

/**
 * 客户端在线状态枚举
 */
public enum PresenceEnum {
    /**
     * 隐身或不在线
     */
    OFFLINE(0),
    /**
     * 在线
     */
    ONLINE(1),
    /**
     * 离开
     */
    LEAVE(2),
    /**
     * 忙碌
     */
    BUZY(3),
    /**
     * push在线
     */
    PUSHONLINE(9);

    private int value;
    private PresenceEnum(int value){
        this.value = value;
    }
    public int value(){
        return this.value;
    }
    public static PresenceEnum fromInt(int intValue){
        switch (intValue){
            case 0:
                return OFFLINE;
            case 1:
                return ONLINE;
            case 2:
                return LEAVE;
            case 3:
                return BUZY;
            case 9:
                return PUSHONLINE;
            default:
                return null;
        }
    }

    @Override
    public String toString(){
        return String.valueOf(this.value);
    }
}
