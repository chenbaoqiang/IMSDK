package com.feinno.sdk.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;

import java.io.Serializable;

/**
 * 消息转短链接结果
 */
public class Msg2ShorturlResult extends ActionResult implements Serializable, Parcelable {
    /**
     * 消息id
     */
    public String imdnId;
    /**
     * 短链接地址
     */
    public String shorturl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(errorCode);
        dest.writeString(errorExtra);
        dest.writeString(imdnId);
        dest.writeString(this.shorturl);
    }

    public static Msg2ShorturlResult fromJson(String json) {
        return JsonUtils.fromJson(json, Msg2ShorturlResult.class);
    }
    protected Msg2ShorturlResult(Parcel in) {
        this.id = in.readInt();
        this.errorCode = in.readInt();
        this.errorExtra = in.readString();
        this.imdnId = in.readString();
        this.shorturl = in.readString();
    }

    public static final Creator<Msg2ShorturlResult> CREATOR = new Creator<Msg2ShorturlResult>() {
        @Override
        public Msg2ShorturlResult createFromParcel(Parcel source) {
            return new Msg2ShorturlResult(source);
        }

        @Override
        public Msg2ShorturlResult[] newArray(int size) {
            return new Msg2ShorturlResult[size];
        }
    };
}
