package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录获取Token的结果
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class TokenResult extends ActionResult implements Serializable, Parcelable {
    /**
     * SDK生成的Token验证串，10分钟有效
     */
    public String token;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeString(token);
    }

    public static TokenResult fromJson(String json) {
        return JsonUtils.fromJson(json, TokenResult.class);
    }

    public static final Creator<TokenResult> CREATOR = new Creator<TokenResult>() {

        @Override
        public TokenResult createFromParcel(Parcel source) {
            TokenResult ret = new TokenResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.token = source.readString();
            return ret;
        }

        @Override
        public TokenResult[] newArray(int size) {
            return new TokenResult[size];
        }
    };
}
