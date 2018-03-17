//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;


import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.lang.Deprecated;
import java.util.Arrays;

/**
 * 此类用于记录群列表的信息
 */
public class GroupListSession implements Serializable, Parcelable {

    /**session id，用于唯一标识一个session*/
    public int id;

    /**用户的user*/
    public String user;

    /**本地群列表版本号*/
    public String version;

    /**同步模式, 1是增强更新,2是全量更新*/
    public int syncMode;

    /**更新时间  UTC时间，单位秒 */
    public String timestamp;

    /**群信息*/
    public Group[] groups;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(user);
        dest.writeString(version);
        dest.writeInt(syncMode);
        dest.writeString(timestamp);
        dest.writeParcelableArray(groups, 0);
    }

    @Override
    public String toString() {
        return "GroupListSession{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", version='" + version + '\'' +
                ", syncMode='" + syncMode + '\'' +
                ", groups=" + Arrays.toString(groups) +
                '}';
    }

    /**
     * 将json字符串转换为GroupSession对象
     * @param json 符合特定格式的json字符串
     * @return GroupSession对象
     */
    public static GroupListSession fromJson(String json) {
        return JsonUtils.fromJson(json, GroupListSession.class);
    }

    public static final Creator<GroupListSession> CREATOR = new Creator<GroupListSession>() {

        @Override
        public GroupListSession createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            GroupListSession ret = new GroupListSession();
            ret.id = source.readInt();
            ret.user = source.readString();
            ret.version = source.readString();
            ret.syncMode = source.readInt();
            ret.timestamp = source.readString();
            Parcelable[] parcelableArray = source.readParcelableArray(Group.class.getClassLoader());
            if (parcelableArray != null) {
                ret.groups = Arrays.copyOf(parcelableArray, parcelableArray.length, Group[].class);
            }
            return ret;
        }

        @Override
        public GroupListSession[] newArray(int size) {
            return new GroupListSession[size];
        }
    };
}
