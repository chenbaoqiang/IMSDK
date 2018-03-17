package com.feinno.sdk.session.v3;


import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.v3.UserBasicInfo;

/**
 * 好友概要信息
 */
public class BuddyInfo implements Parcelable {

    /**
     * 好友 userId
     */
    public int userId;

    /**
     * 设置的备注名
     */
    public String localName;


    public static final int ACTION_NO_OP = 0;
    /**
     * 新增好友
     */
    public static final int ACTION_ADD = 1;
    /**
     * 删除好友
     */
    public static final int ACTION_DELETE = 2;
    /**
     * 好友信息变化
     */
    public static final int ACTION_UPDATE = 3;

    /**
     * 好友信息变化动作
     */
    public int action;

    /**
     * 当 action 为 ACTION_ADD 或者 ACTION_UPDATE 的时候，该字段可用
     */
    public UserBasicInfo userInfo;

    /** 好友消息免打扰开关  0:未开启,1:开启 */
    public int dndFlag;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(localName);
        dest.writeInt(action);
        dest.writeInt(dndFlag);
        dest.writeParcelable(userInfo, 0);
    }


    public static final Creator<BuddyInfo> CREATOR = new Creator<BuddyInfo>() {

        @Override
        public BuddyInfo createFromParcel(Parcel source) {
            BuddyInfo ret = new BuddyInfo();
            ret.userId = source.readInt();
            ret.localName = source.readString();
            ret.action = source.readInt();
            ret.dndFlag = source.readInt();
            ret.userInfo = source.readParcelable(UserBasicInfo.class.getClassLoader());
            return ret;
        }

        @Override
        public BuddyInfo[] newArray(int size) {
            return new BuddyInfo[size];
        }
    };
}

