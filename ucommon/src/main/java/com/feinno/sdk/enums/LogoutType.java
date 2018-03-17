package com.feinno.sdk.enums;

/**
 * 强制下线的类型
 */
public enum LogoutType {
    /**
     * 重复登陆(使用相同的ClientId)
     */
    REPEATLOGIN(1),
    /**
     * 销户
     */
    CLOSED(2),
    /**
     * 冻结
     */
    BLOCKED(3),

    /**
     * 被设备管理踢下线（需用户主动登陆）
     */
    BOOTED(4),

    /**
     * 被设备管理删除(删密码)
     */
    DELETE(5),

    /**
     * 被设备管理删除(删密码)
     */
    RESETPWD(6),

    /**
     * 无理由强制下线
     */
    FORCEOFFLINE(100),

    /**
     * 管理员远程删除(删密码)
     */
    MANAFORCEOFFLINE(101);

    private int nCode;

    private LogoutType(int code) {
        this.nCode = code;
    }

    public int value() {
        return this.nCode;
    }

    /**
     * 将整形值转换为枚举值
     *
     * @param v 整型值
     * @return EndpointType枚举值
     */
    public static LogoutType fromInt(int v) {
        switch (v) {
            case 1:
                return REPEATLOGIN;
            case 2:
                return CLOSED;
            case 3:
                return BLOCKED;
            case 4:
                return BOOTED;
            case 5:
                return DELETE;
            case 6:
                return RESETPWD;
            case 100:
                return FORCEOFFLINE;
            case 101:
                return MANAFORCEOFFLINE;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.nCode);
    }
}

