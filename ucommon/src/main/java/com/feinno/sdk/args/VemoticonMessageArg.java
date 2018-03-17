package com.feinno.sdk.args;

import android.text.TextUtils;

import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.ContentType;
import com.feinno.sdk.session.MessageSession;

/**
 * 商店表情的消息参数
 */
public class VemoticonMessageArg extends MessageArg {
    /** 商店表情id */
    protected String vemoticonId;
    /** 商店表情的名称 (当接收方无法显示表情图片时，就显示此名称) */
    protected String vemoticonName;

    public String getVemoticonId() {
        return vemoticonId;
    }

    public String getVemoticonName() {
        return vemoticonName;
    }

    /**
     * 构建一个商店表情消息的参数
     * @param messageId 消息ID，唯一标记一条消息
     * @param to 接受者ChatUri，参见{@link ChatUri}
     * @param vemoticonId 商店表情id
     * @param vemoticonName 商店表情的名称
     * @param needReport 是否需要回执
     * @param isTransient 是否为阅后即焚
     */
    public VemoticonMessageArg(String messageId, ChatUri to, String vemoticonId, String vemoticonName, boolean needReport, boolean isTransient) {
        this.chatUri = to;
        this.vemoticonId = vemoticonId;
        this.vemoticonName = vemoticonName;
        this.needReport = needReport;
        this.isTransient = isTransient;
        this.contentType = ContentType.VEMOTICON;

        if (TextUtils.isEmpty(messageId)) {
            this.messageID = String.valueOf(System.currentTimeMillis());
        } else {
            this.messageID = messageId;
        }
    }

    public VemoticonMessageArg(MessageSession s) {
        this.chatUri = new ChatUri(ChatType.fromInt(s.chatType), s.toUri);
        this.vemoticonId = s.vemoticonId;
        this.vemoticonName = s.vemoticonName;
        this.needReport = s.needReport;
        this.contentType = ContentType.VEMOTICON;
        this.isTransient = s.isBurn;
        this.messageID = s.imdnId;
    }
}
