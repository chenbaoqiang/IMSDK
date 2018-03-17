package com.feinno.sdk.args.v3;

/**
 * UserInfoArg, 用于修改个人信息时使用，所有字段可以为空
 */
public class UserInfoArg {

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
    public int gender = -1;


    /**
     * 生日
     */
    public String birthday;


    /**
     * Email
     */
    public String email;

    /**
     * 客户端扩展字段,客户端可写入不超过1024长度的任何信息
     */
    public String clientExtra;
}
