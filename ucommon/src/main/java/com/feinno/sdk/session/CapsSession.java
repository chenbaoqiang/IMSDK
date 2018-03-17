//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用来记录会话能力的状态
 */
public class CapsSession implements Serializable, Parcelable {

    /**
     * 手机号
     */
    public String number;

    /**
     * 文本消息能力
     */
    public boolean msg;

    /**
     * 文件传输能力
     */
    public boolean ft;

    /**
     * 阅后即焚能力
     */
    public boolean transientmsg;

    /**
     * 群组会话能力
     */
    public boolean gpchat;

    /**
     * 商店表情消息能力
     */
    public boolean vemoticon;

    /**
     * 彩云文件消息能力
     */
    public boolean cloudfile;

    /**
     * ip音频呼叫能力
     */
    public boolean voicecall;

    /**
     * ip视频呼叫能力
     */
    public boolean videocall;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] b = new boolean[1];
        dest.writeString(number);
        b[0] = msg;
        dest.writeBooleanArray(b);
        b[0] = ft;
        dest.writeBooleanArray(b);
        b[0] = transientmsg;
        dest.writeBooleanArray(b);
        b[0] = gpchat;
        dest.writeBooleanArray(b);
        b[0] = vemoticon;
        dest.writeBooleanArray(b);
        b[0] = cloudfile;
        dest.writeBooleanArray(b);
        b[0] = voicecall;
        dest.writeBooleanArray(b);
        b[0] = videocall;
        dest.writeBooleanArray(b);
    }

    /**
     * 将json字符串转换为OptionSession对象
     *
     * @param json 符合特定格式的json字符串
     * @return OptionSession对象
     */
    public static CapsSession fromJson(String json) {
        return JsonUtils.fromJson(json, CapsSession.class);
    }

    public static final Creator<CapsSession> CREATOR = new Creator<CapsSession>() {

        @Override
        public CapsSession createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            CapsSession ret = new CapsSession();
            ret.number = source.readString();
            source.readBooleanArray(b);
            ret.msg = b[0];
            source.readBooleanArray(b);
            ret.ft = b[0];
            source.readBooleanArray(b);
            ret.transientmsg = b[0];
            source.readBooleanArray(b);
            ret.gpchat = b[0];
            source.readBooleanArray(b);
            ret.vemoticon = b[0];
            source.readBooleanArray(b);
            ret.cloudfile = b[0];
            source.readBooleanArray(b);
            ret.voicecall = b[0];
            source.readBooleanArray(b);
            ret.videocall = b[0];
            return ret;
        }

        @Override
        public CapsSession[] newArray(int size) {
            return new CapsSession[size];
        }
    };
}
