package com.interrcs.uwsdemo;

import android.app.PendingIntent;
import android.app.Service;
import android.content.*;
import android.database.Cursor;
import android.os.*;
import android.text.TextUtils;
import android.util.Log;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.args.ReportMessageArg;
import com.feinno.sdk.dapi.GroupManager;
import com.feinno.sdk.dapi.RCSMessageManager;
import com.feinno.sdk.dapi.v3.UserInfoManager;
import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.ContentType;
import com.feinno.sdk.enums.ErrorCode;
import com.feinno.sdk.enums.ReportType;
import com.feinno.sdk.result.MessageResult;
import com.feinno.sdk.result.v3.UserInfo;
import com.feinno.sdk.result.v3.UserInfoResult;
import com.feinno.sdk.result.v3.UserPortraitResult;
import com.feinno.sdk.result.GroupOpResult;
import com.feinno.sdk.session.*;
import com.feinno.sdk.session.v3.BuddyEventSession;
import com.feinno.sdk.session.v3.BuddyInfo;
import com.feinno.sdk.session.v3.BuddyListSession;
import com.feinno.sdk.session.v3.BuddyOpEnum;
import com.feinno.sdk.utils.JsonUtils;
import com.interrcs.uwsdemo.data.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * 处理应用核心业务逻辑的后台服务。服务运行在一个单独后台线程。
 * 1. 好友列表刷新、好友事件通知；
 * 2. 文本文件消息的接收、消息发送状态、发送报告；
 * 3. 用户资料、头像。
 */
