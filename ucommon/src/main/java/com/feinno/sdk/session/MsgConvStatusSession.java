package com.feinno.sdk.session;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.utils.JsonUtils;
import com.feinno.sdk.enums.ChatType;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 同步会话状态信息
 */
public class MsgConvStatusSession implements Serializable, Parcelable {

    /**
     * 会话状态操作类型, 1:已读 2:删除
     */
    public int convstate;

    /**
     * 聊天类型，参见{@link ChatType}
     */
    public int chatType;

    /**
     * 会话状态
     */
    public Conversation[] conversations;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(convstate);
        dest.writeInt(chatType);
        dest.writeParcelableArray(conversations, 0);
    }

    @Override
    public String toString() {
        return "MsgConvStatusSession{" +
                "convstate=" + convstate +
                ", chatType='" + chatType + '\'' +
                ", conversations=" + Arrays.toString(conversations) +
                '}';
    }

    /**
     * 将json字符串转换为MsgConvStatusSession对象
     * @param json 符合特定格式的json字符串
     * @return MsgConvStatusSession对象
     */
    public static MsgConvStatusSession fromJson(String json) {
        return JsonUtils.fromJson(json, MsgConvStatusSession.class);
    }

    public static final Creator<MsgConvStatusSession> CREATOR = new Creator<MsgConvStatusSession>() {

        @Override
        public MsgConvStatusSession createFromParcel(Parcel source) {
            MsgConvStatusSession ret = new MsgConvStatusSession();
            ret.convstate = source.readInt();
            ret.chatType = source.readInt();
            Parcelable[] parcelableArray = source.readParcelableArray(Conversation.class.getClassLoader());
            if (parcelableArray != null) {
                ret.conversations = Arrays.copyOf(parcelableArray, parcelableArray.length, Conversation[].class);
            }
            return ret;
        }

        @Override
        public MsgConvStatusSession[] newArray(int size) {
            return new MsgConvStatusSession[size];
        }
    };

}
