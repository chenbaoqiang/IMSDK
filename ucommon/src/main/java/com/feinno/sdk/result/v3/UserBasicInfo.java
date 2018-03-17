package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * Created by tianxiaowei on 16/12/26.
 */
public class UserBasicInfo implements Serializable, Parcelable {
    /** 用户 ID */
    public int userId;

    /** 用户名 */
    public String username;

    /** 用户昵称 */
    public String nickname;

    /** 用户签名 */
    public String impresa;

    /** 头像 version，当前上传或者下载头像的 version */
    public int portraitVersion;

    /**
     * 服务器扩展字段,目前存放身份标示
     */
    public String serviceExtra;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(nickname);
        dest.writeString(username);
        dest.writeString(impresa);
        dest.writeInt(portraitVersion);
        dest.writeString(serviceExtra);
    }

    public static UserInfo fromJson(String json) {
        return JsonUtils.fromJson(json, UserInfo.class);
    }

    public static final Parcelable.Creator<UserBasicInfo> CREATOR = new Parcelable.Creator<UserBasicInfo>() {

        @Override
        public UserBasicInfo createFromParcel(Parcel source) {
            UserBasicInfo ret = new UserBasicInfo();
            ret.userId = source.readInt();
            ret.nickname = source.readString();
            ret.username = source.readString();
            ret.impresa = source.readString();
            ret.portraitVersion = source.readInt();
            ret.serviceExtra = source.readString();
            return ret;
        }

        @Override
        public UserBasicInfo[] newArray(int size) {
            return new UserBasicInfo[size];
        }
    };
}
