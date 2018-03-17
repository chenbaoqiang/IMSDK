package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

public class AVCryptoInfo implements Serializable, Parcelable {
    /** 本地音频srtp加密算法描述 */
    public String localAudioCryptoSuite;
    /** 本地音频srtp加密使用的key */
    public String localAudioCryptoKey;
    /** 本地视频srtp加密算法描述 */
    public String localVideoCryptoSuite;
    /** 本地视频srtp加密使用的key */
    public String localVideoCryptoKey;
    /** 远端音频srtp加密算法描述 */
    public String remoteAudioCryptoSuite;
    /** 远端音频加密使用的key */
    public String remoteAudioCryptoKey;
    /** 远端音频加密参数。多个参数的情况下使用空格分隔。KDR UNENCRYPTED_SRTP UNENCRYPTED_SRTCP UNAUTHENTICATED_SRTP FEC_ORDER FEC_KEY WSH */
    public String remoteAudioCryptoParam;
    /** 远端视频srtp加密算法描述 */
    public String remoteVideoCryptoSuite;
    /** 远端视频srtp加密使用的key */
    public String remoteVideoCryptoKey;
    /** 远端视频srtp加密使用的参数。多个参数的情况下使用空格分隔。KDR UNENCRYPTED_SRTP UNENCRYPTED_SRTCP UNAUTHENTICATED_SRTP FEC_ORDER FEC_KEY WSH */
    public String remoteVideoCryptoParam;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(localAudioCryptoSuite);
        dest.writeString(localAudioCryptoKey);
        dest.writeString(localVideoCryptoSuite);
        dest.writeString(localVideoCryptoKey);
        dest.writeString(remoteAudioCryptoSuite);
        dest.writeString(remoteAudioCryptoKey);
        dest.writeString(remoteAudioCryptoParam);
        dest.writeString(remoteVideoCryptoSuite);
        dest.writeString(remoteVideoCryptoKey);
        dest.writeString(remoteVideoCryptoParam);
    }

    public static AVCryptoInfo fromJson(String json) {
        return JsonUtils.fromJson(json, AVCryptoInfo.class);
    }

    public static final Creator<AVCryptoInfo> CREATOR = new Creator<AVCryptoInfo>() {

        @Override
        public AVCryptoInfo createFromParcel(Parcel source) {
            AVCryptoInfo ret = new AVCryptoInfo();
            ret.localAudioCryptoSuite = source.readString();
            ret.localAudioCryptoKey = source.readString();
            ret.localVideoCryptoSuite = source.readString();
            ret.localVideoCryptoKey = source.readString();
            ret.remoteAudioCryptoSuite = source.readString();
            ret.remoteAudioCryptoKey = source.readString();
            ret.remoteAudioCryptoParam = source.readString();
            ret.remoteVideoCryptoSuite = source.readString();
            ret.remoteVideoCryptoKey = source.readString();
            ret.remoteVideoCryptoParam = source.readString();
            return ret;
        }

        @Override
        public AVCryptoInfo[] newArray(int size) {
            return new AVCryptoInfo[size];
        }
    };
}
