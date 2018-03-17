package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;
import com.feinno.sdk.enums.AvOpEnum;

import java.io.Serializable;


/**
 * 此类用于记录音视频操作的结果
 */
public class AVResult extends ActionResult implements Serializable, Parcelable {
    /**会话id*/
    public int sessionId;
    /**操作类型，参见{@link AvOpEnum}*/
    public int op;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeInt(sessionId);
        dest.writeInt(op);
    }

    public static AVResult fromJson(String json) {
        return JsonUtils.fromJson(json, AVResult.class);
    }

    public static final Creator<AVResult> CREATOR = new Creator<AVResult>() {

        @Override
        public AVResult createFromParcel(Parcel source) {
            AVResult ret = new AVResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.sessionId = source.readInt();
            ret.op = source.readInt();
            return ret;
        }

        @Override
        public AVResult[] newArray(int size) {
            return new AVResult[size];
        }
    };
}