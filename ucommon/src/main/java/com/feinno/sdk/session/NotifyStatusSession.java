package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 业务通知状态变化消息
 */
public class NotifyStatusSession implements Serializable, Parcelable {
    /**
     * 通知Id
     */
    public String imdnId;
    /**
     * 操作时间
     */
    public int time;
    /**
     * 业务通知状态 1: 已读
     */
    public int status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] b = new boolean[1];
        dest.writeString(imdnId);
        dest.writeInt(time);
        dest.writeInt(status);
    }

    @Override
    public String toString() {
        return "NotifyStatusSession{" +
                ", time=" + time +
                ", imdnId='" + imdnId + '\'' +
                ", status=" + status +
                '}';
    }

    /**
     * 将json字符串转换为NotifyStatusSession对象
     *
     * @param json 符合特定格式的json字符串
     * @return NotifyStatusSession对象
     */
    public static NotifyStatusSession fromJson(String json) {
        return JsonUtils.fromJson(json, NotifyStatusSession.class);
    }

    public static final Creator<NotifyStatusSession> CREATOR = new Creator<NotifyStatusSession>() {

        @Override
        public NotifyStatusSession createFromParcel(Parcel source) {
            NotifyStatusSession ret = new NotifyStatusSession();
            ret.imdnId = source.readString();
            ret.time = source.readInt();
            ret.status = source.readInt();
            return ret;
        }

        @Override
        public NotifyStatusSession[] newArray(int size) {
            return new NotifyStatusSession[size];
        }
    };


}

