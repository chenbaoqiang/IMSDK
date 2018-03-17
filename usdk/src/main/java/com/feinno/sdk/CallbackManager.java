package com.feinno.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.feinno.sdk.enums.AvSessionStates;
import com.feinno.sdk.result.*;
import com.feinno.sdk.result.v3.*;
import com.feinno.sdk.session.AvSession;
import com.feinno.sdk.utils.LogUtil;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;

class CallbackManager {
    public static final String TAG = "CallbackManager";
    public static final String GET_SMS_CALLBACK = "getsms";
    public static final String PROV_CALLBACK = "provision";
    public static final String LOGIN_CALLBACK = "login";
    public static final String LOGOUT_CALLBACK = "logout";
    public static final String OPTION_CALLBACK = "cap";
    public static final String MSG_CALLBACK = "message";
    public static final String BUDDY_CALLBACK = "buddy";
    public static final String USER_INFO_CALLBACK = "user_info";
    public static final String USER_PROFILE_CALLBACK = "user_profile";
    public static final String USER_PORTRAIT_CALLBACK = "user_portrait";
    public static final String GP_OP_CALLBACK = "group";
    public static final String AV_CALLBACK = "av";
    public static final String ENDPOINT_CALLBACK = "endpoint";
    public static final String ENDPOINTLIST_CALLBACK = "endpointlist";
    public static final String PRESENCE_CALLBACK = "presence_callback";
    public static final String CONVLIST_CALLBACK = "convlist";
    public static final String CONVHISTORY_CALLBACK="convhistory";
    public static final String TOKEN_CALLBACK="token";
    public static final String GETPRESENCE_CALLBACK="getpresence";
    public static final String SEARCHGROUP_CALLBACK="searchgroup";
    public static final String SHAREINFO_CALLBACK= "shareinfo";
    public static final String GETSHARELIST_CALLBACK= "getsflist";
    public static final String GPDND_CALLBACK="gpdnd";
    public static final String MSGSET_CALLBACK = "msgset";
    public static final String BKLISTADD_CALLBACK = "bklistadd";
    public static final String BKLISTREMOVE_CALLBACK = "bklistremove";
    public static final String BKLISTGET_CALLBACK = "bklistget";
    public static final String DEVICEADD_CALLBACK = "deviceadd";
    public static final String DEVICEREMOVE_CALLBACK = "deviceremove";
    public static final String DEVICELISTGET_CALLBACK = "devicelistget";
    public static final String DEVICESTATUSTGET_CALLBACK = "devicestatusget";
    public static final String MSG2URL_CALLBACK = "msg2shorturl";
    public static final String USERSTATE_CALLBACK = "user_state";
    public static final String DNDFLAG_CALLBACK = "user_dndflag";
    public static final String BUDDYFLAG_CALLBACK = "user_buddyflag";
    public static final String GETFILEID_CALLBACK = "getfileid";
    public static final String NOTIFY_READ_CALLBACK="notifyread";
    public static final String PERMISSIONUIDFLAG_CALLBACK = "user_permissionuidflag";
    public static final String PERMISSIONUNAMEFLAG_CALLBACK = "user_permissionunameflag";

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private Sdk.SdkState sdkState;
    private Context context;

    public CallbackManager(Sdk.SdkState state, Context context) {
        this.sdkState = state;
        this.context = context;
    }

