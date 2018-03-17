package com.feinno.sdk.dapi;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.RemoteException;

import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.args.*;
import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.DirectedType;
import com.feinno.sdk.enums.MsgTypeEnum;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.result.MessageResult;
import com.feinno.sdk.result.ShareFileListResult;
import com.feinno.sdk.result.v3.UserProfileResult;
import com.feinno.sdk.utils.LogUtil;

import java.util.List;

/**
 * RCS消息接口
 */
public class RCSMessageManager {
	private static final String TAG = "RCSMessageManager";

    /**
     * 发送一条消息（包括群组消息，公众账号消息），支持文本消息，文件，阅后即焚，广播消息等类型
     * @param owner 操作用户
     * @param arg 消息参数，此处可接收
     *                      {@link TextMessageArg}（文本消息），
     *                      {@link FTMessageArg}（文件传输）。
	 * @param resultIntent 消息发送之后，需要触发的PendingIntent，
	 *                      通过{@link BroadcastActions#getResult(Intent)}可以获取对应的{@link MessageResult}对象.
	 *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_MESSAGE_SEND_RESULT}的广播，
	 *                      通过{@link BroadcastActions#getResult(Intent)}可以获取对应的{@link MessageResult}对象，
	 * @throws Exception
	 */
    public static void sendMessage(String owner, MessageArg arg, PendingIntent resultIntent) throws Exception {
        LogUtil.i(TAG, "sendMessage, owner = " + owner);

        if (arg.getChatUri().getChatType().equals(ChatType.GROUP)) {
            LogUtil.i(TAG, "Group");
            sendGroupChat(owner, arg, resultIntent);
        } else if (arg.getChatUri().getChatType().equals(ChatType.SINGLE)) {
            LogUtil.i(TAG, "Single");
            sendChat(owner, arg, resultIntent);
        } else if (arg.getChatUri().getChatType().equals(ChatType.BROADCAST)) {
            LogUtil.i(TAG, "Broadcast");
            sendBroadcast(owner, arg, resultIntent);
        } else if (arg.getChatUri().getChatType().equals(ChatType.PUBLIC_ACCOUNT)) {
            LogUtil.i(TAG, "PublicAccount");
            sendPublicAccountMessage(owner, arg, resultIntent);
        } else if (arg.getChatUri().getChatType().equals(ChatType.DIRECTED)) {
            LogUtil.i(TAG, "Directed");
            sendDirected(owner, arg, resultIntent);
        }
    }

    /**
     * 发送一条消息的回执
     * @param owner 操作用户
     * @param arg 回执消息参数，参见{@link ReportMessageArg}（回执报告消息）。
     * @param resultIntent 消息发送之后，需要触发的PendingIntent，
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link MessageResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_MESSAGE_SEND_RESULT}的广播，
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link MessageResult}对象。
     * @throws Exception
     */
    public static void sendReportMessage(String owner, ReportMessageArg arg, PendingIntent resultIntent) throws Exception {
        LogUtil.i(TAG, "report message arg");
        WorkingServiceProxy.instance().msgSendReport(owner,
                arg.getMessageTo(),
                arg.getImdnID(),
                arg.getReportType().value(),
                arg.getDirectedType().value(),
                arg.getTarget(),
                resultIntent);
    }

    /**
     *
     * @param owner 操作用户
     * @param number 消息发送者，Uid 或 GroupId
     * @param imdnId 设置消息imdn Id
     * @param msgState 文件状态  1:已打开 (如媒体文件已经播放) 2:已删除 (如媒体文件已删除)
     * @param chatType 聊天类型，参见{@link ChatType}
     * @param resultIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_ENDPOINT_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     * @throws Exception
     */
    public static void msgSetStatus(String owner, String number, String imdnId, int msgState, ChatType chatType, PendingIntent resultIntent) throws Exception {
        LogUtil.i(TAG, "set conv arg");
        WorkingServiceProxy.instance().msgSetStatus(owner,number,imdnId,msgState,chatType.value(),resultIntent);
    }

    /**
     *
     * @param owner 操作用户
     * @param convId 为客户端会话Id，Uid 或 GroupId
     * @param maxImdnId 回话中最后一条消息或notice的imdnId
     * @param convState 会话状态  1:会话已读 2:会话删除
     * @param chatType 聊天类型，参见{@link ChatType}
     * @param resultIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_ENDPOINT_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     * @throws Exception
     */
    public static void msgSetConvStatus(String owner, String convId, String maxImdnId, int convState, ChatType chatType, PendingIntent resultIntent) throws Exception {
        LogUtil.i(TAG, "set conv arg");
        WorkingServiceProxy.instance().msgSetConvStatus(owner,convId,maxImdnId,convState,chatType.value(),resultIntent);
    }

