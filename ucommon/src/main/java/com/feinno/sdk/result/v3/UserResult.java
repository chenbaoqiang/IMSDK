package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

@Deprecated
public class UserResult extends ActionResult implements Serializable, Parcelable{
    public static final int OP_GET_INFO = 1;
    public static final int OP_UPDATE_INFO = 2;
    public static final int OP_SET_PORTRAIT = 3;
    public static final int OP_GET_PORTRAIT = 4;

    /** 操作 */
    public int op;
    /** 用户信息 */
    public UserInfoResult userInfo;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeInt(op);
        dest.writeValue(userInfo);
    }

    public static UserResult fromJson(String json) {
        return JsonUtils.fromJson(json, UserResult.class);
    }

    public static final Creator<UserResult> CREATOR = new Creator<UserResult>() {

        @Override
        public UserResult createFromParcel(Parcel source) {
            UserResult ret = new UserResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.op = source.readInt();
            ret.userInfo = source.readParcelable(UserInfoResult.class.getClassLoader());
            return ret;
        }

        @Override
        public UserResult[] newArray(int size) {
            return new UserResult[size];
        }
    }; 
}
