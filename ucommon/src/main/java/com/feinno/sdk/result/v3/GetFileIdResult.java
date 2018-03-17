package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录获取文件id的结果
 * error_code 解释：
 * 200: 成功
 * 400：路径错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class GetFileIdResult extends ActionResult implements Serializable, Parcelable {
    /** 文件id */
    public String fileid;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeString(fileid);
    }

    public static BuddyResult fromJson(String json) {
        return JsonUtils.fromJson(json, BuddyResult.class);
    }

    public static final Creator<GetFileIdResult> CREATOR = new Creator<GetFileIdResult>() {

        @Override
        public GetFileIdResult createFromParcel(Parcel source) {
            GetFileIdResult ret = new GetFileIdResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.fileid = source.readString();
            return ret;
        }

        @Override
        public GetFileIdResult[] newArray(int size) {
            return new GetFileIdResult[size];
        }
    };
}
