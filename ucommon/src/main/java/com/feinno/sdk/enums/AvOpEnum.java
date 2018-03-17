package com.feinno.sdk.enums;


public enum AvOpEnum {
    CALL(1),
    HUNG_UP(2),
    ANSWER(3),
    RING(4),
    HOLD(5),
    RESUME(6),
    INVITE_USER(7);

    private int nCode;

    private AvOpEnum(int code) {
        this.nCode = code;
    }

    public int value() {
        return this.nCode;
    }

    public static AvOpEnum fromInt(int v) {
        switch(v){
            case 1:
                return CALL;
            case 2:
                return HUNG_UP;
            case 3:
                return ANSWER;
            case 4:
                return RING;
            case 5:
                return HOLD;
            case 6:
                return RESUME;
            case 7:
                return INVITE_USER;
            default:
                return null;
        }
    }
}
