package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 群组搜索结果
 */
public class GroupSearchInfo implements Serializable, Parcelable {

    /**群组id*/
    public int groupId;
    /**群组名称*/
    public String groupName;
    /**群组头像版本*/
    public int portraitVersion;
    /**群组简介*/
    public String introduce;
    /**成员数*/
    public int memberCount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(groupId);
        dest.writeString(groupName);
        dest.writeInt(portraitVersion);
        dest.writeString(introduce);
        dest.writeInt(memberCount);
    }

    public static GroupSearchInfo fromJson(String json) {
        return JsonUtils.fromJson(json, GroupSearchInfo.class);
    }

    public static final Creator<GroupSearchInfo> CREATOR = new Creator<GroupSearchInfo>() {

        @Override
        public GroupSearchInfo createFromParcel(Parcel source) {
            GroupSearchInfo ret = new GroupSearchInfo();
            ret.groupId = source.readInt();
            ret.groupName = source.readString();
            ret.portraitVersion = source.readInt();
            ret.introduce = source.readString();
            ret.memberCount = source.readInt();
            return ret;
        }

        @Override
        public GroupSearchInfo[] newArray(int size) {
            return new GroupSearchInfo[size];
        }
    };
}