package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.enums.LogoutType;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类描述强制下线通知(服务器在下发完通知后会立即关闭连接，客户端收到通知后不应该再自动重连)
 * 已废弃,可监听{@link LoginStateSession} 事件
 */
@Deprecated
public class LogoutSession implements Serializable, Parcelable {

    /**
     * 类型 参见{@link LogoutType}
     */
    public int type;

    /**
     * 原因描述
     */
    public String reason;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(reason);
    }

    @Override
    public String toString() {
        return "LogoutSession{" +
                ", type='" + type + '\'' +
                ", reason=" + reason +
                '}';
    }

    /**
     * 将json字符串转换为LogoutSession对象
     *
     * @param json 符合特定格式的json字符串
     * @return LogoutSession对象
     */
    public static LogoutSession fromJson(String json) {
        return JsonUtils.fromJson(json, LogoutSession.class);
    }

    public static final Creator<LogoutSession> CREATOR = new Creator<LogoutSession>() {

        @Override
        public LogoutSession createFromParcel(Parcel source) {
            LogoutSession ret = new LogoutSession();
            ret.type = source.readInt();
            ret.reason = source.readString();
            return ret;
        }

        @Override
        public LogoutSession[] newArray(int size) {
            return new LogoutSession[size];
        }
    };
}

