package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.JsonUtils;

/**
 * 此类用于记录联系人状态操作的结果
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class PresenceResult extends ActionResult{
    /**
     * 操作类型 0:订阅好友在线状态 1:退订好友在线状态
     */
    public int opType;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(opType);
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
    }

    public static PresenceResult fromJson(String json) {
        return JsonUtils.fromJson(json, PresenceResult.class);
    }

    public static final Parcelable.Creator<PresenceResult> CREATOR = new Parcelable.Creator<PresenceResult>() {

        @Override
        public PresenceResult createFromParcel(Parcel source) {
            PresenceResult ret = new PresenceResult();
            ret.opType = source.readInt();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            return ret;
        }

        @Override
        public PresenceResult[] newArray(int size) {
            return new PresenceResult[size];
        }
    };
}
