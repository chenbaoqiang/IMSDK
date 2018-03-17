//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 此类用于描述群组信息(包含群详细信息和群成员列表)
 */
public class GroupSession implements Serializable, Parcelable {

    /**群组的URI*/
    public String groupUri;

    /**群详细信息Info版本号*/
    public String infoVersion;

    /**群成员Members版本号*/
    public String membersVersion;

    /**群详细信息Info，参见{@link GroupInfo}*/
    public GroupInfo info;

    /**群成员列表，参见{@link GroupMemberInfo}*/
    public GroupMemberInfo[] members;

  @Override
  public int describeContents() {
    return 0;
   }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
	  dest.writeString(groupUri);
	  dest.writeString(infoVersion);
	  dest.writeString(membersVersion);
      dest.writeParcelable(info, 0);
      dest.writeParcelableArray(members, 0);
  }

    @Override
    public String toString() {
        return "GroupSession{" +
                ", groupUri=" + groupUri +
                ", infoVersion='" + infoVersion + '\'' +
                ", membersVersion='" + membersVersion + '\'' +
                ", info='" + info + '\'' +
                "members=" + Arrays.toString(members) +
                '}';
    }

    /**
     * 将json字符串转换为GroupSession对象
     * @param json 符合特定格式的json字符串
     * @return GroupSession对象
     */
  public static GroupSession fromJson(String json) {
    return JsonUtils.fromJson(json, GroupSession.class);
  }

  public static final Creator<GroupSession> CREATOR = new Creator<GroupSession>() {

    @Override
    public GroupSession createFromParcel(Parcel source) {
		GroupSession ret = new GroupSession();
		ret.groupUri = source.readString();
		ret.infoVersion = source.readString();
		ret.membersVersion = source.readString();
        ret.info = source.readParcelable(GroupInfo.class.getClassLoader());
        Parcelable[] parcelableArray = source.readParcelableArray(GroupMemberInfo.class.getClassLoader());
        if (parcelableArray != null) {
            ret.members = Arrays.copyOf(parcelableArray, parcelableArray.length, GroupMemberInfo[].class);
        }
		return ret;
    }

    @Override
    public GroupSession[] newArray(int size) {
      return new GroupSession[size];
    }
  };
}
