package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;
import com.feinno.sdk.session.MessageInfo;

/**
 * 此类用于记录获取历史会话消息的结果
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class ConvHistoryResult extends ActionResult implements Serializable, Parcelable {

    /**
     * 会话的历史消息
     */
    public MessageInfo[] messages;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeParcelableArray(messages, 0);
    }

    public static ConvHistoryResult fromJson(String json) {
        return JsonUtils.fromJson(json, ConvHistoryResult.class);
    }

    public static final Creator<ConvHistoryResult> CREATOR = new Creator<ConvHistoryResult>() {

        @Override
        public ConvHistoryResult createFromParcel(Parcel source) {
            ConvHistoryResult ret = new ConvHistoryResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            Parcelable[] arr = source.readParcelableArray(MessageInfo.class.getClassLoader());
            if (arr != null) {
                ret.messages = Arrays.copyOf(arr, arr.length, MessageInfo[].class);
            }
            return ret;
        }

        @Override
        public ConvHistoryResult[] newArray(int size) {
            return new ConvHistoryResult[size];
        }
    };
}

