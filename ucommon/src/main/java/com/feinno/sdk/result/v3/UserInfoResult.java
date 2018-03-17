package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.session.v3.BuddyInfo;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 此类用于记录用户信息操作的结果
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class UserInfoResult extends ActionResult implements Serializable, Parcelable {

    public UserInfo[] userInfos;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeParcelableArray(userInfos, 0);
    }

    public static UserInfoResult fromJson(String json) {
        return JsonUtils.fromJson(json, UserInfoResult.class);
    }

    public static final Creator<UserInfoResult> CREATOR = new Creator<UserInfoResult>() {

        @Override
        public UserInfoResult createFromParcel(Parcel source) {
            UserInfoResult ret = new UserInfoResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            Parcelable[] arr = source.readParcelableArray(BuddyInfo.class.getClassLoader());
            if (arr != null) {
                ret.userInfos = Arrays.copyOf(arr, arr.length, UserInfo[].class);
            }
            return ret;
        }

        @Override
        public UserInfoResult[] newArray(int size) {
            return new UserInfoResult[size];
        }
    };
}

