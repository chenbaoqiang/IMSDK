package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类描述通用消息数据,用标志位表明消息类型
 */
public class MessageInfo implements Serializable, Parcelable {

    /**
     * 消息类型标志
     * 1 TextMessageSession, 2 FTMessageSession, 3 GroupNotificationSession, 4 MessageReportSession
     */
    public int msgFlag;

    /**
     * 文本消息数据,当msg_flag为1时,有效,否则为null
     */
    public TextMessageSession msgtextSession;

    /**
     * 文件消息数据,当msg_flag为2时,有效,否则为null
     */
    public FTMessageSession msgftSession;

    /**
     * 群组通知消息数据,当msg_flag为3时,有效,否则为null
     */
    public GroupNotificationSession groupnotifySession;

    /**
     * 消息报告数据,当msg_flag为4时,有效,否则为null
     */
    public ReportMessageSession reportSession;

    /**
     * 同步消息状态信息数据,当msg_flag为5时,有效,否则为nil
     */
    public MsgStatusSession msgstatusSession;

    /**
     * 同步会话状态信息数据,当msg_flag为6时,有效,否则为nil
     */
    public MsgConvStatusSession msgconvstatusSession;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(msgFlag);
        dest.writeParcelable(msgtextSession, 0);
        dest.writeParcelable(msgftSession, 0);
        dest.writeParcelable(groupnotifySession, 0);
        dest.writeParcelable(reportSession, 0);
        dest.writeParcelable(msgstatusSession, 0);
        dest.writeParcelable(msgconvstatusSession, 0);
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                ", msgFlag='" + msgFlag + '\'' +
                ", msgtextSession='" + msgtextSession + '\'' +
                ", msgftSession='" + msgftSession + '\'' +
                ", groupnotifySession='" + groupnotifySession + '\'' +
                ", reportSession='" + reportSession + '\'' +
                ", msgstatusSession='" + msgstatusSession + '\'' +
                ", msgconvstatusSession='" + msgconvstatusSession + '\'' +
                '}';
    }

    /**
     * 将json字符串转换为MessageInfo对象
     *
     * @param json 符合特定格式的json字符串
     * @return MessageInfo对象
     */
    public static MessageInfo fromJson(String json) {
        return JsonUtils.fromJson(json, MessageInfo.class);
    }

    public static final Creator<MessageInfo> CREATOR = new Creator<MessageInfo>() {

        @Override
        public MessageInfo createFromParcel(Parcel source) {
            MessageInfo ret = new MessageInfo();
            ret.msgFlag = source.readInt();
            ret.msgtextSession = source.readParcelable(TextMessageSession.class.getClassLoader());
            ret.msgftSession = source.readParcelable(FTMessageSession.class.getClassLoader());
            ret.groupnotifySession = source.readParcelable(GroupNotificationSession.class.getClassLoader());
            ret.reportSession = source.readParcelable(ReportMessageSession.class.getClassLoader());
            ret.msgstatusSession = source.readParcelable(MsgStatusSession.class.getClassLoader());
            ret.msgconvstatusSession = source.readParcelable(MsgConvStatusSession.class.getClassLoader());
            return ret;
        }

        @Override
        public MessageInfo[] newArray(int size) {
            return new MessageInfo[size];
        }
    };
}