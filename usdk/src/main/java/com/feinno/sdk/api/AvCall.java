package com.feinno.sdk.api;

import com.feinno.sdk.Sdk;
import com.feinno.sdk.SdkApi;
import com.feinno.sdk.Callback;
import com.feinno.sdk.session.AvSession;

/**
 * 该类提供了用于音视频通话的相关接口
 */
public class AvCall {

    /**
     * 发送音视频会话邀请
     * @param phoneNumber 邀请的电话号码
     * @param isAudio true表示音频会话，false表示视频会话
     * @param intent 用于回调的PendingIntent
     * @return 异步调用结果
     */
    //public static int call(String phoneNumber, boolean isAudio, PendingIntent intent) {
    //    return -1;
    //}

    /**
     * 发送音视频会话邀请
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param phoneNumber 邀请的电话号码
     * @param isAudio true表示音频会话，false表示视频会话
     * @param cb 用于回调的Callback接口
     * @return 音视频通话session id
     */
    public static int call(Sdk.SdkState sdkState, String phoneNumber, boolean isAudio, Callback<AvSession> cb) {
        int result = SdkApi.invite(sdkState, phoneNumber, isAudio, cb);
        return result;
    }

    /**
     * 接受音视频会话邀请
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param id 音视频会话session id
     */
    public static void answer(Sdk.SdkState sdkState, int id, boolean asAudio) {
        SdkApi.answer(sdkState, id, asAudio);
    }

    /**
     * 程序由后台切到前台时重新恢复当前的音视频会话
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param id 音视频会话session id
     */
    public static void resume(Sdk.SdkState sdkState, int id) {

    }

    /**
     * 程序切到后台时用于维持当前的音视频会话
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param id 音视频会话session id
     */
    public static void hold(Sdk.SdkState sdkState, int id) {

    }

    /**
     * 给音视频邀请方振铃应答
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param id 音视频会话session id
     */
    public static void ringing(Sdk.SdkState sdkState, int id) {
        SdkApi.ring(sdkState, id);
    }

    /**
     * 结束音视频会话
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param id 音视频会话session id
     */
    public static void hangUp(Sdk.SdkState sdkState, int id) {
        SdkApi.hangUp(sdkState, id);
    }
}
