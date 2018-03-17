//Generated file. DO NOT modify manually.

package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.enums.AvSessionStates;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;


/**
 * 此类用于记录音视频通话的状态
 */
public class AvSession implements Serializable, Parcelable {

    /**session的创建时间*/
    public int createTime;
    /**通话对方手机号*/
    public String number;
    /**自己手机号*/
    public String selfNumber;
    /**音视频通话接通时间*/
    public int startTime;
    /**音频通话ip，由server提供*/
    public String audioIp;
    /**视频通话ip，由server提供*/
    public String videoIp;
    /**视频通话port，由server提供*/
    public String videoPort;
    /**音频通话port，由server提供*/
    public String audioPort;
    /** 音频通道 SRC*/
    public int audioSrc;
    /** 视频通道 SRC*/
    public int videoSrc;
    /**
     * 状态码，取值参照枚举{@link AvSessionStates}
     */
    public int state;
    /**session id，用于唯一标识一个session*/
    public int id;
    /**是否为音频通话，用于区分音频或视频通话*/
    public boolean isAudio;
    /**是否为呼入请求，用于区分呼入或呼出*/
    public boolean isCallIn;
    /** 是否为多人模式 */
    public boolean isMulti;
    /** 使用的音频编解码 */
    public String audioCodec;
    /** 使用的视频编解码 */
    public String videoCodec;
    /** 多人模式下的通知信息 */
    public Conference conference;
    /** srtp的加密信息 */
    public AVCryptoInfo cryptoInfo;

  @Override
  public int describeContents() {
    return 0;
   }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
      boolean[] b = new boolean[1];
      dest.writeInt(createTime);
      dest.writeString(number);
      dest.writeString(selfNumber);
      dest.writeInt(startTime);
      dest.writeString(audioIp);
      dest.writeString(videoIp);
      dest.writeString(videoPort);
      dest.writeString(audioPort);
      dest.writeInt(audioSrc);
      dest.writeInt(videoSrc);
      dest.writeInt(state);
      dest.writeInt(id);
      b[0] = isAudio;
      dest.writeBooleanArray(b);
      b[0] = isCallIn;
      dest.writeBooleanArray(b);
      b[0] = isMulti;
      dest.writeBooleanArray(b);
      dest.writeString(audioCodec);
      dest.writeString(videoCodec);
      dest.writeValue(conference);
      dest.writeValue(cryptoInfo);
  }

    @Override
    public String toString() {
        return "AvSession{" +
                "createtime=" + createTime +
                ", number='" + number + '\'' +
                ", selfNumber='" + selfNumber + '\'' +
                ", startTime=" + startTime +
                ", audioIp='" + audioIp + '\'' +
                ", videoIp='" + videoIp + '\'' +
                ", videoPort='" + videoPort + '\'' +
                ", audioPort='" + audioPort + '\'' +
                ", audioSrc='" + audioSrc + '\'' +
                ", videoSrc='" + videoSrc + '\'' +
                ", state=" + state +
                ", id=" + id +
                ", isAudio=" + isAudio +
                ", isCallIn=" + isCallIn +
                ", isMulti=" + isMulti +
                ", audioCodec='" + audioCodec + '\'' +
                ", videoCodec='" + videoCodec + '\'' +
                ", conference=" + conference +
                ", cryptoInfo=" + cryptoInfo +
                '}';
    }

    /**
     * 将json字符转换为AvSession对象
     * @param json 符合特定格式的json字符串
     * @return AvSession对象
     */
  public static AvSession fromJson(String json) {
    return JsonUtils.fromJson(json, AvSession.class);
  }

  public static final Creator<AvSession> CREATOR = new Creator<AvSession>() {

    @Override
    public AvSession createFromParcel(Parcel source) {
        boolean[] b = new boolean[1];
        AvSession ret = new AvSession();
        ret.createTime = source.readInt();
        ret.number = source.readString();
        ret.selfNumber = source.readString();
        ret.startTime = source.readInt();
        ret.audioIp = source.readString();
        ret.videoIp = source.readString();
        ret.videoPort = source.readString();
        ret.audioPort = source.readString();
        ret.audioSrc = source.readInt();
        ret.videoSrc = source.readInt();
        ret.state = source.readInt();
        ret.id = source.readInt();
        source.readBooleanArray(b);
        ret.isAudio = b[0];
        source.readBooleanArray(b);
        ret.isCallIn = b[0];
        source.readBooleanArray(b);
        ret.isMulti = b[0];
        ret.audioCodec = source.readString();
        ret.videoCodec = source.readString();
        ret.conference = (Conference) source.readValue(Conference.class.getClassLoader());
        ret.cryptoInfo = (AVCryptoInfo) source.readValue(AVCryptoInfo.class.getClassLoader());
        return ret;
    }

    @Override
    public AvSession[] newArray(int size) {
      return new AvSession[size];
    }
  };
}
