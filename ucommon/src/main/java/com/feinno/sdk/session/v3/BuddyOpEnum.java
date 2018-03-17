package com.feinno.sdk.session.v3;

/**
 * 好友业务相关操作的枚举值
 */
public enum BuddyOpEnum {
    /** 添加好友 */
    ADD_BUDDY(1),
    /** 删除好友 */
    DELETE_BUDDY(2),
    /** 备注好友昵称 */
    MEMO_BUDDY(3),
    /** 处理好友添加请求 */
    HANDLE_BUDDY_REQ(4),

    /** 同步好友列表*/
    SYNC_BUDDY_LIST(5),
    /** 被添加为好友 */
    ADDED_BUDDY(6),
    /** 添加好友处理结果通知 */
    REQ_HANDLED(7),
    /** 设置好友消息屏蔽 */
    DND_SET(8);

    private int nCode;

    private BuddyOpEnum(int code) {
        this.nCode = code;
    }

    public int value() { return this.nCode; }

    /**
     * 将整形值转换为枚举值
     * @param v 整型值
     * @return BuddyOpEnum 枚举
     */
    public static BuddyOpEnum fromInt(int v) {
        switch(v){
            case 6:
                return ADDED_BUDDY;
            case 7:
                return REQ_HANDLED;
            case 5:
                return SYNC_BUDDY_LIST;
            case 4:
                return HANDLE_BUDDY_REQ;
            case 2:
                return DELETE_BUDDY;
            case 1:
                return ADD_BUDDY;
            case 3:
                return MEMO_BUDDY;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}
