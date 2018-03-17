package com.feinno.sdk.dapi;

import android.app.PendingIntent;

import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.session.AvSession;
import com.feinno.sdk.utils.LogUtil;

/**
 * VoWifi 音视频通话通话接口
 */
public class AVCallManager {
	private static final String TAG = "AVCallManager";

    /**
     * 发送音视频会话邀请
     * @param owner 操作用户
     * @param number 邀请的电话号码
     * @param isAudio true表示音频会话，false表示视频会话
     * @param pendingIntent 用于处理回调结果的PendingIntent，通话结束之前的所有回调都会通过此接口返回
     *               		通过intent.getExtras().getParcelable({@link BroadcastActions}.EXTRA_SESSION)可以获取{@link AvSession}对象。
	 *                      如果为null，则会发送一条action为{@link BroadcastActions}.ACTION_AV_CALL_OUT的广播，
	 *                      通过intent.getExtras().getParcelable({@link BroadcastActions}.EXTRA_SESSION)可以获取{@link AvSession}对象。
     * @throws Exception
     */
    public static void call(String owner, String number, boolean isAudio, PendingIntent pendingIntent) throws Exception {
		LogUtil.i(TAG, "av call, owner = " + owner + ", number = " + number + ", is audio = " + isAudio);
        WorkingServiceProxy.instance().avInvite(owner, number, isAudio, pendingIntent);
    }

    public static void multiCall(String owner, String[] numbers, boolean isAudio, PendingIntent pengdingIntent) throws Exception {
        LogUtil.i(TAG, "av multi call");
        StringBuilder builder = new StringBuilder();
        for (String number : numbers) {
            builder.append(number);
            builder.append(";");
        }
        if (numbers.length > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        WorkingServiceProxy.instance().avMultiInvite(owner, builder.toString(), isAudio, pengdingIntent);
    }

    public static void inviteUser(String owner, int id, String number, PendingIntent pendingIntent) throws Exception {
        LogUtil.i(TAG, "invite user, owner = " + owner + ", number = " + number);
        WorkingServiceProxy.instance().avInviteUser(owner, id, number, pendingIntent);
    }

    /**
     * 接受音视频会话邀请
     * @param owner 操作用户
     * @param id 音视频会话session id，可以通过{@link AvSession}获得
     * @param asAudio 是否以音频方式接听
     * @throws Exception
     */
    public static void answer(String owner, int id, boolean asAudio) throws Exception {
		LogUtil.i(TAG, "av answer, owner = " + owner);
        WorkingServiceProxy.instance().avAnswer(owner, id, asAudio);
    }

    /**
     * 重新回到前台（暂未实现）
     * @param owner 操作用户
     * @param id 音视频会话session id，可以通过{@link AvSession}获得
     * @throws Exception
     */
    private static void resume(String owner, int id) throws Exception {
		LogUtil.i(TAG, "av resume, owner = " + owner);
    }

    /**
     * 维持后台接通（暂未实现）
     * @param owner 操作用户
     * @param id 音视频会话session id，可以通过{@link AvSession}获得
     * @throws Exception
     */
    private static void hold(String owner, int id) throws Exception {
		LogUtil.i(TAG, "av hold, owner = " + owner);
    }

    /**
     * 给音视频邀请方振铃应答
     * @param owner 操作用户
     * @param id 音视频会话session id，可以通过{@link AvSession}获得
     * @throws Exception
     */
    public static void ring(String owner, int id) throws Exception {
		LogUtil.i(TAG, "av ring, owner = " + owner);
        WorkingServiceProxy.instance().avRing(owner, id);
    }

    /**
     * 结束音视频会话
     * @param owner 操作用户
     * @param id 音视频会话session id，可以通过{@link AvSession}获得
     * @throws Exception
     */
    public static void hungUp(String owner, int id) throws Exception {
		LogUtil.i(TAG, "av hungUp, owner = " + owner);
        WorkingServiceProxy.instance().avHungUp(owner, id);
    }

    /**
     * 静音（暂未实现）
     * @param owner 操作用户
     * @param id 音视频会话session id，可以通过{@link AvSession}获得
     * @throws Exception
     */
    private static void mute(String owner, int id) throws Exception {
		LogUtil.i(TAG, "av mute, owner = " + owner);
        WorkingServiceProxy.instance().avMute(owner, id);
    }


    /**
     * 音视频转换（暂未实现）
     * @param owner 操作用户
     * @param id 音视频会话session id，可以通过{@link AvSession}获得
     * @throws Exception
     */
    private static void toggle(String owner, int id) throws Exception {
		LogUtil.i(TAG, "av toggle, owner = " + owner);
        WorkingServiceProxy.instance().avToggle(owner, id);
    }
}
