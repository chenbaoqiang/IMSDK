package com.feinno.sdk.session.v3;

import android.os.Parcel;
import android.os.Parcelable;
import com.feinno.sdk.result.v3.UserBasicInfo;

public class BuddyEventSession implements Parcelable {


    /** 参照 {@link BuddyOpEnum} */
    public int op;

    /** 事件发生时间, 类型:Interval */
    public int time;

    /** 事件发起方 */
    public String fromUser;

    /**
     * 事件接收方
     */
    public String toUser;

    /**op 为{@link BuddyOpEnum}.REQ_HANDLED 时，{@link BuddyOpEnum}.ADDED_BUDDY时使用
     * 添加好友处理结果，默认 0：未处理时，1 加好友成功，2 拒绝"
     * */
    public int handleResult;

    /**op 为为{@link BuddyOpEnum}.REQ_HANDLED 时，{@link BuddyOpEnum}.ADDED_BUDDY时使用， 描述了原因*/
    public String reason;

    /**
     * 通知id
     */
    public String imdnId;

    /**
     * 被添加好友时使用，包含了添加者的基本信息
     */
    public UserBasicInfo userInfo;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(op);
        dest.writeInt(time);
        dest.writeString(fromUser);
        dest.writeString(toUser);
        dest.writeInt(handleResult);
        dest.writeString(reason);
        dest.writeString(imdnId);
        dest.writeParcelable(userInfo, 0);
    }

    public static final Creator<BuddyEventSession> CREATOR = new Creator<BuddyEventSession>() {

        @Override
        public BuddyEventSession createFromParcel(Parcel source) {
            BuddyEventSession ret = new BuddyEventSession();
            ret.op = source.readInt();
            ret.time = source.readInt();
            ret.fromUser = source.readString();
            ret.toUser = source.readString();
            ret.handleResult = source.readInt();
            ret.reason = source.readString();
            ret.imdnId = source.readString();
            ret.userInfo = source.readParcelable(UserBasicInfo.class.getClassLoader());
            return ret;
        }

        @Override
        public BuddyEventSession[] newArray(int size) {
            return new BuddyEventSession[size];
        }
    };

}
