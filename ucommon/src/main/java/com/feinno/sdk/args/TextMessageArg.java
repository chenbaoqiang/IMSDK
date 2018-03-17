package com.feinno.sdk.args;

import android.text.TextUtils;

import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.ContentType;
import com.feinno.sdk.session.MessageSession;

/**
 * 文本消息的消息参数
 */
public class TextMessageArg extends MessageArg {
    protected String content;

    /**
     * 构造一个文本消息参数
     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
     * @param to 消息接收者
     * @param content 消息内容
     * @param needReport 是否需要送达回执报告
     */
    public TextMessageArg(String messageId, ChatUri to, String content, boolean needReport) {
        this(messageId, to, content, needReport, false, null);
    }

    /**
     * 构造一个文本消息参数
     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
     * @param to 消息接收者
     * @param content 消息内容
     * @param needReport 是否需要送达回执报告
     * @param extension 扩展字段（由客户端自定义,服务端透传）
     */
    public TextMessageArg(String messageId, ChatUri to, String content, boolean needReport, String extension) {
        this(messageId, to, content, needReport, false, extension);
    }

    /**
     * 构造一个文本消息参数
     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
     * @param to 消息接收者
     * @param content 消息内容
     * @param needReport 是否需要送达回执报告
     * @param needReadReport 是否需要已读回执报告
     * @param extension 扩展字段（由客户端自定义,服务端透传）
     */
    public TextMessageArg(String messageId, ChatUri to, String content, boolean needReport, boolean needReadReport, String extension) {
        this.chatUri = to;
        this.content = content;
        this.needReport = needReport;
        this.contentType = ContentType.TEXT;
        this.needReadReport = needReadReport;
        this.extension = extension;

        if (TextUtils.isEmpty(messageId)) {
            this.messageID = String.valueOf(System.currentTimeMillis());
        } else {
            this.messageID = messageId;
        }
    }

    /**
     * 构造一个文本或自定义消息参数
     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
     * @param to 消息接收者
     * @param content 消息内容
     * @param needReport 是否需要送达回执报告
     * @param needReadReport 是否需要已读回执报告
     * @param extension 扩展字段（由客户端自定义,服务端透传）
     * @param flag 标志位,表示是否文本消息,true为文本消息,false为透传自定义消息
     */
    public TextMessageArg(String messageId, ChatUri to, String content, boolean needReport, boolean needReadReport, String extension, boolean flag) {
        this.chatUri = to;
        this.content = content;
        this.needReport = needReport;
        if(flag)
            this.contentType = ContentType.TEXT;
        else
            this.contentType = ContentType.CUSTOM_MSG;
        this.needReadReport = needReadReport;
        this.extension = extension;

        if (TextUtils.isEmpty(messageId)) {
            this.messageID = String.valueOf(System.currentTimeMillis());
        } else {
            this.messageID = messageId;
        }
    }

    /**
     * 构造一个文本或自定义消息参数
     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
     * @param to 消息接收者
     * @param content 消息内容
     * @param extension 扩展字段（由客户端自定义,服务端透传）
     * @param isIot 标志位,表示是否IOT文本消息,true为IOT文本消息,false为透传普通消息
     */
    public TextMessageArg(String messageId, ChatUri to, String content, String extension, boolean isIot) {
        this.chatUri = to;
        this.content = content;
        if(isIot)
            this.contentType = ContentType.MSG_TYPE_NONSTOP;
        else
            this.contentType = ContentType.TEXT;
        this.extension = extension;

        if (TextUtils.isEmpty(messageId)) {
            this.messageID = String.valueOf(System.currentTimeMillis());
        } else {
            this.messageID = messageId;
        }
    }



    @Override
    public String toString() {
        return "TextMessageArg{" +
                "content='" + content + '\'' +
                "} " + super.toString();
    }

    public TextMessageArg(MessageSession s) {
        this.chatUri = new ChatUri(ChatType.fromInt(s.chatType), s.toUri);
        this.content = s.content;
        this.needReport = s.needReport;
        this.contentType = ContentType.TEXT;
        this.isTransient = s.isBurn;
        this.messageID = s.imdnId;
        this.extension = s.extension;
    }

    @Override
    public Object getPayload() {
        return content;
    }

    public String getContent() {
        return content;
    }
}
