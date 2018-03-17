package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.DirectedType;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 彩云文件的消息
 */
public class CloudfileSession implements Serializable, Parcelable {
    /**文件名*/
    public String cloudfileName;
    /**彩云文件大小*/
    public String cloudfileSize;
    /**彩云文件下载地址*/
    public String cloudfileUrl;
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
        dest.writeString(cloudfileName);
        dest.writeString(cloudfileSize);
        dest.writeString(cloudfileUrl);
        dest.writeString(fromUri);
        b[0] = isBurn;
        dest.writeBooleanArray(b);
        dest.writeString(imdnId);
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
    public static CloudfileSession fromJson(String json) {
        return JsonUtils.fromJson(json, CloudfileSession.class);
    }

    public static final Parcelable.Creator<CloudfileSession> CREATOR = new Parcelable.Creator<CloudfileSession>() {

        @Override
        public CloudfileSession createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            CloudfileSession ret = new CloudfileSession();
            ret.cloudfileName = source.readString();
            ret.cloudfileSize = source.readString();
            ret.cloudfileUrl = source.readString();
            ret.fromUri = source.readString();
            source.readBooleanArray(b);
            ret.isBurn = b[0];
            ret.imdnId = source.readString();
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
        public CloudfileSession[] newArray(int size) {
            return new CloudfileSession[size];
        }
    };

    public static CloudfileSession fromMessageSession(MessageSession session) {
        CloudfileSession cSession = new CloudfileSession();
        cSession.cloudfileName = session.cloudfileName;
        cSession.cloudfileSize = session.cloudfileSize;
        cSession.cloudfileUrl = session.cloudfileUrl;
        cSession.fromUri = session.fromUri;
        cSession.chatType = session.chatType;
        cSession.isBurn = session.isBurn;
        cSession.toUri = session.toUri;
        cSession.sendTime = session.sendTime;
        cSession.imdnId = session.imdnId;
        cSession.id = session.id;
        cSession.contributionId = session.contributionId;
        cSession.needReport = session.needReport;
        cSession.isSilence = session.isSilence;
        cSession.directedType = session.directedType;

        return cSession;
    }
}
