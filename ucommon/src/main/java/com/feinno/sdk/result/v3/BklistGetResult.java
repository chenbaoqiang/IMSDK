package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 获取黑名单列表结果
 */
public class BklistGetResult extends ActionResult implements Serializable, Parcelable {
    /**
     * 黑名单列表
     */
    public BlacklistInfo[] blacklist;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeParcelableArray(blacklist, 0);
    }

    public static BklistGetResult fromJson(String json) {
        return JsonUtils.fromJson(json, BklistGetResult.class);
    }

    public static final Creator<BklistGetResult> CREATOR = new Creator<BklistGetResult>() {
        @Override
        public BklistGetResult createFromParcel(Parcel source) {
            BklistGetResult res = new BklistGetResult();
            res.id = source.readInt();
            res.errorCode = source.readInt();
            res.errorExtra = source.readString();
            Parcelable[] arr = source.readParcelableArray(BlacklistInfo.class.getClassLoader());
            if (arr != null) {
                res.blacklist = Arrays.copyOf(arr, arr.length, BlacklistInfo[].class);
            }
            return  res;
        }

        @Override
        public BklistGetResult[] newArray(int size) {
            return new BklistGetResult[size];
        }
    };
}
