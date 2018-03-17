package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 头像处理结果
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class UserPortraitResult extends ActionResult implements Serializable, Parcelable {
    /**
     * 用户id 对应调用方
     */
    public int uid;

    /**
     * 是否压缩, 与调用传入一致
     */
    public boolean isSmall;

    /**
     * 头像路径
     */
    public String filePath;

    /**
     * 头像版本号
     */
    public int version;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        boolean[] b = new boolean[1];
        b[0] = isSmall;
        dest.writeBooleanArray(b);
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeString(filePath);
        dest.writeInt(version);
    }

    public static UserPortraitResult fromJson(String json) {
        return JsonUtils.fromJson(json, UserPortraitResult.class);
    }

    public static final Creator<UserPortraitResult> CREATOR = new Creator<UserPortraitResult>() {

        @Override
        public UserPortraitResult createFromParcel(Parcel source) {
            UserPortraitResult ret = new UserPortraitResult();
            ret.uid = source.readInt();
            boolean[] b = new boolean[1];
            source.readBooleanArray(b);
            ret.isSmall=b[0];
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.filePath = source.readString();
            ret.version = source.readInt();
            return ret;
        }

        @Override
        public UserPortraitResult[] newArray(int size) {
            return new UserPortraitResult[size];
        }
    };
}
