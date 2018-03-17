package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;
import com.feinno.sdk.enums.ClientType;

import java.io.Serializable;

/**
 * 设备对象
 */
public class EndPoint implements Serializable, Parcelable {

    /**
     * 是否自己设备 0: 否, 1 是
     */
    public int isSelf;

    /**
     * 客户端标识
     */
    public String clientId;

    /**
     * 客户端类型 参见{@link ClientType}
     */
    public int clientType;

    /**
     * 客户端名称、服务器根据client_type配置，用于UI显示
     */
    public String clientName;

    /**
     * 客户端版本号
     */
    public String clientVersion;

    /**
     * 客户端能力
     * Flag位	能力	    描述
     * bit0	SimpleIm	简单二人文字聊天
     * bit1	RichIm	富文本二人消息
     * bit2	SimpleGroup	简单文本群聊
     * bit3	RichGroup	富文本群聊
     * bit4	Audio	二人音频
     * bit5	Video	二人视频
     * bit6	MultiAudio	多人音频
     * bit7	MultiVideo	多人视频
     * bit8	ChatRoom	聊天室
     * bit9	Burn	阅后即焚
     */
    public int clientCaps;

    /**
     * 最后活跃时间  UTC 时间，秒
     */
    public int lastActiveTime;

    /**
     * 在线状态 -1 不在线 0	隐身或不在线 1 在线
     */
    public int presence;

    /**
     * 设备Model
     */
    public String deviceModel;

    /**
     * 设备激活时间  UTC 时间，秒
     */
    public int createTime;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(isSelf);
        dest.writeString(clientId);
        dest.writeInt(clientType);
        dest.writeString(clientName);
        dest.writeString(clientVersion);
        dest.writeInt(clientCaps);
        dest.writeInt(lastActiveTime);
        dest.writeInt(presence);
        dest.writeString(deviceModel);
        dest.writeInt(createTime);
    }

    @Override
    public String toString() {
        return "EndPoint{" +
                "isSelf='" + isSelf + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientType='" + clientType + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientVersion='" + clientVersion + '\'' +
                ", clientCaps='" + clientCaps + '\'' +
                ", lastActiveTime='" + lastActiveTime + '\'' +
                ", presence='" + presence + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public static EndPoint fromJson(String json) {
        return JsonUtils.fromJson(json, EndPoint.class);
    }

    public static final Parcelable.Creator<EndPoint> CREATOR = new Parcelable.Creator<EndPoint>() {

        @Override
        public EndPoint createFromParcel(Parcel source) {
            EndPoint ret = new EndPoint();
            ret.isSelf = source.readInt();
            ret.clientId = source.readString();
            ret.clientType = source.readInt();
            ret.clientName = source.readString();
            ret.clientVersion = source.readString();
            ret.clientCaps = source.readInt();
            ret.lastActiveTime = source.readInt();
            ret.presence = source.readInt();
            ret.deviceModel = source.readString();
            ret.createTime = source.readInt();
            return ret;
        }

        @Override
        public EndPoint[] newArray(int size) {
            return new EndPoint[size];
        }
    };
}
