package com.feinno.sdk;

import com.feinno.sdk.result.v3.UserInfo;
import com.feinno.sdk.session.*;
import com.feinno.sdk.session.v3.BlackListSession;
import com.feinno.sdk.session.v3.BuddyEventSession;
import com.feinno.sdk.session.v3.BuddyListSession;
import com.feinno.sdk.session.v3.SyncCompletedSession;

/**
 * 被叫所有的业务逻辑处理入口
 */
public interface IListenerProvider {


    /**
     * 提供被叫处理音视频通话事件的接口
     * @return 处理音视频通话事件的接口
     */
    Listener<AvSession> getAvListener();

    /**
     * 提供接收文本消息时间的处理接口
     * @return 处理文本消息接口
     */
    Listener<TextMessageSession> getMessageTextListener();
    Listener<FTMessageSession> getMessageFTListener();
    Listener<ReportMessageSession> getMessageFTProgListener();
    Listener<ReportMessageSession> getMessageReportListener();
    Listener<PubMessageSession> getMessagePubListener();
    Listener<CloudfileSession> getMessageCloudFileListener();
    Listener<EmoticonSession> getMessageEmoticonListener();


    /**
     * 提供处理自定义消息的处理接口
     * @return 处理自定义消息的接口
     */
    Listener<CustomMessageSession> getCustomMessageListener();


    /**
     * 提供被叫处理能力交换事件的接口
     * @return 处理能力交换事件的接口
     */
    Listener<CapsSession> getCapListener();

    /**
     * 提供被叫处理群组事件的接口
     * @return 处理群组事件的接口
     */
    Listener<GroupEventSession> getGroupEventListener();

    /**
     * 提供被叫处理群组列表事件的接口
     * @return 处理群组列表事件的接口
     */
    Listener<GroupListSession> getGroupListListener();

    /***
     * 提供群组通知的处理接口
     * @return 处理群组通知的接口
     */
    Listener<GroupNotificationSession> getGroupNotifyListener();


    /**
     * 提供群信息变化的处理接口
     * @return
     */
    Listener<GroupSession> getGroupInfoListener();

    /**
     * 提供处理好友相关事件的接口, 例如：被添加好友、添加好友结果通知等
     * @return 处理好友事件接口
     */
    Listener<BuddyEventSession> getBuddyEventListener();


    /**
     * 用于处理好友列表变化通知的接口
     * @return 好友列表变化通知接口
     */
    Listener<BuddyListSession> getBuddyListListener();

    /**
     * 用于黑名单列表变化通知的接口
     * @return 黑名单列表变化通知接口
     */
    Listener<BlackListSession> getBlackListListener();

    /**
     * 用于服务端推送用户在其他设备上登录账号通知事件的接口
     * @return 用户在其他设备上激活账号通知事件通知接口
     */
    Listener<EndpointChangedSession> getEpChangedListener();


    /**
     * 用于服务端推送用户强制下线通知事件的接口
     * @return 用户强制下线通知事件的接口
     */
    Listener<LogoutSession> getLogoutListener();

    /**
     * 用于服务端推送消息状态变化事件的接口
     * @return 消息状态变化事件的接口
     */
    Listener<MsgStatusSession> getMsgStatusListener();

    /**
     * 用于服务端推送用户会话状态同步事件的接口
     * @return 用户会话状态同步事件的接口
     */
    Listener<MsgConvStatusSession> getMsgConvListener();

    /**
     * 用于服务端推送业务通知状态变化事件的接口
     * @return 业务通知状态变化事件的接口
     */
    Listener<NotifyStatusSession> getNotifyStatusListener();

    /**
     * 用于服务端推送批量消息事件的接口
     * @return 批量消息事件的接口
     */
    Listener<MessageInfosSession> getMsgBatchListener();

    /**
     * 用户服务端推送联系人状态变化的事件接口
     * @return 联系人状态变化同步事件接口
     */
    Listener<PresenceSession> getPresenceListener();

    /**
     * 同步离线消息的事件接口
     * @return 同步离线消息事件接口
     */
    Listener<SyncSession> getSyncListener();

    /**
     * 同步SDK登陆状态的事件接口
     * @return登陆状态的事件接口
     */
    Listener<LoginStateSession> getLoginStateListener();

    /**
     * 获取用户信息变更接口
     * @return
     */
    Listener<UserInfo> getUserInfoListener();

    /**
     * 获取同步完成
     * @return
     */
    Listener<SyncCompletedSession> getSyncCompletedListener();
}
