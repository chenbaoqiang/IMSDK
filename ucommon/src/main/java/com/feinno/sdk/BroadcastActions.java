package com.feinno.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.feinno.sdk.result.ActionResult;

/**
 * SDK提供的广播的action
 *
 * 其中被叫方需要监听的广播有：
 *      ACTION_MESSAGE_TEXT_SESSION：表示收到了一条文本消息，包括群文本消息、公众账号文本消息。
 *      ACTION_MESSAGE_FILE_SESSION：表示收到了文件传输消息，包括群文件消息、公众账号文件消息。
 *      ACTION_MESSAGE_REPORT_SESSION：表示收到了消息回执。
 *      ACTION_MESSAGE_FILE_PROGRESS_SESSION：表示收到了文件传输的进度报告。
 *      ACTION_AV_SESSION：表示收到了一个音视频会话的状态信息。
 *      ACTION_GROUP_LIST_SESSION：表示收到群组列表的信息
 *      ACTION_GROUP_EVENT_SESSION：表示收到了一个群组会话信息，包括群信息和群成员信息。
 *      ACTION_GROUP_EVENT：表示收到了一个群组事件。
 *      ACTION_ENDPOINT_SESSION：当前终端在线状态的事件通知。
 *
 * 表示请求执行结果的广播：
 *      ACTION_GETSMSCODE_RESULT：获取短信验证码的执行结果
 *      ACTION_PROVISION_RESULT：注册的执行结果
 *      ACTION_LOGIN_RESULT：登录的执行结果
 *      ACTION_LOGOUT_RESULT：登出的执行结果
 *      ACTION_CAPS_RESULT：能力交换的执行结果
 *      ACTION_MESSAGE_SEND_RESULT：消息发送的执行结果
 *      ACTION_MESSAGE_FETCH_FILE_RESULT：下载文件的执行结果
 *      ACTION_GROUP_OP_RESULT：群组操作的执行结果
 *      ACTION_AV_RESULT：音视频操作的执行结果
 */
public class BroadcastActions {


    /**此Action用来发送有关RCSWorkingService的启动与停止状态的广播*/
    public static final String ACTION_SERVICE_STATE = "com.interrcs.sdk.yourapp.broadcast.ACTION_SERVICE_STATE";
    /**广播结果的key参数*/
    public static final String EXTRA_SERVICE_STATE = "extra_service_state";
    /**RCSWorkingService启动*/
    public static final int SERVICE_STATE_START = 1;
    /**RCSWorkingService停止*/
    public static final int SERVICE_STATE_STOP = 2;

    /**此Action用来发送有关RCS SDK的启动与停止状态的广播*/
    public static final String ACTION_SDK_STATE = "com.interrcs.sdk.yourapp.broadcast.ACTION_SDK_STATE";
    /**广播结果的key参数*/
    public static final String EXTRA_SDK_STATE = "extra_sdk_state";
    /**RCS SDK启动*/
    public static final int SDK_STATE_START = 1;
    /**RCS SDK停止*/
    public static final int SDK_STATE_STOP = 2;

    /**此Action用来发送有关RCS用户状态的广播*/
    public static final String ACTION_USER_STATE = "com.interrcs.sdk.yourapp.broadcast.ACTION_USER_STATE";
    /**广播结果用户状态的key参数*/
    public static final String EXTRA_USER_NUMBER = "extra_user_state";
    /**广播结果用户号码的key参数*/
    public static final String EXTRA_USER_STATE = "extra_user_number";
    /**用户已添加到RCSWorkingService中，用户登出以后亦为此状态*/
    public static final int USER_STATE_STARTED = 1;
    /**该用户已经不再存在于RCSWorkingService中*/
    public static final int USER_STATE_NOT_STOPPED = 3;

    /**此Action用来发送与服务平台之间连接状态的广播*/
    public static final String ACTION_CONNECTION_STATE = "com.interrcs.sdk.yourapp.broadcast.ACTION_CONNECTION_STATE";
    /**广播结果连接状态的key参数*/
    public static final String EXTRA_CONNECTION_STATE = "extra_connection_state";
    /**广播结果的连接主机key参数*/
    public static final String EXTRA_CONNECTION_HOST = "extra_connection_host";
    /**广播结果的连接端口key参数*/
    public static final String EXTRA_CONNECTION_PORT = "extra_connection_port";
    /**已与服务平台断开连接*/
    public static final int CONNECTION_STATE_DISCONNECTED = 1;
    /**已连接到服务平台*/
    public static final int CONNECTION_STATE_CONNECTED = 2;


    /**用户开通广播*/
    public static final String ACTION_GETSMSCODE_RESULT = "com.interrcs.sdk.yourapp.broadcast.getsmscode_result";
    public static final String ACTION_PROVISION_RESULT = "com.interrcs.sdk.yourapp.broadcast.provision_result";


