package com.feinno.sdk.session.v3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tianxiaowei on 17/3/29.
 */

public class BlackInfo implements Parcelable {
    /**
     * 好友 userId
     */
    public int userId;

    /**
     * 昵称
     */
    public String nickname;


    public static final int ACTION_NO_OP = 0;
    /**
     * 新增黑名单
     */
    public static final int ACTION_ADD = 1;
    /**
     * 删除黑名单
     */
    public static final int ACTION_DELETE = 2;
    /**
     * 黑名单变化动作
     */
    public int action;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(nickname);
        dest.writeInt(action);

    }

    public static final Creator<BlackInfo> CREATOR = new Creator<BlackInfo>() {

        @Override
        public BlackInfo createFromParcel(Parcel source) {
            BlackInfo ret = new BlackInfo();
            ret.userId = source.readInt();
            ret.nickname = source.readString();
            ret.action = source.readInt();
            return ret;
        }

        @Override
        public BlackInfo[] newArray(int size) {
            return new BlackInfo[size];
        }
    };
}
