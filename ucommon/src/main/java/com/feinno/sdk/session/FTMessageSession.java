package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.ContentType;
import com.feinno.sdk.enums.DirectedType;
import com.feinno.sdk.utils.JsonUtils;
import java.io.Serializable;

public class FTMessageSession implements Serializable, Parcelable {
    /**文件存放路径*/
    public String filePath;
    /**消息发送方*/
    public String from;
    /**消息的聊天类型，参见{@link ChatType}*/
    public int chatType;
    /**是否是阅后即焚*/
    public boolean isBurn;
    /**消息接收方URI*/
    public String to;
    /**消息内容类型，参见{@link ContentType}*/
    public int contentType;
    /**消息发送时间*/
    public int sendTime;
    /**消息唯一标识*/
    public String imdnId;
    /**session id，用于唯一标识一个session*/
    public int id;
    /**文件名，发送方原始文件名*/
    public String fileName;
    public String contributionId;
    /**文件总长度*/
    public int fileSize;
    /**文件transfer id*/
    public String transferId;
    /**文件hash值*/
    public String hash;
    /**缩略图路径**/
    public String thumbnailPath;
    /**接收到的消息是否需要回执报告*/
    public boolean needReport;
    /** * 是否需要已读回执报告 */
    public boolean needReadReport;
    /** * 是否需要已焚报告 */
    public boolean needBurnReport;
    /**是否需要静默*/
    public boolean isSilence;
    /** 定向消息终端来源，参见{@link DirectedType} */
    public int directedType;
    /** 是否已发送送达报告 */
    public boolean isReport;
    /** 是否已发送已读报告 */
    public boolean isReadReport;
    /** 是否已发送已焚报告 */
    public boolean isBurnReport;
    /** 是否已读 */
    public boolean isRead;
    /** 是否已打开 */
    public boolean isOpen;
    public boolean isDelivered;
    /** 扩展字段（由客户端自定义,服务端透传） */
    public String extension;

    @Override
    public String toString() {
        return "FTMessageSession{" +
                "filePath='" + filePath + '\'' +
                ", from='" + from + '\'' +
                ", chatType=" + chatType +
                ", isBurn=" + isBurn +
                ", to='" + to + '\'' +
                ", contentType=" + contentType +
                ", sendTime=" + sendTime +
                ", imdnId='" + imdnId + '\'' +
                ", id=" + id +
                ", fileName='" + fileName + '\'' +
                ", contributionId='" + contributionId + '\'' +
                ", fileSize=" + fileSize +
                ", transferId='" + transferId + '\'' +
                ", hash='" + hash + '\'' +
                ", thumbnailPath='" + thumbnailPath + '\'' +
                ", needReport=" + needReport +
                ", needReadReport=" + needReadReport +
                ", needBurnReport=" + needBurnReport +
                ", isReport=" + isReport +
                ", isReadReport=" + isReadReport +
                ", isBurnReport=" + isBurnReport +
                ", isRead=" + isRead +
                ", isOpen=" + isOpen +
                ", extension=" + extension +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] b = new boolean[1];
        dest.writeString(filePath);
        dest.writeString(from);
        b[0] = isBurn;
        dest.writeBooleanArray(b);
        dest.writeString(thumbnailPath);
        dest.writeString(imdnId);
        dest.writeInt(fileSize);
        dest.writeInt(chatType);
        dest.writeString(transferId);
        dest.writeString(to);
        dest.writeInt(contentType);
        dest.writeInt(sendTime);
        dest.writeString(fileName);
        dest.writeInt(id);
        dest.writeString(hash);
        dest.writeString(contributionId);
        b[0] = needReport;
        dest.writeBooleanArray(b);
        b[0] = needReadReport;
        dest.writeBooleanArray(b);
        b[0] = needBurnReport;
        dest.writeBooleanArray(b);
        b[0] = isSilence;
        dest.writeBooleanArray(b);
        dest.writeInt(directedType);
        b[0] = isReport;
        dest.writeBooleanArray(b);
        b[0] = isReadReport;
        dest.writeBooleanArray(b);
        b[0] = isBurnReport;
        dest.writeBooleanArray(b);
        b[0] = isRead;
        dest.writeBooleanArray(b);
        b[0] = isOpen;
        dest.writeBooleanArray(b);
        b[0] = isDelivered;
        dest.writeBooleanArray(b);
        dest.writeString(extension);
    }

    /**
     * 将json字符串转换为FTMessageSession对象
     * @param json 符合特定格式的json字符串
     * @return FTMessageSession对象
     */
    public static FTMessageSession fromJson(String json) {
        return JsonUtils.fromJson(json, FTMessageSession.class);
    }

    public static final Creator<FTMessageSession> CREATOR = new Creator<FTMessageSession>() {

        @Override
        public FTMessageSession createFromParcel(Parcel source) {
            boolean[] b = new boolean[1];
            FTMessageSession ret = new FTMessageSession();
            ret.filePath = source.readString();
            ret.from = source.readString();
            source.readBooleanArray(b);
            ret.isBurn = b[0];
            ret.thumbnailPath = source.readString();
            ret.imdnId = source.readString();
            ret.fileSize = source.readInt();
            ret.chatType = source.readInt();
            ret.transferId = source.readString();
            ret.to = source.readString();
            ret.contentType = source.readInt();
            ret.sendTime = source.readInt();
            ret.fileName = source.readString();
            ret.id = source.readInt();
            ret.hash = source.readString();
            ret.contributionId = source.readString();
            source.readBooleanArray(b);
            ret.needReport = b[0];
            source.readBooleanArray(b);
            ret.needReadReport = b[0];
            source.readBooleanArray(b);
            ret.needBurnReport = b[0];
            source.readBooleanArray(b);
            ret.isSilence = b[0];
            ret.directedType = source.readInt();
            source.readBooleanArray(b);
            ret.isReport = b[0];
            source.readBooleanArray(b);
            ret.isReadReport = b[0];
            source.readBooleanArray(b);
            ret.isBurnReport = b[0];
            source.readBooleanArray(b);
            ret.isRead = b[0];
            source.readBooleanArray(b);
            ret.isOpen = b[0];
            source.readBooleanArray(b);
            ret.isDelivered = b[0];
            ret.extension = source.readString();
            return ret;
        }

        @Override
        public FTMessageSession[] newArray(int size) {
            return new FTMessageSession[size];
        }
    };


}
