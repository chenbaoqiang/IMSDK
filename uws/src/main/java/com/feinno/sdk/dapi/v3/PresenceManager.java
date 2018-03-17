package com.feinno.sdk.dapi.v3;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.RemoteException;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.dapi.WorkingServiceProxy;
import com.feinno.sdk.result.v3.PresenceResult;
import com.feinno.sdk.utils.LogUtil;

public class PresenceManager {
    public static final String TAG = "PresenceManager";

    /**
     * 订阅联系人在线状态,目前订阅关系服务器保存6小时,订阅关系过期后好友状态将不推送,需要重新订阅
     * @param owner 当前用户 UserId
     * @param cids 被订阅联系人userid 逗号分隔字符串,如:"849180,849179"
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link PresenceResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_PRESENCE_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link PresenceResult}对象。
     */
    public static void subPresence(String owner, String cids, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "subPresence, owner = " + owner + ", cids = " + cids);
        WorkingServiceProxy.instance().subPresence(owner, cids, pendingIntent);
    }

    /**
     * 退订好友在线状态
     * @param owner 当前用户 UserId
     * @param cids 被退订联系人userid 逗号分隔字符串,如:"849180,849179"
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link PresenceResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_PRESENCE_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link PresenceResult}对象。
     */
    public static void unSubPresence(String owner, String cids, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "unSubPresence, owner = " + owner + ", cids = " + cids);
        WorkingServiceProxy.instance().unSubPresence(owner, cids, pendingIntent);
    }


    /**
     * 获取联系人在线状态
     * @param owner 当前用户 UserId
     * @param contactId 被订阅联系人userid
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link GetPresenceResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_PRESENCE_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link GetPresenceResult}对象。
     */
    public static void getPresence(String owner, String contactId, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "getPresence, owner = " + owner + ", contactId = " + contactId);
        WorkingServiceProxy.instance().getPresence(owner, contactId, pendingIntent);
    }
}
