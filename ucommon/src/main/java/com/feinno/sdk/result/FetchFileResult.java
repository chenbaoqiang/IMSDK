package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于下载文件类消息的结果（图片，视频，文件）
 * error_code 解释：
 * 200: 成功
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class FetchFileResult extends ActionResult implements Serializable, Parcelable {
    /**消息唯一标识*/
    public String imdnId;
    /**文件路径*/
    public String filePath;

    /** 文件 hash */
    public String fileHash;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeString(imdnId);
        dest.writeString(filePath);
        dest.writeString(fileHash);
    }

    public static FetchFileResult fromJson(String json) {
        return JsonUtils.fromJson(json, FetchFileResult.class);
    }

    public static final Creator<FetchFileResult> CREATOR = new Creator<FetchFileResult>() {

        @Override
        public FetchFileResult createFromParcel(Parcel source) {
            FetchFileResult ret = new FetchFileResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.imdnId = source.readString();
            ret.filePath = source.readString();
            ret.fileHash = source.readString();
            return ret;
        }

        @Override
        public FetchFileResult[] newArray(int size) {
            return new FetchFileResult[size];
        }
    };
}