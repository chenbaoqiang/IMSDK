package com.feinno.sdk.session;


import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.enums.ClientType;
import com.feinno.sdk.enums.PresenceEnum;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

public class PresenceInfo implements Serializable, Parcelable {
    /**
     * 客户端标识
     */
    public String clientId;
    /**
     * 客户端在线状态标识 参见{@link PresenceEnum}
     */
    public int presence;
    /**
     * 客户端类型 参见{@link ClientType}
     */
    public int clientType;
    /**
     * 客户端版本号
     */
    public String clientVersion;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientId);
        dest.writeInt(presence);
        dest.writeInt(clientType);
        dest.writeString(clientVersion);
    }

    @Override
    public String toString() {
        return "PresenceInfo{" +
                ", clientId='" + clientId + '\'' +
                ", clientType='" + clientType + '\'' +
                ", clientVersion='" + clientVersion + '\'' +
                ", presence='" + presence + '\'' +
                '}';
    }

    public static PresenceInfo fromJson(String json) {
        return JsonUtils.fromJson(json, PresenceInfo.class);
    }

    public static final Parcelable.Creator<PresenceInfo> CREATOR = new Parcelable.Creator<PresenceInfo>() {

        @Override
        public PresenceInfo createFromParcel(Parcel source) {
            PresenceInfo ret = new PresenceInfo();
            ret.clientId = source.readString();
            ret.presence = source.readInt();
            ret.clientType = source.readInt();
            ret.clientVersion = source.readString();
            return ret;
        }

        @Override
        public PresenceInfo[] newArray(int size) {
            return new PresenceInfo[size];
        }
    };
}
