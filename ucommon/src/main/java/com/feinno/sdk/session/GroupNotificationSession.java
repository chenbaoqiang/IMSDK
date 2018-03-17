package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类保存了一个群组通知的信息
 *
 * 这个类型中,描述的是群组其他人发生的和自己无关,不需要额外处理
 */
public class GroupNotificationSession implements Serializable, Parcelable {

    /**
     * 事件发起人
     */
    public String source;
    /**
     * 事件发起人昵称
     */
    public String sourceNickname;
    /**
     * 事件目标人
     */
    public String target;
    /**
     * 事件目标人昵称
     */
    public String targetNickname;
    /**
     * 事件类型, 参考 {@link com.feinno.sdk.enums.GroupEventType}
     */
    public int eventType;

    /**
     * 事件, 秒为单位的 UTC 时间
     */
    public int time;


    /**session id，用于唯一标识一个session*/
    public int id;
    /**群组聊天的URI*/
    public String groupUri;

    /**
     * 群通知信息Id
     * */
    public String imdnId;

    /** 是否已读 */
    public boolean isRead;

    /** 名称 */
    public String groupName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] b = new boolean[1];
        dest.writeInt(id);
        dest.writeString(groupUri);
        dest.writeString(source);
        dest.writeString(sourceNickname);
        dest.writeString(target);
        dest.writeString(targetNickname);
        dest.writeInt(eventType);
        dest.writeInt(time);
        dest.writeString(imdnId);
        b[0] = isRead;
        dest.writeBooleanArray(b);
        dest.writeString(groupName);
    }

    @Override
    public String toString() {
        return "GroupNotificationSession{" +
                ", id=" + id +
                ", groupUri='" + groupUri + '\'' +
                ", source='" + source + '\'' +
                ", sourceNickname='" + sourceNickname + '\'' +
                ", target='" + target + '\'' +
                ", targetNickname='" + targetNickname + '\'' +
                ", eventType='" + eventType + '\'' +
                ", time='" + time + '\'' +
                ", imdnId='" + imdnId + '\'' +
                ", isRead=" + isRead +
                ", groupName=" + groupName +
                '}';
    }

    public static GroupNotificationSession fromJson(String json) {
        return JsonUtils.fromJson(json, GroupNotificationSession.class);
    }

    public static final Creator<GroupNotificationSession> CREATOR = new Creator<GroupNotificationSession>() {

        @Override
        public GroupNotificationSession createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            GroupNotificationSession ret = new GroupNotificationSession();
            ret.id = source.readInt();
            ret.groupUri = source.readString();
            ret.source = source.readString();
            ret.sourceNickname = source.readString();
            ret.target = source.readString();
            ret.targetNickname = source.readString();
            ret.eventType = source.readInt();
            ret.time = source.readInt();
            ret.imdnId = source.readString();
            source.readBooleanArray(b);
            ret.isRead = b[0];
            ret.groupName = source.readString();
            return ret;
        }

        @Override
        public GroupNotificationSession[] newArray(int size) {
            return new GroupNotificationSession[size];
        }
    };
}
