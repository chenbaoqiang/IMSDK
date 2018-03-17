package com.feinno.sdk.enums;

/**
 * 表示登录状态变化
 */
public enum LoginStates {
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
     * 断开与服务器的网络连接(SDK会尝试自动重连)
     */
    DISCONNECTED(6),

    /**
     * 已重连接上服务器
     */
    CONNECTED(7),

    /**
     * 被设备管理删除(删密码)
     */
    RESETPWD(8),

    /**
     * 无理由强制下线
     */
    FORCEOFFLINE(100);

    private int nCode;

    private LoginStates(int code) {
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
    public static LoginStates fromInt(int v) {
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
                return DISCONNECTED;
            case 7:
                return CONNECTED;
            case 100:
                return FORCEOFFLINE;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.nCode);
    }
}

