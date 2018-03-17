//Generated. DO NOT modify.
package com.feinno.sdk;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.feinno.sdk.result.FetchFileResult;
import com.feinno.sdk.result.MessageResult;
import com.feinno.sdk.session.AvSession;
import com.feinno.sdk.session.CapsSession;
import com.feinno.sdk.utils.JsonUtils;
import com.feinno.sdk.utils.LogUtil;
//import com.google.gson.reflect.TypeToken;
import org.keplerproject.luajava.Helper;
import org.keplerproject.luajava.LuaException;

import java.util.List;

public class SdkApi {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static String TAG = "SdkApi";


    //Callback interfaces
    public interface IgetAllAvSessionCallback {
        void run(List<AvSession> r);
    }

    public interface IgetAvSessionCallback {
        void run(AvSession r);
    }

    public interface IgetOptionSessionCallback {
        void run(CapsSession r);
    }

    // sync apis
    public static void gettxopts(Sdk.SdkState sdkState) throws LuaException {
        String s = "sdk:_gettxopts()";
        //LogUtil.d(TAG, "create task: " + s);
        Helper.evalLua(sdkState.getLuaState(), s);
    }

    public static void step(Sdk.SdkState sdkState, double to) throws LuaException {
        String s = String.format("sdk:step(%s)", to);
        //LogUtil.d(TAG, "create task: " + s);
        Helper.evalLua(sdkState.getLuaState(), s);
    }

    public static void init(Sdk.SdkState sdkState, String imei, String imsi, String dvenor, String dmodel, String dos,
                            String dosver, String cvendor, String cversion, String storage, String syspath, String appid, String number ) throws LuaException {
        String args = String.format("[[%s]],[[%s]],[[%s]],[[%s]],[[%s]],[[%s]],[[%s]],[[%s]],[[%s]],[[%s]],[[%s]],[[%s]]",
                imei, imsi, dvenor, dmodel, dos, dosver, cvendor,cversion, storage, syspath, appid, number);

        String s = "sdk:init("+args+")";
        LogUtil.d(TAG, "Sdk init: " + s);
        Helper.evalLua(sdkState.getLuaState(), s);
    }

    public static void startInterruptor(Sdk.SdkState sdkState, int port, int listenfd) throws LuaException {
        String s = String.format("sdk:startinterruptor(%d,%d)", port, listenfd);
        //LogUtil.d(TAG, "create task: " + s);
        Helper.evalLua(sdkState.getLuaState(), s);
    }


    //Async api
    /******* sdk api ******/
    public static void setDmUrl(Sdk.SdkState sdkState, String ip, String port, String sslPort) throws LuaException {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.setdmurl");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "setdmurl", id, null, id, ip, port, sslPort);
        sdkState.addTask(st);
    }

    public static int updatedm(Sdk.SdkState sdkState, String number, String clientvendor, String clientversion, String terminalvendor, String terminalmode, String terminalswversion, String imsi, String imei) {
        return updatedm(sdkState, number, clientvendor, clientversion, terminalvendor, terminalmode, terminalswversion, imsi, imei, null);
    }

    public static int updatedm(Sdk.SdkState sdkState, String number, String clientvendor, String clientversion, String terminalvendor, String terminalmode, String terminalswversion, String imsi, String imei, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.updatedm");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "updatedm", id, callback, id, number, clientvendor, clientversion, terminalvendor, terminalmode, terminalswversion, imsi, imei);
        sdkState.addTask(st);
        return id;
    }

    public static int start(Sdk.SdkState sdkState, boolean nosip) {
        return start(sdkState, nosip, null);
    }

    public static int start(Sdk.SdkState sdkState, boolean nosip, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.start");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "start", id, callback, id, nosip);
        sdkState.addTask(st);
        return id;
    }

    public static int restart(Sdk.SdkState sdkState) {
        return restart(sdkState, null);
    }

    public static int restart(Sdk.SdkState sdkState, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.restart");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "restart", id, callback, id);
        sdkState.addTask(st);
        return id;
    }

    public static int startnrepl(Sdk.SdkState sdkState, int port) {
        return startnrepl(sdkState, port, null);
    }

    public static int startnrepl(Sdk.SdkState sdkState, int port, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.startnrepl");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "startnrepl", id, callback, id, port);
        sdkState.addTask(st);
        return id;
    }

    public static void doConnect(Sdk.SdkState sdkState) {
        doConnect(sdkState, null);
    }

    public static void doConnect(Sdk.SdkState sdkState, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.doconnect");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "doconnect", id, callback, id);
        sdkState.addTask(st);

    }

    public static void getUserStates(Sdk.SdkState sdkState, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.getUserStates");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "getuserstates", id, callback, id);
        sdkState.addTask(st);

    }