    /**
     * 拉取文件
     * @param owner 操作用户
     * @param arg 消息参数，此处接收{@link FetchFileMessageArg}
	 * @param resultIntent 文件拉取之后，需要触发的PendingIntent，
	 *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link MessageResult}对象。
	 *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_MESSAGE_SEND_RESULT}的广播，
	 *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link MessageResult}对象。
	 * @throws Exception
     */
    public static void fetchFile(String owner, FetchFileMessageArg arg, PendingIntent resultIntent) throws Exception {
        LogUtil.i(TAG, "fetchFile, owner = " + owner);

        WorkingServiceProxy.instance().msgFetchFile(owner,
                arg.getChatUri().getUri(),
                arg.getMessageID(),
                arg.getChatUri().getChatType().value(),
                arg.getFilePath(),
                arg.getContentType().value(),
                arg.getFileName(),
                arg.getTransferId(),
                arg.getStart(),
                arg.getFileSize(),
                arg.getFileHash(),
                arg.isTransient(),
                resultIntent);
    }

    public static void fetchHttpFile(String owner, FetchFileMessageArg arg, PendingIntent resultIntent) throws Exception {
        LogUtil.i(TAG, "fetchHttpFile, owner = " + owner);
        WorkingServiceProxy.instance().msgHttpFetch(owner,
                arg.getMessageID(),
                arg.getContentType().value(),
                arg.getFilePath(),
                arg.getStart(),
                arg.getFileSize(),
                arg.getOriginalLink(),
                resultIntent);
    }

    /**
     * 上传共享文件
     * @param owner 操作用户
     * @param targetId 共享目标Id,此处指群Id
     * @param messageId 消息Id,用于发送文件进度
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param expire 文件保存天数
     * @param resultIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_UPLOAD_SHARE_FILE_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     * @throws Exception
     */
    public static void uploadShareFile(String owner, String targetId, String messageId, String filePath, String fileName, int expire, PendingIntent resultIntent) throws Exception {
        WorkingServiceProxy.instance().uploadShareFile(owner, targetId, messageId, filePath, fileName, expire, resultIntent);
    }

    /**
     * 下载共享文件
     * @param owner 操作用户
     * @param targetId 共享目标Id,此处指群Id
     * @param messageId 消息Id,用于发送文件进度
     * @param fileId 文件传输Id
     * @param shareFileId 共享文件的标识Id
     * @param start 开始传送位置
     * @param size 文件大小
     * @param filePath 文件保存路径
     * @param resultIntent 消息发送之后，需要触发的PendingIntent，
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取对应的{@link MessageResult}对象.
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_FETCH_SHARE_FILE_RESULT}的广播，
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取对应的{@link MessageResult}对象，
     * @throws Exception
     */
    public static void fetchShareFile(String owner, String targetId, String messageId, String fileId, String shareFileId, int start, int size, String filePath, PendingIntent resultIntent) throws Exception {
        WorkingServiceProxy.instance().fetchShareFile(owner, targetId, messageId, fileId, shareFileId, start, size, filePath, resultIntent);
    }

    /**
     * 删除共享文件
     * @param owner 操作用户
     * @param targetId 共享目标Id,此处指群Id
     * @param fileId 文件传输Id
     * @param shareFileId 共享文件的标识Id
     * @param resultIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_DELETE_SHARE_FILE_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     * @throws Exception
     */
    public static void deleteShareFile(String owner, String targetId, String fileId, String shareFileId, PendingIntent resultIntent) throws Exception {
        WorkingServiceProxy.instance().deleteShareFile(owner, targetId, fileId, shareFileId, resultIntent);
    }

    /**
     * 获取共享文件列表
     * @param owner 操作用户
     * @param targetId 共享目标Id,此处指群Id
     * @param resultIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ShareFileListResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_GET_SHARE_FILE_LIST_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ShareFileListResult}对象。
     * @throws Exception
     */
    public static void getShareFileList(String owner, String targetId, PendingIntent resultIntent) throws Exception {
        WorkingServiceProxy.instance().getShareFileList(owner, targetId, resultIntent);
    }

    /**
     * 取消文件上传下载操作
     * @param owner 操作用户
     * @param messageId 消息Id
     * @param resultIntent 消息发送之后，需要触发的PendingIntent，
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取对应的{@link MessageResult}对象.
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_CANCEL_TRANSFER_RESULT}的广播，
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取对应的{@link MessageResult}对象，
     *  @throws Exception
     */
    public static void cancelTransfer(String owner, String messageId, PendingIntent resultIntent) throws Exception {
        WorkingServiceProxy.instance().cancelTransfer(owner, messageId, resultIntent);
    }

