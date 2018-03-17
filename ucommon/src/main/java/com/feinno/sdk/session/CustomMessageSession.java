package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 自定义消息 Session， SDK 制作透传,不处理
 */
public class CustomMessageSession implements Serializable, Parcelable {

    /**消息内容*/
    public String data;

    /**
     * 消息内容定义
     */
    public String dataId;

    /**
     * 消息类型, SDK 不做处理,直接透传
     */
    public int dataType;

    /**消息来源方ID*/
    public String from;

    /**消息接收方URI*/
    public String to;

    /**消息发送时间*/
    public int sendTime;

    /**消息唯一标识*/
    public String imdnId;

    /**session id，用于唯一标识一个session*/
    public int id;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] b = new boolean[1];
        dest.writeString(from);
        dest.writeString(imdnId);
        dest.writeString(data);
        dest.writeString(dataId);
        dest.writeInt(dataType);
        dest.writeString(to);
        dest.writeInt(sendTime);
        dest.writeInt(id);
    }

    @Override
    public String toString() {
        return "CustomMessageSession{" +
                "data='" + data + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", sendTime=" + sendTime +
                ", imdnId='" + imdnId + '\'' +
                ", dataId=" + dataId +
                ", dataType=" + dataType +
                '}';
    }

    /**
     * 将json字符串转换为CustomMessageSession对象
     * @param json 符合特定格式的json字符串
     * @return CustomMessageSession对象
     */
    public static CustomMessageSession fromJson(String json) {
        return JsonUtils.fromJson(json, CustomMessageSession.class);
    }

    public static final Parcelable.Creator<CustomMessageSession> CREATOR = new Parcelable.Creator<CustomMessageSession>() {

        @Override
        public CustomMessageSession createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            CustomMessageSession ret = new CustomMessageSession();
            ret.from = source.readString();
            ret.imdnId = source.readString();
            ret.data = source.readString();
            ret.dataId = source.readString();
            ret.dataType = source.readInt();
            ret.to = source.readString();
            ret.sendTime = source.readInt();
            ret.id = source.readInt();
            return ret;
        }

        @Override
        public CustomMessageSession[] newArray(int size) {
            return new CustomMessageSession[size];
        }
    };

}
