package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 同步消息状态信息
 */
public class MsgStatusSession implements Serializable, Parcelable {
    /** 消息唯一标识 */
    public String imdnId;
    /** 同步时间 */
    public int time;
    /** 是否已发送送达报告 */
    public boolean deliverySent;
    /** 是否已发送已读报告 */
    public boolean readSent;
    /** 是否已发送已焚报告 */
    public boolean burnSent;
    /** 是否已读 */
    public boolean read;
    /** 是否已打开 */
    public boolean opened;
    /** 是否已删除状态 */
    public boolean deleted;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] b = new boolean[1];
        dest.writeString(imdnId);
        dest.writeInt(time);
        b[0] = deliverySent;
        dest.writeBooleanArray(b);
        b[0] = readSent;
        dest.writeBooleanArray(b);
        b[0] = burnSent;
        dest.writeBooleanArray(b);
        b[0] = read;
        dest.writeBooleanArray(b);
        b[0] = opened;
        dest.writeBooleanArray(b);
        b[0] = deleted;
        dest.writeBooleanArray(b);
    }

    @Override
    public String toString() {
        return "MsgStatusSession{" +
                ", time=" + time +
                ", imdnId='" + imdnId + '\'' +
                ", deliverySent=" + deliverySent +
                ", readSent=" + readSent +
                ", burnSent=" + burnSent +
                ", read=" + read +
                ", opened=" + opened +
                ", deleted=" + deleted +
                '}';
    }

    /**
     * 将json字符串转换为MsgStatusSession对象
     * @param json 符合特定格式的json字符串
     * @return MsgStatusSession对象
     */
    public static MsgStatusSession fromJson(String json) { return JsonUtils.fromJson(json, MsgStatusSession.class); }

    public static final Creator<MsgStatusSession> CREATOR = new Creator<MsgStatusSession>() {

        @Override
        public MsgStatusSession createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            MsgStatusSession ret = new MsgStatusSession();
            ret.imdnId = source.readString();
            ret.time = source.readInt();
            source.readBooleanArray(b);
            ret.deliverySent = b[0];
            source.readBooleanArray(b);
            ret.readSent = b[0];
            source.readBooleanArray(b);
            ret.burnSent = b[0];
            source.readBooleanArray(b);
            ret.read = b[0];
            source.readBooleanArray(b);
            ret.opened = b[0];
            source.readBooleanArray(b);
            ret.deleted = b[0];
            return ret;
        }

        @Override
        public MsgStatusSession[] newArray(int size) {
            return new MsgStatusSession[size];
        }
    };


}

