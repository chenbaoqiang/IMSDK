package com.feinno.sdk.session.v3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tianxiaowei on 17/3/14.
 */

public class SyncCompletedSession implements Parcelable{

    protected SyncCompletedSession(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SyncCompletedSession> CREATOR = new Creator<SyncCompletedSession>() {
        @Override
        public SyncCompletedSession createFromParcel(Parcel in) {
            return new SyncCompletedSession(in);
        }

        @Override
        public SyncCompletedSession[] newArray(int size) {
            return new SyncCompletedSession[size];
        }
    };
}