    public void registerCallbacks() throws SdkException {
        LogUtil.i(TAG, "register all callbacks");

        try {
            setAvCallback();
        } catch (LuaException e) {
            throw new SdkException(e);
        }

        try {
            setCallback(GET_SMS_CALLBACK,GET_SMS_CALLBACK+"_callback", GetSmsResult.class);
            setCallback(PROV_CALLBACK,PROV_CALLBACK+"_callback", ProvisionResult.class);
            setCallback(LOGOUT_CALLBACK, LOGOUT_CALLBACK+"_callback", LogoutResult.class);
            setCallback(LOGIN_CALLBACK, LOGIN_CALLBACK+"_callback", LoginResult.class);
            setCallback(OPTION_CALLBACK, OPTION_CALLBACK+"_callback", CapsResult.class);
            setCallback(MSG_CALLBACK, MSG_CALLBACK+"_callback", MessageResult.class);
            setCallback(BUDDY_CALLBACK, BUDDY_CALLBACK+"_callback", BuddyResult.class);
            setCallback(USER_PORTRAIT_CALLBACK, USER_PORTRAIT_CALLBACK+"_callback", UserPortraitResult.class);
            setCallback(USER_INFO_CALLBACK, USER_INFO_CALLBACK+"_callback", UserInfoResult.class);
            setCallback(USER_PROFILE_CALLBACK, USER_PROFILE_CALLBACK+"_callback", UserProfileResult.class);
            setCallback(AV_CALLBACK, AV_CALLBACK+"_callback", AVResult.class);
            setCallback(GP_OP_CALLBACK, GP_OP_CALLBACK+"_callback", GroupOpResult.class);
            setCallback(ENDPOINT_CALLBACK, ENDPOINT_CALLBACK+"_callback", ActionResult.class);
            setCallback(ENDPOINTLIST_CALLBACK, ENDPOINTLIST_CALLBACK+"_callback", EndPointResult.class);
            setCallback(PRESENCE_CALLBACK, PRESENCE_CALLBACK+"_callback", PresenceResult.class);
            setCallback(CONVLIST_CALLBACK, CONVLIST_CALLBACK+"_callback", ConvListResult.class);
            setCallback(CONVHISTORY_CALLBACK, CONVHISTORY_CALLBACK+"_callback", ConvHistoryResult.class);
            setCallback(TOKEN_CALLBACK, TOKEN_CALLBACK+"_callback", TokenResult.class);
            setCallback(GETPRESENCE_CALLBACK,GETPRESENCE_CALLBACK+"_callback",GetPresenceResult.class);
            setCallback(SEARCHGROUP_CALLBACK,SEARCHGROUP_CALLBACK+"_callback",GroupSearchResult.class);
            setCallback(SHAREINFO_CALLBACK,SHAREINFO_CALLBACK+"_callback",GroupShareInfoResult.class);
            setCallback(GETSHARELIST_CALLBACK,GETSHARELIST_CALLBACK+"_callback",ShareFileListResult.class);
            setCallback(GPDND_CALLBACK,GPDND_CALLBACK+"_callback",ActionResult.class);
            setCallback(MSGSET_CALLBACK, MSGSET_CALLBACK+"_callback", MsgSetResult.class);
            setCallback(BKLISTADD_CALLBACK, BKLISTADD_CALLBACK+"_callback", ActionResult.class);
            setCallback(BKLISTREMOVE_CALLBACK, BKLISTREMOVE_CALLBACK+"_callback", ActionResult.class);
            setCallback(BKLISTGET_CALLBACK, BKLISTGET_CALLBACK+"_callback", BklistGetResult.class);
            setCallback(DEVICEADD_CALLBACK, DEVICEADD_CALLBACK+"_callback", ActionResult.class);
            setCallback(DEVICEREMOVE_CALLBACK, DEVICEREMOVE_CALLBACK+"_callback", ActionResult.class);
            setCallback(DEVICELISTGET_CALLBACK, DEVICELISTGET_CALLBACK+"_callback", DeviceListGetResult.class);
            setCallback(DEVICESTATUSTGET_CALLBACK, DEVICESTATUSTGET_CALLBACK+"_callback", DeviceStatusGetResult.class);
            setCallback(MSG2URL_CALLBACK, MSGSET_CALLBACK+"_callback", Msg2ShorturlResult.class);
            setCallback(USERSTATE_CALLBACK, USERSTATE_CALLBACK+"_callback", UserStateResult.class);
            setCallback(DNDFLAG_CALLBACK, DNDFLAG_CALLBACK+"_callback", ActionResult.class);
            setCallback(BUDDYFLAG_CALLBACK, BUDDYFLAG_CALLBACK+"_callback", ActionResult.class);
            setCallback(GETFILEID_CALLBACK, GETFILEID_CALLBACK+ "_callback", GetFileIdResult.class);
            setCallback(NOTIFY_READ_CALLBACK, NOTIFY_READ_CALLBACK+ "_callback", ActionResult.class);
            setCallback(PERMISSIONUIDFLAG_CALLBACK, PERMISSIONUIDFLAG_CALLBACK+ "_callback", ActionResult.class);
            setCallback(PERMISSIONUNAMEFLAG_CALLBACK, PERMISSIONUNAMEFLAG_CALLBACK+ "_callback", ActionResult.class);
        } catch (LuaException e) {
            throw new SdkException(e);
        }
    }

