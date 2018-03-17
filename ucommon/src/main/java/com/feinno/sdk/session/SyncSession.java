package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类描述同步离线消息事件
 */
public class SyncSession implements Serializable, Parcelable {
    /**
     * 错误码 410时,客户端需要调用拉取会话列表接口，然后按需调用获取指定会话历史消息
     */
    public int statusCode;
    /**
     * 错误描述
     */
    public String reason;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statusCode);
        dest.writeString(reason);
    }

    @Override
    public String toString() {
        return "SyncSession{" +
                "statusCode='" + statusCode + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }

    public static SyncSession fromJson(String json) {
        return JsonUtils.fromJson(json, SyncSession.class);
    }

    public static final Parcelable.Creator<SyncSession> CREATOR = new Parcelable.Creator<SyncSession>() {

        @Override
        public SyncSession createFromParcel(Parcel source) {
            SyncSession ret = new SyncSession();
            ret.statusCode = source.readInt();
            ret.reason = source.readString();

            return ret;
        }

        @Override
        public SyncSession[] newArray(int size) {
            return new SyncSession[size];
        }
    };
}
