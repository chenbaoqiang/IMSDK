package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.ErrorCode;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 执行结果的基类
 */
public class ActionResult implements Serializable, Parcelable {

    /**执行中*/
    public static final int PROGRESS = 183;
    /**成功*/
    public static final int OK = 200;
    /**被禁止*/
    public static final int FORBIDDEN = 403;
    /**无法找到请求的资源*/
    public static final int NOT_EXSIT = 404;
    /**超时*/
    public static final int TIMEOUT = 408;
    /**资源已经不存在*/
    public static final int GONE = 410;
    /**请求已经成功，但是被叫方当前不可用*/
    public static final int UNAVAILABLE = 480;
    /**已经在执行，当前的请求不被受理*/
    public static final int BUSY = 486;
    /**服务器错误*/
    public static final int SERVER_ERROR = 500;
    /**传输层错误*/
    public static final int NETWORK_ERROR = -2;
    /**其它未知的错误*/
    public static final int OTHER_ERROR = -1;


    /**对应请求的id*/
    public int id;
    /**状态, 参见{@link ErrorCode}*/
    public int errorCode;
    /**状态的文字描述*/
    public String errorExtra;

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

    public static ActionResult fromJson(String json) {
        return JsonUtils.fromJson(json, ActionResult.class);
    }
    public static <T> T fromJson(String json, Class<T> klass) {
        return JsonUtils.fromJson(json, klass);
    }

    public static final Creator<ActionResult> CREATOR = new Creator<ActionResult>() {

        @Override
        public ActionResult createFromParcel(Parcel source) {
            ActionResult ret = new ActionResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            return ret;
        }

        @Override
        public ActionResult[] newArray(int size) {
            return new ActionResult[size];
        }
    };
}