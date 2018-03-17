package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录获取群共享信息的结果
 * error_code 解释：
 * 200: 成功
 * 401: 权限验证未通过
 * 405: 下载HTTP请求使用的方法不对，需要为GET
 * 408: 请求超时
 * 500: 服务器错误
 * -2: 网络错误
 * -1: 未知错误
 */
public class GroupShareInfoResult extends ActionResult implements Serializable, Parcelable {

    /**
     * 最大共享空间,单位Ｍ
     */
    public int maxStorageSpace;

    /**
     * 最大上传单个文件大小,单位M
     */
    public int maxUploadFileSize;

    /**
     * 已使用的存储空间,单位Ｍ
     */
    public int usedStorageSpace;

    /**
     * 文件数
     */
    public int fileCount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeInt(maxStorageSpace);
        dest.writeInt(maxUploadFileSize);
        dest.writeInt(usedStorageSpace);
        dest.writeInt(fileCount);
    }

    public static GroupShareInfoResult fromJson(String json) {
        return JsonUtils.fromJson(json, GroupShareInfoResult.class);
    }

    public static final Creator<GroupShareInfoResult> CREATOR = new Creator<GroupShareInfoResult>() {

        @Override
        public GroupShareInfoResult createFromParcel(Parcel source) {
            GroupShareInfoResult ret = new GroupShareInfoResult();
            ret.id = source.readInt();
            ret.errorCode = source.readInt();
            ret.errorExtra = source.readString();
            ret.maxStorageSpace = source.readInt();
            ret.maxUploadFileSize = source.readInt();
            ret.usedStorageSpace = source.readInt();
            ret.fileCount = source.readInt();
            return ret;
        }

        @Override
        public GroupShareInfoResult[] newArray(int size) {
            return new GroupShareInfoResult[size];
        }
    };
}

