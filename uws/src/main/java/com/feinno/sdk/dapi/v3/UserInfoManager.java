package com.feinno.sdk.dapi.v3;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.RemoteException;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.args.v3.UserInfoArg;
import com.feinno.sdk.dapi.WorkingServiceProxy;
import com.feinno.sdk.enums.ClientType;
import com.feinno.sdk.result.*;
import com.feinno.sdk.result.v3.UserInfoResult;
import com.feinno.sdk.result.v3.UserPortraitResult;
import com.feinno.sdk.result.v3.UserProfileResult;
import com.feinno.sdk.utils.LogUtil;

/**
 * 个人信息接口
 */
public class UserInfoManager {

    private static final String TAG = "UserInfoManager";

    /**
     * 修改个人信息
     *
     * @param owner         操作用户
     * @param arg           要修改的用户信息
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserProfileResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_USERPROFILE_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserProfileResult}对象。
     */
    public static void setProfile(String owner, UserInfoArg arg, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "set user profile, owner = " + owner);
        WorkingServiceProxy.instance().userSetProfile(
                owner,
                arg.nickName,
                arg.impresa,
                arg.firstName,
                arg.lastName,
                arg.gender,
                arg.email,
                arg.birthday,
                arg.clientExtra,
                pendingIntent);
    }

    /**
     * 修改个人职业信息
     *
     * @param owner         操作用户
     * @param company       要修改的公司
     * @param position      要修改的职位
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserProfileResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_USERPROFILE_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserProfileResult}对象。
     */
    public static void setCareer(String owner, String company, String position, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "set user profile, owner = " + owner);
        WorkingServiceProxy.instance().userSetCareer(
                owner,
                company,
                position,
                pendingIntent);
    }

    /**
     * 设置个人消息免打扰
     *
     * @param owner         操作用户
     * @param dndFlag       消息免打扰  0:未设置 1:已设置
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_DNDFLAG_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     */
    public static void setDndFlag(String owner, int dndFlag, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "setDndFlag, owner = " + owner);
        WorkingServiceProxy.instance().userSetDndFlag(
                owner,
                dndFlag,
                pendingIntent);
    }

    /**
     * 设置加好友自动同意开关
     *
     * @param owner         操作用户
     * @param buddyFlag     加好友自动同意开关 0 需要申请 1 自动同意
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_DNDFLAG_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     */
    public static void setBuddyFlag(String owner, int buddyFlag, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "setDndFlag, owner = " + owner);
        WorkingServiceProxy.instance().userSetBuddyFlag(
                owner,
                buddyFlag,
                pendingIntent);
    }

    /**
     * 设置个人隐私uid查询开关
     *
     * @param owner         操作用户
     * @param uidFlag       设置个人隐私uid查询开关 0 公开 1 不公开
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_PERMISSIONUIDFLAG_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     */
    public static void setPermissionUidFlag(String owner, int uidFlag, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "setDndFlag, owner = " + owner);
        WorkingServiceProxy.instance().useretPermissionUidFlag(
                owner,
                uidFlag,
                pendingIntent);
    }

    /**
     * 设置个人隐私username查询开关
     *
     * @param owner         操作用户
     * @param unameFlag       设置个人隐私username查询开关 0 公开 1 不公开
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_PERMISSIONUNAMEFLAG_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     */
    public static void setPermissionUnameFlag(String owner, int unameFlag, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "setDndFlag, owner = " + owner);
        WorkingServiceProxy.instance().useretPermissionUnameFlag(
                owner,
                unameFlag,
                pendingIntent);
    }

    /***
     * 获取个人信息
     * @param owner 操作用户
     * @param user 获取对象用户
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserProfileResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_USERPROFILE_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserProfileResult}对象。
     * @throws RemoteException
     */
    public static void getProfile(String owner, String user, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "get user profile, owner = " + owner + ", user = " + user);
        WorkingServiceProxy.instance().userGetProfile(owner, user, pendingIntent);
    }

    /***
     * 获取用户的基本信息
     * @param owner 操作用户
     * @param ids 获取对象的id,可以用";"号分隔来传入多个对象id
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserInfoResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_USERINFO_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserInfoResult}对象。
     * @throws RemoteException
     */
    public static void getUserInfo(String owner, String ids, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "get user info, owner = " + owner + ", ids = " + ids);
        WorkingServiceProxy.instance().userGetInfo(owner, ids, pendingIntent);
    }


    /***
     * 获取头像信息
     * @param owner 操作用户
     * @param userId 要获取的用户id
     * @param isSmall 是否获取小图
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserPortraitResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_USERPROTRAIT_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserPortraitResult}对象。
     * @throws RemoteException
     */
    public static void getPortrait(String owner, int userId, boolean isSmall, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "get user portrait, owner = " + owner + ", user = " + userId);
        WorkingServiceProxy.instance().userGetPortrait(owner, userId, isSmall, pendingIntent);
    }


    /***
     * 设置自己的头像
     * @param owner 操作用户
     * @param filePath 头像本地路径
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserPortraitResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_USERPROTRAIT_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link UserPortraitResult}对象。
     * @throws RemoteException
     */
    public static void setPortrait(String owner, String filePath, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "set user portrait, owner = " + owner + ", path = " + filePath);
        WorkingServiceProxy.instance().userSetPortrait(owner, filePath, pendingIntent);
    }

    /**
     * 获取激活设备列表
     * @param owner 操作用户
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link EndPointResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_ENDPOINTLIST_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link EndPointResult}对象。
     * @throws RemoteException
     */
    public static void getEndpointList(String owner, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "getEndpointList, owner = " + owner);
        WorkingServiceProxy.instance().getEndpointList(owner, pendingIntent);
    }

    /**
     * 踢设备下线
     * @param owner 操作用户
     * @param clientId 客户端ID
     * @param clientType 客户端类型 参见{@link ClientType}
     * @param clientVersion 客户端版本
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_ENDPOINT_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     * @throws RemoteException
     */
    public static void bootEndpoint(String owner, String clientId, int clientType, String clientVersion, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "bootEndpoint, owner = " + owner + ", clientId = " + clientId);
        WorkingServiceProxy.instance().bootEndpoint(owner, clientId, clientType, clientVersion, pendingIntent);
    }

    /**
     * 删除设备信息
     * @param owner 操作用户
     * @param clientId 客户端ID
     * @param clientType 客户端类型 参见{@link ClientType}
     * @param clientVersion 客户端版本
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_ENDPOINT_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     * @throws RemoteException
     */
    public static void deleteEndpoint(String owner, String clientId, int clientType, String clientVersion, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "deleteEndpoint, owner = " + owner + ", clientId = " + clientId);
        WorkingServiceProxy.instance().deleteEndpoint(owner, clientId, clientType, clientVersion, pendingIntent);
    }

    /**
     * 设置设备消息免打扰
     *  未填写begintime与endtime，表示全天免打扰。这两个属性需同时添写
     *  设置了开始行结束时间后,每天的这个时间段都是免打扰状态,直到flag设置为0为至
     * @param owner 操作用户
     * @param flag 消息免打扰 0:未开启,1:开启
     * @param beginTime 免打扰开始时间,格式：H:M. 如 10:10 表示10点10分开始
     * @param endTime 免打扰结否时间,格式：H:M, 如 14:10 表示14点10分结束
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_SET_ENDPOINT_DND_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ActionResult}对象。
     * @throws RemoteException
     */
    public static void setEndpointDND(String owner, int flag, String beginTime, String endTime, PendingIntent pendingIntent) throws RemoteException {
        WorkingServiceProxy.instance().setEndpointDND(owner, flag, beginTime, endTime, pendingIntent);
    }

    /**
     * 获取会话列表
     * @param owner 操作用户
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ConvListResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_GET_CONVLIST_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ConvListResult}对象。
     * @throws RemoteException
     */
    public static void getConvList(String owner, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "getConvList, owner = " + owner);
        WorkingServiceProxy.instance().getConvList(owner, pendingIntent);
    }

    /**
     * 获取指定会话历史消息,根据历史消息接收时间的反序,分页拉去一个会话的历史消息
     * @param owner 操作用户
     * @param convType 会话类型: 1 二人,2 群,3 通知，@see {ConvType}
     * @param convId 会话ID
     * @param pageLimit 一页最大条数
     * @param beginImdnId 起始位置,填NULL表示获取最新一页
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ConvHistoryResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_GET_CONV_HISTORY_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link ConvHistoryResult}对象。
     * @throws RemoteException
     */
    public static void getConvHistory(String owner, int convType, String convId, int pageLimit, String beginImdnId, PendingIntent pendingIntent) throws RemoteException {
        WorkingServiceProxy.instance().getConvHistory(owner, convType, convId, pageLimit, beginImdnId, pendingIntent);
    }

    /**
     * 获取用户Token
     * @param owner 操作用户
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link TokenResult}对象。
     *                      如果为null，则会发送一条action为{@link BroadcastActions#ACTION_TOKEN_RESULT} 的广播
     *                      通过{@link BroadcastActions#getResult(Intent)}可以获取{@link TokenResult}对象。
     * @throws RemoteException
     */
    public static void token(String owner, PendingIntent pendingIntent) throws RemoteException {
        LogUtil.i(TAG, "token, owner = " + owner);
        WorkingServiceProxy.instance().token(owner, pendingIntent);
    }
}