    private void setAvCallback() throws LuaException {
        JavaFunction avCallback = new JavaFunction(sdkState.getLuaState()) {
            @Override
            public int execute() throws LuaException {
                LogUtil.i(TAG, "av callback execute");
                String args = params();
                if (TextUtils.isEmpty(args)) {
                    LogUtil.i(TAG, "av callback args is empty, return now");
                    return 0;
                } else {
                    LogUtil.i(TAG, "av callback args = " + args);
                }

                try {
                    final AvSession session = AvSession.fromJson(args);
                    if (session == null) {
                        LogUtil.i(TAG, "av callback av session is null, return now");
                        return 0;
                    }
                    Runnable r = new Runnable() {
                        public void run() {
                            Object object = sdkState.callbacks.get(session.id);
                            Callback<AvSession> cb;
                            if (object != null && object instanceof Callback) {
                                cb = (Callback<AvSession>) object;
                            } else {
                                LogUtil.i(TAG, "object is null or not instanceof callback, return now");
                                return;
                            }
                            cb.run(session);

                            AvSessionStates state = AvSessionStates.fromInt(session.state);
                            if (state == null) {
                                sdkState.callbacks.remove(session.id);
                                LogUtil.i(TAG, "av session state is null, return now");
                                return;
                            }
                            switch (state) {
                                case HUNG_UP:
                                case END:
                                case BUSY:
                                case REJECTED:
                                case ERROR:
                                case NOT_REACHABLE:
                                case FAILED:
                                case TIMEOUT:
                                    sdkState.callbacks.remove(session.id);
                                    LogUtil.i(TAG, "callbacks size = " + sdkState.callbacks.size());
                                    break;
                                default:
                                    break;
                            }
                        }
                    };
                    mHandler.post(r);
                } catch (Exception e) {
                    LogUtil.e(TAG, "av callback: cannot get session from lua object", e);
                }
                return 0;
            }
        };
        avCallback.register(Sdk.CALLBACK_MODULE_NAME + "." + AV_CALLBACK);
    }

    private <T extends ActionResult> void setCallback(String funcName, final String tag, final Class<T> classOfT) throws LuaException {
        JavaFunction callback = new JavaFunction(sdkState.getLuaState()) {
            @Override
            public int execute() throws LuaException {
                String args = params();
                if(TextUtils.isEmpty(args)) {
                    LogUtil.i(tag, "callback args is empty, return now");
                    return 0;
                } else{
                    LogUtil.i(tag, "callback args is not empty:"+ args);
                }

                try {
                    final T result = classOfT.cast(T.fromJson(args, classOfT));
                    LogUtil.i(tag, result.getClass().getSimpleName());
                    if(result == null) {
                        LogUtil.i(tag, "result is null, return now");
                        return 0;
                    }
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            Object object = sdkState.callbacks.remove(result.id);
                            LogUtil.i(tag, "callbacks size = " + sdkState.callbacks.size());
                            Callback<T> cb;
                            if (object != null && object instanceof Callback) {
                                cb = (Callback<T>) object;
                            } else {
                                LogUtil.i(tag, "object is null or not instanceof callback, return now");
                                return;
                            }
                            cb.run(result);
                        }
                    };
                    mHandler.post(r);
                }catch (Exception e) {
                    LogUtil.e(tag, "callback error:" , e);
                }

                return 0;
            }

        };
        callback.register(Sdk.CALLBACK_MODULE_NAME + "." + funcName);
    }

}