    /**用户登录结果广播*/
    public static final String ACTION_LOGIN_RESULT = "com.interrcs.sdk.yourapp.broadcast.login_result";
    /**用户注销结果广播*/
    public static final String ACTION_LOGOUT_RESULT = "com.interrcs.sdk.yourapp.broadcast.logout_result";


    /**当前终端状态广播*/
    public static final String ACTION_ENDPOINT_SESSION = "com.feinno.sdk.yourapp.broadcast.endpoint_session";

    /** 推送用户强制下线通知事件广播 */
    public static final String ACTION_LOGOUT_SESSION = "com.feinno.sdk.yourapp.broadcast.logout_session";

    /**收到能力交换广播*/
    public static final String ACTION_CAPS_SESSION = "com.interrcs.sdk.yourapp.broadcast.caps_session";
    /**能力交换结果广播*/
    public static final String ACTION_CAPS_RESULT = "com.interrcs.sdk.yourapp.broadcast.caps_result";

    /**订阅联系状态结果广播*/
    public static final String ACTION_PRESENCE_RESULT = "com.interrcs.sdk.yourapp.broadcast.presence_result";
    /**订阅联系状态变化通知广播*/
    public static final String ACTION_PRESENCE_SESSION = "com.interrcs.sdk.yourapp.broadcast.presence_session";

    /**订阅联系状态结果广播*/
    public static final String ACTION_GET_PRESENCE_RESULT = "com.interrcs.sdk.yourapp.broadcast.get_presence_result";

    /**文本消息广播*/
    public static final String ACTION_MESSAGE_TEXT_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_text_session";
    /**文件广播*/
    public static final String ACTION_MESSAGE_FILE_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_file_session";
    /**文件传输进度广播*/
    public static final String ACTION_MESSAGE_FILE_PROGRESS_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_file_progress_session";
    /**公众号图文混排类消息广播*/
    public static final String ACTION_MESSAGE_PUBLIC_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_public_session";
    /**商店表情的消息广播*/
    public static final String ACTION_MESSAGE_EMOTICON_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_vemoticon_session";
    /**自定义消息广播*/
    public static final String ACTION_MESSAGE_CUSTOM_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_custom_session";
    /**彩云文件的消息广播*/
    public static final String ACTION_MESSAGE_CLOUDFILE_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_cloudfile_session";
    /**消息回执报告广播*/
    public static final String ACTION_MESSAGE_REPORT_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_report_session";
    /**消息发送结果广播*/
    public static final String ACTION_MESSAGE_SEND_RESULT = "com.interrcs.sdk.yourapp.broadcast.message_send_result";
    /**下载文件结果*/
    public static final String ACTION_MESSAGE_FETCH_RESULT = "com.interrcs.sdk.yourapp.broadcast.message_fetch_result";

    /**消息状态变化广播*/
    public static final String ACTION_MESSAGE_STATUS_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_status_session";
    /**用户会话状态同步广播*/
    public static final String ACTION_MESSAGE_CONV_STATUS_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_conv_status_session";
    /**业务通知状态变化广播*/
    public static final String ACTION_NOTIFY_STATUS_SESSION = "com.interrcs.sdk.yourapp.broadcast.notify_status_session";

    /**批量消息广播*/
    public static final String ACTION_MESSAGE_BATCH_SESSION = "com.interrcs.sdk.yourapp.broadcast.message_batch_session";

    /**设置富媒体消息状态结果*/
    public static final String ACTION_MESSAGE_SETSTATES_RESULT = "com.interrcs.sdk.yourapp.broadcast.message_set_states_result";
    /**设置会话状态结果*/
    public static final String ACTION_MESSAGE_SETCONV_RESULT = "com.interrcs.sdk.yourapp.broadcast.message_set_conv_result";


    /**上传共享文件结果*/
    public static final String ACTION_UPLOAD_SHARE_FILE_RESULT = "com.interrcs.sdk.yourapp.broadcast.message_upload_share_file_result";
    /**下载共享文件结果*/
    public static final String ACTION_FETCH_SHARE_FILE_RESULT = "com.interrcs.sdk.yourapp.broadcast.message_fetch_share_file_result";
    /**删除共享文件结果*/
    public static final String ACTION_DELETE_SHARE_FILE_RESULT = "com.interrcs.sdk.yourapp.broadcast.message_delete_share_file_result";
    /**获取共享文件列表*/
    public static final String ACTION_GET_SHARE_FILE_LIST_RESULT = "com.interrcs.sdk.yourapp.broadcast.message_get_share_file_list_result";
    /**取消文件上传下载操作结果*/
    public static final String ACTION_CANCEL_TRANSFER_RESULT = "com.interrcs.sdk.yourapp.broadcast.message_cancel_transfer_result";
    /**设置业务通知已读操作结果*/
    public static final String ACTION_SET_NOTIFY_READ_RESULT = "com.interrcs.sdk.yourapp.broadcast.set_notify_read_result";

