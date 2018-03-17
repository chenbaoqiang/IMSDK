//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录登录状态的变化
 */
public class LoginStateSession implements Serializable, Parcelable {

    /**
     * 类型 参见{@link com.feinno.sdk.enums.LoginStates}
     */
    public int type;

    /**
     * 原因描述
     */
    public String reason;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(reason);
    }

    @Override
    public String toString() {
        return "LoginState{" +
                ", type='" + type + '\'' +
                ", reason=" + reason +
                '}';
    }

    /**
     * 将json字符串转换为LoginStateSession对象
     *
     * @param json 符合特定格式的json字符串
     * @return LogoutSession对象
     */
    public static LoginStateSession fromJson(String json) {
        return JsonUtils.fromJson(json, LoginStateSession.class);
    }

    public static final Creator<LoginStateSession> CREATOR = new Creator<LoginStateSession>() {

        @Override
        public LoginStateSession createFromParcel(Parcel source) {
            LoginStateSession ret = new LoginStateSession();
            ret.type = source.readInt();
            ret.reason = source.readString();
            return ret;
        }

        @Override
        public LoginStateSession[] newArray(int size) {
            return new LoginStateSession[size];
        }
    };
}
