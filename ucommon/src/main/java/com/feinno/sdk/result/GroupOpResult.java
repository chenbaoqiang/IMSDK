package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.GroupOpEnum;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于描述群组相关操作的结果
 */
public class GroupOpResult extends ActionResult implements Serializable, Parcelable {

    /**群组uri*/
    public String groupUri;
    /**操作类型，参见{@link GroupOpEnum}*/
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
        dest.writeString(groupUri);
        dest.writeInt(op);
    }

    public static GroupOpResult fromJson(String json) {
        return JsonUtils.fromJson(json, GroupOpResult.class);
    }

    public static final Creator<GroupOpResult> CREATOR = new Creator<GroupOpResult>() {

        @Override
        public GroupOpResult createFromParcel(Parcel source) {
            GroupOpResult ret = new GroupOpResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.groupUri = source.readString();
            ret.op = source.readInt();
            return ret;
        }

        @Override
        public GroupOpResult[] newArray(int size) {
            return new GroupOpResult[size];
        }
    };
}