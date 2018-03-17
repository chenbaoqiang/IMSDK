package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录用户获取短信验证码的结果
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class GetSmsResult extends ActionResult implements Serializable, Parcelable {
    /**会话id，在接下来调用provision时需要传入*/
    public String sessionId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeString(sessionId);
    }

    public static GetSmsResult fromJson(String json) {
        return JsonUtils.fromJson(json, GetSmsResult.class);
    }

    public static final Creator<GetSmsResult> CREATOR = new Creator<GetSmsResult>() {

        @Override
        public GetSmsResult createFromParcel(Parcel source) {
            GetSmsResult ret = new GetSmsResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.sessionId = source.readString();
            return ret;
        }

        @Override
        public GetSmsResult[] newArray(int size) {
            return new GetSmsResult[size];
        }
    };
}