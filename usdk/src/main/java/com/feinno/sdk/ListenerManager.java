//This file should be generated

package com.feinno.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.TextUtils;
import com.feinno.sdk.result.v3.UserInfo;
import com.feinno.sdk.session.*;
import com.feinno.sdk.session.v3.BlackListSession;
import com.feinno.sdk.session.v3.BuddyEventSession;
import com.feinno.sdk.session.v3.BuddyListSession;
import com.feinno.sdk.session.v3.SyncCompletedSession;
import com.feinno.sdk.utils.JsonUtils;
import com.feinno.sdk.utils.LogUtil;
import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;

class ListenerManager {
    public static final String TAG = "ListenerManager";

    public static final String AV_EVENT = "av_event";
    public static final String GROUP_EVENT = "group_event";
    public static final String GROUP_NOTIFY = "group_notify";
    public static final String GROUP_LIST = "group_list";
    public static final String GROUP_INFO = "group_info";
    public static final String ENDPOINT = "endpoint";
    public static final String MSG_TEXT = "msg_text";
    public static final String MSG_FT = "msg_ft";
    public static final String MSG_FT_PROG = "msg_ft_progress";
    public static final String MSG_REPORT = "msg_report";
    public static final String MSG_PUB = "msg_pub";
    public static final String MSG_CLOUDFILE = "msg_cloudfile";
    public static final String MSG_EMOTICON = "msg_emoticon";
    public static final String MSG_CUSTOM = "msg_custom";
    public static final String BUDDY_EVENT = "buddy_event";
    public static final String BUDDY_LIST = "buddy_list";
    public static final String BLACKLIST = "sync_blacklist";
    public static final String EP_CHANGED = "ep_changed";
    public static final String LOGOUT = "logout";
    public static final String MSG_STATUS = "msg_status";
    public static final String MSG_CONV_STATUS = "msg_conv_status";
    public static final String NOTIFY_STATUS = "notify_status";
    public static final String MSG_BATCH = "msg_batch";
    public static final String PRESENCE = "presence_notify";
    public static final String SYNC = "sync";
    public static final String LOGIN_STATE = "login_state";
    public static final String USERINFO = "user_info";
    public static final String SYNCCOMPLETED = "sync_completed";

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Sdk.SdkState sdkState;
    private Context context;
    private IListenerProvider listenerProvider;

    public ListenerManager(Sdk.SdkState sdkState, Context context, IListenerProvider listenerProvider) {
        this.sdkState = sdkState;
        this.context = context;
        this.listenerProvider = listenerProvider;
    }

