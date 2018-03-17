package com.feinno.sdk.session.v3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by tianxiaowei on 17/3/29.
 */

public class BlackListSession implements Parcelable {
    /**
     * 差量同步好友列表
     */
    public static final int SYNC_MODE_PARTIAL = 1;
    /**
     * 全量同步好友列表
     */
    public static final int SYNC_MODE_FULL = 2;

    /**
     * 同步模式, SYNC_MODE_PARTIAL 或者是 SYNC_MODE_FULL
     */
    public int syncMode;

    /**
     * 同步模式为 SYNC_MODE_FULL 的时候，该字段可用
     */
    public BlackInfo[] full;
    /**
     * 同步模式为 SYNC_MODE_PARTIAL的时候，该字段可用
     */
    public BlackInfo[] partial;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(syncMode);
        dest.writeParcelableArray(partial, 0);
        dest.writeParcelableArray(full, 0);
    }

    public static final Creator<BlackListSession> CREATOR = new Creator<BlackListSession>() {

        @Override
        public BlackListSession createFromParcel(Parcel source) {
            BlackListSession ret = new BlackListSession();
            ret.syncMode = source.readInt();
            Parcelable[] arr = source.readParcelableArray(BlackInfo.class.getClassLoader());
            if (arr != null) {
                ret.partial = Arrays.copyOf(arr, arr.length, BlackInfo[].class);
            }

            Parcelable[] arr2 = source.readParcelableArray(BlackInfo.class.getClassLoader());
            if (arr2 != null) {
                ret.full = Arrays.copyOf(arr2, arr2.length, BlackInfo[].class);
            }

            return ret;
        }

        @Override
        public BlackListSession[] newArray(int size) {
            return new BlackListSession[size];
        }
    };
}
