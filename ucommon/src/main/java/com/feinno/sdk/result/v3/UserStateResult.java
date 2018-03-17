package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.enums.UserStates;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * getUserStates 接口结果类
 */
public class UserStateResult extends ActionResult implements Serializable, Parcelable {
    /** sdk状态枚举, 参照{@link UserStates} */
    public int userState;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeInt(userState);
    }

    public static UserStateResult fromJson(String json) {
        return JsonUtils.fromJson(json, UserStateResult.class);
    }

    public static final Parcelable.Creator<UserStateResult> CREATOR = new Parcelable.Creator<UserStateResult>() {

        @Override
        public UserStateResult createFromParcel(Parcel source) {
            UserStateResult ret = new UserStateResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.userState = source.readInt();
            return ret;
        }

        @Override
        public UserStateResult[] newArray(int size) {
            return new UserStateResult[size];
        }
    };
}
