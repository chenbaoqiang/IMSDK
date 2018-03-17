package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.DirectedType;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 商店表情的消息
 */
public class EmoticonSession implements Serializable, Parcelable {
    /**表情id*/
    public String vemoticonId;
    /**表情名称*/
    public String vemoticonName;
    /**消息发送方URI*/
    public String fromUri;
    /**消息的聊天类型，参见{@link ChatType}*/
    public int chatType;
    /**是否是阅后即焚*/
    public boolean isBurn;
    /**消息接收方URI*/
    public String toUri;
    /**消息发送时间*/
    public int sendTime;
    /**消息唯一标识*/
    public String imdnId;
    /**session id，用于唯一标识一个session*/
    public int id;
    public String contributionId;
    /**接收到的消息是否需要回执报告*/
    public boolean needReport;
    /**是否需要静默*/
    public boolean isSilence;
    /** 定向消息终端来源，参见{@link DirectedType} */
    public int directedType;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] b = new boolean[1];
        dest.writeString(fromUri);
        b[0] = isBurn;
        dest.writeBooleanArray(b);
        dest.writeString(imdnId);
        dest.writeString(vemoticonId);
        dest.writeString(vemoticonName);
        dest.writeInt(chatType);
        dest.writeString(toUri);
        dest.writeInt(sendTime);
        dest.writeInt(id);
        dest.writeString(contributionId);
        b[0] = needReport;
        dest.writeBooleanArray(b);
        b[0] = isSilence;
        dest.writeBooleanArray(b);
        dest.writeInt(directedType);
    }

    /**
     * 将json字符串转换为TextMessageSession对象
     * @param json 符合特定格式的json字符串
     * @return TextMessageSession对象
     */
    public static EmoticonSession fromJson(String json) {
        return JsonUtils.fromJson(json, EmoticonSession.class);
    }

    public static final Creator<EmoticonSession> CREATOR = new Creator<EmoticonSession>() {

        @Override
        public EmoticonSession createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            EmoticonSession ret = new EmoticonSession();
            ret.fromUri = source.readString();
            source.readBooleanArray(b);
            ret.isBurn = b[0];
            ret.imdnId = source.readString();
            ret.vemoticonId = source.readString();
            ret.vemoticonName = source.readString();
            ret.chatType = source.readInt();
            ret.toUri = source.readString();
            ret.sendTime = source.readInt();
            ret.id = source.readInt();
            ret.contributionId = source.readString();
            source.readBooleanArray(b);
            ret.needReport = b[0];
            source.readBooleanArray(b);
            ret.isSilence = b[0];
            ret.directedType = source.readInt();
            return ret;
        }

        @Override
        public EmoticonSession[] newArray(int size) {
            return new EmoticonSession[size];
        }
    };

    public static EmoticonSession fromMessageSession(MessageSession session) {
        EmoticonSession vSession = new EmoticonSession();
        vSession.vemoticonId = session.vemoticonId;
        vSession.vemoticonName = session.vemoticonName;
        vSession.fromUri = session.fromUri;
        vSession.chatType = session.chatType;
        vSession.isBurn = session.isBurn;
        vSession.toUri = session.toUri;
        vSession.sendTime = session.sendTime;
        vSession.imdnId = session.imdnId;
        vSession.id = session.id;
        vSession.contributionId = session.contributionId;
        vSession.needReport = session.needReport;
        vSession.isSilence = session.isSilence;
        vSession.directedType = session.directedType;

        return vSession;
    }
}
