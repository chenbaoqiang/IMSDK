package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 黑名单信息
 */
public class DeviceListInfo implements Serializable, Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.nickname);
    }

    public static DeviceListInfo fromJson(String json) {
        return JsonUtils.fromJson(json, DeviceListInfo.class);
    }

    protected DeviceListInfo(Parcel in) {
        this.userId = in.readInt();
        this.username = in.readString();
        this.nickname = in.readString();
    }

    public static final Creator<DeviceListInfo> CREATOR = new Creator<DeviceListInfo>() {
        @Override
        public DeviceListInfo createFromParcel(Parcel source) {
            return new DeviceListInfo(source);
        }

        @Override
        public DeviceListInfo[] newArray(int size) {
            return new DeviceListInfo[size];
        }
    };
}
