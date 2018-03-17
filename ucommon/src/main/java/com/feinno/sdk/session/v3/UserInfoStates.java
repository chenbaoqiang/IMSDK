package com.feinno.sdk.session.v3;



/**
 * 用户信息操作的结果枚举
 */
public enum UserInfoStates {
    /**
     * 操作成功
     */
    OK(200),
    /**
     * 用户不存在
     */
    NotExist(404),
    /**
     * 操作失败
     */
    Failed(500);

    private int nCode;
    private UserInfoStates(int code) {
        this.nCode = code;
    }
    public int value() { return this.nCode; }

    /**
     * 根据整形值生成枚举值
     * @param v 整形值
     * @return 枚举值
     */
    public static UserInfoStates fromInt(int v) {
        switch(v){
            case 200:
                return OK;
            case 404:
                return NotExist;
            case 500:
                return Failed;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}