    /**群组列表广播*/
    public static final String ACTION_GROUP_LIST_SESSION = "com.interrcs.sdk.yourapp.broadcast.group_list_session";
    /**群组广播信息*/
    public static final String ACTION_GROUP_NOTIFY_SESSION = "com.interrcs.sdk.yourapp.broadcast.group_notify_session";
    /** 群详情 */
    public static final String ACTION_GROUP_INFO_SESSION = "com.interrcs.sdk.yourapp.broadcast.group_info_session";
    /**群组事件*/
    public static final String ACTION_GROUP_EVENT_SESSION = "com.interrcs.sdk.yourapp.broadcast.group_event_session";
    /**群组操作结果广播*/
    public static final String ACTION_GROUP_OP_RESULT = "com.interrcs.sdk.yourapp.broadcast.group_op_result";
    /**群组搜索结果广播*/
    public static final String ACTION_GROUP_SEARCH_RESULT = "com.interrcs.sdk.yourapp.broadcast.group_search_result";

    /**获取群共享信息结果广播*/
    public static final String ACTION_GROUP_SHARE_INFO_RESULT = "com.interrcs.sdk.yourapp.broadcast.group_share_info_result";

    /**设置群消息免打扰结果广播*/
    public static final String ACTION_GROUP_SET_DND_RESULT = "com.interrcs.sdk.yourapp.broadcast.group_set_dnd_result";

    /**音视频被叫广播*/
    public static final String ACTION_AV_SESSION = "com.interrcs.sdk.yourapp.broadcast.av_session";
    /**音视频主叫广播*/
    public static final String ACTION_AV_CALL_OUT = "com.interrcs.sdk.yourapp.broadcast.av_call_out";
    /**音视频会话操作结果广播*/
    public static final String ACTION_AV_RESULT = "com.interrcs.sdk.yourapp.broadcast.av_result";

    /**终端管理结果广播*/
    public static final String ACTION_EP_MANAGER_RESULT = "com.interrcs.sdk.yourapp.broadcast.epmanager_result";

    /**获取激活设备列表结果广播**/
    public static final String ACTION_ENDPOINTLIST_RESULT = "com.interrcs.sdk.yourapp.broadcast.endpointlist_result";
    /**设备管理操作结果广播**/
    public static final String ACTION_ENDPOINT_RESULT = "com.interrcs.sdk.yourapp.broadcast.endpoint_result";
    /**用户在其它设备登录广播*/
    public static final String ACTION_EP_CHANGED_SESSION = "com.feinno.sdk.yourapp.broadcast.endpointchanged_session";

    /**设置设备免打扰结果广播**/
    public static final String ACTION_SET_ENDPOINT_DND_RESULT = "com.interrcs.sdk.yourapp.broadcast.set_endpoint_dnd_result";

    /**获取会话列表的结果广播**/
    public static final String ACTION_GET_CONVLIST_RESULT = "com.interrcs.sdk.yourapp.broadcast.get_convlist_result";

    /**获取历史会话消息结果广播**/
    public static final String ACTION_GET_CONV_HISTORY_RESULT = "com.interrcs.sdk.yourapp.broadcast.get_conv_histroy_result";

    /**发送文件进度广播*/
    @Deprecated
    public static final String ACTION_SEND_FILE_PROGRESS = "com.interrcs.sdk.yourapp.broadcast.send_file_progress";
    /**拉取文件进度广播*/
    @Deprecated
    public static final String ACTION_FETCH_FILE_PROGRESS = "com.interrcs.sdk.yourapp.broadcast.fetch_file_progress";
    /** 修改好友的结果广播Action */
    public static final String ACTION_USERINFO_SET = "com.feinno.sdk.v3.yourapp.broadcast.userinfo_set";
    /** 获取个人信息的广播 */
    public static  final String ACTION_USERINFO_GET = "com.feinno.sdk.v3.yourapp.broadcast.userinfo_get";
    /** 用户信息操作的应答广播 */
    public static final String ACTION_USERINFO_OP = "com.feinno.sdk.v3.yourapp.broadcast.userinfo_op";
    /**
     * 用户基本信息操作结果广播
     */
    public static final String ACTION_USERINFO_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.userinfo_result";

    /** 用户详细资料操作结果广播 */
    public static final String ACTION_USERPROFILE_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.userprofile_result";

