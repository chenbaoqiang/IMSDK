package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录登出的结果
 * error_code 解释：
 * 200: 成功
 */
public class LogoutResult extends ActionResult implements Serializable, Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
    }

    public static LogoutResult fromJson(String json) {
        return JsonUtils.fromJson(json, LogoutResult.class);
    }

    public static final Parcelable.Creator<LogoutResult> CREATOR = new Parcelable.Creator<LogoutResult>() {

        @Override
        public LogoutResult createFromParcel(Parcel source) {
            LogoutResult ret = new LogoutResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            return ret;
        }

        @Override
        public LogoutResult[] newArray(int size) {
            return new LogoutResult[size];
        }
    };
}