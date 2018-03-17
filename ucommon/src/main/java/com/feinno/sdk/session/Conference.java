//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;


import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 此类用于记录群组的详细信息
 */
public class Conference implements Serializable, Parcelable {

    /**群成员列表，参见{@link GroupMemberInfo}*/
    public GroupMemberInfo[] members;

    /**Conference版本号*/
    public String version;

    /**当前群成员数量*/
    public int userCount;

    /**Conference状态，用于区分是全部用户信息还是部分用户信息*/
    public String state;

	/**群名称*/
	public String subject;

	@Override
	public int describeContents() {
		return 0;
	}

    @Override
    public String toString() {
        return "Conference{" +
                "members=" + Arrays.toString(members) +
                ", version='" + version + '\'' +
                ", userCount=" + userCount +
                ", state='" + state + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

    @Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(subject);
		dest.writeParcelableArray(members, 0);
		dest.writeString(version);
		dest.writeInt(userCount);
		dest.writeString(state);
	}

	public static Conference fromJson(String json) {
		return JsonUtils.fromJson(json, Conference.class);
	}

	public static final Creator<Conference> CREATOR = new Creator<Conference>() {

		@Override
		public Conference createFromParcel(Parcel source) {
			Conference ret = new Conference();
			ret.subject = source.readString();
			Parcelable[] parcelableArray = source.readParcelableArray(GroupMemberInfo.class.getClassLoader());
			if (parcelableArray != null) {
				ret.members = Arrays.copyOf(parcelableArray, parcelableArray.length, GroupMemberInfo[].class);
			}
			ret.version = source.readString();
			ret.userCount = source.readInt();
			ret.state = source.readString();
			return ret;
		}

		@Override
		public Conference[] newArray(int size) {
			return new Conference[size];
		}
	};
}
