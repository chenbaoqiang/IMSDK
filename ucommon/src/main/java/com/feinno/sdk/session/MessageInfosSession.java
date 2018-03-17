package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 此类描述批量同步消息事件
 */
public class MessageInfosSession implements Serializable, Parcelable {

    /**
     * 批量消息
     */
    public MessageInfo[] messages;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(messages, 0);
    }

    @Override
    public String toString() {
        return "MessageInfosSession{" +
                ", messages='" + messages + '\'' +
                '}';
    }

    /**
     * 将json字符串转换为MessageInfosSession对象
     *
     * @param json 符合特定格式的json字符串
     * @return MessageInfosSession对象
     */
    public static MessageInfosSession fromJson(String json) {
        return JsonUtils.fromJson(json, MessageInfosSession.class);
    }

    public static final Creator<MessageInfosSession> CREATOR = new Creator<MessageInfosSession>() {

        @Override
        public MessageInfosSession createFromParcel(Parcel source) {
            MessageInfosSession ret = new MessageInfosSession();
            Parcelable[] arr = source.readParcelableArray(MessageInfo.class.getClassLoader());
            if (arr != null) {
                ret.messages = Arrays.copyOf(arr, arr.length, MessageInfo[].class);
            }
            return ret;
        }

        @Override
        public MessageInfosSession[] newArray(int size) {
            return new MessageInfosSession[size];
        }
    };
}