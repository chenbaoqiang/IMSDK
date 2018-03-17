package com.feinno.sdk.result.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.ActionResult;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 用户 Profile 信息
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class UserProfileResult extends ActionResult implements Serializable, Parcelable{

    /** 用户 ID */
    public int userId;

    /** 用户昵称 */
    public String nickname;

    public String username;


    /** 用户签名 */
    public String impresa;

    /** 名字 */
    public String firstname;

    /** 姓氏 */
    public String lastname;


    /** 性别，未设置：0， 男：1， 女：2 */
    public int gender;


    /** 生日 */
    public String birthday;


    /** Email */
    public String workEmail;


    /** 头像 version，当前上传或者下载头像的 version */
    public int portraitVersion;

    /**
     * 客户端扩展字段
     */
    public String clientExtra;

    /**
     * 服务器扩展字段,目前用于存放身份标示
     */
    public String serviceExtra;

    /** 消息免打扰  0:未设置 1:已设置 --好友列表此值无意义*/
    public int userDnd;

    /** 公司*/
    public String company;

    /** 职位*/
    public String position;

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
        dest.writeString(username);
        dest.writeString(nickname);
        dest.writeString(impresa);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeInt(gender);
        dest.writeString(birthday);
        dest.writeString(workEmail);
        dest.writeInt(portraitVersion);
        dest.writeString(clientExtra);
        dest.writeString(serviceExtra);
        dest.writeInt(userDnd);
        dest.writeString(company);
        dest.writeString(position);
    }

    public static UserProfileResult fromJson(String json) {
        return JsonUtils.fromJson(json, UserProfileResult.class);
    }

    public static final Creator<UserProfileResult> CREATOR = new Creator<UserProfileResult>() {

        @Override
        public UserProfileResult createFromParcel(Parcel source) {
            UserProfileResult ret = new UserProfileResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.userId = source.readInt();
            ret.username = source.readString();
            ret.nickname = source.readString();
            ret.impresa = source.readString();
            ret.firstname = source.readString();
            ret.lastname = source.readString();
            ret.gender = source.readInt();
            ret.birthday =  source.readString();
            ret.workEmail = source.readString();
            ret.portraitVersion = source.readInt();
            ret.clientExtra = source.readString();
            ret.serviceExtra = source.readString();
            ret.userDnd = source.readInt();
            ret.company = source.readString();
            ret.position = source.readString();
            return ret;
        }

        @Override
        public UserProfileResult[] newArray(int size) {
            return new UserProfileResult[size];
        }
    };
}