    public static int setProvisionUrl(Sdk.SdkState sdkState, String getSMSCodeUrl, String provisionUrl) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.setprovisionurl");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "setprovisionurl", id, null, id, getSMSCodeUrl, provisionUrl);
        sdkState.addTask(st);
        return id;
    }

    public static int setConf(Sdk.SdkState sdkState, String host, int port, String realm, String storage, String domain, String grouprescuri, String groupdomain, String urischeme) {
        return setConf(sdkState, host, port, realm, storage, domain, grouprescuri, groupdomain, urischeme, null);
    }

    public static int setConf(Sdk.SdkState sdkState, String host, int port, String realm, String storage, String domain, String grouprescuri, String groupdomain, String urischeme, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.setconf");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "setconf", id, callback, id, host, port, realm, storage, domain, grouprescuri, groupdomain, urischeme);
        sdkState.addTask(st);
        return id;
    }

    /******* login api ******/
    public static int getsmscode(Sdk.SdkState sdkState, String number) {
        return getsmscode(sdkState, number, null);
    }

    public static int getsmscode(Sdk.SdkState sdkState, String number, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.getsmscode");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "getsmscode", id, callback, id, number);
        sdkState.addTask(st);
        return id;
    }

    public static int getsmscode2(Sdk.SdkState sdkState, String number) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.getsmscode2");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "getsmscode2", id, null, id, number);
        sdkState.addTask(st);
        return id;
    }

    public static int provision(Sdk.SdkState sdkState, String number, String password) {
        return provision(sdkState, number, password, null);
    }

    public static int provision(Sdk.SdkState sdkState, String number, String password, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.provision");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "provision", id, callback, id, number, password);
        sdkState.addTask(st);
        return id;
    }

    public static int provision2(Sdk.SdkState sdkState, int sessid, String number, String password, String smscode, String imsi, String imei, String devicemodel, String devicetype, String deviceos, String subhost) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.provision2");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "provision2", id, null, sessid, number, password, smscode, imsi, imei, devicemodel, devicetype, deviceos, subhost);
        sdkState.addTask(st);
        return id;
    }

    public static int provisionOtp(Sdk.SdkState sdkState, String smsCode, String userName, String otp, String sessionId, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.provisionOtp");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "provisionotp", id, callback, id, smsCode, userName, otp, sessionId);
        sdkState.addTask(st);
        return id;
    }

    public static int provisioIot(Sdk.SdkState sdkState, String userName, String password, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.provisionIot");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "provisioniot", id, callback, id, userName, password);
        sdkState.addTask(st);
        return id;
    }

    public static int provisionpwd() {
        return -1;
    }

    public static int provisiondm() {
        return -1;
    }

