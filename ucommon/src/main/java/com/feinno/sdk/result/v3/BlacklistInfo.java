package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 黑名单信息
 */
public class BlacklistInfo implements Serializable, Parcelable {
    /**
     * 用户userid
     */
    public String userId;
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
        dest.writeString(this.userId);
        dest.writeString(this.nickname);
    }

    public static BlacklistInfo fromJson(String json) {
        return JsonUtils.fromJson(json, BlacklistInfo.class);
    }

    protected BlacklistInfo(Parcel in) {
        this.userId = in.readString();
        this.nickname = in.readString();
    }

    public static final Creator<BlacklistInfo> CREATOR = new Creator<BlacklistInfo>() {
        @Override
        public BlacklistInfo createFromParcel(Parcel source) {
            return new BlacklistInfo(source);
        }

        @Override
        public BlacklistInfo[] newArray(int size) {
            return new BlacklistInfo[size];
        }
    };
}
