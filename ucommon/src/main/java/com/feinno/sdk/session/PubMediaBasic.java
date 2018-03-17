package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 公众消息中的常规信息格式定义
 */
public class PubMediaBasic implements Serializable, Parcelable {
    /** 缩略图链接 */
    public String thumbLink;
    /** 原文件链接 */
    public String originalLink;
    /** 内容标题 */
    public String title;
    /** 文件大小，单位为B */
    public String fileSize;
    /** 文件时长，单位为秒 */
    public String duration;
    /** 文件类型 */
    public String fileType;
    /** 内容所属公众号唯一标志 */
    public String paUuid;
    /** 内容创建时间 */
    public String createTime;
    /** 基本媒体资源在公众平台的唯一标志 */
    public String mediaUuid;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbLink);
        dest.writeString(originalLink);
        dest.writeString(title);
        dest.writeString(fileSize);
        dest.writeString(duration);
        dest.writeString(fileType);
        dest.writeString(paUuid);
        dest.writeString(createTime);
        dest.writeString(mediaUuid);
    }

    public static final Creator<PubMediaBasic> CREATOR = new Creator<PubMediaBasic>() {

        @Override
        public PubMediaBasic createFromParcel(Parcel source) {
            PubMediaBasic ret = new PubMediaBasic();
            ret.thumbLink = source.readString();
            ret.originalLink = source.readString();
            ret.title = source.readString();
            ret.fileSize = source.readString();
            ret.duration = source.readString();
            ret.fileType = source.readString();
            ret.paUuid = source.readString();
            ret.createTime = source.readString();
            ret.mediaUuid = source.readString();
            return ret;
        }

        @Override
        public PubMediaBasic[] newArray(int size) {
            return new PubMediaBasic[size];
        }
    };
}
