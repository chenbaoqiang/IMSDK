//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.GroupEventType;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录群组通知事件的状态
 *
 * 这个类型中描述的时间都是与当前用户有关的信息
 */
public class GroupEventSession implements Serializable, Parcelable {

    /**
     * 群名称
     */
    public String subject;
    /**
     * session id，用于唯一标识一个session
     */
    public int id;
    /**
     * 群URI
     */
    public String groupUri;
    /**
     * 群通知事件类型，参见{@link GroupEventType}
     */
    public int eventType;
    /**
     * 事件发起者
     */
    public String source;

    /**
     * 发起者昵称
     */
    public String sourceNickname;

    /**
     * 事件时间, UTC 时间, 秒
     */
    public int time;

    /**
     * 是否已经同意过, 0: 未同意, 1: 已经同意, 2: 已经拒绝
     */
    public int handleResult;

    /**
     * 群通知Id,用来发送设置通知已读状态
     * */
    public String imdnId;

    /**
     * 申请信息
     */
    public String msg;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(subject);
        dest.writeString(groupUri);
        dest.writeInt(eventType);
        dest.writeString(source);
        dest.writeString(sourceNickname);
        dest.writeInt(handleResult);
        dest.writeInt(time);
        dest.writeString(imdnId);
        dest.writeString(msg);
    }

    @Override
    public String toString() {
        return "GroupEventSession{" +
                ", subject='" + subject + '\'' +
                ", id=" + id +
                ", groupUri='" + groupUri + '\'' +
                ", eventType=" + eventType +
                ", source='" + source + '\'' +
                ", sourceNickname='" + sourceNickname + '\'' +
                ", handleResult='" + handleResult + '\'' +
                ", time='" + time + '\'' +
                ", imdnId='" + imdnId + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static GroupEventSession fromJson(String json) {
        return JsonUtils.fromJson(json, GroupEventSession.class);
    }

    public static final Creator<GroupEventSession> CREATOR = new Creator<GroupEventSession>() {

        @Override
        public GroupEventSession createFromParcel(Parcel source) {
            GroupEventSession ret = new GroupEventSession();
            ret.id = source.readInt();
            ret.subject = source.readString();
            ret.groupUri = source.readString();
            ret.eventType = source.readInt();
            ret.source = source.readString();
            ret.sourceNickname = source.readString();
            ret.handleResult = source.readInt();
            ret.time = source.readInt();
            ret.imdnId = source.readString();
            ret.msg = source.readString();
            return ret;
        }

        @Override
        public GroupEventSession[] newArray(int size) {
            return new GroupEventSession[size];
        }
    };
}
