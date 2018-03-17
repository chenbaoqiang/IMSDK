package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.enums.ConvType;
import com.feinno.sdk.session.MessageInfo;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

public class ConvInfo implements Serializable, Parcelable {

    /**
     * 会话类型 参见{@link ConvType}
     */
    public int convType;

    /**
     * 会话ID
     */
    public int convId;

    /**
     * 未读消息数量
     */
    public int unreadCount;

    /**
     * 最后更新时间  UTC 时间，秒
     */
    public int updateTime;

    /**
     * 会话创建时间  UTC 时间，秒
     */
    public int createTime;

    /**
     * 会话中的最后一条消息内容
     */
    public MessageInfo lastMessage;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(convType);
        dest.writeInt(convId);
        dest.writeInt(unreadCount);
        dest.writeInt(updateTime);
        dest.writeInt(createTime);
        dest.writeParcelable(lastMessage, 0);
    }

    @Override
    public String toString() {
        return "ConvInfo{" +
                "convType='" + convType + '\'' +
                ", convId='" + convId + '\'' +
                ", unreadCount='" + unreadCount + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastMessage='" + lastMessage + '\'' +
                '}';
    }

    public static ConvInfo fromJson(String json) {
        return JsonUtils.fromJson(json, ConvInfo.class);
    }

    public static final Parcelable.Creator<ConvInfo> CREATOR = new Parcelable.Creator<ConvInfo>() {

        @Override
        public ConvInfo createFromParcel(Parcel source) {
            ConvInfo ret = new ConvInfo();
            ret.convType = source.readInt();
            ret.convId = source.readInt();
            ret.unreadCount = source.readInt();
            ret.updateTime = source.readInt();
            ret.createTime = source.readInt();
            ret.lastMessage = source.readParcelable(MessageInfo.class.getClassLoader());
            return ret;
        }

        @Override
        public ConvInfo[] newArray(int size) {
            return new ConvInfo[size];
        }
    };
}
