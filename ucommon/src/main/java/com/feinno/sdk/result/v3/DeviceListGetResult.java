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
public class DeviceListGetResult extends ActionResult implements Serializable, Parcelable {
    /**
     * 黑名单列表
     */
    public DeviceListInfo[] deviceList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeParcelableArray(deviceList, 0);
    }

    public static DeviceListGetResult fromJson(String json) {
        return JsonUtils.fromJson(json, DeviceListGetResult.class);
    }

    public static final Creator<DeviceListGetResult> CREATOR = new Creator<DeviceListGetResult>() {
        @Override
        public DeviceListGetResult createFromParcel(Parcel source) {
            DeviceListGetResult res = new DeviceListGetResult();
            res.id = source.readInt();
            res.errorCode = source.readInt();
            res.errorExtra = source.readString();
            Parcelable[] arr = source.readParcelableArray(DeviceListInfo.class.getClassLoader());
            if (arr != null) {
                res.deviceList = Arrays.copyOf(arr, arr.length, DeviceListInfo[].class);
            }
            return  res;
        }

        @Override
        public DeviceListGetResult[] newArray(int size) {
            return new DeviceListGetResult[size];
        }
    };
}