//    public static int login(Sdk.SdkState sdkState,String username,String password) {
//      return login(sdkState,username,password,null);
//    }
//
//    public static int login(Sdk.SdkState sdkState,String username,String password,Callback callback) {
//      int id = sdkState.newStateId();
//      String s = String.format("sdk:a_i_login(%d,[=[%s]=],[=[%s]=])", id,username,password);
//      LogUtil.d(TAG, "create task: " + s);
//
//      SdkTask st = new SdkTask(sdkState.getLuaState(), id, s, callback);
//      sdkState.addTask(st);
//      return id;
//    }

    public static int login(Sdk.SdkState sdkState, String username, String password, String clientvendor, String clientversion, String terminalvendor, String terminalmode, String terminalswversion, String imsi, String imei, String token) {
        return login(sdkState, username, password, clientvendor, clientversion, terminalvendor, terminalmode, terminalswversion, imsi, imei, token, null);
    }

    public static int login(Sdk.SdkState sdkState, String username, String password, String clientvendor, String clientversion, String terminalvendor, String terminalmode, String terminalswversion, String imsi, String imei, String token, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.login");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "login", id, callback, id, username, password, clientvendor, clientversion, terminalvendor, terminalmode, terminalswversion, imsi, imei, token);
        sdkState.addTask(st);
        return id;
    }

    public static int logout(Sdk.SdkState sdkState) {
        return logout(sdkState, null);
    }

    public static int logout(Sdk.SdkState sdkState, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.logout");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "logout", id, callback, id);
        sdkState.addTask(st);
        return id;
    }

    public static int stop(Sdk.SdkState sdkState) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.stop");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "stop", id, null, id);
        sdkState.addTask(st);
        return id;
    }

    /******* capsexchange caps api ******/
    public static int capsexchange(Sdk.SdkState sdkState, String phoneNum) {
        return capsexchange(sdkState, phoneNum, null);
    }

    public static int capsexchange(Sdk.SdkState sdkState, String phoneNum, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.capsexchange");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "capsexchange", id, callback, id, phoneNum);
        sdkState.addTask(st);
        return id;
    }

    /******* av api ******/
    public static int invite(Sdk.SdkState sdkState, String phoneNum, boolean isAudio) {
        return invite(sdkState, phoneNum, isAudio, null);
    }

    public static int invite(Sdk.SdkState sdkState, String phoneNum, boolean isAudio, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.avcall");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "avcall", id, callback, id, phoneNum, isAudio);
        sdkState.addTask(st);
        return id;
    }

    public static int avMultiInvite(Sdk.SdkState sdkState, String numbers, boolean isAudio) {
        return avMultiInvite(sdkState, numbers, isAudio, null);
    }

    public static int avMultiInvite(Sdk.SdkState sdkState, String numbers, boolean isAudio, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.avmulticall");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "avmcall", id, callback, id, numbers, isAudio);
        sdkState.addTask(st);
        return id;
    }

    public static void avInviteUser(Sdk.SdkState sdkState, int id, String number) {
        avInviteUser(sdkState, id, number, null);
    }

    public static void avInviteUser(Sdk.SdkState sdkState, int id, String number, Callback callback) {
        LogUtil.d(TAG, "create task: sdk.avinviteuser");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "avinviteuser", id, callback, id, number);
        sdkState.addTask(st);
    }

    public static void ring(Sdk.SdkState sdkState, int sid) {
        ring(sdkState, sid, null);
    }

    public static void ring(Sdk.SdkState sdkState, int sid, Callback callback) {
        LogUtil.d(TAG, "create task: sdk.avring");
        int id = sdkState.newStateId();
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "avring", id, callback, id, sid);
        sdkState.addTask(st);
    }

    public static void hangUp(Sdk.SdkState sdkState, int sid) {
        hangUp(sdkState, sid, null);
    }

    public static void hangUp(Sdk.SdkState sdkState, int sid, Callback callback) {
        LogUtil.d(TAG, "create task: sdk.avhangup");
        int id = sdkState.newStateId();
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "avhangup", id, callback, id, sid);
        sdkState.addTask(st);
    }

    public static void answer(Sdk.SdkState sdkState, int sid, boolean asAudio) {
        answer(sdkState, sid, asAudio, null);
    }

    public static void answer(Sdk.SdkState sdkState, int sid, boolean asAudio, Callback callback) {
        LogUtil.d(TAG, "create task: sdk.avanswer");
        int id = sdkState.newStateId();
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "avanswer", id, callback, id, sid, asAudio);
        sdkState.addTask(st);
    }

    public static void hold(Sdk.SdkState sdkState, int sid) {
        hold(sdkState, sid, null);
    }

    public static void hold(Sdk.SdkState sdkState, int sid, Callback callback) {
        LogUtil.d(TAG, "create task: sdk.avhold");
        int id = sdkState.newStateId();
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "avhold", id, callback, id, sid);
        sdkState.addTask(st);

    }

    public static void resume(Sdk.SdkState sdkState, int sid) {
        resume(sdkState, sid, null);
    }

    public static void resume(Sdk.SdkState sdkState, int sid, Callback callback) {
        int id = sdkState.newStateId();
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "avresume", id, callback, id, sid);
        sdkState.addTask(st);
    }


    /******* message api ******/

    public static int msgSendReport(Sdk.SdkState sdkState, String number, String messageId, int reportType, int directType, String target, Callback<MessageResult> callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msgsendreport");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msgsendreport", id, callback, id, number, messageId, reportType, directType, target);

        sdkState.addTask(st);
        return id;
    }

    public static int msgSetStatus(Sdk.SdkState sdkState, String number, String messageId, int msgState, int chatType, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msgsetstatus");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msgsetstatus", id, cb, id, number, messageId, msgState, chatType);

        sdkState.addTask(st);
        return id;
    }

    public static int msgSetConvStatus(Sdk.SdkState sdkState, String convId, String messageId, int convState, int chatType, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msgsetconvstatus");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msgsetconvstatus", id, cb, id, convId, messageId, convState, chatType);

        sdkState.addTask(st);
        return id;
    }

    public static int msgSendText(Sdk.SdkState sdkState, String number, String messageId, String content, boolean needReport, boolean isBurn, int directedType, boolean needReadReport, String extension, int contentType, Callback<MessageResult> callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msgsendtext");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msgsendtext", id, callback, id, number, messageId, content, needReport, isBurn, directedType, needReadReport, extension, contentType);
        sdkState.addTask(st);
        return id;
    }

    public static int msgSendFile(Sdk.SdkState sdkState, String number, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail, int duration,
                                  boolean isBurn, int directedType, boolean needReadReport, String extension, Callback<MessageResult> callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msgsendfile");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msgsendfile", id, callback, id, number, messageId, filePath, contentType, fileName, needReport,
                start, thumbnail, duration ,isBurn, directedType, needReadReport, extension);
        sdkState.addTask(st);
        return id;
    }

    public static int msgFetchFile(Sdk.SdkState sdkState, String number, String messageId, int chatType, String filePath, int contentType, String fileName,
                                   String transferId, int start, int fileSize, String hash, boolean isBurn, Callback<FetchFileResult> callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msgfetchfile");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msgfetchfile", id, callback, id, number, messageId, chatType, filePath, contentType, fileName, transferId, start, fileSize, hash, isBurn);
        sdkState.addTask(st);
        return id;
    }

    public static int msgHttpFetch(Sdk.SdkState sdkState, String messageId, int contentType, String filePath, int start, int fileSize, String originalLink, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msghttpfetch");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msghttpfetch", id, callback, id, messageId, contentType, filePath, start, fileSize, originalLink);
        sdkState.addTask(st);
        return id;
    }
    
