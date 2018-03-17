//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.ProvisionStates;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录用户开通验证的状态
 */
public class ProvisionSession implements Serializable, Parcelable {

    /**登录验证是否成功*/
    public boolean isProvisionOK;
    /**用户开通时获取验证码和注册时使用的cookie*/
    public String cookie;
    /**手机号*/
    public String number;
    /**错误码*/
    public int errcode;
    /**session id，用于唯一标识一个session*/
    public int id;
    /**状态标识，参见{@link ProvisionStates}*/
    public int state;
    /**短信验证码是否正确*/
    public boolean isSmsCodeOK;
  @Override
  public int describeContents() {
    return 0;
   }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
      boolean[] b = new boolean[1];
      b[0] = isProvisionOK;
      dest.writeBooleanArray(b);
      dest.writeString(cookie);
      dest.writeString(number);
      dest.writeInt(errcode);
      dest.writeInt(id);
      dest.writeInt(state);
      b[0] = isSmsCodeOK;
      dest.writeBooleanArray(b);
  }

    /**
     * 将json字符串转换为ProvisionSession对象
     * @param json 符合特定格式的json字符串
     * @return ProvisionSession对象
     */
  public static ProvisionSession fromJson(String json) {
    return JsonUtils.fromJson(json, ProvisionSession.class);
  }

  public static final Creator<ProvisionSession> CREATOR = new Creator<ProvisionSession>() {

    @Override
    public ProvisionSession createFromParcel(Parcel source) {
      boolean[] b = new boolean[1];
      ProvisionSession ret = new ProvisionSession();
      source.readBooleanArray(b);
      ret.isProvisionOK = b[0];
      ret.cookie = source.readString();
      ret.number = source.readString();
      ret.errcode = source.readInt();
      ret.id = source.readInt();
      ret.state = source.readInt();
      source.readBooleanArray(b);
      ret.isSmsCodeOK = b[0];
      return ret;
    }

    @Override
    public ProvisionSession[] newArray(int size) {
      return new ProvisionSession[size];
    }
  };
}
