package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 群组概要信息
 */
public class Group implements Serializable, Parcelable {
    /**群uri*/
    public String uri;

    /**
     * 数据变化类型, 1 为添加, 2为删除, 3为更新
     */
    public int action;

    /**群名称*/
    public String subject;

    /**消息免打扰 0:未开启,1:开启*/
    public int dndFlag;

    /**群Info版本号*/
    public String infoVersion;

    /**群成员版本号*/
    public String memberVersion;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uri);
        dest.writeInt(action);
        dest.writeString(subject);
        dest.writeInt(dndFlag);
        dest.writeString(infoVersion);
        dest.writeString(memberVersion);
    }

    @Override
    public String toString() {
        return "Group{" +
                "uri='" + uri + '\'' +
                ", action='" + action + '\'' +
                ", subject='" + subject + '\'' +
                ", dndFlag='" + dndFlag + '\'' +
                ", infoVersion='" + infoVersion + '\'' +
                ", memberVersion='" + memberVersion + '\'' +
                '}';
    }

    public static Group fromJson(String json) {
        return JsonUtils.fromJson(json, Group.class);
    }

    public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>() {

        @Override
        public Group createFromParcel(Parcel source) {
            Group ret = new Group();
            ret.uri = source.readString();
            ret.action = source.readInt();
            ret.subject = source.readString();
            ret.dndFlag = source.readInt();
            ret.infoVersion = source.readString();
            ret.memberVersion = source.readString();

            return ret;
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
