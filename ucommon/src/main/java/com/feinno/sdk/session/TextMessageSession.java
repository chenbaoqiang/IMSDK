package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.ContentType;
import com.feinno.sdk.enums.DirectedType;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

public class TextMessageSession implements Serializable, Parcelable {
    /**消息内容*/
    public String content;
    /**消息发送方*/
    public String from;
    /**消息的聊天类型，参见{@link ChatType}*/
    public int chatType;
    /**是否是阅后即焚*/
    public boolean isBurn;
    /**消息接收方URI*/
    public String to;
    /**消息内容类型, 此处取值为TEXT or CUSTOM_MSG，参见{@link ContentType}*/
    public int contentType;
    /**消息发送时间*/
    public int sendTime;
    /**消息唯一标识*/
    public String imdnId;
    /**session id，用于唯一标识一个session*/
    public int id;
    public String contributionId;
    /**接收到的消息是否需要回执报告*/
    public boolean needReport;
    /** * 是否需要已读回执报告 */
    public boolean needReadReport;
    /** * 是否需要已焚报告 */
    public boolean needBurnReport;
    /**是否需要静默*/
    public boolean isSilence;
    /**需要@的群成员号码，只在群消息中使用*/
    public String ccNumber;
    /** 定向消息终端来源，参见{@link DirectedType} */
    public int directedType;
    /** 是否已发送送达报告 */
    public boolean isReport;
    /** 是否已发送已读报告 */
    public boolean isReadReport;
    /** 是否已发送已焚报告 */
    public boolean isBurnReport;
    /** 是否已读 */
    public boolean isRead;
    /** 是否已打开 */
    public boolean isOpen;
    public boolean isDelivered;
    /** 扩展字段（由客户端自定义,服务端透传） */
    public String extension;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] b = new boolean[1];
        dest.writeString(from);
        b[0] = isBurn;
        dest.writeBooleanArray(b);
        dest.writeString(imdnId);
        dest.writeString(content);
        dest.writeInt(chatType);
        dest.writeString(to);
        dest.writeInt(contentType);
        dest.writeInt(sendTime);
        dest.writeInt(id);
        dest.writeString(contributionId);
        b[0] = needReport;
        dest.writeBooleanArray(b);
        b[0] = needReadReport;
        dest.writeBooleanArray(b);
        b[0] = needBurnReport;
        dest.writeBooleanArray(b);
        b[0] = isSilence;
        dest.writeBooleanArray(b);
        dest.writeString(ccNumber);
        dest.writeInt(directedType);
        b[0] = isReport;
        dest.writeBooleanArray(b);
        b[0] = isReadReport;
        dest.writeBooleanArray(b);
        b[0] = isBurnReport;
        dest.writeBooleanArray(b);
        b[0] = isRead;
        dest.writeBooleanArray(b);
        b[0] = isOpen;
        dest.writeBooleanArray(b);
        b[0] = isDelivered;
        dest.writeBooleanArray(b);
        dest.writeString(extension);
    }

    @Override
    public String toString() {
        return "TextMessageSession{" +
                "content='" + content + '\'' +
                ", from='" + from + '\'' +
                ", chatType=" + chatType +
                ", isBurn=" + isBurn +
                ", to='" + to + '\'' +
                ", contentType=" + contentType +
                ", sendTime=" + sendTime +
                ", imdnId='" + imdnId + '\'' +
                ", id=" + id +
                ", contributionId='" + contributionId + '\'' +
                ", needReport=" + needReport +
                ", needReadReport=" + needReadReport +
                ", needBurnReport=" + needBurnReport +
                ", isSilence=" + isSilence +
                ", ccNumber='" + ccNumber + '\'' +
                ", directedType=" + directedType +
                ", isReport=" + isReport +
                ", isReadReport=" + isReadReport +
                ", isBurnReport=" + isBurnReport +
                ", isRead=" + isRead +
                ", isOpen=" + isOpen +
                ", extension=" + extension +
                '}';
    }

    /**
     * 将json字符串转换为TextMessageSession对象
     * @param json 符合特定格式的json字符串
     * @return TextMessageSession对象
     */
    public static TextMessageSession fromJson(String json) {
        return JsonUtils.fromJson(json, TextMessageSession.class);
    }

    public static final Creator<TextMessageSession> CREATOR = new Creator<TextMessageSession>() {

        @Override
        public TextMessageSession createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            TextMessageSession ret = new TextMessageSession();
            ret.from = source.readString();
            source.readBooleanArray(b);
            ret.isBurn = b[0];
            ret.imdnId = source.readString();
            ret.content = source.readString();
            ret.chatType = source.readInt();
            ret.to = source.readString();
            ret.contentType = source.readInt();
            ret.sendTime = source.readInt();
            ret.id = source.readInt();
            ret.contributionId = source.readString();
            source.readBooleanArray(b);
            ret.needReport = b[0];
            source.readBooleanArray(b);
            ret.needReadReport = b[0];
            source.readBooleanArray(b);
            ret.needBurnReport = b[0];
            source.readBooleanArray(b);
            ret.isSilence = b[0];
            ret.ccNumber = source.readString();
            ret.directedType = source.readInt();
            source.readBooleanArray(b);
            ret.isReport = b[0];
            source.readBooleanArray(b);
            ret.isReadReport = b[0];
            source.readBooleanArray(b);
            ret.isBurnReport = b[0];
            source.readBooleanArray(b);
            ret.isRead = b[0];
            source.readBooleanArray(b);
            ret.isOpen = b[0];
            source.readBooleanArray(b);
            ret.isDelivered = b[0];
            ret.extension = source.readString();
            return ret;
        }

        @Override
        public TextMessageSession[] newArray(int size) {
            return new TextMessageSession[size];
        }
    };


}