public class UWSReceiverService extends Service {
    private static final String TAG = "UWSReceiverService";

    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread(TAG, android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() startId:" + startId + ", intent:" + intent);

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mServiceLooper.quit();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            int serviceId = msg.arg1;
            try {
                Intent intent = (Intent) msg.obj;
                if (intent == null) {
                    return;
                }
                String action = intent.getAction();
                Log.d(TAG, "Handler.handleMessage action: " + action + " intent: " + intent.toString());

                if (action.equals(BroadcastActions.ACTION_BUDDY_EVENT_SESSION)) {
                    // 处理好友事件通知
                    handleBuddyEvent(getApplicationContext(), intent);
                } else if (action.equals(BroadcastActions.ACTION_BUDDY_LIST_SESSION)) {
                    // 处理好友别表刷新
                    handleBuddyList(getApplicationContext(), intent);
                } else if (action.equals(BroadcastActions.ACTION_USERINFO_RESULT)) {
                    // 处理用户资料刷新
                    handleUserInfoResult(getApplicationContext(), intent);
                } else if (action.equals(BroadcastActions.ACTION_USERPROTRAIT_RESULT)) {

                } else if (action.equals(BroadcastActions.ACTION_USERPROFILE_RESULT)) {
                    
                } else if (action.equals(BroadcastActions.ACTION_MESSAGE_TEXT_SESSION)){
                    // 处理接收的文本消息
                    handleTextMessage(getApplicationContext(), intent);
                } else if(action.equals(BroadcastActions.ACTION_MESSAGE_REPORT_SESSION)) {
                    // 处理消息报告
                    handleReportMessage(getApplicationContext(), intent);
                } else if(action.equals(BroadcastActions.ACTION_MESSAGE_FILE_SESSION)) {
                    // 处理接收的文件消息
                    handleFileMessage(getApplicationContext(), intent);
                } else if (action.equals(BroadcastActions.ACTION_MESSAGE_FETCH_RESULT)) {
                    // 处理fetch下载文件结果
                    handleFileFetchResult(getApplicationContext(), intent);
                } else if(action.equals(BroadcastActions.ACTION_MESSAGE_SEND_RESULT)) {
                    // 处理消息发送结果
                    handleMessageResult(getApplicationContext(), intent);
                } else if(action.equals(BroadcastActions.ACTION_GROUP_EVENT_SESSION)) {
                    //处理群组事件通知
                    handleGroupEvt(getApplicationContext(), intent);
                } else if(action.equals(BroadcastActions.ACTION_GROUP_NOTIFY_SESSION)) {
                    //处理群组广播事件
                    handleGroupNotify(getApplicationContext(), intent);
                } else if(action.equals(BroadcastActions.ACTION_GROUP_LIST_SESSION)) {
                    //处理群组列表变化通知
                    handleGroupList(getApplicationContext(), intent);
                } else if(action.equals(BroadcastActions.ACTION_GROUP_INFO_SESSION)) {
                    // 群Info&members结果
                    handleGroupInfo(getApplicationContext(), intent);
                }else if(action.equals(BroadcastActions.ACTION_MESSAGE_CUSTOM_SESSION)) {
                    // [废弃]
                  handleCustomMessage(getApplicationContext(), intent);
                }else if(action.equals(BroadcastActions.ACTION_EP_CHANGED_SESSION)) {
                    // 登录点变化通知
                    EndpointChangedSession session = (EndpointChangedSession) BroadcastActions.getSession(intent);
                    Log.d(TAG, "receive ep changed session:" + JsonUtils.toJson(session));
                }else if(action.equals(BroadcastActions.ACTION_LOGOUT_SESSION)) {
                    // 强制等出通知
                    LogoutSession session = (LogoutSession) BroadcastActions.getSession(intent);
                    Log.d(TAG, "receive logout session:" + JsonUtils.toJson(session));
                }else if(action.equals(BroadcastActions.ACTION_PRESENCE_SESSION)){
                    // 状态订阅变化通知
                    PresenceSession session = (PresenceSession) BroadcastActions.getSession(intent);
                    Log.d(TAG, "receive presence session:" + JsonUtils.toJson(session));
                }else if(action.equals(BroadcastActions.ACTION_LOGIN_STATE_SESSION)){
                    // 在线状态变化通知
                    LoginStateSession session = (LoginStateSession)BroadcastActions.getSession(intent);
//                    Intent loginIntent = new Intent("sdk_login_state");
//                    Bundle bundle = new Bundle();
//                    bundle.setClassLoader(getClass().getClassLoader());
//                    bundle.putParcelable(BroadcastActions.EXTRA_SESSION, session);
//                    loginIntent.putExtra("_bundle", bundle);
//                    getApplicationContext().sendBroadcast(loginIntent);
                }
            }
            catch (Exception ex) {
                Log.e(TAG, "handle action error:", ex);
            }
            finally {
                // NOTE: We MUST not call stopSelf() directly, since we need to
                // make sure the wake lock acquired by UWSReceiver is released.
                UWSReceiver.finishStartingService(UWSReceiverService.this, serviceId);
            }
        }
    }


    void handleCustomMessage(Context context, Intent intent) {
        CustomMessageSession session = (CustomMessageSession)BroadcastActions.getSession(intent);

        int chatType = 128; //为推送消息制定一个特殊的 ChatType
        //查看数据库中是否已经存在当前条目
        if (!checkMessageExists(context, session.imdnId, false, false, false)) {
            //写入新新消息
            ContentValues values = new ContentValues();
            values.put("IMDN_ID", session.imdnId);
            values.put("MSG_FROM", session.from);
            values.put("MSG_TO", session.to);
            values.put("RECEIVE_TIME", session.sendTime * 1000L); //second to millsecond
            values.put("CHAT_TYPE", chatType);
            //NOTE:由于这里只做SDK使用演示，具体操作数据方式请根据具体业务进行操作
            values.put("CONTENT", session.data);
            values.put("STATUS", MessageContentProvider.STATUS_RECEIVED);
            context.getContentResolver().insert(MessageContentProvider.CONTENT_URI, values);
        }
    }

    /**
     * 处理文件下载结果
     * @param context
     * @param intent
     */
    void handleFileFetchResult(Context context, Intent intent) {
        MessageResult result = BroadcastActions.getResult(intent);

        ContentValues values = new ContentValues();
        // 消息发送成功
        if(result.errorCode == ErrorCode.OK.value()) {
            values.put("STATUS", MessageContentProvider.STATUS_DOWNLOADED);
        }
        // 消息发送失败
        else {
            values.put("STATUS", MessageContentProvider.STATUS_FAILED);
        }

        context.getContentResolver().update(MessageContentProvider.CONTENT_URI,
                values, "IMDN_ID=?", new String[] {result.imdnId});

    }

    /**
     * 刷新群组详细信息
     * @param groupId
     */
    private void updateGroupInfo(int groupId) {
        String action = "com.intercs.uwsdemo.group_sub_info_" + groupId;
        Intent intent = new Intent(action);
        Context mContext = getApplicationContext();
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                GroupOpResult result = BroadcastActions.getResult(intent);
            }
        }, new IntentFilter(action));
    
        // TODO: subGroup 可能会失败(remote进程重启等原因)
        // 客户端数据库应该 分别保存 info&member 在group_list中和 group_info 结果的version，在app启动的时候检查version是否一致
        // 如果不一致，需要重新subGroup 更新info&members 数据
        
        try {
            String oldGroupInfoVersion = "12314"; // 从db中获取
            String oldGroupMemberVersion = "134123"; // 从db中获取
            GroupManager.subGroup(LoginState.getStartUser(mContext), groupId+"", oldGroupInfoVersion, oldGroupMemberVersion, pi);
        } catch (Exception e) {
            Log.e(TAG, "updateGroup Info error", e);
        }
    }


    /**
     * 处理群组详细
     * @param context
     * @param intent
     */
    private void handleGroupInfo(Context context, Intent intent) {
        GroupSession session = (GroupSession) BroadcastActions.getSession(intent);
        ContentResolver resolver = context.getContentResolver();
        // 1. info
        if(session.info != null) {
            //更新 Group 表信息
            ContentValues values = new ContentValues();
            values.put("GROUP_NAME", session.info.subject);
            resolver.update(GroupContentProvider.CONTENT_URI, values, "GROUP_ID=?", new String[]{session.info.groupUri});
        }else{
            // 群Info没变化
        } 
        // 2. members
        if (session.members != null){
            // 更新 GROUP_MEMBER 表数据
            resolver.delete(GroupMemberContentProvider.CONTENT_URI, "GROUP_ID=?", new String[]{session.groupUri});
            ContentValues[] batch = new ContentValues[session.members.length];
            int i = 0;
            for (GroupMemberInfo member : session.members) {
                ContentValues values = new ContentValues();
                values.put("GROUP_ID", session.info.groupUri);
                values.put("USER_ID", member.user);
                values.put("LOCAL_NAME", member.displayName);
                values.put("ROLE", member.role);
                values.put("JOIN_TIME", member.joinTime * 1000L); //转为毫秒
                batch[i++] = values;

                // TODO: 存储 userinfo，头像下载应该延时处理
//            if(member.userInfo!= null && member.userInfo.portraitVersion != 0){
//                downloadPortrait(context, member.userInfo.userId);
//            }
            }
            resolver.bulkInsert(GroupMemberContentProvider.CONTENT_URI, batch);
        }else{
            // 群成员没变化
        }
       
        
        // 3. TODO: update group info&version
    }

    /**
     * 处理群组列表, 更新群组数据
     * @param context
     * @param intent
     */
    private void handleGroupList(Context context, Intent intent){
        String sql = "CREATE TABLE 'GROUP' (" +
                "'_id' INTEGER PRIMARY KEY ," +
                "'GROUP_ID' INTEGER ," +
                "'GROUP_NAME' TEXT ," +
                "'GROUP_VERSION' TEXT " +
                ");";

        GroupListSession session = (GroupListSession) BroadcastActions.getSession(intent);
        ContentResolver resolver = context.getContentResolver();

        if(session.syncMode == 1) { // 增量更新
            for(Group group: session.groups) {
                if (group.action == 1) {
                    //添加群：写入 DB
                    ContentValues values = new ContentValues();
                    values.put("GROUP_ID", group.uri);
                    values.put("GROUP_NAME", group.subject);
                    resolver.insert(GroupContentProvider.CONTENT_URI, values);
                    //刷新详情
                    updateGroupInfo(Integer.parseInt(group.uri));
                } else if (group.action == 2) {
                    //删除群 TODO: 根据产品需求也可以删除 CHAT_MESSAGE 表
                    resolver.delete(GroupContentProvider.CONTENT_URI, "GROUP_ID=?", new String[] {group.uri});
                    resolver.delete(GroupMemberContentProvider.CONTENT_URI, "GROUP_ID=?", new String[] {group.uri});
                } else if(group.action == 3){
                    // 群更新
                    updateGroupInfo(Integer.parseInt(group.uri));
                }
            }
        } else {
            // 全量更新
            // 注意: 真实环境中,需要使用事物保证数据完整性: http://stackoverflow.com/a/3943917/189961

            // 删除所有的元数据
            resolver.delete(GroupContentProvider.CONTENT_URI, null, null);

            if(session.groups == null) {
                return;
            }

            // 将新数据全部存储
            ContentValues[] batch =new ContentValues[session.groups.length];
            int i = 0;
            for(Group group: session.groups) {
                ContentValues values = new ContentValues();
                values.put("GROUP_ID", group.uri);
                values.put("GROUP_NAME", group.subject);
                batch[i++] = values;
            }
            resolver.bulkInsert(GroupContentProvider.CONTENT_URI, batch);
            
            for(Group group: session.groups) {
                //刷新群组详情
                updateGroupInfo(Integer.parseInt(group.uri));
            }
        }
    }

    /**
     * 处理群组广播事件, 和 GroupEventSession 不同的是,这里是群组其他人员信息的变化
     * @param context
     * @param intent
     */
    private void handleGroupNotify(Context context, Intent intent) {
        GroupNotificationSession session = (GroupNotificationSession) BroadcastActions.getSession(intent);
        /*
                String sql = "CREATE TABLE 'CHAT_MESSAGE' ( " +
                "'_id' INTEGER PRIMARY KEY ," +
                "'IMDN_ID' TEXT NOT NULL ," +
                "'MSG_FROM' TEXT," +
                "'MSG_TO' TEXT," +
                "'RECEIVE_TIME' INTEGER," +
                "'CONTENT' TEXT," +
                "'CHAT_TYPE' INTEGER," +
                "'CONTENT_TYPE' INTEGER," +
                "'STATUS' INTEGER," +
                "'TRANSFER_ID' TEXT," +
                "'PROGRESS' INTEGER," +
                "'FILE_SIZE' INTEGER" +
                ");";
         */

        //将广播事件直接写入 Message 表即可,因为这些信息多数会展现在聊天页面
        ContentValues values = new ContentValues();
        values.put("IMDN_ID", UUID.randomUUID().toString());
        values.put("MSG_FROM", session.groupUri);
        values.put("RECEIVE_TIME", session.time * 1000L);
        values.put("CONTENT", JsonUtils.toJson(session)); // 将事件内容转为 JSON， 保存在DB中, 方便界面展示
        values.put("CHAT_TYPE", ChatType.GROUP.value());
        values.put("CONTENT_TYPE", ContentType.NOTIFICATION.value());
        context.getContentResolver().insert(MessageContentProvider.CONTENT_URI, values);
        Log.d(TAG, "GroupNotify saved to Message Table");

        //注意: 如果群组信息(成员等信息) 需要保证强一致,这里可以判断 Notify 的类型, 根据不同类型来进行 subGroupInfo 操作
    }

    /***
     * 处理群组事件通知, 群组之中,涉及自己的通知信息
     * @param context
     * @param intent
     */
    private void handleGroupEvt(Context context, Intent intent) {
        GroupEventSession session = (GroupEventSession) BroadcastActions.getSession(intent);
        if(session.handleResult == 0 ) {
            //TODO: 这里也可以尝试发送一个 Notification
        }
        ContentValues values = new ContentValues();
        values.put("SOURCE_USER", session.source);
        values.put("SOURCE_USER_NICKNAME", session.sourceNickname);
        values.put("TIME", session.time * 1000L); //转化为毫秒
        values.put("GROUP_ID", session.groupUri);
        values.put("HANDLE_RESULT", session.handleResult);
        values.put("EVENT", session.eventType);

        context.getContentResolver().insert(GroupEvtContentProvider.CONTENT_URI,
                values);

        //刷新群组详情
        updateGroupInfo(Integer.parseInt(session.groupUri));
    }

    private void handleMessageResult(Context context, Intent intent) {
        MessageResult result = BroadcastActions.getResult(intent);
        ContentValues values = new ContentValues();
        // 消息发送成功
        if(result.errorCode == ErrorCode.OK.value()) {
            values.put("STATUS", MessageContentProvider.STATUS_SENT);
        }
        // 消息发送失败
        else {
            values.put("STATUS", MessageContentProvider.STATUS_FAILED);
        }

        context.getContentResolver().update(MessageContentProvider.CONTENT_URI,
                values, "IMDN_ID=?", new String[] {result.imdnId});
    }

    private boolean checkMessageExists(Context context, String imdnId, boolean delivered, boolean read, boolean open) {
        Cursor cursor = context.getContentResolver().query(MessageContentProvider.CONTENT_URI,
                null, "IMDN_ID=?", new String[]{imdnId}, null);

        //查看数据库中是否已经存在当前条目, 如果存在，更新其状态
        if (cursor != null && cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex("STATUS"));
            if (status < 0 || status == MessageContentProvider.STATUS_SENDING) {
                status = MessageContentProvider.STATUS_SENT;
            }
            if (delivered) {
                status = MessageContentProvider.STATUS_DELIVERED;
            }
            if (read) {
                status = MessageContentProvider.STATUS_READ;
            }
            if (open) {
                status = MessageContentProvider.STATUS_OPEN;
            }
            ContentValues values = new ContentValues();
            values.put("STATUS", status);
            context.getContentResolver().update(MessageContentProvider.CONTENT_URI, values, "IMDN_ID=?",
                    new String[]{imdnId});
            return true;
        }
        return false;
    }

    private void handleTextMessage(Context context, Intent intent) {
        TextMessageSession session = (TextMessageSession) BroadcastActions.getSession(intent);
        Log.d(TAG, "handle textMessage:" + JsonUtils.toJson(session));

        //查看数据库中是否已经存在当前条目
        if (!checkMessageExists(context, session.imdnId, session.isDelivered, session.isRead, session.isOpen)) {
            //写入新新消息
            ContentValues values = new ContentValues();
            values.put("IMDN_ID", session.imdnId);
            values.put("MSG_FROM", session.from);
            values.put("MSG_TO", session.to);
            values.put("RECEIVE_TIME", session.sendTime * 1000L); //second to millsecond
            values.put("CONTENT", session.content);
            values.put("CHAT_TYPE", session.chatType);
            values.put("CONTENT_TYPE", ContentType.TEXT.value());
            if (session.from.equals(LoginState.getUserId(context) + "")) {
                values.put("STATUS", MessageContentProvider.STATUS_SENT);
            } else {
                values.put("STATUS", MessageContentProvider.STATUS_RECEIVED);
            }
            context.getContentResolver().insert(MessageContentProvider.CONTENT_URI, values);

            //如果发件人是自己，则是多端同步，直接返回
            if(session.from.equals(LoginState.getUserId(context) + "")) {
                return;
            }
            
            // TODO: 消息回执， 如果不需要不要打开
            
//            //发送消息送达报告、已读报告
//            ReportMessageArg rma1 = null;
//            ReportMessageArg rma2 = null;
//            if (session.chatType == ChatType.GROUP.value()) {
//                rma1 = new ReportMessageArg(session.from, session.imdnId, ReportType.GROUP_DELIVERED);
//                rma2 = new ReportMessageArg(session.from, session.imdnId, ReportType.GROUP_READ);
//            }
//            else {
//                rma1 = new ReportMessageArg(session.from, session.imdnId, ReportType.DELIVERED);
//                rma2 = new ReportMessageArg(session.from, session.imdnId, ReportType.READ);
//            }
//            try {
//                if(session.needReport) {
//                    RCSMessageManager.sendReportMessage(LoginState.getStartUser(context), rma1, null);
//                }
//                if(session.needReadReport) {
//                    RCSMessageManager.sendReportMessage(LoginState.getStartUser(context), rma2, null);
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "", e);
//            }
        }
    }

    // 处理消息报告
    private void handleReportMessage(Context context, Intent intent) {
        ReportMessageSession session = (ReportMessageSession) BroadcastActions.getSession(intent);
        Log.d(TAG, "handle report:"+ JsonUtils.toJson(session));

        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        // 文件进度
        if(session.reportType == ReportType.FILE_PROGRESS.value()) {
            String progress = session.reportValue.split("/")[0];
            values.put("PROGRESS", Integer.parseInt(progress));
            resolver.update(MessageContentProvider.CONTENT_URI, values, "IMDN_ID=?", new String[] {session.imdnId});
        }
        // 投递报告
        else if(session.reportType == ReportType.DELIVERED.value() || session.reportType == ReportType.GROUP_DELIVERED.value()) {
            values.put("STATUS", MessageContentProvider.STATUS_DELIVERED);
            resolver.update(MessageContentProvider.CONTENT_URI, values, "IMDN_ID=?", new String[]{session.imdnId});
        }
        // 更新消息 ID
        else if(session.reportType == ReportType.UPDATE_MSG_ID.value()) {
            values.put("IMDN_ID", session.reportValue);
            resolver.update(MessageContentProvider.CONTENT_URI, values, "IMDN_ID=?", new String[]{session.imdnId});
        }
        //消息已读报告
        else if (session.reportType == ReportType.READ.value() || session.reportType == ReportType.GROUP_DELIVERED.value()) {
            values.put("STATUS", MessageContentProvider.STATUS_READ);
            resolver.update(MessageContentProvider.CONTENT_URI, values, "IMDN_ID=?", new String[] {session.imdnId});
        }
    }



    private void handleFileMessage(Context context, Intent intent) {
        FTMessageSession session = (FTMessageSession) BroadcastActions.getSession(intent);
        Log.d(TAG, "handle file:"+ JsonUtils.toJson(session));

        //查看数据库中是否已经存在当前条目
        if (!checkMessageExists(context, session.imdnId, session.isDelivered, session.isRead, session.isOpen)) {
            ContentValues values = new ContentValues();
            values.put("IMDN_ID", session.imdnId);
            values.put("MSG_FROM", session.from);
            values.put("MSG_TO", session.to);
            values.put("RECEIVE_TIME", session.sendTime * 1000L); //second to millsecond
            values.put("CONTENT", session.filePath);
            values.put("CHAT_TYPE", session.chatType);
            values.put("CONTENT_TYPE", session.contentType);
            values.put("TRANSFER_ID", session.transferId);
            if (session.from.equals(LoginState.getUserId(context) + "")) {
                values.put("STATUS", MessageContentProvider.STATUS_RECEIVED);
            } else {

                values.put("STATUS", MessageContentProvider.STATUS_RECEIVED);
            }
            values.put("FILE_SIZE", session.fileSize);
            if (!TextUtils.isEmpty(session.filePath)) {
                values.put("PROGRESS", session.fileSize);
                values.put("STATUS", MessageContentProvider.STATUS_DOWNLOADED);
            }
            context.getContentResolver().insert(MessageContentProvider.CONTENT_URI, values);

            //如果发件人是自己，则是多端同步，直接返回
            if(session.from.equals(LoginState.getUserId(context) + "")) {
                return;
            }

            // TODO: 消息回执， 如果不需要不要打开
//            //发送消息送达报告、已读报告
//            ReportMessageArg rma1 = null;
//            ReportMessageArg rma2 = null;
//            if (session.chatType == ChatType.GROUP.value()) {
//                rma1 = new ReportMessageArg(session.from, session.imdnId, ReportType.GROUP_DELIVERED);
//                rma2 = new ReportMessageArg(session.from, session.imdnId, ReportType.GROUP_READ);
//            }
//            else {
//                rma1 = new ReportMessageArg(session.from, session.imdnId, ReportType.DELIVERED);
//                rma2 = new ReportMessageArg(session.from, session.imdnId, ReportType.READ);
//            }
//            try {
//                if(session.needReport) {
//                    RCSMessageManager.sendReportMessage(LoginState.getStartUser(context), rma1, null);
//                }
//                if(session.needReadReport) {
//                    RCSMessageManager.sendReportMessage(LoginState.getStartUser(context), rma2, null);
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "", e);
//            }
        }
    }

    public static void downloadPortrait(Context context, int userId) {
        Log.e(TAG, "downloadPortrait:"+userId);
        try {
            //下载头像：
            String action = "com.intercs.uwsdemo.get-portrailt";
            Intent ni = new Intent(action);
            ni.putExtra("userId", userId);
            ni.setExtrasClassLoader(context.getClassLoader());
            ni.getExtras().setClassLoader(context.getClassLoader());
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, ni, PendingIntent.FLAG_UPDATE_CURRENT);

            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    context.unregisterReceiver(this);
                    UserPortraitResult result = BroadcastActions.getResult(intent);
                    if (result == null) {
                        Log.e(TAG, "cannot get userinforesult from intent");
                        return;
                    }

                    Log.d(TAG, "download result:"+ JsonUtils.toJson(result));
                    Log.d(TAG, "userid"+ intent.getExtras().getInt("userId"));

                    //下载成功，更新数据库
                    if (result.errorCode == ErrorCode.OK.value()) {
                        ContentValues cv = new ContentValues();
                        cv.put("PORTRAIT_VERSION", result.version);
                        cv.put("PORTRAIT_PATH", result.filePath);
                        context.getContentResolver().update(BuddyContentProvider.CONTENT_URI,
                                cv, "USER_ID=?", new String[]{intent.getExtras().getInt("userId")+""});
                    } else if (result.errorCode == ErrorCode.NOT_EXSIT.value()){
                        ContentValues cv = new ContentValues();
                        cv.put("PORTRAIT_VERSION", 0);
                        cv.put("PORTRAIT_PATH", "");
                        context.getContentResolver().update(BuddyContentProvider.CONTENT_URI,
                                cv, "USER_ID=?", new String[]{intent.getExtras().getInt("userId")+""});
                    }
                }
            };
            context.registerReceiver(receiver, new IntentFilter(action));
            UserInfoManager.getPortrait(LoginState.getStartUser(context), userId, true, pi);
        } catch (RemoteException e) {
            Log.e(TAG, "download portrait error!", e);
            //下载失败处理： 也可以将当前任务保存起来（SP, DB) 后续进行重试
        }
    }

    private void handleUserInfoResult(Context context, Intent intent) {
        // 批量获取好友成功，刷新数据库
        UserInfoResult result = BroadcastActions.getResult(intent);
        if(result == null) {
            Log.e(TAG, "cannot get userinforesult from intent");
            return;
        }

        if (result.userInfos != null) {
            Log.d(TAG, "handleUserInfoResult count:" + result.userInfos.length);
            for (UserInfo info: result.userInfos) {
                Cursor c = context.getContentResolver().query(BuddyContentProvider.CONTENT_URI, null,
                        "USER_ID=?", new String[]{info.userId+""},  null);
                try {
                    ContentValues cv = new ContentValues();
                    cv.put("NICK_NAME", info.nickname);
                    cv.put("IMPRESA", info.impresa);

                    //检查头像版本号
                    boolean protrait_changed = false;
                    if (c != null && c.moveToFirst()) {
                        int ver = c.getInt(c.getColumnIndex("PORTRAIT_VERSION"));
                        //无头像
                        if (info.portraitVersion == 0) { //无头像，清空当前
                            cv.put("PORTRAIT_VERSION", 0);
                            cv.put("PORTRAIT_PATH", "");
                        } else {
                            if (info.portraitVersion != ver) {
                                //头像版本号不同，需要刷新下载
                                protrait_changed = true;
                            }
                        }
                    }else if (info.portraitVersion != 0){
                         // 新的数据，头像不为空则下载
                         protrait_changed = true;
                    }
                    
                    // TODO： 头像更新的时机 有客户端自己定，可以不必立即更新
                    // 客户端需要先标记头像是否变更，因为这里下载也可能失败，需要有重试机制
                    if(protrait_changed){
                        downloadPortrait(context, info.userId);
                    }

                    //更新 Buddy 表
                    context.getContentResolver().update(BuddyContentProvider.CONTENT_URI, cv, "USER_ID=?",
                            new String[]{info.userId + ""});
                }catch (Exception ex){
                    Log.e(TAG, "", ex);
                }finally {
                    if(c != null) {
                        c.close();
                    }
                }
            }
        }
    }

    // 处理好友列表刷新
    private void handleBuddyList(Context context, Intent intent) {
        Log.d(TAG, "handleBuddyList");
        BuddyListSession listSession = (BuddyListSession)BroadcastActions.getSession(intent);
        if (listSession == null) {
            Log.e(TAG, "cannot get buddy list session from intetnt!");
            return;
        }

        Log.d(TAG, "handleBuddyList, syncMode " + listSession.syncMode);
        Log.d(TAG, "handleBuddyList " + JsonUtils.toJson(listSession));
        if(listSession.syncMode == BuddyListSession.SYNC_MODE_FULL) {
            // 全量刷新好友列表
            syncFullBuddyList(context, listSession);
        } else {
            // 差量同步好友列表
            syncPartialBuddyList(context, listSession);
        }
    }


    private void syncPartialBuddyList(Context context, BuddyListSession listSession) {
        Log.d(TAG, "syncPartialBuddyList");

        if(listSession.partial != null) {
            for(BuddyInfo info : listSession.partial) {
                if(info == null) {
                    continue;
                }

                try {
                    ContentResolver resolver = context.getContentResolver();
                    if(info.action == BuddyInfo.ACTION_DELETE) { //删除好友
                        resolver.delete(BuddyContentProvider.CONTENT_URI, "USER_ID=?", new String[] {info.userId+""});
                    } else if(info.action == BuddyInfo.ACTION_ADD) { // 新增好友
                        Cursor cursor = context.getContentResolver().query(BuddyContentProvider.CONTENT_URI, null,
                                "USER_ID=?", new String[] {info.userId+""}, null);

                        //如果存在当前好友，则更新数据库信息
                        if(cursor != null && cursor.getCount() > 0) {
                            ContentValues values = getBuddyContentValues(info);
                            resolver.update(BuddyContentProvider.CONTENT_URI, values, "USER_ID=?", new String[] {info.userId+""});
                        }
                        else {
                            insertOneBuddy(context, info);
                        }
                    } else if(info.action == BuddyInfo.ACTION_UPDATE) { //更新好友信息
                        ContentValues values = getBuddyContentValues(info);
                        resolver.update(BuddyContentProvider.CONTENT_URI, values, "USER_ID=?", new String[] {info.userId+""});
                    }
                } catch (Exception e) {
                    Log.e(TAG, "handle buddy partial list error", e);
                }
            }
        }

    }

    private ContentValues getBuddyContentValues(BuddyInfo buddyInfo) {
        ContentValues values = new ContentValues();
        values.put("USER_ID", buddyInfo.userId);
        if(!TextUtils.isEmpty(buddyInfo.localName)) {
            values.put("LOCAL_NAME", buddyInfo.localName);
        }
        if(buddyInfo.userInfo != null) {
            values.put("PORTRAIT_VERSION", buddyInfo.userInfo.portraitVersion);

            if(buddyInfo.userInfo.username != null) {
                values.put("USER_NAME", buddyInfo.userInfo.username);
            }
            if(buddyInfo.userInfo.impresa != null) {
                values.put("IMPRESA", buddyInfo.userInfo.impresa);
            }
            if(buddyInfo.userInfo.nickname != null) {
                values.put("NICK_NAME", buddyInfo.userInfo.nickname);
            }
        }
        return values;
    }

    private void insertOneBuddy(Context context, BuddyInfo buddyInfo) {
        ContentValues values = getBuddyContentValues(buddyInfo);
        context.getContentResolver().insert(BuddyContentProvider.CONTENT_URI, values);
    }
    
    private void insertBatchBuddy(Context context, BuddyInfo[] buddyInfos) {
        ContentValues[] batch = new ContentValues[buddyInfos.length];
        int i=0;
        for(BuddyInfo info : buddyInfos) {
            ContentValues value = getBuddyContentValues(info);
            batch[i++] = value;
        }
        context.getContentResolver().bulkInsert(BuddyContentProvider.CONTENT_URI, batch);
    }

    private void syncFullBuddyList(Context context, BuddyListSession listSession) {
        Log.d(TAG, "syncFullBuddyList");
        //NOTE: 在生产环境中，更建议通过使用 ContentProviderOperation 在事物中实现如下操作

        //删除所有现有好友
        context.getContentResolver().delete(BuddyContentProvider.CONTENT_URI, null, null);
        ArrayList<String> arr = new ArrayList<String>();
        //添加最新好友
        if(listSession.full != null) {
            insertBatchBuddy(context, listSession.full);
            for(BuddyInfo buddyInfo: listSession.full) {
                arr.add(buddyInfo.userId + "");
            }

            // 批量获取 UserInfo
            String ids = TextUtils.join(";",arr);
            try {
                UserInfoManager.getUserInfo(LoginState.getStartUser(getApplicationContext()), ids, null);
            } catch (RemoteException e) {
                Log.e(TAG, "batch get user info error:", e);
            }
        }
    }

    private void handleBuddyEvent(Context context, Intent intent) {
        //获取通知事件
        BuddyEventSession besession = (BuddyEventSession)BroadcastActions.getSession(intent);
        if(besession == null) {
            Log.e(TAG, "cannot get buddy event session from intetnt!");
            return;
        }

        // 将通知写入数据库
        ContentValues values = new ContentValues();
        values.put("FROM_USER_ID", besession.fromUser);
        values.put("TO_USER_ID", besession.toUser);
        values.put("REASON", besession.reason);
        values.put("TIME", besession.time * 1000L); // 转化为毫秒
        if(besession.op == BuddyOpEnum.ADDED_BUDDY.value()) {
            values.put("STATE", 1); // 被添加为好友
        } else {
            values.put("STATE", 2); // 添加请求的处理结果通知
        }
        if(besession.userInfo != null) {
            values.put("FROM_USER_NAME", besession.userInfo.nickname);
        }
        values.put("REFUSE_REASON", besession.reason);
        context.getContentResolver().insert(BuddyEvtContentProvider.CONTENT_URI, values);

        //如果需要，也可以在此处发送 Notification
    }

}
