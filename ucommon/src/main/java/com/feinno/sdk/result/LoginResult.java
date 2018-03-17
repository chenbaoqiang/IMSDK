package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录登录的结果
 * error_code 解释：
 * 200: 登录成功
 * 403: 登陆验证失败，用户名或密码错误
 * 408: 请求超时
 * 460: 客户端不在当前服务区(appid不符)，要求客户端重新激活获取配置
 * 461: 需挑战登陆(重新对时或图验)
 * 462: 用户不存在(销号)
 * 463: ClientId不存在(需重新激活)
 * 464: 被强制下线，需要用户主动重新点击登录(保留密码)
 * 466: 被冻结，禁止登录
 * 500: 服务器不可用Service Unavailable
 * -2: 网络错误
 * -1: 未知错误
 */
public class LoginResult extends ActionResult implements Serializable, Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
    }

    public static LoginResult fromJson(String json) {
        return JsonUtils.fromJson(json, LoginResult.class);
    }

    public static final Parcelable.Creator<LoginResult> CREATOR = new Parcelable.Creator<LoginResult>() {

        @Override
        public LoginResult createFromParcel(Parcel source) {
            LoginResult ret = new LoginResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            return ret;
        }

        @Override
        public LoginResult[] newArray(int size) {
            return new LoginResult[size];
        }
    };
}