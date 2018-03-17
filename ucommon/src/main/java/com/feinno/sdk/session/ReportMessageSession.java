package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.ReportType;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

public class ReportMessageSession implements Serializable, Parcelable {
    /**回执类型，参见{@link ReportType}*/
    public int reportType;
    /**消息唯一标识*/
    public String imdnId;

    /**
     * 仅在reportType为{@link ReportType#TYPING}时使用, 表示发送用户
     */
    public String fromUser;

    /**
     * 仅在reportType为{@link ReportType#UPDATE_MSG_ID} 或者 {@link ReportType#FILE_PROGRESS} 时使用.
     * 分别表示该消息的唯一全局id, 和文件进度.（文件进度格式: progress/total, 例如: 1024/2048）
     */
    public String reportValue;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imdnId);
        dest.writeInt(reportType);
        dest.writeString(reportValue);
        dest.writeString(fromUser);
    }

    /**
     * 将json字符串转换为MessageSession对象
     * @param json 符合特定格式的json字符串
     * @return MessageSession对象
     */
    public static ReportMessageSession fromJson(String json) {
        return JsonUtils.fromJson(json, ReportMessageSession.class);
    }

    public static final Creator<ReportMessageSession> CREATOR = new Creator<ReportMessageSession>() {

        @Override
        public ReportMessageSession createFromParcel(Parcel source) {
            ReportMessageSession ret = new ReportMessageSession();
            ret.imdnId = source.readString();
            ret.reportType = source.readInt();
            ret.reportValue = source.readString();
            ret.fromUser = source.readString();
            return ret;
        }

        @Override
        public ReportMessageSession[] newArray(int size) {
            return new ReportMessageSession[size];
        }
    };

    public static ReportMessageSession fromMessageSession(MessageSession session) {
        ReportMessageSession rpSession = new ReportMessageSession();
        rpSession.reportType = session.reportType;
        rpSession.imdnId = session.imdnId;
        return rpSession;
    }
}
