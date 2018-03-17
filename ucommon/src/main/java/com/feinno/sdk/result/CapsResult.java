package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用来记录能力交互的结果
 * error_code 解释：
 * 200: 对方在线
 * 404: 对方无能力
 * 480: 对方离线
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class CapsResult extends ActionResult implements Serializable, Parcelable {

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
    public boolean transientMsg;

    /**
     * 群组会话能力
     */
    public boolean groupChat;

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
    public boolean voiceCall;

    /**
     * ip视频呼叫能力
     */
    public boolean videoCall;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] b = new boolean[1];
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeString(number);
        b[0] = msg;
        dest.writeBooleanArray(b);
        b[0] = ft;
        dest.writeBooleanArray(b);
        b[0] = transientMsg;
        dest.writeBooleanArray(b);
        b[0] = groupChat;
        dest.writeBooleanArray(b);
        b[0] = vemoticon;
        dest.writeBooleanArray(b);
        b[0] = cloudfile;
        dest.writeBooleanArray(b);
        b[0] = voiceCall;
        dest.writeBooleanArray(b);
        b[0] = videoCall;
        dest.writeBooleanArray(b);


    }

    public static CapsResult fromJson(String json) {
        return JsonUtils.fromJson(json, CapsResult.class);
    }

    public static final Creator<CapsResult> CREATOR = new Creator<CapsResult>() {

        @Override
        public CapsResult createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            CapsResult ret = new CapsResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.number = source.readString();
            source.readBooleanArray(b);
            ret.msg = b[0];
            source.readBooleanArray(b);
            ret.ft = b[0];
            source.readBooleanArray(b);
            ret.transientMsg = b[0];
            source.readBooleanArray(b);
            ret.groupChat = b[0];
            source.readBooleanArray(b);
            ret.vemoticon = b[0];
            source.readBooleanArray(b);
            ret.cloudfile = b[0];
            source.readBooleanArray(b);
            ret.voiceCall = b[0];
            source.readBooleanArray(b);
            ret.videoCall = b[0];
            return ret;
        }

        @Override
        public CapsResult[] newArray(int size) {
            return new CapsResult[size];
        }
    };
}