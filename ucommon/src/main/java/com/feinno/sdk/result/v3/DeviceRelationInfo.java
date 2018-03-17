package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 黑名单信息
 */
public class DeviceRelationInfo implements Serializable, Parcelable {
    /**
     * 用户userid
     */
    public int userId;
    /**
     * 用户名
     */
    public String username;
    /**
     * 用户昵称
     */
    public String nickname;
    /**
     * 头像版本号
     */
    public int portraitVersion;
    /**
     * 心情短语
     */
    public String impresa;
    /**
     * 服务扩展
     */
    public String serviceExtra;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.nickname);
        dest.writeInt(this.portraitVersion);
        dest.writeString(this.impresa);
        dest.writeString(this.serviceExtra);
    }

    public static DeviceRelationInfo fromJson(String json) {
        return JsonUtils.fromJson(json, DeviceRelationInfo.class);
    }

    protected DeviceRelationInfo(Parcel in) {
        this.userId = in.readInt();
        this.username = in.readString();
        this.nickname = in.readString();
        this.portraitVersion = in.readInt();
        this.impresa = in.readString();
        this.serviceExtra = in.readString();
    }

    public static final Creator<DeviceRelationInfo> CREATOR = new Creator<DeviceRelationInfo>() {
        @Override
        public DeviceRelationInfo createFromParcel(Parcel source) {
            return new DeviceRelationInfo(source);
        }

        @Override
        public DeviceRelationInfo[] newArray(int size) {
            return new DeviceRelationInfo[size];
        }
    };
}
