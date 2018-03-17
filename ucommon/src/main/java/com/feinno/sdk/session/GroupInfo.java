package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于描述群组详细信息
 */
public class GroupInfo implements Serializable, Parcelable {

    /**群组聊天的URI*/
    public String groupUri;

    /**群名称*/
    public String subject;

    /**群简介*/
    public String introduce;

    /**群公告*/
    public String bulletin;

    /**成员上限*/
    public int maxUserCount;

    /**群创建时间，UTC时间，秒*/
    public int createTime;

    /**群信息更新时间，UTC时间，秒*/
    public int updateTime;

    /**群状态 0:活动的,1:已删除*/
    public int flag;

    /**群类型 0:普通群,1:部门群,3:讨论组*/
    public int type;

    /**创建者(发起者)*/
    public String creator;

    /**当前群成员数量*/
    public int userCount;

    public String state;

    /** 群邀请设置 0: 不限制  1: 只有群主可以邀请*/
    public int inviteFlag;

    /** 群扩展信息,客户端可自己定制 */
    public String extra;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupUri);
        dest.writeString(subject);
        dest.writeString(introduce);
        dest.writeString(bulletin);
        dest.writeInt(maxUserCount);
        dest.writeInt(createTime);
        dest.writeInt(updateTime);
        dest.writeInt(flag);
        dest.writeInt(type);
        dest.writeString(creator);
        dest.writeInt(userCount);
        dest.writeString(state);
        dest.writeInt(inviteFlag);
        dest.writeString(extra);
    }

    @Override
    public String toString() {
        return "GroupSession{" +
                ", groupUri='" + groupUri + '\'' +
                ", subject='" + subject + '\'' +
                ", introduce='" + introduce + '\'' +
                ", bulletin='" + bulletin + '\'' +
                ", maxUserCount=" + maxUserCount +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", flag='" + flag + '\'' +
                ", type='" + type + '\'' +
                ", creator='" + creator + '\'' +
                ", userCount='" + userCount + '\'' +
                ", state='" + state + '\'' +
                ", inviteFlag='" + inviteFlag + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }

    /**
     * 将json字符串转换为GroupInfo对象
     * @param json 符合特定格式的json字符串
     * @return GroupInfo
     */
    public static GroupInfo fromJson(String json) {
        return JsonUtils.fromJson(json, GroupInfo.class);
    }

    public static final Creator<GroupInfo> CREATOR = new Creator<GroupInfo>() {

        @Override
        public GroupInfo createFromParcel(Parcel source) {
            GroupInfo ret = new GroupInfo();
            ret.groupUri = source.readString();
            ret.subject = source.readString();
            ret.introduce = source.readString();
            ret.bulletin = source.readString();
            ret.maxUserCount = source.readInt();
            ret.createTime = source.readInt();
            ret.updateTime = source.readInt();
            ret.flag = source.readInt();
            ret.type = source.readInt();
            ret.creator = source.readString();
            ret.userCount = source.readInt();
            ret.state = source.readString();
            ret.inviteFlag = source.readInt();
            ret.extra = source.readString();
            return ret;
        }

        @Override
        public GroupInfo[] newArray(int size) {
            return new GroupInfo[size];
        }
    };
}