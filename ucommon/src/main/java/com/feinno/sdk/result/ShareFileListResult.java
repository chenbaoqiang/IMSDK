package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 此类用于记录获取共享文件列表的结果
 * error_code 解释：
 * 200: 成功
 * 401: 权限验证未通过
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class ShareFileListResult extends ActionResult implements Serializable, Parcelable {

    public ShareFile[] sharefiles;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeParcelableArray(sharefiles, 0);
    }

    public static ShareFileListResult fromJson(String json) {
        return JsonUtils.fromJson(json, ShareFileListResult.class);
    }

    public static final Creator<ShareFileListResult> CREATOR = new Creator<ShareFileListResult>() {

        @Override
        public ShareFileListResult createFromParcel(Parcel source) {
            ShareFileListResult ret = new ShareFileListResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            Parcelable[] arr = source.readParcelableArray(ShareFile.class.getClassLoader());
            if (arr != null) {
                ret.sharefiles = Arrays.copyOf(arr, arr.length, ShareFile[].class);
            }
            return ret;
        }

        @Override
        public ShareFileListResult[] newArray(int size) {
            return new ShareFileListResult[size];
        }
    };
}

