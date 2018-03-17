//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.result.v3.UserBasicInfo;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于描述群组成员的信息
 */
public class GroupMemberInfo implements Serializable, Parcelable {

    /**群组成员*/
    public String user;

	/**加入群组的时间 UTC 时间，秒*/
	public int joinTime;

	/**角色, 1为普通成员 2为管理员*/
	public int role;

	/**群内昵称*/
	public String displayName;

	/**成员基本信息*/
	public UserBasicInfo userInfo;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(joinTime);
		dest.writeInt(role);
		dest.writeString(displayName);
		dest.writeString(user);
		dest.writeParcelable(userInfo, 0);
	}

    @Override
    public String toString() {
        return "GroupMemberInfo{" +
                ", joinTime='" + joinTime + '\'' +
                ", role='" + role + '\'' +
                ", displayName='" + displayName + '\'' +
				", user='" + user + '\'' +
                '}';
    }

    public static GroupMemberInfo fromJson(String json) {
		return JsonUtils.fromJson(json, GroupMemberInfo.class);
	}

	public static final Creator<GroupMemberInfo> CREATOR = new Creator<GroupMemberInfo>() {

		@Override
		public GroupMemberInfo createFromParcel(Parcel source) {
			GroupMemberInfo ret = new GroupMemberInfo();
			ret.joinTime = source.readInt();
			ret.role = source.readInt();
			ret.displayName = source.readString();
			ret.user = source.readString();
			ret.userInfo = source.readParcelable(UserBasicInfo.class.getClassLoader());
			return ret;
		}

		@Override
		public GroupMemberInfo[] newArray(int size) {
			return new GroupMemberInfo[size];
		}
	};
}