    /**
     * 设置业务通知已读,设置已读状态后,新设备激活用户不再推送该通知
     * @param owner 操作用户
     * @param imdnId 对应业务Event通知的imdnId
     * @param resultIntent 消息发送之后，需要触发的PendingIntent，
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取对应的{@link ActionResult}对象.
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_SET_NOTIFY_READ_RESULT}的广播，
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取对应的{@link ActionResult}对象，
     *  @throws Exception
     */
    public static void setNotifyRead(String owner, String imdnId, PendingIntent resultIntent) throws Exception {
        WorkingServiceProxy.instance().setNotifyRead(owner, imdnId, resultIntent);
    }

    /**
     * 消息转短链接接口
     * @param owner 操作用户
     * @param imdnId 客户端生成的消息唯一id
     * @param msgType 消息类型 {@link MsgTypeEnum}
     * @param content 消息内容 msgType 对应 1 Text
     * @param filePath 文件路径 msgType 对应 2,3
     */
    public static void msg2Shorturl(String owner, String imdnId, int msgType, String content, String filePath, PendingIntent resultIntent) throws Exception{
        LogUtil.i(TAG, "msg2Shorturl, owner = " + owner);
        WorkingServiceProxy.instance().msg2Shorturl(owner, imdnId, msgType, content, filePath, resultIntent);
    }

    private static void sendChat(String owner, MessageArg arg, PendingIntent resultIntent) throws Exception {
		LogUtil.i(TAG, "sendChat, owner = " + owner);
        if (arg instanceof TextMessageArg) {
			LogUtil.i(TAG, "text message arg");
            TextMessageArg textArg = (TextMessageArg) arg;
            WorkingServiceProxy.instance().msgSendText(owner,
                    textArg.getChatUri().getUri(),
                    textArg.getMessageID(),
                    textArg.getContent(),
                    textArg.needReport(),
                    textArg.isTransient(),
                    DirectedType.NONE.value(),
                    textArg.getNeedReadReport(),
                    textArg.getExtension(),
                    textArg.getContentType().value(),
                    resultIntent);
        } else if (arg instanceof FTMessageArg) {
			LogUtil.i(TAG, "file transfer message arg");
            FTMessageArg ftArg = (FTMessageArg) arg;
            WorkingServiceProxy.instance().msgSendFile(owner,
                    ftArg.getChatUri().getUri(),
                    ftArg.getMessageID(),
                    ftArg.getFilePath(),
                    ftArg.getContentType().value(),
                    ftArg.getFileName(),
                    ftArg.needReport(),
                    ftArg.getStart(),
                    ftArg.getThumbnail(),
                    ftArg.isTransient(),
                    DirectedType.NONE.value(),
                    ftArg.getNeedReadReport(),
                    ftArg.getExtension(),
                    resultIntent);
        } else if (arg instanceof VemoticonMessageArg) {
            LogUtil.i(TAG, "send vemoticon message");
            VemoticonMessageArg vArg = (VemoticonMessageArg) arg;
            WorkingServiceProxy.instance().msgSendVemoticon(owner,
                    vArg.getChatUri().getUri(),
                    vArg.getMessageID(),
                    vArg.getVemoticonId(),
                    vArg.getVemoticonName(),
                    vArg.needReport(),
                    vArg.isTransient(),
                    DirectedType.NONE.value(),
                    resultIntent);
        } else if (arg instanceof CloudfileMessageArg) {
            LogUtil.i(TAG, "send cloudfile message");
            CloudfileMessageArg cfArg = (CloudfileMessageArg) arg;
            WorkingServiceProxy.instance().msgSendCloudfile(owner,
                    cfArg.getChatUri().getUri(),
                    cfArg.getMessageID(),
                    cfArg.getFileName(),
                    cfArg.getFileSize(),
                    cfArg.getFileUrl(),
                    cfArg.needReport(),
                    cfArg.isTransient(),
                    DirectedType.NONE.value(),
                    resultIntent);
        }
    }