//    public static int pmSendDirected(Sdk.SdkState sdkState, String messageId, String content, boolean needReport, boolean isBurn, int directedType, Callback callback) {
//        int id = sdkState.newStateId();
//        LogUtil.d(TAG, "create task: sdk.sendtext");
//
//        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "sendtext", id, callback, id, null, messageId, content, needReport, isBurn, directedType);
//        sdkState.addTask(st);
//        return id;
//    }

    public static int msgSendVemoticon(Sdk.SdkState sdkState, String number, String messageId, String vemoticonId, String vemoticonName, boolean needReport, boolean isBurn, int directedType, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msgsendvemoticon");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msgsendvemoticon", id, callback, id, number, messageId, vemoticonId, vemoticonName, needReport, isBurn, directedType);
        sdkState.addTask(st);
        return id;
    }

    public static int msgSendCloudfile(Sdk.SdkState sdkState, String number, String messageId, String fileName, String fileSize, String fileUrl, boolean needReport, boolean isBurn, int directedType, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msgsendcloudfile");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msgsendcloudfile", id, callback, id, number, messageId, fileName, fileSize, fileUrl, needReport, isBurn, directedType);
        sdkState.addTask(st);
        return id;
    }

    public static int msgPubSendText(Sdk.SdkState sdkState, String uri, String messageId, String content, boolean needReport, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msgpubsendtext");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msgpubsendtext", id, callback, id, uri, messageId, content, needReport);
        sdkState.addTask(st);
        return id;
    }

    public static int msgPubSendFile(Sdk.SdkState sdkState, String uri, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msgpubsendfile");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msgpubsendfile", id, callback, id, uri, messageId, filePath, contentType, fileName, needReport, start, thumbnail);
        sdkState.addTask(st);
        return id;
    }


    public static int msgGpSendText(Sdk.SdkState sdkState, String groupUri, String messageId, String content, boolean needReport, String ccNumber,
                                    boolean needReadReport, String extension, int contentType, Callback<MessageResult> callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msggpsendtext");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msggpsendtext", id, callback, id, groupUri, messageId, content, needReport, ccNumber, needReadReport, extension, contentType);
        sdkState.addTask(st);
        return id;
    }

    public static int msgGpSendFile(Sdk.SdkState sdkState, String groupUri, String messageId, String filePath, int contentType, String fileName, boolean needReport,
                                    int start, String thumbnail, boolean needReadReport, String extension, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msggpsendfile");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msggpsendfile", id, callback, id, groupUri, messageId, filePath, contentType, fileName, needReport,
                start, thumbnail, needReadReport, extension);
        sdkState.addTask(st);
        return id;
    }

    public static int msgGpSendVemoticon(Sdk.SdkState sdkState, String groupUri, String messageId, String vemoticonId, String vemoticonName, boolean needReport, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msggpsendvemoticon");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msggpsendvemoticon", id, callback, id, groupUri, messageId, vemoticonId, vemoticonName, needReport);
        sdkState.addTask(st);
        return id;
    }

    public static int msgGpSendCloudfile(Sdk.SdkState sdkState, String groupUri, String messageId, String fileName, String fileSize, String fileUrl, boolean needReport, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msggpsendcloudfile");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msggpsendcloudfile", id, callback, id, groupUri, messageId, fileName, fileSize, fileUrl, needReport);
        sdkState.addTask(st);
        return id;
    }

    public static int uploadShareFile(Sdk.SdkState sdkState, String targetId, String messageId, String filePath, String fileName, int expire, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.uploadsharefile");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "uploadsharefile", id, callback, id, targetId, messageId, filePath, fileName, expire);
        sdkState.addTask(st);
        return id;
    }

    public static int fetchShareFile(Sdk.SdkState sdkState, String targetId, String messageId, String fileId, String shareFileId, int start, int size, String filePath, Callback callback)  {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.fetchsharefile");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "fetchsharefile", id, callback, id, targetId, messageId, fileId, shareFileId, start, size, filePath);
        sdkState.addTask(st);
        return id;
    }

    public static int  deleteShareFile(Sdk.SdkState sdkState, String targetId, String fileId, String shareFileId, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.deletesharefile");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "deletesharefile", id, callback, id, targetId, fileId, shareFileId);
        sdkState.addTask(st);
        return id;
    }

    public static int  getShareFileList(Sdk.SdkState sdkState, String targetId, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.getsharefilelist");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "getsharefilelist", id, callback, id, targetId);
        sdkState.addTask(st);
        return id;
    }

    public static int  cancelTransfer(Sdk.SdkState sdkState, String messageId, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.canceltransfer");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "canceltransfer", id, callback, id, messageId);
        sdkState.addTask(st);
        return id;
    }

    public static int  setNotifyRead(Sdk.SdkState sdkState, String messageId, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.setnotifyread");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "setnotifyread", id, callback, id, messageId);
        sdkState.addTask(st);
        return id;
    }

    /******* group api ******/
    public static int createGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, String introduce, String bulletin) {
        return createGroup(sdkState, groupUri, resourcelists, subject, introduce, bulletin, 0, null);
    }

    public static int createGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, String introduce, String bulletin, int groupType, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.creategroup");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpcreate", id, callback, id, resourcelists, subject, introduce, bulletin, groupType);
        sdkState.addTask(st);
        return id;
    }

    public static int joinGroup(Sdk.SdkState sdkState, String groupUri, String inviter, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpjoin");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpjoin", id, callback, id, groupUri, inviter);
        sdkState.addTask(st);
        return id;
    }

    public static int rejectGroup(Sdk.SdkState sdkState, String groupUri, String inviter, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpreject");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpreject", id, callback, id, groupUri, inviter);
        sdkState.addTask(st);
        return id;
    }

    public static int deleteGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject) {
        return exitGroup(sdkState, groupUri, resourcelists, subject, null);
    }

    public static int deleteGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpdelete");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpdelete", id, callback, id, groupUri);
        sdkState.addTask(st);
        return id;
    }

    public static int exitGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject) {
        return exitGroup(sdkState, groupUri, resourcelists, subject, null);
    }

    public static int exitGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.exitgroup");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpquit", id, callback, id, groupUri);
        sdkState.addTask(st);
        return id;
    }

    public static int gpModifyNickName(Sdk.SdkState sdkState, String groupUri, String resourcelists, String nickName) {
        return gpModifyNickName(sdkState, groupUri, resourcelists, nickName, null);
    }

    public static int gpModifyNickName(Sdk.SdkState sdkState, String groupUri, String resourcelists, String nickName, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpmodifynickname");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpmodifynickname", id, callback, id, groupUri, nickName);
        sdkState.addTask(st);
        return id;
    }

    public static int gpChangeManager(Sdk.SdkState sdkState, String groupUri, String resourcelists, String target) {
        return gpChangeManager(sdkState, groupUri, resourcelists, target, null);
    }

    public static int gpChangeManager(Sdk.SdkState sdkState, String groupUri, String resourcelists, String target, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpchangemanager");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpchangemanager", id, callback, id, groupUri, target);
        sdkState.addTask(st);
        return id;
    }

    public static int gpRemoveUser(Sdk.SdkState sdkState, String groupUri, String resourcelists, String target) {
        return gpRemoveUser(sdkState, groupUri, resourcelists, target, null);
    }

    public static int gpRemoveUser(Sdk.SdkState sdkState, String groupUri, String resourcelists, String target, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpremovemember");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpremovemember", id, callback, id, groupUri, target);
        sdkState.addTask(st);
        return id;
    }

    public static int gpModifySubject(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject) {
        return gpModifySubject(sdkState, groupUri, resourcelists, subject, null);
    }

    public static int gpModifySubject(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpmodifysubject");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpmodifysubject", id, callback, id, groupUri, subject);
        sdkState.addTask(st);
        return id;
    }

    public static int gpSetInviteFlag(Sdk.SdkState sdkState, String groupUri, String resourcelists, int flag, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpmodifyinviteflag");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpmodifyinviteflag", id, callback, id, groupUri, flag);
        sdkState.addTask(st);
        return id;
    }

    public static int gpSetExtra(Sdk.SdkState sdkState, String groupUri, String resourcelists, String extra, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpmodifyextra");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpmodifyextra", id, callback, id, groupUri, extra);
        sdkState.addTask(st);
        return id;
    }

    public static int gpModifyIntroduce(Sdk.SdkState sdkState, String groupUri, String resourcelists, String introduce) {
        return gpModifyIntroduce(sdkState, groupUri, resourcelists, introduce, null);
    }

    public static int gpModifyIntroduce(Sdk.SdkState sdkState, String groupUri, String resourcelists, String introduce, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpmodifyintroduce");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpmodifyintroduce", id, callback, id, groupUri, introduce);
        sdkState.addTask(st);
        return id;
    }

    public static int gpModifyBulletin(Sdk.SdkState sdkState, String groupUri, String resourcelists, String bulletin) {
        return gpModifyBulletin(sdkState, groupUri, resourcelists, bulletin, null);
    }

    public static int gpModifyBulletin(Sdk.SdkState sdkState, String groupUri, String resourcelists, String bulletin, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpmodifybulletin");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpmodifybulletin", id, callback, id, groupUri, bulletin);
        sdkState.addTask(st);
        return id;
    }

    public static int inviteGroupMember(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, String member) {
        return inviteGroupMember(sdkState, groupUri, resourcelists, subject, member, null);
    }

    public static int inviteGroupMember(Sdk.SdkState sdkState, String groupUri, String resourcelists, String subject, String member, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpinvitemember");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpinvitemember", id, callback, id, groupUri, member);
        sdkState.addTask(st);
        return id;
    }

    public static int applyGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String remark, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpapply");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpapply", id, callback, id, groupUri, remark);
        sdkState.addTask(st);
        return id;
    }

    public static int approvalGroup(Sdk.SdkState sdkState, String groupUri, String resourcelists, String applicant, int handleResult, String replyMsg, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpapproval");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpapproval", id, callback, id, groupUri, applicant, handleResult, replyMsg);
        sdkState.addTask(st);
        return id;
    }

    public static int subGroupList(Sdk.SdkState sdkState, String version, boolean increase, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.subgrouplist");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "subgrouplist", id, callback, id, version, increase);
        sdkState.addTask(st);
        return id;
    }

    public static int subGroupList(Sdk.SdkState sdkState, String version, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpsublist");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpsublist", id, callback, id, version);
        sdkState.addTask(st);
        return id;
    }

    public static int subGroupInfo(Sdk.SdkState sdkState, String groupUri, String infoVersion, String membersVersion, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpsubinfo");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpsubinfo", id, callback, id, groupUri, infoVersion, membersVersion);
        sdkState.addTask(st);
        return id;
    }

    public static int searchGroup(Sdk.SdkState sdkState, String subject, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpsearch");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpsearch", id, callback, id, subject);
        sdkState.addTask(st);
        return id;
    }

    public static int gpShareInfo(Sdk.SdkState sdkState, String groupUri, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpshareinfo");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpshareinfo", id, callback, id, groupUri);
        sdkState.addTask(st);
        return id;
    }

    public static int gpSetDND(Sdk.SdkState sdkState, String groupUri, int flag, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.gpsetdnd");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "gpsetdnd", id, callback, id, groupUri, flag);
        sdkState.addTask(st);
        return id;
    }

    /******* endpoint api ******/
    public static int getEPStatus(Sdk.SdkState sdkState, String number, String token, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.getepstatus");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "getepstatus", id, callback, id, number, token);
        sdkState.addTask(st);
        return id;
    }

    public static int kickEndPoint(Sdk.SdkState sdkState, String number, String token, String clientType, String clientVersion, String epid, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.kickendpoint");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "kickendpoint", id, callback, id, number, token, clientType, clientVersion, epid);
        sdkState.addTask(st);
        return id;
    }

    public static int genPCToken(Sdk.SdkState sdkState, String number, String sessionid, String token, Callback callback) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.genpctoken");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "genpctoken", id, callback, id, number, sessionid, token);
        sdkState.addTask(st);
        return id;
    }

    //buddy
    public static int buddyAdd(Sdk.SdkState sdkState, String user, String reason, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.buddyAdd");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "buddyadd", id, cb, id, user, reason);
        sdkState.addTask(st);
        return id;
    }

    public static int buddyDel(Sdk.SdkState sdkState, int userId, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.buddyDel");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "buddydel", id, cb, id, userId);
        sdkState.addTask(st);
        return id;
    }

    public static int buddyMemo(Sdk.SdkState sdkState, int userId, String memo, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.buddyMemo");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "buddymemo", id, cb, id, userId, memo);
        sdkState.addTask(st);
        return id;
    }

    public static int buddyDnd(Sdk.SdkState sdkState, int userId, int dndFlag, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.buddyDnd");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "buddydnd", id, cb, id, userId, dndFlag);
        sdkState.addTask(st);
        return id;
    }

    public static int buddyHandle(Sdk.SdkState sdkState, int userId, boolean accept, String reason, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.buddyHandle");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "buddyhandle", id, cb, id, userId, accept, reason);
        sdkState.addTask(st);
        return id;
    }

    //Users
    public static int userGetInfo(Sdk.SdkState sdkState, String ids, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.userGetInfo");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "usergetinfo", id, cb, id, ids);
        sdkState.addTask(st);
        return id;
    }

    public static int userGetProfile(Sdk.SdkState sdkState, String user, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.userGetProfile");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "usergetprofile", id, cb, id, user);
        sdkState.addTask(st);
        return id;
    }

    public static int userSetProfile(Sdk.SdkState sdkState, String nickName, String impresa, String firstName,
                                      String lastName, int gender, String email, String birthday, String clientExtra, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.userSetProfile");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "usersetprofile", id, cb, id, nickName, impresa,
                firstName, lastName, gender, email, birthday, clientExtra);
        sdkState.addTask(st);
        return id;
    }

    public static int userSetCareer(Sdk.SdkState sdkState, String company, String position, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.usersetcareer");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "usersetcareer", id, cb, id, company, position);
        sdkState.addTask(st);
        return id;
    }

    public static int userSetDndFlag(Sdk.SdkState sdkState, int dndFlag, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.userSetDndFlag");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "usersetdnd", id, cb, id, dndFlag);
        sdkState.addTask(st);
        return id;
    }

    public static int userSetBuddyFlag(Sdk.SdkState sdkState, int buddyFlag, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.usersetbdflag");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "usersetbdflag", id, cb, id, buddyFlag);
        sdkState.addTask(st);
        return id;
    }

    public static int userSetPermissionUidFlag(Sdk.SdkState sdkState, int uidFlag, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.usersetpermissionuidflag");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "usersetpermissionuidflag", id, cb, id, uidFlag);
        sdkState.addTask(st);
        return id;
    }

    public static int userSetPermissionUnameFlag(Sdk.SdkState sdkState, int unameFlag, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.usersetpermissionunameflag");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "usersetpermissionunameflag", id, cb, id, unameFlag);
        sdkState.addTask(st);
        return id;
    }

    public static int userGetPortrait(Sdk.SdkState sdkState, int userId, boolean isSmall, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.userGetPortrait");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "usergetportrait", id, cb, id, userId, isSmall);
        sdkState.addTask(st);
        return id;
    }

    public static int userSetPortrait(Sdk.SdkState sdkState, String filePath, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.userSetPortrait");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "usersetportrait", id, cb, id, filePath);
        sdkState.addTask(st);
        return id;
    }

    //block sync call api
    public static void onbell(Sdk.SdkState sdkState, String name) throws Exception {
        onbell(sdkState, name, 1000);
    }

    public static void onbell(Sdk.SdkState sdkState, String name, long timeout) throws Exception {
        LogUtil.d(TAG, "create task: sdk.onbell");

        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "onbell", 0, null, name);
        sdkState.runTask(st, timeout);
    }

    //callback api:

