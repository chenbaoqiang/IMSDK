package com.feinno.sdk.enums;

/**
 * 群组的事件类型
 */
public enum GroupEventType {
    /**被提升为管理员*/
    TRANSFER(1),
    /**被踢出群*/
    BOOTED(2),
    /**群被解散*/
    DISMISSED(3),
    /**被邀请入群*/
    INVITED(4),
    /** 群邀请处理结果 */
    CONFIRMED(5),
    /** 退出群 */
    QUIT(6),
    /** 讨论组名称修改 */
    SETNAME(7),
    /**申请加入群*/
    APPLY(8),
    /**审批处理通知*/
    APPLYRESULT(9);

    private int nCode;

    private GroupEventType(int code) {
        this.nCode = code;
    }

    public int value() { return this.nCode; }

    /**
     * 将整形值转换为枚举值
     * @param v 整型值
     * @return GroupOpEnum枚举值
     */
    public static GroupEventType fromInt(int v) {
        switch(v){
            case 1:
                return TRANSFER;
            case 2:
                return BOOTED;
            case 3:
                return DISMISSED;
            case 4:
                return INVITED;
            case 5:
                return CONFIRMED;
            case 6:
                return QUIT;
            case 7:
                return SETNAME;
            case 8:
                return APPLY;
            case 9:
                return APPLYRESULT;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}