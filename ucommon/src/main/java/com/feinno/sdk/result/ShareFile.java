package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;
import java.io.Serializable;

/**
 * 设备对象
 */
public class ShareFile implements Serializable, Parcelable {

    /**
     * 共享文件的标识Id
     */
    public String shareFileId;

    /**
     * 共享者的UId
     */
    public String sharerId;

    /**
     * 文件传输Id
     */
    public String fileId;

    /**
     * 文件名称
     */
    public String fileName;

    /**
     * 文件大小
     */
    public int fileSize;

    /**
     * 文件上传时间
     */
    public String uploadTime;

    /**
     * 文件过期时间
     */
    public int expire;

    /**
     * 文件下载次数
     */
    public int downloadCount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shareFileId);
        dest.writeString(sharerId);
        dest.writeString(fileId);
        dest.writeString(fileName);
        dest.writeInt(fileSize);
        dest.writeString(uploadTime);
        dest.writeInt(expire);
        dest.writeInt(downloadCount);
    }

    @Override
    public String toString() {
        return "ShareFile{" +
                "shareFileId='" + shareFileId + '\'' +
                ", sharerId='" + sharerId + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", expire='" + expire + '\'' +
                ", downloadCount='" + downloadCount + '\'' +
                '}';
    }

    public static ShareFile fromJson(String json) {
        return JsonUtils.fromJson(json, ShareFile.class);
    }

    public static final Parcelable.Creator<ShareFile> CREATOR = new Parcelable.Creator<ShareFile>() {

        @Override
        public ShareFile createFromParcel(Parcel source) {
            ShareFile ret = new ShareFile();
            ret.shareFileId = source.readString();
            ret.sharerId = source.readString();
            ret.fileId = source.readString();
            ret.fileName = source.readString();
            ret.fileSize = source.readInt();
            ret.uploadTime = source.readString();
            ret.expire = source.readInt();
            ret.downloadCount = source.readInt();
            return ret;
        }

        @Override
        public ShareFile[] newArray(int size) {
            return new ShareFile[size];
        }
    };
}