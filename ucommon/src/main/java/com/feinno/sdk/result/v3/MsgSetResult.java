package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录好友操作的结果
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class MsgSetResult extends ActionResult implements Serializable, Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
    }

    public static MsgSetResult fromJson(String json) {
        return JsonUtils.fromJson(json, MsgSetResult.class);
    }

    public static final Creator<MsgSetResult> CREATOR = new Creator<MsgSetResult>() {

        @Override
        public MsgSetResult createFromParcel(Parcel source) {
            MsgSetResult ret = new MsgSetResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            return ret;
        }

        @Override
        public MsgSetResult[] newArray(int size) {
            return new MsgSetResult[size];
        }
    };
}
