package com.feinno.sdk.dapi;


import android.content.Context;

import com.feinno.sdk.IListenerProvider;
import com.feinno.sdk.Listener;
import com.feinno.sdk.result.v3.UserInfo;
import com.feinno.sdk.session.*;
import com.feinno.sdk.session.v3.BlackListSession;
import com.feinno.sdk.session.v3.BuddyEventSession;
import com.feinno.sdk.session.v3.BuddyListSession;
import com.feinno.sdk.session.v3.SyncCompletedSession;

@Deprecated
public class ListenerProvider implements IListenerProvider {

	private Context context;

	public ListenerProvider(Context context) {
		this.context = context;
	}

    @Override
    public Listener<AvSession> getAvListener() {
        return null;
    }

    @Override
    public Listener<TextMessageSession> getMessageTextListener() {
        return null;
    }

    @Override
    public Listener<FTMessageSession> getMessageFTListener() {
        return null;
    }

    @Override
    public Listener<ReportMessageSession> getMessageFTProgListener() {
        return null;
    }

    @Override
    public Listener<ReportMessageSession> getMessageReportListener() {
        return null;
    }

    @Override
    public Listener<PubMessageSession> getMessagePubListener() {
        return null;
    }

    @Override
    public Listener<CloudfileSession> getMessageCloudFileListener() {
        return null;
    }

    @Override
    public Listener<EmoticonSession> getMessageEmoticonListener() {
        return null;
    }

    @Override
    public Listener<CustomMessageSession> getCustomMessageListener() {
        return null;
    }

    @Override
    public Listener<CapsSession> getCapListener() {
        return null;
    }

    @Override
    public Listener<GroupEventSession> getGroupEventListener() {
        return null;
    }

    @Override
    public Listener<GroupListSession> getGroupListListener() {
        return null;
    }

    @Override
    public Listener<GroupNotificationSession> getGroupNotifyListener() {
        return null;
    }

    @Override
    public Listener<GroupSession> getGroupInfoListener() {
        return null;
    }

    @Override
    public Listener<BuddyEventSession> getBuddyEventListener() {
        return null;
    }

    @Override
    public Listener<BuddyListSession> getBuddyListListener() {
        return null;
    }

    @Override
    public Listener<BlackListSession> getBlackListListener() {
        return null;
    }

    @Override
    public Listener<EndpointChangedSession> getEpChangedListener() { return null; }

    @Override
    public Listener<LogoutSession> getLogoutListener() {
        return null;
    }

    @Override
    public Listener<MsgStatusSession> getMsgStatusListener() {
        return null;
    }

    @Override
    public Listener<MsgConvStatusSession> getMsgConvListener() {
        return null;
    }

    @Override
    public Listener<NotifyStatusSession> getNotifyStatusListener() {
        return null;
    }

    @Override
    public Listener<MessageInfosSession> getMsgBatchListener() {
        return null;
    }

    @Override
    public Listener<PresenceSession> getPresenceListener() {
        return null;
    }

    @Override
    public Listener<SyncSession> getSyncListener() {
        return null;
    }

    @Override
    public Listener<LoginStateSession> getLoginStateListener() {return  null;}

    @Override
    public Listener<UserInfo> getUserInfoListener() {return  null;}

    @Override
    public Listener<SyncCompletedSession> getSyncCompletedListener() {
        return null;
    }
}
