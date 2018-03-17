package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.PubMediaTypeEnum;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 公众账号消息定义
 */
public class PubMessageSession implements Serializable, Parcelable {
    /**
     * session id，用于唯一标识一个session
     */
    public int id;
    /**
     * 消息唯一标识
     */
    public String imdnId;
    /**
     * 消息接收方URI
     */
    public String to;
    /**
     * 消息发送方URI
     */
    public String from;
    /**
     * 是否需要静默
     */
    public boolean isSilence;
    /**
     * 消息所属公众帐号唯一标识
     */
    public String paUuid;
    /**
     * 消息类型，参照{@link PubMediaTypeEnum}
     * 10:文本消息
     * 18：电子名片消息
     * 19:位置信息
     * 20:纯图片消息
     * 30:纯视频消息
     * 40:纯音频消息
     * 51:单图文混排消息
     * 52:多图文混排消息
     * 60:短信
     */
    @Deprecated
    public int mediaType;
    /**
     * 消息创建时间
     */
    public String createTime;
    /**
     * 消息内容唯一标志ID
     */
    public String msgUuid;
    /**
     * 短信摘要。公众账号下发消息转发为短信时的短信内容，编码格式为UTF-8 + base64。
     */
    @Deprecated
    public String smsDigest;
    /**
     * 当属于文本消息时，包含消息具体内容
     */
    @Deprecated
    public String text;
    /**
     * 公众帐号当前状态：
     * 0：正常
     * 1：暂停
     * 2：关闭
     */
    public int activeStatus;
    /**
     * 是否支持转发：
     * 0：可转发
     * 1：不可转发
     */
    public int forwardable;
    /**
     * 当属于音频消息时，包含音频文件基本信息
     */
    @Deprecated
    public PubMediaBasic audio;
    /**
     * 当属于视频消息时，包含视频文件基本信息
     */
    @Deprecated
    public PubMediaBasic video;
    /**
     * 当属于图片消息时，包含图片文件基本信息
     */
    @Deprecated
    public PubMediaBasic pic;
    /**
     * 当属于电子名片消息时，包含电子名片文件基本信息
     */
    @Deprecated
    public PubMediaBasic vcard;
    /**
     * 当属于图文混排消息时，包含图文混排具体内容
     */
    public ArrayList<PubMediaArticle> article = new ArrayList();

    public boolean isRead;
    public boolean isOpen;
    public boolean isDelivered;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] b = new boolean[1];
        dest.writeInt(id);
        dest.writeString(imdnId);
        dest.writeString(to);
        dest.writeString(from);
        b[0] = isSilence;
        dest.writeBooleanArray(b);
        dest.writeString(paUuid);
        dest.writeInt(mediaType);
        dest.writeString(createTime);
        dest.writeString(msgUuid);
        dest.writeString(smsDigest);
        dest.writeString(text);
        dest.writeInt(activeStatus);
        dest.writeInt(forwardable);
        dest.writeParcelable(audio, flags);
        dest.writeParcelable(video, flags);
        dest.writeParcelable(pic, flags);
        dest.writeParcelable(vcard, flags);
        dest.writeList(article);

        b[0] = isRead;
        dest.writeBooleanArray(b);
        b[0] = isOpen;
        dest.writeBooleanArray(b);
        b[0] = isDelivered;
        dest.writeBooleanArray(b);

    }

    public static final Creator<PubMessageSession> CREATOR = new Creator<PubMessageSession>() {

        @Override
        public PubMessageSession createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            PubMessageSession ret = new PubMessageSession();
            ret.id = source.readInt();
            ret.imdnId = source.readString();
            ret.to = source.readString();
            ret.from = source.readString();
            source.readBooleanArray(b);
            ret.isSilence = b[0];
            ret.paUuid = source.readString();
            ret.mediaType = source.readInt();
            ret.createTime = source.readString();
            ret.msgUuid = source.readString();
            ret.smsDigest = source.readString();
            ret.text = source.readString();
            ret.activeStatus = source.readInt();
            ret.forwardable = source.readInt();
            ret.audio = source.readParcelable(PubMediaBasic.class.getClassLoader());
            ret.video = source.readParcelable(PubMediaBasic.class.getClassLoader());
            ret.pic = source.readParcelable(PubMediaBasic.class.getClassLoader());
            ret.vcard = source.readParcelable(PubMediaBasic.class.getClassLoader());
            ret.article = source.readArrayList(PubMediaArticle.class.getClassLoader());
            source.readBooleanArray(b);
            ret.isRead = b[0];
            source.readBooleanArray(b);
            ret.isOpen = b[0];
            source.readBooleanArray(b);
            ret.isDelivered = b[0];
            return ret;
        }

        @Override
        public PubMessageSession[] newArray(int size) {
            return new PubMessageSession[size];
        }
    };

    @Override
    public String toString() {
        return "PubMessageSession{" +
                "id=" + id +
                ", imdnId='" + imdnId + '\'' +
                ", to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", isSilence=" + isSilence +
                ", paUuid='" + paUuid + '\'' +
                ", mediaType=" + mediaType +
                ", createtime='" + createTime + '\'' +
                ", msgUuid='" + msgUuid + '\'' +
                ", smsDigest='" + smsDigest + '\'' +
                ", text='" + text + '\'' +
                ", activestatus=" + activeStatus +
                ", forwardable=" + forwardable +
                ", audio=" + audio +
                ", video=" + video +
                ", pic=" + pic +
                ", vcard=" + vcard +
                ", article=" + article +
                '}';
    }
}
