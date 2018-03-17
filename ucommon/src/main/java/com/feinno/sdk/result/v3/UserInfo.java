package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

/**
 * 用户概要信息
 */
public class UserInfo extends UserBasicInfo{

    /** 客户端扩展, 目前存放消息置顶列表*/
    public String clientExtra;

    /** 消息免打扰  0:未设置 1:已设置*/
    public int dndFlag;

    /** 消息免打扰  加好友自动同意开关 0 需要申请 1 自动同意*/
    public int buddyFlag;

    /** 隐私设置  uid是否可查看个人资料开关 0 公开 1 不公开*/
    public int permissionuidFlag;

    /** 隐私设置  username是否可查看个人资料开关 0 公开 1 不公开*/
    public int permissionunameFlag;

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
        dest.writeString(clientExtra);
        dest.writeInt(dndFlag);
        dest.writeInt(buddyFlag);
        dest.writeInt(permissionuidFlag);
        dest.writeInt(permissionunameFlag);
    }

    public static UserInfo fromJson(String json) {
        return JsonUtils.fromJson(json, UserInfo.class);
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {

        @Override
        public UserInfo createFromParcel(Parcel source) {
            UserInfo ret = new UserInfo();
            ret.userId = source.readInt();
            ret.nickname = source.readString();
            ret.username = source.readString();
            ret.impresa = source.readString();
            ret.portraitVersion = source.readInt();
            ret.serviceExtra = source.readString();
            ret.clientExtra = source.readString();
            ret.dndFlag = source.readInt();
            ret.buddyFlag = source.readInt();
            ret.permissionuidFlag = source.readInt();
            ret.permissionunameFlag = source.readInt();
            return ret;
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
