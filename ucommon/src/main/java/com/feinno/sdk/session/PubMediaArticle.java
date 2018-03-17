package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 公众消息中的图文混排格式定义
 */
public class PubMediaArticle implements Serializable, Parcelable {
    /** 文章标题 */
    public String title;
    /** 文章作者 */
    public String author;
    /** 有缩略图时图片缩略图链接 */
    public String thumbLink;
    /** 有缩略图时图片原文件链接 */
    public String originalLink;
    /** 当文章链接到外部网页时的外部链接地址 */
    public String sourceLink;
    /** 若是单图文消息时显示较多文本内容，此处为用户在Portal上编辑的文本内容。 */
    public String mainText;
    /** 文章资源在公众平台上的唯一标识 */
    public String mediaUuid;
    /** 图文正文内容页的链接地址 */
    public String bodyLink;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(thumbLink);
        dest.writeString(originalLink);
        dest.writeString(sourceLink);
        dest.writeString(mainText);
        dest.writeString(mediaUuid);
        dest.writeString(bodyLink);
    }

    public static final Creator<PubMediaArticle> CREATOR = new Creator<PubMediaArticle>() {

        @Override
        public PubMediaArticle createFromParcel(Parcel source) {
            PubMediaArticle ret = new PubMediaArticle();
            ret.title = source.readString();
            ret.author = source.readString();
            ret.thumbLink = source.readString();
            ret.originalLink = source.readString();
            ret.sourceLink = source.readString();
            ret.mainText = source.readString();
            ret.mediaUuid = source.readString();
            ret.bodyLink = source.readString();
            return ret;
        }

        @Override
        public PubMediaArticle[] newArray(int size) {
            return new PubMediaArticle[size];
        }
    };
}
