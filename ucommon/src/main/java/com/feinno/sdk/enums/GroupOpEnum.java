//Generated. DO NOT modify.
package com.feinno.sdk.enums;

/**
 * 群操作的枚举值
 */
public enum GroupOpEnum {
    /**
     * 邀请群成员
     */
    INVITE_MEMBER(1),
    /**
     * 创建群
     */
    CREATE_GROUP(2),
    /**
     * 加入群
     */
    JOIN_GROUP(3),
    /**
     * 退出群组
     */
    EXIT_GROUP(4),
    /**
     * 移除群成员
     */
    REMOVE_USER(5),
    /**
     * 转让群管理员
     */
    CHANGE_MANAGER(6),
    /**
     * 修改群名称
     */
    MODIFY_SUBJECT(7),
    /**
     * 修改群昵称
     */
    MODIFY_NICKNAME(8),
    /**
     * 订阅群列表
     */
    SUB_GROUPLIST(9),
    /**
     * 订阅群信息
     */
    SUB_GROUPINFO(10),
    /**
     * 删除群组
     */
    DELETE_GROUP(11),
    /**
     * 拒绝加入群组
     */
    REJECT_GROUP(12),
    /**
     * 修改群简介
     */
    MODIFY_INTRODUCE(13),
    /**
     * 修改群公告
     */
    MODIFY_BULLETIN(14),
    /**
     * 修改群邀请设置
     */
    MODIFY_INVITEFLAG(15),
    /**
     * 修改扩展信息
     */
    MODIFY_EXTRA_SETTING(16),

    /**
     * 群消息免打扰设置
     */
    MOFIY_DND(17),

    /**
     * 申请加入群
     */
    APPLY(18),

    /**
     * 审批申请加入
     */
    APPROVAL(19);

    private int nCode;

    private GroupOpEnum(int code) {
        this.nCode = code;
    }

    public int value() {
        return this.nCode;
    }

    /**
     * 将整形值转换为枚举值
     *
     * @param v 整型值
     * @return GroupOpEnum枚举值
     */
    public static GroupOpEnum fromInt(int v) {
        switch (v) {
            case 1:
                return INVITE_MEMBER;
            case 2:
                return CREATE_GROUP;
            case 3:
                return JOIN_GROUP;
            case 4:
                return EXIT_GROUP;
            case 5:
                return REMOVE_USER;
            case 6:
                return CHANGE_MANAGER;
            case 7:
                return MODIFY_SUBJECT;
            case 8:
                return MODIFY_NICKNAME;
            case 9:
                return SUB_GROUPLIST;
            case 10:
                return SUB_GROUPINFO;
            case 11:
                return DELETE_GROUP;
            case 12:
                return REJECT_GROUP;
            case 13:
                return MODIFY_INTRODUCE;
            case 14:
                return MODIFY_BULLETIN;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.nCode);
    }
}
