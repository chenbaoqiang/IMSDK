package com.feinno.sdk.enums;

/**
 * 执行结果的状态码表示
 */
public enum ErrorCode {
    /**执行中*/
    PROGRESS(183),
    /**成功*/
    OK(200),
    /**被禁止*/
    FORBIDDEN(403),
    /**无法找到请求的资源*/
    NOT_EXSIT(404),
    /**超时*/
    TIMEOUT(408),
    /**资源已经不存在*/
    GONE(410),
    /**请求已经成功，但是被叫方当前不可用*/
    UNAVAILABLE(480),
    /**已经在执行，当前的请求不被受理*/
    BUSY(486),
    /**服务器错误*/
    SERVER_ERROR(500),
    /**传输层错误*/
    NETWORK_ERROR(-2),
    /**其它未知的错误*/
    OTHER_ERROR(-1);


    private int nCode;
    private ErrorCode(int code) {
        this.nCode = code;
    }
    public int value() { return this.nCode; }

    /**
     * 将整形值转换为枚举值
     * @param v 整型值
     * @return LoginStates枚举值
     */
    public static ErrorCode fromInt(int v) {
        switch(v) {
            case 183:
                return PROGRESS;
            case 200:
                return OK;
            case 403:
                return FORBIDDEN;
            case 404:
                return NOT_EXSIT;
            case 408:
                return TIMEOUT;
            case 410:
                return GONE;
            case 480:
                return UNAVAILABLE;
            case 486:
                return BUSY;
            case -2:
                return NETWORK_ERROR;
            case 500:
                return SERVER_ERROR;
            default:
                return OTHER_ERROR;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}