	private static void sendBroadcast(String owner, MessageArg arg, PendingIntent resultIntent) throws Exception {
		LogUtil.i(TAG, "sendBroadcast, owner = " + owner);

        List<String> uris = arg.getChatUri().getUriList();

        String recipients = "";
        for (String uri : uris) {
            recipients += uri + ";";
        }
        if (recipients.endsWith(";")) {
            recipients.substring(0, recipients.length()-1);
        }

        if (arg instanceof TextMessageArg) {
			LogUtil.i(TAG, "broadcast message arg");
            TextMessageArg textArg = (TextMessageArg) arg;

			WorkingServiceProxy.instance().msgSendText(owner,
                    textArg.getChatUri().getUri(),
                    textArg.getMessageID(),
                    textArg.getContent(),
                    textArg.needReport(),
                    textArg.isTransient(),
                    DirectedType.NONE.value(),
                    textArg.getNeedReadReport(),
                    textArg.getExtension(),
                    textArg.getContentType().value(),
                    resultIntent);
		} else if (arg instanceof FTMessageArg) {
            LogUtil.i(TAG, "file transfer message arg");
            FTMessageArg ftArg = (FTMessageArg) arg;
            WorkingServiceProxy.instance().msgSendFile(owner,
                    ftArg.getChatUri().getUri(),
                    ftArg.getMessageID(),
                    ftArg.getFilePath(),
                    ftArg.getContentType().value(),
                    ftArg.getFileName(),
                    ftArg.needReport(),
                    ftArg.getStart(),
                    ftArg.getThumbnail(),
                    ftArg.getDuration(),
                    ftArg.isTransient(),
                    DirectedType.NONE.value(),
                    ftArg.getNeedReadReport(),
                    ftArg.getExtension(),
                    resultIntent);
        } else if (arg instanceof VemoticonMessageArg) {
            LogUtil.i(TAG, "send vemoticon message");
            VemoticonMessageArg vArg = (VemoticonMessageArg) arg;
            WorkingServiceProxy.instance().msgSendVemoticon(owner,
                    vArg.getChatUri().getUri(),
                    vArg.getMessageID(),
                    vArg.getVemoticonId(),
                    vArg.getVemoticonName(),
                    vArg.needReport(),
                    vArg.isTransient(),
                    DirectedType.NONE.value(),
                    resultIntent);
        } else if (arg instanceof CloudfileMessageArg) {
            LogUtil.i(TAG, "send cloudfile message");
            CloudfileMessageArg cfArg = (CloudfileMessageArg) arg;
            WorkingServiceProxy.instance().msgSendCloudfile(owner,
                    cfArg.getChatUri().getUri(),
                    cfArg.getMessageID(),
                    cfArg.getFileName(),
                    cfArg.getFileSize(),
                    cfArg.getFileUrl(),
                    cfArg.needReport(),
                    cfArg.isTransient(),
                    DirectedType.NONE.value(),
                    resultIntent);
        }
	}

    private static void sendGroupChat(String owner, MessageArg arg, PendingIntent resultIntent) throws Exception {
		LogUtil.i(TAG, "sendGroupChat, owner = " + owner);

        if (arg instanceof TextMessageArg) {
			LogUtil.i(TAG, "group text arg");
            TextMessageArg textArg = (TextMessageArg) arg;
            WorkingServiceProxy.instance().msgGpSendText(owner,
                    textArg.getChatUri().getUri(),
                    textArg.getMessageID(),
                    textArg.getContent(),
                    textArg.needReport(),
                    textArg.getccNumber(),
                    textArg.getNeedReadReport(),
                    textArg.getExtension(),
                    textArg.getContentType().value(),
                    resultIntent);
        } else if (arg instanceof FTMessageArg) {
			LogUtil.i(TAG, "group file transfer message arg");
            FTMessageArg ftArg = (FTMessageArg) arg;
            WorkingServiceProxy.instance().msgGpSendFile(owner,
                    ftArg.getChatUri().getUri(),
                    ftArg.getMessageID(),
                    ftArg.getFilePath(),
                    ftArg.getContentType().value(),
                    ftArg.getFileName(),
                    ftArg.needReport(),
                    ftArg.getStart(),
                    ftArg.getThumbnail(),
                    ftArg.getNeedReadReport(),
                    ftArg.getExtension(),
                    resultIntent);
        } else if (arg instanceof VemoticonMessageArg) {
            LogUtil.i(TAG, "send group vemoticon message");
            VemoticonMessageArg vArg = (VemoticonMessageArg) arg;
            WorkingServiceProxy.instance().msgGpSendVemoticon(owner,
                    vArg.getChatUri().getUri(),
                    vArg.getMessageID(),
                    vArg.getVemoticonId(),
                    vArg.getVemoticonName(),
                    vArg.needReport(),
                    resultIntent);
        } else if (arg instanceof CloudfileMessageArg) {
            LogUtil.i(TAG, "send group cloudfile message");
            CloudfileMessageArg cfArg = (CloudfileMessageArg) arg;
            WorkingServiceProxy.instance().msgGpSendCloudfile(owner,
                    cfArg.getChatUri().getUri(),
                    cfArg.getMessageID(),
                    cfArg.getFileName(),
                    cfArg.getFileSize(),
                    cfArg.getFileUrl(),
                    cfArg.needReport(),
                    resultIntent);
        }
    }

