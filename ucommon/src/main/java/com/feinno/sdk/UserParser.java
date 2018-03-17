package com.feinno.sdk;

/**
 * 用于帮助解析user字段的类型信息
 */
public class UserParser {

    /** user id 类型 */
    public final static int TYPE_USER_ID = 1;
    /** 手机号类型 */
    public final static int TYPE_USER_TEL = 2;
    /** 用户名类型 */
    public final static int TYPE_USER_NAME = 3;

    /**
     * 解析一个String类型的user字段, 返回其类型
     * @param user user字符串
     * @return 类型
     */
    public static int parse(String user) {
        boolean result = user.matches("[0-9]+");
        if (result) {
            return TYPE_USER_ID;
        } else {
            if (user.startsWith("+86")) {
                return TYPE_USER_TEL;
            }
        }

        return TYPE_USER_NAME;
    }
}
