package com.feinno.sdk.dapi.v3;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.RemoteException;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.dapi.WorkingServiceProxy;
import com.feinno.sdk.result.v3.BuddyResult;
import com.feinno.sdk.utils.LogUtil;


/**
 * 好友操作API接口
 *
 */
public class BuddyManager {

    public static final String TAG = "BuddyManager";

    /**
     * 添加好友
     * @param owner 当前用户 UserId
     * @param user 被添加用户的号码
     * @param reason 申请信息
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_BUDDY_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     */
    public static void addBuddy(String owner, String user, String reason, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "addBuddy, owner = " + owner + ", user = " + user);
        WorkingServiceProxy.instance().buddyAdd(owner, user, reason, pendingIntent);
    }


    /**
     * 删除好友
     * @param owner 当前用户 UserId
     * @param userId 被删除的用户 UserId
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_BUDDY_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     */
    public static void deleteBuddy(String owner, int userId, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "deleteBuddy, owner = " + owner + ", userid = " + userId);
        WorkingServiceProxy.instance().buddyDel(owner, userId, pendingIntent);
    }

    /**
     * 同意添加好友请求
     * @param owner 当前用户
     * @param userId 邀请人的 user id
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_BUDDY_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     */
    public static void acceptReq(String owner, int userId, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "acceptReq, owner = " + owner + ", userid = " + userId);
        WorkingServiceProxy.instance().buddyAccept(owner, userId, pendingIntent);
    }

    /**
     * 拒绝添加好友请求
     * @param owner 当前用户
     * @param userId 邀请人的 User Id
     * @param reason 拒绝原因
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_BUDDY_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     */
    public static void refuseReq(String owner, int userId, String reason, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "buddyRefuse, owner = " + owner + ", userid = " + userId);
        WorkingServiceProxy.instance().buddyRefuse(owner, userId, reason, pendingIntent);
    }

    /**
     * 给好友添加备注名称
     * @param owner 当前用户 UserId
     * @param userId 被添加备注 UserId
     * @param name 备注名称
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_BUDDY_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     */
    public static void memoBuddy(String owner, int userId, String name, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "getCap, owner = " + owner + ", userid = " + userId);
        WorkingServiceProxy.instance().buddyMemo(owner, userId, name, pendingIntent);
    }

    /**
     * 设置好友消息屏蔽
     * @param owner 当前用户 UserId
     * @param userId 被设置用户 UserId
     * @param dndFlag 消息免打扰开关，0: 提醒(默认)  1: 句打扰
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_BUDDY_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link BuddyResult}对象。
     */
    public static void dndBuddy(String owner, int userId, int dndFlag, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "getCap, owner = " + owner + ", userid = " + userId);
        WorkingServiceProxy.instance().dndBuddy(owner, userId, dndFlag, pendingIntent);
    }

    /**
     * 拉黑某人
     * @param owner 当前用户 UserId
     * @param userId 要拉黑的用户 UserId or UserName(+86手机号)
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_BKLIST_ADD_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.ActionResult}对象。
     */
    public static void addBlacklist(String owner, String userId, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "bklistAdd, owner = " + owner + ", userid = " + userId);
        WorkingServiceProxy.instance().bklistAdd(owner, userId, pendingIntent);
    }

    /**
     * 移除黑名单
     * @param owner 当前用户 UserId
     * @param userId 要移除的用户 UserId or UserName(+86手机号)
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_BKLIST_REMOVE_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.ActionResult}对象。
     */
    public static void removeBlacklist(String owner, String userId, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "bklistRemove, owner = " + owner + ", userid = " + userId);
        WorkingServiceProxy.instance().bklistRemove(owner, userId, pendingIntent);
    }

    /**
     * 获取黑名单列表
     * @param owner 当前用户 UserId
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link }对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_BKLIST_ADD_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.v3.BklistGetResult}对象。
     */
    public static void getBlacklist(String owner, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "bklistGet, owner = " + owner);
        WorkingServiceProxy.instance().bklistGet(owner, pendingIntent);
    }

    /**
     * 添加设备
     * @param owner 当前用户 UserId
     * @param userId 要添加的设备 UserId or UserName(IOT:xxxxx)
     * @param password 设备密码
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_DEVICE_ADD_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.ActionResult}对象。
     */
    public static void addDevice(String owner, String userId, String password, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "DeviceAdd, owner = " + owner + ", userid = " + userId);
        WorkingServiceProxy.instance().deviceAdd(owner, userId, password,pendingIntent);
    }

    /**
     * 移除设备
     * @param owner 当前用户 UserId
     * @param userId 要移除的用户 UserId or UserName
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_DEVICE_REMOVE_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.ActionResult}对象。
     */
    public static void removeDevice(String owner, String userId, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "deviceRemove, owner = " + owner + ", userid = " + userId);
        WorkingServiceProxy.instance().deviceRemove(owner, userId, pendingIntent);
    }

    /**
     * 获取黑名单列表
     * @param owner 当前用户 UserId
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link }对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_DEVICE_LIST_GET_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.v3.DeviceListGetResult}对象。
     */
    public static void getDeviceList(String owner, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "devicelistGet, owner = " + owner);
        WorkingServiceProxy.instance().deviceListGet(owner, pendingIntent);
    }

    /**
     * 获取设备状态
     * @param owner 当前用户 UserId
     * @param userId 要添加的设备 UserId or UserName(IOT:xxxxx)
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过{@link BroadcastActions#getResult(Intent)}可以获取{@link }对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_DEVICE_STATUS_GET_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link com.feinno.sdk.result.v3.DeviceStatusGetResult}对象。
     */
    public static void getDeviceStatus(String owner, String userId, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "devicelistGet, owner = " + owner);
        WorkingServiceProxy.instance().deviceStatusGet(owner, userId ,pendingIntent);
    }
}
