package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 获取设备状态信息
 */
public class DeviceStatusGetResult extends ActionResult implements Serializable, Parcelable {
    /**
     * 关联用户信息
     */
    public int presence;
    /**
     * 关联用户信息
     */
    public DeviceRelationInfo[] relationList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeInt(presence);
        dest.writeParcelableArray(relationList, 0);
    }

    public static DeviceStatusGetResult fromJson(String json) {
        return JsonUtils.fromJson(json, DeviceStatusGetResult.class);
    }

    public static final Creator<DeviceStatusGetResult> CREATOR = new Creator<DeviceStatusGetResult>() {
        @Override
        public DeviceStatusGetResult createFromParcel(Parcel source) {
            DeviceStatusGetResult res = new DeviceStatusGetResult();
            res.id = source.readInt();
            res.errorCode = source.readInt();
            res.errorExtra = source.readString();
            res.presence = source.readInt();
            Parcelable[] arr = source.readParcelableArray(DeviceRelationInfo.class.getClassLoader());
            if (arr != null) {
                res.relationList = Arrays.copyOf(arr, arr.length, DeviceRelationInfo[].class);
            }
            return  res;
        }

        @Override
        public DeviceStatusGetResult[] newArray(int size) {
            return new DeviceStatusGetResult[size];
        }
    };
}
