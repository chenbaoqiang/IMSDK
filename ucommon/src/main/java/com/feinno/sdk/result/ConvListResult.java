package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 此类用于记录获取会话列表的结果
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class ConvListResult extends ActionResult implements Serializable, Parcelable {

    /**
     * 会话信息列表
     */
    public ConvInfo[] convInfos;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeParcelableArray(convInfos, 0);
    }

    public static ConvListResult fromJson(String json) {
        return JsonUtils.fromJson(json, ConvListResult.class);
    }

    public static final Creator<ConvListResult> CREATOR = new Creator<ConvListResult>() {

        @Override
        public ConvListResult createFromParcel(Parcel source) {
            ConvListResult ret = new ConvListResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            Parcelable[] arr = source.readParcelableArray(ConvInfo.class.getClassLoader());
            if (arr != null) {
                ret.convInfos = Arrays.copyOf(arr, arr.length, ConvInfo[].class);
            }
            return ret;
        }

        @Override
        public ConvListResult[] newArray(int size) {
            return new ConvListResult[size];
        }
    };
}

