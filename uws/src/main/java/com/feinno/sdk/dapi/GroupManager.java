package com.feinno.sdk.dapi;

import android.app.PendingIntent;
import android.content.Intent;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.result.GroupOpResult;
import com.feinno.sdk.result.GroupShareInfoResult;
import com.feinno.sdk.session.GroupSession;
import com.feinno.sdk.utils.LogUtil;

/**
 * 群组操作接口
 */
public class GroupManager {
    private static final String TAG = "GroupManager";

    /**
     * 创建群组
     *
     * @param owner         操作用户
     * @param users         群组成员
     * @param subject       群组名称
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void createGroup(String owner, String[] users, String subject, PendingIntent pendingIntent) throws Exception {
        GroupManager.createGroup(owner, users, subject, "", "", pendingIntent);
    }

    /**
     * 创建群组
     *
     * @param owner         操作用户
     * @param users         群组成员
     * @param subject       群组名称
     * @param introduce     群简介
     * @param bulletin      群公告
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void createGroup(String owner, String[] users, String subject, String introduce, String bulletin, PendingIntent pendingIntent) throws Exception {
        GroupManager.createGroup(owner, users, subject, introduce, bulletin, 0, pendingIntent);
    }

    /**
     * 创建群组
     *
     * @param owner         操作用户
     * @param users         群组成员
     * @param subject       群组名称
     * @param introduce     群简介
     * @param bulletin      群公告
     * @param groupType     群类型 0:普通群(默认),3:讨论组
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void createGroup(String owner, String[] users, String subject, String introduce, String bulletin, int groupType, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "createGroup, owner = " + owner + ", subject = " + subject + ", introduce = " + introduce + ", bulletin = " + bulletin);
        StringBuilder builder = new StringBuilder();
        for (String number : users) {
            builder.append(number);
            builder.append(";");
        }
        if (users.length > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        String list = builder.toString();
        WorkingServiceProxy.instance().gpCreate(owner, list, subject, introduce, bulletin, groupType, pendingIntent);
    }

    /**
     * 邀请成员
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param member        要邀请的成员
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void inviteMember(String owner, String groupUri, String member, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "inviteMember, owner = " + owner + ", groupUri = " + groupUri + ", member = " + member);
        WorkingServiceProxy.instance().gpInviteMember(owner, groupUri, member, pendingIntent);
    }

    /**
     * 申请加入群
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param remark        申请说明
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void apply(String owner, String groupUri, String remark, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "apply, owner = " + owner + ", groupUri = " + groupUri + ", remark = " + remark);
        WorkingServiceProxy.instance().gpApply(owner, groupUri, remark, pendingIntent);
    }

    /**
     * 审批申请加入
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param applicant     申请人
     * @param handleResult  审批结果1 : 同意 2：拒绝
     * @param replyMsg      说明
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void approval(String owner, String groupUri, String applicant, int handleResult, String replyMsg, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "approval, owner = " + owner + ", groupUri = " + groupUri + ", applicant = " + applicant + "handleResult = " + handleResult);
        WorkingServiceProxy.instance().gpApproval(owner, groupUri, applicant, handleResult, replyMsg, pendingIntent);
    }

    /**
     * 移除成员
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param member        要移除的成员
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void removeMember(String owner, String groupUri, String member, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "removeMember, owner = " + owner + ", groupUri = " + groupUri + ", member = " + member);
        WorkingServiceProxy.instance().gpRemoveMember(owner, groupUri, member, pendingIntent);
    }

    /**
     * 修改群名称
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param subject       新的群名称
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void modifySubject(String owner, String groupUri, String subject, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "modifySubject, owner = " + owner + ", groupUri = " + groupUri + ", subject = " + subject);
        WorkingServiceProxy.instance().gpSetSubject(owner, groupUri, subject, pendingIntent);
    }

    /**
     * 修改群邀请设置
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param falg       群邀请设置 0: 不限制  1: 只有群主可以邀请
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void modifyInviteFlag(String owner, String groupUri, int falg, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "modifyInviteFlag, owner = " + owner + ", groupUri = " + groupUri + ", falg = " + falg);
        WorkingServiceProxy.instance().gpSetInviteFlag(owner, groupUri, falg, pendingIntent);
    }

    /**
     * 修改群名称
     *
     * @param owner         修改群扩展信息,此信息客户端定制字段
     * @param groupUri      群组URI
     * @param extra       群扩展信息
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void modifyExtra(String owner, String groupUri, String extra, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "modifyExtra, owner = " + owner + ", groupUri = " + groupUri + ", extra = " + extra);
        WorkingServiceProxy.instance().gpSetExtra(owner, groupUri, extra, pendingIntent);
    }
    /**
     * 修改群简介
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param introduce     新的群简介
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void modifyIntroduce(String owner, String groupUri, String introduce, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "modifyIntroduce, owner = " + owner + ", groupUri = " + groupUri + ", introduce = " + introduce);
        WorkingServiceProxy.instance().gpSetIntroduce(owner, groupUri, introduce, pendingIntent);
    }

    /**
     * 修改群公告
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param bulletin      新的群公告
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void modifyBulletin(String owner, String groupUri, String bulletin, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "modifyBulletin, owner = " + owner + ", groupUri = " + groupUri + ", bulletin = " + bulletin);
        WorkingServiceProxy.instance().gpSetBulletin(owner, groupUri, bulletin, pendingIntent);
    }

    /**
     * 修改自己在群中的昵称
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param nickname      新的昵称
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void modifyNickname(String owner, String groupUri, String nickname, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "modifyNickname, owner = " + owner + ", groupUri = " + groupUri + "nick name = " + nickname);
        WorkingServiceProxy.instance().gpModifyNickName(owner, groupUri, nickname, pendingIntent);
    }

    /**
     * 转交群管理员
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param member        转交管理员的对象成员
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void changeManager(String owner, String groupUri, String member, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "changeManager, owner = " + owner + ", groupUri = " + groupUri + ", member = " + member);
        WorkingServiceProxy.instance().gpAssignAdmin(owner, groupUri, member, pendingIntent);
    }

    /**
     * 退出群组
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void quitGroup(String owner, String groupUri, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "quitGroup, owner = " + owner + ", groupUri = " + groupUri);
        WorkingServiceProxy.instance().gpExit(owner, groupUri, pendingIntent);
    }


    /**
     * 删除群组
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void deleteGroup(String owner, String groupUri, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "delete group, owner=" + owner + "uri=" + groupUri);
        WorkingServiceProxy.instance().gpDelete(owner, groupUri, pendingIntent);
    }

    /**
     * 订阅群组列表
     * @param owner 操作用户
     * @param version 群列表版本号
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void subList(String owner, String version, PendingIntent pendingIntent)throws Exception {
        LogUtil.i(TAG, "subList");
        WorkingServiceProxy.instance().gpSubList(owner, version, pendingIntent);
    }

    /**
     * 订阅单个群信息
     * <p>
     * 订阅后，会发送一条action为{@link BroadcastActions}.ACTION_GROUP_INFO_SESSION 的广播，
     * 通过{@link BroadcastActions}.getSession() 可以获取 {@link GroupSession} 对象。
     * @param owner    操作用户
     * @param groupUri 群组URI
     * @param infoVersion 群详细信息Info版本号
     * @param membersVersion 群成员Members版本号
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void subGroup(String owner, String groupUri, String infoVersion, String membersVersion, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "subGroup, groupUri = " + groupUri);
        WorkingServiceProxy.instance().gpSubInfo(owner, groupUri, infoVersion, membersVersion, pendingIntent);
    }

    /***
     * 加入一个群
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void joinGroup(String owner, String groupUri, String inviter, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "joinGroup, groupUri = " + groupUri);
        WorkingServiceProxy.instance().gpJoin(owner, groupUri, inviter, pendingIntent);
    }

    /***
     * 搜索群组
     *
     * @param owner         操作用户
     * @param subject       群名称
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link com.feinno.sdk.result.GroupSearchResult} 对象
     * @throws Exception
     */
    public static void searchGroup(String owner, String subject, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "searchGroup, subject = " + subject);
        WorkingServiceProxy.instance().gpSearchGroup(owner, subject, pendingIntent);
    }

    /***
     * 拒绝加入一个群
     *
     * @param owner         操作用户
     * @param groupUri      群组URI
     * @param pendingIntent 用于处理回调结果的PendingIntent, 通过 {@link BroadcastActions}.getResult 方法获得 {@link GroupOpResult} 对象
     * @throws Exception
     */
    public static void rejectGroup(String owner, String groupUri, String inviter, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "joinGroup, groupUri = " + groupUri);
        WorkingServiceProxy.instance().gpReject(owner, groupUri, inviter, pendingIntent);
    }

    /**
     * 获取群共享信息
     * @param owner 操作用户
     * @param groupUri 群组URI
     * @param resultIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link GroupShareInfoResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_GROUP_SHARE_INFO_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link GroupShareInfoResult}对象。
     * @throws Exception
     */
    public static void shareInfo(String owner, String groupUri, PendingIntent resultIntent) throws Exception {
        WorkingServiceProxy.instance().gpShareInfo(owner, groupUri, resultIntent);
    }

    /**
     * 设置群消息免打扰
     * @param owner 操作用户
     * @param groupUri 群组URI
     * @param flag 消息免打扰 0:未开启,1:开启
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_GROUP_SET_DND_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     * @throws Exception
     */
    public static void setDND(String owner, String groupUri, int flag, PendingIntent pendingIntent) throws Exception {
        WorkingServiceProxy.instance().gpSetDND(owner, groupUri, flag, pendingIntent);
    }

}
