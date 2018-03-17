package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 此类用于记录获取设备节点操作的结果
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class EndPointResult extends ActionResult implements Serializable, Parcelable {

    public EndPoint[] endpoints;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeParcelableArray(endpoints, 0);
    }

    public static EndPointResult fromJson(String json) {
        return JsonUtils.fromJson(json, EndPointResult.class);
    }

    public static final Creator<EndPointResult> CREATOR = new Creator<EndPointResult>() {

        @Override
        public EndPointResult createFromParcel(Parcel source) {
            EndPointResult ret = new EndPointResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            Parcelable[] arr = source.readParcelableArray(EndPoint.class.getClassLoader());
            if (arr != null) {
                ret.endpoints = Arrays.copyOf(arr, arr.length, EndPoint[].class);
            }
            return ret;
        }

        @Override
        public EndPointResult[] newArray(int size) {
            return new EndPointResult[size];
        }
    };
}

