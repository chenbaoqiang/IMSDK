package com.feinno.sdk.session.v3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class BuddyListSession implements Parcelable {
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
    public BuddyInfo[] full;
    /**
     * 同步模式为 SYNC_MODE_PARTIAL的时候，该字段可用
     */
    public BuddyInfo[] partial;

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
    
    public static final Creator<BuddyListSession> CREATOR = new Creator<BuddyListSession>() {

        @Override
        public BuddyListSession createFromParcel(Parcel source) {
            BuddyListSession ret = new BuddyListSession();
            ret.syncMode = source.readInt();
            Parcelable[] arr = source.readParcelableArray(BuddyInfo.class.getClassLoader());
            if (arr != null) {
                ret.partial = Arrays.copyOf(arr, arr.length, BuddyInfo[].class);
            }

            Parcelable[] arr2 = source.readParcelableArray(BuddyInfo.class.getClassLoader());
            if (arr2 != null) {
                ret.full = Arrays.copyOf(arr2, arr2.length, BuddyInfo[].class);
            }

            return ret;
        }

        @Override
        public BuddyListSession[] newArray(int size) {
            return new BuddyListSession[size];
        }
    };

}
