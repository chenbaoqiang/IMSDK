package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;
import java.io.Serializable;

/**
 * 会话状态信息
 */
public class Conversation implements Serializable, Parcelable {
    /**
     * 客户端会话Id，为: Uid / GroupId
     */
    public String convId;
    /**
     * 会话最大消息id
     */
    public String maxImdnId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(convId);
        dest.writeString(maxImdnId);
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "convId='" + convId + '\'' +
                ", maxImdnId='" + maxImdnId + '\'' +
                '}';
    }

    public static Conversation fromJson(String json) {
        return JsonUtils.fromJson(json, Conversation.class);
    }

    public static final Parcelable.Creator<Conversation> CREATOR = new Parcelable.Creator<Conversation>() {

        @Override
        public Conversation createFromParcel(Parcel source) {
            Conversation ret = new Conversation();
            ret.convId = source.readString();
            ret.maxImdnId = source.readString();

            return ret;
        }

        @Override
        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };
}
