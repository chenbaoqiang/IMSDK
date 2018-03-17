package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.session.v3.BuddyOpEnum;
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
public class BuddyResult extends ActionResult implements Serializable, Parcelable{

    /** 用户操作, 参照{@link BuddyOpEnum} */
    public int op;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeInt(op);
    }

    public static BuddyResult fromJson(String json) {
        return JsonUtils.fromJson(json, BuddyResult.class);
    }

    public static final Creator<BuddyResult> CREATOR = new Creator<BuddyResult>() {

        @Override
        public BuddyResult createFromParcel(Parcel source) {
            BuddyResult ret = new BuddyResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.op = source.readInt();
            return ret;
        }

        @Override
        public BuddyResult[] newArray(int size) {
            return new BuddyResult[size];
        }
    };
}
