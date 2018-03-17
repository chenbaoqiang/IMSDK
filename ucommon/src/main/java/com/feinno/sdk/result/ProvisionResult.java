package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.result.v3.UserInfo;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录用户开通的结果
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class ProvisionResult extends ActionResult implements Serializable, Parcelable {

    /**
     * 用户ID
     */
    public int userId;

    /**
     * 用户基本信息
     */
    public UserInfo userinfo;

    /**
     * 客户端ID
     */
    public String clientId;

    /**
     * 设备免打扰设置,0:未开启 1:开启
     */
    public int dndFlag;

    /**
     * 设备免打扰开始时间
     */
    public String dndBeginTime;

    /**
     * 设备免打扰结束时间
     */
    public String dndEndTime;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeInt(userId);
        dest.writeParcelable(userinfo, 0);
        dest.writeString(clientId);
        dest.writeInt(dndFlag);
        dest.writeString(dndBeginTime);
        dest.writeString(dndEndTime);
    }

    public static ProvisionResult fromJson(String json) {
        return JsonUtils.fromJson(json, ProvisionResult.class);
    }

    public static final Creator<ProvisionResult> CREATOR = new Creator<ProvisionResult>() {

        @Override
        public ProvisionResult createFromParcel(Parcel source) {
            ProvisionResult ret = new ProvisionResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.userId = source.readInt();
            ret.userinfo = source.readParcelable(UserInfo.class.getClassLoader());
            ret.clientId = source.readString();
            ret.dndFlag = source.readInt();
            ret.dndBeginTime = source.readString();
            ret.dndEndTime = source.readString();
            return ret;
        }

        @Override
        public ProvisionResult[] newArray(int size) {
            return new ProvisionResult[size];
        }
    };
}