//    public static void getAllAvSession(Sdk.SdkState sdkState, final IgetAllAvSessionCallback cb) {
//        String s = "sdk:ca_AvSession_getAllAvSession()";
//        SdkTask st = new SdkTask(sdkState.getLuaState(), -1, s);
//        LogUtil.d(TAG, "create task: " + s);
//
//        st.cb = new SdkTask.ISdkCallback() {
//            @Override
//            public void run(final String json) {
//                final List<AvSession> ret;
//                ret = JsonUtils.fromJson(json, new TypeToken<List<AvSession>>() {
//                }.getType());
//                try {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            cb.run(ret);
//                        }
//                    });
//                } catch (Exception e1) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            cb.run(null);
//                        }
//                    });
//                    Log.e(TAG, e1.toString());
//                }
//            }
//        };
//
//        sdkState.addTask(st);
//    }

    public static void getAvSession(Sdk.SdkState sdkState, int id, final IgetAvSessionCallback cb) {
        String s = String.format("sdk:c_AvSession_getAvSession(%d)", id);
        SdkTask st = new SdkTask(sdkState.getLuaState(), id, s);
        LogUtil.d(TAG, "create task: " + s);

        st.cb = new SdkTask.ISdkCallback() {
            @Override
            public void run(final String json) {
                final AvSession ret;
                ret = JsonUtils.fromJson(json, AvSession.class);
                try {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cb.run(ret);
                        }
                    });
                } catch (Exception e1) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cb.run(null);
                        }
                    });
                    Log.e(TAG, e1.toString());
                }
            }
        };

        sdkState.addTask(st);
    }

    public static void getOptionSession(Sdk.SdkState sdkState, int id, final IgetOptionSessionCallback cb) {
        String s = String.format("sdk:c_OptionSession_getOptionSession(%d)", id);
        SdkTask st = new SdkTask(sdkState.getLuaState(), id, s);
        LogUtil.d(TAG, "create task: " + s);

        st.cb = new SdkTask.ISdkCallback() {
            @Override
            public void run(final String json) {
                final CapsSession ret;
                ret = JsonUtils.fromJson(json, CapsSession.class);
                try {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cb.run(ret);
                        }
                    });
                } catch (Exception e1) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cb.run(null);
                        }
                    });
                    Log.e(TAG, e1.toString());
                }
            }
        };

        sdkState.addTask(st);
    }

    //endpoint
    public static int getEndpointList(Sdk.SdkState sdkState, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.getEndpointList");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "getendpointlist", id, cb, id);
        sdkState.addTask(st);
        return id;
    }
    public static int bootEndpoint(Sdk.SdkState sdkState, String clientId, int clientType, String clientVersion, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.bootendpoint");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "bootendpoint", id, cb, id, clientId, clientType, clientVersion);
        sdkState.addTask(st);
        return id;
    }

    public static int deleteEndpoint(Sdk.SdkState sdkState, String clientId, int clientType, String clientVersion, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.deleteendpoint");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "deleteendpoint", id, cb, id, clientId, clientType, clientVersion);
        sdkState.addTask(st);
        return id;
    }

    public static int setEndpointDND(Sdk.SdkState sdkState, int flag, String beginTime, String endTime, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.setendpointdnd");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "setendpointdnd", id, cb, id, flag, beginTime, endTime);
        sdkState.addTask(st);
        return id;
    }

    public static int getConvList(Sdk.SdkState sdkState, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.getconvlist");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "getconvlist", id, cb, id);
        sdkState.addTask(st);
        return id;
    }

    public static int subPresence(Sdk.SdkState sdkState, String cids, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.subPresence");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "subpresence", id, cb, id, cids);
        sdkState.addTask(st);
        return id;
    }

    public static int getConvHistory(Sdk.SdkState sdkState, int convType, String convId, int pageLimit, String beginImdnId, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.getconvhistory");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "getconvhistory", id, cb, id, convType, convId, pageLimit, beginImdnId);
        sdkState.addTask(st);
        return id;
    }

    public static int unSubPresence(Sdk.SdkState sdkState, String cids, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "ucreate task: sdk.unSubPresence");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "unsubpresence", id, cb, id, cids);
        sdkState.addTask(st);
        return id;
    }

    public static int token(Sdk.SdkState sdkState, Callback cb) {
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.token");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "token", id, cb, id);
        sdkState.addTask(st);
        return id;
    }

    public static int getPresence(Sdk.SdkState sdkState, String contactId, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.getPresence");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "getpresence", id, cb, id, contactId);
        sdkState.addTask(st);
        return id;
    }

    public static int bklistAdd(Sdk.SdkState sdkState, String userId, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.bklistAdd");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "bklistadd", id, cb, id, userId);
        sdkState.addTask(st);
        return id;
    }

    public static int bklistRemove(Sdk.SdkState sdkState, String userId, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.bklistRemove");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "bklistremove", id, cb, id, userId);
        sdkState.addTask(st);
        return id;
    }

    public static int bklistGet(Sdk.SdkState sdkState, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.bklistGet");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "bklistget", id, cb, id);
        sdkState.addTask(st);
        return id;
    }

    public static int deviceAdd(Sdk.SdkState sdkState, String userId, String password, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.deviceAdd");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "deviceadd", id, cb, id, userId, password);
        sdkState.addTask(st);
        return id;
    }

    public static int deviceRemove(Sdk.SdkState sdkState, String userId, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.deviceRemove");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "deviceremove", id, cb, id, userId);
        sdkState.addTask(st);
        return id;
    }

    public static int devicelistGet(Sdk.SdkState sdkState, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.devicelistGet");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "devicelistget", id, cb, id);
        sdkState.addTask(st);
        return id;
    }

    public static int deviceStatusGet(Sdk.SdkState sdkState, String userId,  Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.deviceStatusGet");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "devicestatusget", id, cb, id, userId);
        sdkState.addTask(st);
        return id;
    }

    public static int msg2Shorturl(Sdk.SdkState sdkState,String imdnId, int msgType, String content, String filePath, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.msg2Shorturl");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "msg2shorturl", id, cb, id, imdnId, msgType, content, filePath);
        sdkState.addTask(st);
        return id;
    }

    public static int getFileId(Sdk.SdkState sdkState,String fileName, Callback cb){
        int id = sdkState.newStateId();
        LogUtil.d(TAG, "create task: sdk.getfileid");
        SdkTask st = new SdkTask(sdkState.getLuaState(), "sdk", "getfileid", id, cb, id, fileName);
        sdkState.addTask(st);
        return id;
    }
}

