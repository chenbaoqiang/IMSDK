package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.EndPoint;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于描述变化设备节点信息
 */
public class EndpointChangedSession implements Serializable, Parcelable {

    /**
     * 1. login 2. logout
     */
    public int action;

    public EndPoint endpoint;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(action);
        dest.writeParcelable(endpoint, 0);
    }

    @Override
    public String toString() {
        return "EndpointChangedSession{" +
                ", action='" + action + '\'' +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }

    /**
     * 将json字符串转换为EndpointNewSession对象
     *
     * @param json 符合特定格式的json字符串
     * @return EndpointNewSession对象
     */
    public static EndpointChangedSession fromJson(String json) {
        return JsonUtils.fromJson(json, EndpointChangedSession.class);
    }

    public static final Creator<EndpointChangedSession> CREATOR = new Creator<EndpointChangedSession>() {

        @Override
        public EndpointChangedSession createFromParcel(Parcel source) {
            EndpointChangedSession ret = new EndpointChangedSession();
            ret.action = source.readInt();
            ret.endpoint = source.readParcelable(EndPoint.class.getClassLoader());
            return ret;
        }

        @Override
        public EndpointChangedSession[] newArray(int size) {
            return new EndpointChangedSession[size];
        }
    };
}
