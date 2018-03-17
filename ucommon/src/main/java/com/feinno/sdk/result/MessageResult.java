package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录消息发送的结果
 * error_code 解释：
 * 200: 消息发送成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 * */
public class MessageResult extends ActionResult implements Serializable, Parcelable {
    /**消息唯一标识*/
    public String imdnId;

    /** 下载完成的文件路径, 仅在下载文件时使用 */
    public String filePath;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeString(imdnId);
        dest.writeString(filePath);
    }

    public static MessageResult fromJson(String json) {
        return JsonUtils.fromJson(json, MessageResult.class);
    }

    public static final Creator<MessageResult> CREATOR = new Creator<MessageResult>() {

        @Override
        public MessageResult createFromParcel(Parcel source) {
            MessageResult ret = new MessageResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.imdnId = source.readString();
            ret.filePath = source.readString();
            return ret;
        }

        @Override
        public MessageResult[] newArray(int size) {
            return new MessageResult[size];
        }
    };
}
