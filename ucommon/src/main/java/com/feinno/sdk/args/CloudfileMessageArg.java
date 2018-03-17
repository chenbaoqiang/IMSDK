package com.feinno.sdk.args;

import android.text.TextUtils;

import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.ContentType;
import com.feinno.sdk.session.MessageSession;

/**
 * 发送彩云文件消息的参数
 */
public class CloudfileMessageArg extends MessageArg {
    /**彩云文件名*/
    protected String fileName;
    /**彩云文件大小*/
    protected String fileSize;
    /**彩云下载链接*/
    protected String fileUrl;

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    /***
     * 构建一个彩云文件消息的参数
     * @param messageId 消息ID，唯一标记一条消息
     * @param to 接受者ChatUri，参见{@link ChatUri}
     * @param fileName 彩云文件名
     * @param fileSize 彩云文件大小
     * @param fileUrl 彩云文件下载链接
     * @param needReport 是否需要回执
     * @param isTransient 是否为阅后即焚
     */
    public CloudfileMessageArg(String messageId, ChatUri to, String fileName, String fileSize, String fileUrl, boolean needReport, boolean isTransient) {
        this.chatUri = to;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
        this.needReport = needReport;
        this.isTransient = isTransient;
        this.contentType = ContentType.CLOUDFILE;

        if (TextUtils.isEmpty(messageId)) {
            this.messageID = String.valueOf(System.currentTimeMillis());
        } else {
            this.messageID = messageId;
        }
    }

    public CloudfileMessageArg(MessageSession s) {
        this.chatUri = new ChatUri(ChatType.fromInt(s.chatType), s.toUri);
        this.fileName = s.cloudfileName;
        this.fileSize = s.cloudfileSize;
        this.fileUrl = s.cloudfileUrl;
        this.needReport = s.needReport;
        this.isTransient = s.isBurn;
        this.contentType = ContentType.CLOUDFILE;
        this.messageID = s.imdnId;
    }
}
