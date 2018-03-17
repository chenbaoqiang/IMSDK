package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 *订阅联系人后,服务器推送的状态通知,每次推送为当前用户的在线登陆点情况(包括所有登陆点)
 */
public class PresenceSession implements Serializable, Parcelable {
    /**
     * 变更用户uid
     */
    public long uid;

    /**
     * 登陆点状态列表 参见{@link PresenceInfo}
     */
    public PresenceInfo[] presences;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(uid);
        dest.writeParcelableArray(presences, 0);
    }
    @Override
    public String toString() {
        return "PresenceSession{" +
                ", udi=" + uid +
                "presences=" + Arrays.toString(presences) +
                '}';
    }

    /**
     * 将json字符串转换为PresenceSession对象
     * @param json 符合特定格式的json字符串
     * @return PresenceSession对象
     */
    public static PresenceSession fromJson(String json) {
        return JsonUtils.fromJson(json, PresenceSession.class);
    }

    public static final Creator<PresenceSession> CREATOR = new Creator<PresenceSession>() {

        @Override
        public PresenceSession createFromParcel(Parcel source) {
            PresenceSession ret = new PresenceSession();
            ret.uid = source.readLong();
            Parcelable[] parcelableArray = source.readParcelableArray(PresenceInfo.class.getClassLoader());
            if (parcelableArray != null) {
                ret.presences = Arrays.copyOf(parcelableArray, parcelableArray.length, PresenceInfo[].class);
            }
            return ret;
        }

        @Override
        public PresenceSession[] newArray(int size) {
            return new PresenceSession[size];
        }
    };
}