    /** 设置消息免打扰操作结果广播 */
    public static final String ACTION_DNDFLAG_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.dndflag_result";

    /** 设置消息免打扰操作结果广播 */
    public static final String ACTION_PERMISSIONUIDFLAG_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.permissionuidflag_result";

    /** 设置消息免打扰操作结果广播 */
    public static final String ACTION_PERMISSIONUNAMEFLAG_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.permissionunameflag_result";

    /** 设置加好友自动同意操作结果广播 */
    public static final String ACTION_BUDDYFLAG_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.buddyflag_result";

    /**
     * 用户头像的操作结果广播
     */
    public static final String ACTION_USERPROTRAIT_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.userportrait_result";
    /**
     * 好友操作结果广播
     */
    public static final String ACTION_BUDDY_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.buddy_result";
    /**
     * 好友事件通知广播
     */
    public static final String ACTION_BUDDY_EVENT_SESSION = "com.feinno.sdk.v3.yourapp.broadcast.buddy_event_session";
    /**
     * 好友列表变化通知
     */
    public static final String ACTION_BUDDY_LIST_SESSION  = "com.feinno.sdk.v3.yourapp.broadcast.buddy_list_session";
    /**
     * 黑名单列表变化通知
     */
    public static final String ACTION_BLACKLIST_SESSION  = "com.feinno.sdk.v3.yourapp.broadcast.balcklist_session";
    /**
     * 获取Token结果广播
     */
    public static final String ACTION_TOKEN_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.token_result";

    /**
     * 拉黑某人结果广播
     */
    public static final String ACTION_BKLIST_REMOVE_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.bklistremove_result";

    /**
     * 移除黑名单结果广播
     */
    public static final String ACTION_BKLIST_GET_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.bklistget_result";

    /**
     * 消息转短链接结果广播
     */
    public static final String ACTION_MSG2SHORTURL_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.msg2shorturl_result";

    /**
     * 获取黑名单列表结果广播
     */
    public static final String ACTION_BKLIST_ADD_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.bklistadd_result";

    /**
     * 添加设备结果广播
     */
    public static final String ACTION_DEVICE_ADD_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.device_add_result";

    /**
     * 移除设备结果广播
     */
    public static final String ACTION_DEVICE_REMOVE_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.device_remove_result";

    /**
     * 获取设备列表结果广播
     */
    public static final String ACTION_DEVICE_LIST_GET_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.device_list_get_result";

    /**
     * 获取设备状态信息
     */
    public static final String ACTION_DEVICE_STATUS_GET_RESULT = "com.feinno.sdk.v3.yourapp.broadcast" +
            ".device_status_get_result";

    /**
     * 获取User 状态结果广播
     */
    public static final String ACTION_GET_USERSTATES_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.get_userstates_result";
    /**
     * 离线消息事件通知
     */
    public static final String ACTION_SYNC_SESSION  = "com.feinno.sdk.v3.yourapp.broadcast.sync_session";

    /** sdk连接状态变化通知 */
    public static final String ACTION_LOGIN_STATE_SESSION = "com.feinno.sdk.v3.yourapp.broadcast.login_state";

    /** 个人信息变化通知 */
    public static final String ACTION_USER_INFO = "com.feinno.sdk.v3.yourapp.broadcast.user_info";

    /** 信息同步完成 */
    public static final String ACTION_SYNC_COMPLETED = "com.feinno.sdk.v3.yourapp.broadcast.sync_completed";

    /** 获取文件id */
    public static final String ACTION_GETFILEID_RESULT = "com.feinno.sdk.v3.yourapp.broadcast.getfileid";

    /**广播参数 session*/
    public static final String EXTRA_SESSION = "session";
    /**广播参数 result*/
    public static final String EXTRA_RESULT = "result";


    /**
     * 从广播intent中获得一个ActionResult的子类对象
     * @param intent 广播intent
     * @param <T> ActionResult的子类对象
     * @return ActionResult的子类对象
     */
    public static <T extends ActionResult> T getResult(Intent intent) {
        if(intent.getExtras().containsKey("_bundle")) {
            Bundle bundle = intent.getExtras().getParcelable("_bundle");
            return bundle.getParcelable(EXTRA_RESULT);
        } else {
            return intent.getExtras().getParcelable(EXTRA_RESULT);
        }
    }

    /**
     * 从广播intent中获得一个Parcelable对象
     * @param intent 广播intent
     * @return Parcelable对象
     */
    public static Parcelable getSession(Intent intent) {
        if(intent.getExtras().containsKey("_bundle")) {
            Bundle bundle = intent.getExtras().getParcelable("_bundle");
            return bundle.getParcelable(EXTRA_SESSION);
        } else {
            return intent.getExtras().getParcelable(EXTRA_SESSION);
        }
    }
}


