//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.ContentType;
import com.feinno.sdk.enums.DirectedType;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 此类用于记录消息的状态
 */
public class MessageSession implements Serializable, Parcelable {

    /**回执类型，参见{@link com.feinno.sdk.args.MessageArg}*/
    public int reportType;
    /**消息内容*/
    public String content;
    /**消息发送方URI*/
    public String fromUri;
    /**消息的聊天类型，参见{@link ChatType}*/
    public int chatType;
    /**是否已回执*/
    public boolean isReport;
    /**是否是阅后即焚*/
    public boolean isBurn;
    /**消息接收方URI*/
    public String toUri;
    /**消息内容类型，参见{@link ContentType}*/
    public int contentType;
    /**消息发送时间*/
    public int sendTime;
    /**消息唯一标识*/
    public String imdnId;
    /**文件发送或接收进度*/
    public int progressSize;
    /**session id，用于唯一标识一个session*/
    public int id;
    /**文件名*/
    public String fileName;
    public String contributionId;
    /**文件总长度*/
    public int fileSize;
    /**文件transfer id*/
    public String transferId;
    /**文件hash值*/
    public String hash;
	/**缩略图路径**/
	public String thumbnail;
    /**接收到的消息是否需要回执报告*/
    public boolean needReport;
    /**表情id， 商店表情消息使用*/
    public String vemoticonId;
    /**表情名称， 商店表情消息使用*/
    public String vemoticonName;
    /**彩云文件名称, 彩云消息使用*/
    public String cloudfileName;
    /**彩云文件大小, 彩云消息使用*/
    public String cloudfileSize;
    /**彩云文件下载地址, 彩云消息使用*/
    public String cloudfileUrl;
    /**是否需要静默*/
    public boolean isSilence;
    /**需要@的群成员号码，只在群消息中使用。多个号码之间使用";"(分号)隔开*/
    public String ccNumber;
    /** 定向消息终端来源，参见{@link DirectedType} */
    public int directedType;
    /** 扩展字段（由客户端自定义,服务端透传） */
    public String extension;
    public PubMessageSession publicMsg;

  @Override
  public int describeContents() {
    return 0;
   }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
	  boolean[] b = new boolean[1];
	  dest.writeString(fromUri);
	  b[0] = isBurn;
	  dest.writeBooleanArray(b);
	  b[0] = isReport;
	  dest.writeBooleanArray(b);
	  dest.writeString(thumbnail);
	  dest.writeString(imdnId);
	  dest.writeBooleanArray(b);
	  dest.writeInt(reportType);
	  dest.writeString(content);
	  dest.writeInt(fileSize);
	  dest.writeInt(chatType);
	  dest.writeString(transferId);
	  dest.writeString(toUri);
	  dest.writeInt(contentType);
	  dest.writeInt(sendTime);
	  dest.writeString(fileName);
	  dest.writeInt(progressSize);
	  dest.writeInt(id);
	  dest.writeString(hash);
	  dest.writeString(contributionId);
      b[0] = needReport;
      dest.writeBooleanArray(b);
      dest.writeString(vemoticonId);
      dest.writeString(vemoticonName);
      dest.writeString(cloudfileName);
      dest.writeString(cloudfileSize);
      dest.writeString(cloudfileUrl);
      b[0] = isSilence;
      dest.writeBooleanArray(b);
      dest.writeString(ccNumber);
      dest.writeInt(directedType);
      dest.writeString(extension);
  }

    /**
     * 将json字符串转换为MessageSession对象
     * @param json 符合特定格式的json字符串
     * @return MessageSession对象
     */
  public static MessageSession fromJson(String json) {
      MessageSession msg =  JsonUtils.fromJson(json, MessageSession.class);
      return msg;
  }

  public static final Creator<MessageSession> CREATOR = new Creator<MessageSession>() {

    @Override
    public MessageSession createFromParcel(Parcel source) {
		boolean[] b = new boolean[1];
		MessageSession ret = new MessageSession();
		ret.fromUri = source.readString();
		source.readBooleanArray(b);
		ret.isBurn = b[0];
		source.readBooleanArray(b);
		ret.isReport = b[0];
		ret.thumbnail = source.readString();
		ret.imdnId = source.readString();
		source.readBooleanArray(b);
		ret.reportType = source.readInt();
		ret.content = source.readString();
		ret.fileSize = source.readInt();
		ret.chatType = source.readInt();
		ret.transferId = source.readString();
		ret.toUri = source.readString();
		ret.contentType = source.readInt();
		ret.sendTime = source.readInt();
		ret.fileName = source.readString();
		ret.progressSize = source.readInt();
		ret.id = source.readInt();
		ret.hash = source.readString();
		ret.contributionId = source.readString();
		source.readBooleanArray(b);
		ret.needReport = b[0];
        ret.vemoticonId = source.readString();
        ret.vemoticonName = source.readString();
        ret.cloudfileName = source.readString();
        ret.cloudfileSize = source.readString();
        ret.cloudfileUrl = source.readString();
        source.readBooleanArray(b);
        ret.isSilence = b[0];
        ret.ccNumber = source.readString();
        ret.directedType = source.readInt();
        ret.extension = source.readString();
		return ret;
    }

    @Override
    public MessageSession[] newArray(int size) {
      return new MessageSession[size];
    }
  };
}
