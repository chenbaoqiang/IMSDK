package com.feinno.sdk.api;

import com.feinno.sdk.Sdk;
import com.feinno.sdk.SdkApi;
import com.feinno.sdk.Callback;
import com.feinno.sdk.session.GroupCBSession;
import com.feinno.sdk.session.GroupSession;

/**
 * 该类提供了群组的相关接口
 */
public class Group {

    /**
     * 创建一个新的群组
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param groupUri 群组URI
     * @param resourcelists 群组成员列表, 以`;`间隔
     * @param subject 群组名称
     * @param introduce 群简介
     * @param bulletin 群公告
     * @return group session id
     */
    public static int createGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, String introduce, String bulletin) {
        return createGroup(sdkState, groupUri, resourcelists, subject, introduce, bulletin, null);
    }

    /**
     * 创建一个新的群组
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param groupUri 群组URI
     * @param resourcelists 群组成员列表, 以`;`间隔
     * @param subject 群组名称
     * @param introduce 群简介
     * @param bulletin 群公告
     * @return group session id
     */
    public static int createGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, String introduce, String bulletin, Callback<GroupSession> cb) {
        return SdkApi.createGroup(sdkState, groupUri, resourcelists, subject, introduce, bulletin);
    }

    //TODO: 我们是否需要这个方法?
    /**
     * 重新加入/激活一个新的群组
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param groupUri 群组URI
     * @param resourcelists 群组成员列表，以`;`间隔
     * @param subject 群组名称
     * @return group session id
     */
    public static int rejoinGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject) {
        return rejoinGroup(sdkState, groupUri, resourcelists, subject, null);
    }


    /**
     * 重新加入/激活一个新的群组
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param groupUri 群组URI
     * @param resourcelists 群组成员列表，以`;`间隔
     * @param subject 群组名称
     * @return group session id
     */
    public static int rejoinGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, Callback<GroupSession> cb) {
        return SdkApi.createGroup(sdkState, groupUri, resourcelists, subject, "", "");
    }


    /**
     * 邀请群成员
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param groupUri 群组URI
     * @param resourcelists 群组成员列表
     * @param subject 群组名称
     * @param member 要邀请的成员
     */
    public static int inviteMember(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, String member) {
        return inviteMember(sdkState, groupUri, resourcelists, subject, member, null);
    }

    /**
     * 邀请群成员
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param groupUri 群组URI
     * @param resourcelists 群组成员列表
     * @param subject 群组名称
     * @param member 要邀请的成员
     */
    public static int inviteMember(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, String member, Callback<GroupCBSession> cb) {
        return SdkApi.inviteGroupMember(sdkState, groupUri, resourcelists, subject, member, cb);
    }


    /**
     * 离开群组
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param groupUri 群组URI
     * @param resourcelists 群组成员列表
     * @param subject 群组名称
     */
    public static void quitGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject) {
        quitGroup(sdkState, groupUri, resourcelists, subject, null);
    }

    /**
     * 离开群组
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param groupUri 群组URI
     * @param resourcelists 群组成员列表
     * @param subject 群组名称
     * @param cb 操作结果的 Callback
     */
    public static void quitGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, Callback<GroupCBSession> cb) {
        SdkApi.exitGroup(sdkState, groupUri, resourcelists, subject, cb);
    }


    public static void modifySubject(Sdk.SdkState sdkState, String groupUri, String subject, Callback<GroupCBSession> cb) {
        SdkApi.gpModifySubject(sdkState, groupUri, "", subject, cb);
    }

    public static void removeMember(Sdk.SdkState sdkState, String groupUri, String member, Callback<GroupCBSession> cb) {
        SdkApi.gpRemoveUser(sdkState, groupUri, "", member, cb);
    }

    public static void changeManager(Sdk.SdkState sdkState, String groupUri, String member, Callback<GroupCBSession> cb) {
        SdkApi.gpChangeManager(sdkState, groupUri, "", member, cb);
    }

    public static void modifyNickName(Sdk.SdkState sdkState, String groupUri, String name, Callback<GroupCBSession> cb) {
        SdkApi.gpModifyNickName(sdkState, groupUri, "", name, cb);

    }


}