    public void setAllListener() throws SdkException {
        LogUtil.i(TAG, "setAllListener");

        if (listenerProvider == null) {
            LogUtil.i(TAG, "listener provider is null");
        }

        try {
            setListener(AV_EVENT, AV_EVENT + "listener", AvSession.class, listenerProvider.getAvListener(), BroadcastActions.ACTION_AV_SESSION);
            setListener(GROUP_EVENT, GROUP_EVENT + "listener", GroupEventSession.class, listenerProvider.getGroupEventListener(), BroadcastActions.ACTION_GROUP_EVENT_SESSION);
            setListener(GROUP_LIST, GROUP_LIST + "listener", GroupListSession.class, listenerProvider.getGroupListListener(), BroadcastActions.ACTION_GROUP_LIST_SESSION);
            setListener(GROUP_NOTIFY, GROUP_NOTIFY + "listener", GroupNotificationSession.class, listenerProvider.getGroupNotifyListener(), BroadcastActions.ACTION_GROUP_NOTIFY_SESSION);
            setListener(GROUP_INFO, GROUP_INFO + "listener", GroupSession.class, listenerProvider.getGroupInfoListener(), BroadcastActions.ACTION_GROUP_INFO_SESSION);
            setListener(MSG_TEXT, MSG_TEXT + "listener", TextMessageSession.class, listenerProvider.getMessageTextListener(), BroadcastActions.ACTION_MESSAGE_TEXT_SESSION);
            setListener(MSG_FT, MSG_FT + "listener", FTMessageSession.class, listenerProvider.getMessageFTListener(), BroadcastActions.ACTION_MESSAGE_FILE_SESSION);
            setListener(MSG_FT_PROG, MSG_FT_PROG + "listener", ReportMessageSession.class, listenerProvider.getMessageFTProgListener(), BroadcastActions.ACTION_MESSAGE_FILE_PROGRESS_SESSION);
            setListener(MSG_REPORT, MSG_REPORT + "listener", ReportMessageSession.class, listenerProvider.getMessageReportListener(), BroadcastActions.ACTION_MESSAGE_REPORT_SESSION);
            setListener(MSG_PUB, MSG_PUB + "listener", PubMessageSession.class, listenerProvider.getMessagePubListener(), BroadcastActions.ACTION_MESSAGE_PUBLIC_SESSION);
            setListener(MSG_CLOUDFILE, MSG_CLOUDFILE + "listener", CloudfileSession.class, listenerProvider.getMessageCloudFileListener(), BroadcastActions.ACTION_MESSAGE_CLOUDFILE_SESSION);
            setListener(MSG_EMOTICON, MSG_EMOTICON + "listener", EmoticonSession.class, listenerProvider.getMessageEmoticonListener(), BroadcastActions.ACTION_MESSAGE_EMOTICON_SESSION);
            setListener(MSG_CUSTOM, MSG_CUSTOM + "listener", CustomMessageSession.class, listenerProvider.getCustomMessageListener(), BroadcastActions.ACTION_MESSAGE_CUSTOM_SESSION);
            setListener(BUDDY_EVENT, BUDDY_EVENT + "listener", BuddyEventSession.class, listenerProvider.getBuddyEventListener(), BroadcastActions.ACTION_BUDDY_EVENT_SESSION);
            setListener(BUDDY_LIST, BUDDY_LIST + "listener", BuddyListSession.class, listenerProvider.getBuddyListListener(), BroadcastActions.ACTION_BUDDY_LIST_SESSION);
            setListener(BLACKLIST, BLACKLIST + "listener", BlackListSession.class, listenerProvider.getBlackListListener(), BroadcastActions.ACTION_BLACKLIST_SESSION);
            setListener(EP_CHANGED, EP_CHANGED + "listener", EndpointChangedSession.class, listenerProvider.getEpChangedListener(), BroadcastActions.ACTION_EP_CHANGED_SESSION);
            setListener(LOGOUT, LOGOUT + "listener", LogoutSession.class, listenerProvider.getLogoutListener(), BroadcastActions.ACTION_LOGOUT_SESSION);
            setListener(MSG_STATUS, MSG_STATUS + "listener", MsgStatusSession.class, listenerProvider.getMsgStatusListener(), BroadcastActions.ACTION_MESSAGE_STATUS_SESSION);
            setListener(MSG_CONV_STATUS, MSG_CONV_STATUS + "listener", MsgConvStatusSession.class, listenerProvider.getMsgConvListener(), BroadcastActions.ACTION_MESSAGE_CONV_STATUS_SESSION);
            setListener(NOTIFY_STATUS, NOTIFY_STATUS + "listener", NotifyStatusSession.class, listenerProvider.getNotifyStatusListener(), BroadcastActions.ACTION_NOTIFY_STATUS_SESSION);
            setListener(MSG_BATCH, MSG_BATCH + "listener", MessageInfosSession.class, listenerProvider.getMsgBatchListener(), BroadcastActions.ACTION_MESSAGE_BATCH_SESSION);
            setListener(PRESENCE, PRESENCE + "listener", PresenceSession.class, listenerProvider.getPresenceListener(), BroadcastActions.ACTION_PRESENCE_SESSION);
            setListener(SYNC, SYNC + "listener", SyncSession.class, listenerProvider.getSyncListener(), BroadcastActions.ACTION_SYNC_SESSION);
            setListener(LOGIN_STATE, LOGIN_STATE + "listener", LoginStateSession.class, listenerProvider.getLoginStateListener(), BroadcastActions.ACTION_LOGIN_STATE_SESSION);
            setListener(USERINFO, USERINFO + "listener", UserInfo.class, listenerProvider.getUserInfoListener(), BroadcastActions.ACTION_USER_INFO);
            setListener(SYNCCOMPLETED, SYNCCOMPLETED+ "listener", SyncCompletedSession.class, listenerProvider.getSyncCompletedListener(), BroadcastActions.ACTION_SYNC_COMPLETED);
        } catch (LuaException e) {
            throw new SdkException(e);
        }
    }


    private <T extends Parcelable> void setListener(String funcName, final String tag, final Class<T> classOfT, final Listener<T> listener, final String action) throws LuaException {
        final JavaFunction l = new JavaFunction(sdkState.getLuaState()) {
            @Override
            public int execute() throws LuaException {
                String args = params();
                if (TextUtils.isEmpty(args)) {
                    LogUtil.i(tag, "listener args is empty, return now");
                    return 0;
                } else {
                    LogUtil.i(tag, "listener args = " + args);
                }
                try {
                    final T session = JsonUtils.fromJson(args, classOfT);
                    if (session == null) {
                        LogUtil.i(tag, "session is null, return now");
                        return 0;
                    }
                    else {
                        LogUtil.i(tag, session.toString());
                    }
                    Runnable r = new Runnable() {
                        public void run() {
                            if (listenerProvider != null) {
                                if (listener != null) {
                                    LogUtil.i(tag, "listener is not null, invoke its run method");
                                    listener.run(session);
                                    return;
                                }
                            }

                            LogUtil.i(tag, "listener is null, send a broadcast with action " + action);
                            Intent intent = new Intent(action);
                            Bundle bundle = new Bundle();
                            bundle.setClassLoader(getClass().getClassLoader());
                            bundle.putParcelable(BroadcastActions.EXTRA_SESSION, session);
                            intent.putExtra("_bundle", bundle);
                            context.sendBroadcast(intent);
                        }
                    };
                    mHandler.post(r);
                } catch (Exception e) {
                    LogUtil.e(tag, "listener: cannot get session from lua object", e);
                }
                return 0;
            }
        };
        l.register(Sdk.LISTENER_MODULE_NAME + "." + funcName);
    }
}
