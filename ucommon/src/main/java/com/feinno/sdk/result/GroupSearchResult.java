package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 群组搜索结果
 */
public class GroupSearchResult extends ActionResult implements Serializable, Parcelable {

    public GroupSearchInfo[] groups;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeParcelableArray(groups, 0);
    }

    public static GroupSearchResult fromJson(String json) {
        return JsonUtils.fromJson(json, GroupSearchResult.class);
    }

    public static final Creator<GroupSearchResult> CREATOR = new Creator<GroupSearchResult>() {

        @Override
        public GroupSearchResult createFromParcel(Parcel source) {
            GroupSearchResult ret = new GroupSearchResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            Parcelable[] arr = source.readParcelableArray(GroupSearchInfo.class.getClassLoader());
            if (arr != null) {
                ret.groups = Arrays.copyOf(arr, arr.length, GroupSearchInfo[].class);
            }
            return ret;
        }

        @Override
        public GroupSearchResult[] newArray(int size) {
            return new GroupSearchResult[size];
        }
    };
}