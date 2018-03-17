//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.GroupOpEnum;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 群组相关操作回调的返回的Session
 */
@Deprecated
public class GroupCBSession implements Serializable, Parcelable {

    /**状态, 参见{@link GroupOpStates}*/
    public int state;
    /**唯一ID*/
    public int id;
    /**是否成功*/
    public boolean isSuccess;
    /**操作类型，参见{@link GroupOpEnum}*/
    public int op;
  @Override
  public int describeContents() {
    return 0;
   }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
      boolean[] b = new boolean[1];
      dest.writeInt(state);
      dest.writeInt(id);
      b[0] = isSuccess;
      dest.writeBooleanArray(b);
      dest.writeInt(op);
  }

  public static GroupCBSession fromJson(String json) {
      return JsonUtils.fromJson(json, GroupCBSession.class);
  }

  public static final Creator<GroupCBSession> CREATOR = new Creator<GroupCBSession>() {

    @Override
    public GroupCBSession createFromParcel(Parcel source) {
      boolean[] b = new boolean[1];
      GroupCBSession ret = new GroupCBSession();
      ret.state = source.readInt();
      ret.id = source.readInt();
      source.readBooleanArray(b);
      ret.isSuccess = b[0];
      ret.op = source.readInt();
      return ret;
    }

    @Override
    public GroupCBSession[] newArray(int size) {
      return new GroupCBSession[size];
    }
  };
}