    private static void sendPublicAccountMessage(String owner, MessageArg arg, PendingIntent resultIntent) throws Exception {
		LogUtil.i(TAG, "send public account message, owner = " + owner);
		// TODO : send public account message. page-mode or large-mode message.
        if (arg instanceof TextMessageArg) {
            LogUtil.i(TAG, "text message arg");
            TextMessageArg textArg = (TextMessageArg) arg;

            WorkingServiceProxy.instance().msgPubSendText(owner,
                    textArg.getChatUri().getUri(),
                    textArg.getMessageID(),
                    textArg.getContent(),
                    textArg.needReport(),
                    resultIntent);
        } else if (arg instanceof FTMessageArg) {
            LogUtil.i(TAG, "file transfer message arg");
            FTMessageArg ftArg = (FTMessageArg) arg;
            WorkingServiceProxy.instance().msgPubSendFile(owner,
                    ftArg.getChatUri().getUri(),
                    ftArg.getMessageID(),
                    ftArg.getFilePath(),
                    ftArg.getContentType().value(),
                    ftArg.getFileName(),
                    ftArg.needReport(),
                    ftArg.getStart(),
                    ftArg.getThumbnail(),
                    resultIntent);
        }
    }

    private static void sendDirected(String owner, MessageArg arg, PendingIntent resultIntent) throws Exception {
        LogUtil.i(TAG, "sendDirected, owner = " + owner);
        if (arg instanceof TextMessageArg) {
            LogUtil.i(TAG, "text message arg");
            TextMessageArg textArg = (TextMessageArg) arg;

            WorkingServiceProxy.instance().msgSendText(owner,
                    textArg.getChatUri().getUri(),
                    textArg.getMessageID(),
                    textArg.getContent(),
                    textArg.needReport(),
                    textArg.isTransient(),
                    textArg.getChatUri().getmDirectedType().value(),
                    textArg.getNeedReadReport(),
                    textArg.getExtension(),
                    textArg.getContentType().value(),
                    resultIntent);
        } else if (arg instanceof FTMessageArg) {
            LogUtil.i(TAG, "file transfer message arg");
            FTMessageArg ftArg = (FTMessageArg) arg;
            WorkingServiceProxy.instance().msgSendFile(owner,
                    ftArg.getChatUri().getUri(),
                    ftArg.getMessageID(),
                    ftArg.getFilePath(),
                    ftArg.getContentType().value(),
                    ftArg.getFileName(),
                    ftArg.needReport(),
                    ftArg.getStart(),
                    ftArg.getThumbnail(),
                    ftArg.isTransient(),
                    ftArg.getChatUri().getmDirectedType().value(),
                    ftArg.getNeedReadReport(),
                    ftArg.getExtension(),
                    resultIntent);
        } else if (arg instanceof VemoticonMessageArg) {
            LogUtil.i(TAG, "send vemoticon message");
            VemoticonMessageArg vArg = (VemoticonMessageArg) arg;
            WorkingServiceProxy.instance().msgSendVemoticon(owner,
                    vArg.getChatUri().getUri(),
                    vArg.getMessageID(),
                    vArg.getVemoticonId(),
                    vArg.getVemoticonName(),
                    vArg.needReport(),
                    vArg.isTransient(),
                    vArg.getChatUri().getmDirectedType().value(),
                    resultIntent);
        } else if (arg instanceof CloudfileMessageArg) {
            LogUtil.i(TAG, "send cloudfile message");
            CloudfileMessageArg cfArg = (CloudfileMessageArg) arg;
            WorkingServiceProxy.instance().msgSendCloudfile(owner,
                    cfArg.getChatUri().getUri(),
                    cfArg.getMessageID(),
                    cfArg.getFileName(),
                    cfArg.getFileSize(),
                    cfArg.getFileUrl(),
                    cfArg.needReport(),
                    cfArg.isTransient(),
                    cfArg.getChatUri().getmDirectedType().value(),
                    resultIntent);
        }
    }

    /**
     * 获取文件id
     *
     * @param owner         操作用户
     * @param fileName      要获取的文件路径+名字
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.v3.GetFileIdResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_GETFILEID_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.v3.GetFileIdResult}对象。
     */
    public static void getFileId(String owner, String fileName, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "set user profile, owner = " + owner);
        WorkingServiceProxy.instance().getFileId(
                owner,
                fileName,
                pendingIntent);
    }
}
