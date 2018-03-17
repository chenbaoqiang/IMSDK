package com.feinno.sdk.session.v3;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * UserInfoSession, 用户信息操作结果
 */
public class UserInfoSession implements Parcelable{

    /**session id，用于唯一标识一个session*/
    public int id;
    /**用户操作返回的响应码，参见{@link UserInfoStates}*/
    public int state;

    /**
     * 用户昵称
     */
    public String nickName;


    /**
     * 用户昵称
     */
    public String impresa;

    /**
     * 名字
     */
    public String firstName;

    /**
     * 姓氏
     */
    public String lastName;


    /**
     * 性别，未设置：0， 男：1， 女：2
     */
    public Integer gender;


    /**
     * 生日
     */
    public Date birthday;


    /**
     * Email
     */
    public String email;


    /**
     * 头像 Crc Hash，当前上传或者下载头像的 Crc
     */
    public String portraitCrc;

    /**
     * 头像路径，当用户获取头像的时候，操作成功，SDK 会将头像保存在该路径
     */
    public String portailPath;



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
