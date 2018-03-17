package com.feinno.sdk.args;

import com.feinno.sdk.enums.ContentType;

import java.util.List;

/**
 * 发送消息时的消息参数
 */
public abstract class MessageArg implements IPayload {

    protected String messageID;

    @Deprecated
    protected String messageTo;
    protected ChatUri chatUri;
    protected ContentType contentType = ContentType.TEXT;
    protected boolean needReport = false;
    protected boolean needReadReport = false;
    protected boolean isTransient = false;
//    /**定向消息类型，请参照{@link DirectedType}*/
//    protected DirectedType directType = DirectedType.PC;
    /** 需要@的群成员，只能在群消息中使用 */
    protected String ccNumber;

    protected String extension;

    @Override
    public String toString() {
        return "MessageArg{" +
                "messageID='" + messageID + '\'' +
                ", messageTo='" + messageTo + '\'' +
                ", chatUri=" + chatUri +
                ", contentType=" + contentType +
                ", needReport=" + needReport +
                ", isTransient=" + isTransient +
                ", ccNumber=" + ccNumber +
                ", extension=" + extension +
                '}';
    }

    protected MessageArg() {}

    /**
     * 获取消息的接受者
     * @return 消息接受者
     */
    @Deprecated
    public String getMessageTo() {
        return messageTo;
    }

    /**
     * 获取消息的唯一ID
     * @return 消息ID
     */
	public String getMessageID() {
        return messageID;
    }

    /**
     * 设置消息ID
     * @param messageID 消息ID
     */
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    /**
     * 获取消息的内容类型
     * @return 消息的内容类型，参见{@link ContentType}
     */
	public ContentType getContentType() {
        return contentType;
    }

    /**
     * 参见{@link IPayload}
     * @return
     */
    public Object getPayload() {
        return null;
    }

    /**
     * 检查消息是否需要回执报告
     * @return 是否需要回执报告
     */
    public boolean needReport() {
        return needReport;
    }

    /**
     * 消息是否需要已读报告
     * @return
     */
    public boolean getNeedReadReport() {
        return needReadReport;
    }

    public void setNeedReadReport(boolean need) {
        needReadReport = need;
    }

    /**
     * 检查消息是否为阅后即焚
     * @return 是否为阅后即焚
     */
    public boolean isTransient() {
        return isTransient;
    }

    /**
     * 设置消息是否为阅后即焚
     * @param isTransient
     */
    public void setIsTransient(boolean isTransient) {
        this.isTransient = isTransient;
    }

    /**
     * 获得消息接受者的ChatUri
     * @return {@link ChatUri} 消息接受者的ChatUri对象
     */
    public ChatUri getChatUri() {
        return chatUri;
    }

    /**
     * 设置消息接受者的ChatUri
     * @param chatUri {@link ChatUri} 消息接受者的ChatUri对象
     */
    public void setChatUri(ChatUri chatUri) {
        this.chatUri = chatUri;
    }

//    public DirectedType getDirectType() {
//        return directType;
//    }
//
//    public void setDirectType(DirectedType directType) {
//        this.directType = directType;
//    }

    public String getccNumber() {
        return ccNumber;
    }

    public void setccNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    /**
     * 获取extension扩展字段（由客户端自定义,服务端透传）
     * @return extension扩展字段
     */
    public String getExtension() {
        return extension;
    }

    /**
     * 设置extension扩展字段（由客户端自定义,服务端透传）
     * @param extension extension扩展字段
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

}
