package com.feinno.sdk.result.v3;

import android.os.Parcel;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.JsonUtils;

/**
 * 此类用于记录联系人状态操作的结果
 * error_code 解释：
 * 200: 成功
 * 404: 联系人不存在
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class GetPresenceResult extends ActionResult{
    /**
     * 操作类型 0:订阅好友在线状态 1:退订好友在线状态
     */
    public long userId;
    /**
     * 在线状态说明参加 {@link com.feinno.sdk.enums.PresenceEnum}
     */
    public int presence;
    public String presenceDes;
    public long caps;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeLong(userId);
        dest.writeInt(presence);
        dest.writeString(presenceDes);
        dest.writeLong(caps);
    }

    public static GetPresenceResult fromJson(String json) {
        return JsonUtils.fromJson(json, GetPresenceResult.class);
    }

    public static final Creator<GetPresenceResult> CREATOR = new Creator<GetPresenceResult>() {

        @Override
        public GetPresenceResult createFromParcel(Parcel source) {
            GetPresenceResult ret = new GetPresenceResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.userId = source.readLong();
            ret.presence = source.readInt();
            ret.presenceDes = source.readString();
            ret.caps = source.readLong();
            return ret;
        }

        @Override
        public GetPresenceResult[] newArray(int size) {
            return new GetPresenceResult[size];
        }
    };
}
