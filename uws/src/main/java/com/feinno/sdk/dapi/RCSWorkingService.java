package com.feinno.sdk.dapi;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.feinno.sdk.*;
import com.feinno.sdk.dapi.callback.AvCallback;
import com.feinno.sdk.result.*;
import com.feinno.sdk.result.v3.*;
import com.feinno.sdk.session.IGetAvSessionCallback;
import com.feinno.sdk.utils.LogUtil;
import com.feinno.sdk.utils.NgccTextUtils;
import org.keplerproject.luajava.LuaException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.feinno.sdk.Sdk.newSdkState;

public class RCSWorkingService extends Service {
	private static final String TAG = "RCSWorkingService";

    private static final int NOTIFICATION_ID = 10086;

	private static Map<String, Sdk.SdkState> sdkStateMap = new HashMap<String, Sdk.SdkState>();
	private RCSWorkingServiceBinder binder;

	private String dmUrl;

	@Override
	public void onCreate() {
		LogUtil.i(TAG, "onCreate");
		super.onCreate();
		startForegroundEmpty();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtil.i(TAG, "onStartCommand");
		super.onStartCommand(intent, flags, startId);

		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		LogUtil.i(TAG, "onBind");
		if (binder == null) {
			String pn = intent.getStringExtra("PackageName");
			binder = new RCSWorkingServiceBinder(pn);
		}

		return binder;
	}

	@Override
	public void onRebind(Intent intent) {
		LogUtil.i(TAG, "onRebind");

	}

	@Override
	public boolean onUnbind(Intent intent) {
		LogUtil.i(TAG, "onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		LogUtil.i(TAG, "onDestroy");
		super.onDestroy();
		if (sdkStateMap != null) {
			Set<HashMap.Entry<String, Sdk.SdkState>> set = sdkStateMap.entrySet();
			for (HashMap.Entry<String, Sdk.SdkState> entry : set) {
				entry.getValue().stop();
			}
			sdkStateMap.clear();
		}
	}

	public static class NetworkReceiver extends BroadcastReceiver {

		public NetworkReceiver() {}

		private static final String TAG = "NetworkReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			LogUtil.d(TAG, "onReceive");
			try {
				ConnectivityManager cm =
						(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

				NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
				boolean isConnected = activeNetwork != null &&
						activeNetwork.isConnectedOrConnecting();

				if (isConnected) {
					LogUtil.d(TAG, "networking is connected now, call doConnect()");
					Set<HashMap.Entry<String, Sdk.SdkState>> set = sdkStateMap.entrySet();
					for (HashMap.Entry<String, Sdk.SdkState> entry : set) {
						Sdk.SdkState sdkState = entry.getValue();
						if (sdkState != null) {
							SdkApi.doConnect(sdkState);
						}
					}
				}
			}
			catch (Exception ex) {
				LogUtil.e(TAG, ex);
			}
		}
	}

	public static Notification createNotification(Context ctx) {
		Notification.Builder builder = new Notification.Builder(ctx);
		builder.setSmallIcon(android.R.drawable.arrow_up_float);
		builder.setContentTitle("UWS");
		builder.setContentText("UWS");
		return builder.build();
	}

	private void startForegroundEmpty(){
		// 后台服务被系统回收优先级紧次于Activity，且默认最多只允许16个
		// 在部分设备上remote可能被杀后无法启动
		// 使用startForeground 使得进程处于 Perceptible 状态，优先级仅次于 Visible

		//  Api > 17: 空的Notification 会显示为 "xxx"正在运行
		// 通过反编译微信电话本得到适配方案：
		// 使用 另外一个Service startForeground 相同 NotifyId 再stopForeground
		// 通知会消失，但服务的Perceptible还保留

		Notification notify = createNotification(getApplicationContext());
		//setLatestEventInfo 不知道干啥用的，不用貌似也可以，保险还是留着吧
		notify.setLatestEventInfo(this, null, null,
				PendingIntent.getActivity(this, 0, new Intent(), 0));
		startForeground(NOTIFICATION_ID, notify);

		if(Build.VERSION.SDK_INT > 17) {
			Context c = getApplication().getApplicationContext();
			c.startService(new Intent(c, RemoveNotificationService.class).putExtra(RemoveNotificationService.KEY_NOTIFICATION_ID, NOTIFICATION_ID));
		}
	}

	private final class RCSWorkingServiceBinder extends IRCSWorkingService.Stub {

		String clientPackageName;
		public RCSWorkingServiceBinder(String clientPackageName) {
			this.clientPackageName = clientPackageName;
		}
		protected void notifyUserStateChange(String number, int state) {
			Intent intent = new Intent(StateManager.ACTION_USER_STATE);
			Bundle bundle = new Bundle();
			bundle.putString(StateManager.EXTRA_USER_NUMBER, number);
			bundle.putInt(StateManager.EXTRA_USER_STATE, state);
			intent.putExtras(bundle);
			getBaseContext().sendBroadcast(intent);
		}

		@Override
		//0, failed; 1 success; 2 already added
		public int addUser(String number, String imsi, String storage, String clientVendor, String clientVersion, String sysPath, String appId) throws RemoteException {
            LogUtil.i(TAG, "startUser, number = " + number + ", storage = " + storage + ", imsi = " + imsi);
            if (sdkStateMap == null) {
                return 0;
            }

            String numberKey = NgccTextUtils.getNationalNumber(number);
            if (TextUtils.isEmpty(numberKey)) {
                LogUtil.d(TAG, "not number.");
				// number canbe username
				numberKey = number;
            }

            try {
                Sdk.SdkState sdkState = sdkStateMap.get(numberKey);
                if (sdkState != null) {
                    LogUtil.e(TAG, "sdk state is not null, user " + number + " already exist");
                    notifyUserStateChange(number, StateManager.USER_STATE_STARTED);
                    return 2;
                }
                ListenerProvider lp = new ListenerProvider(getBaseContext());
                SdkConf conf = new SdkConf(getApplicationContext(), number, imsi, storage, clientVendor, clientVersion, sysPath, appId);
                sdkState = newSdkState(getBaseContext(), conf, lp);
                // 多用户情况下需确保端口可用
                sdkState.startNrepl(8000);

                sdkStateMap.put(numberKey, sdkState);
            } catch (Exception e) {
                LogUtil.e(TAG, "add user failed.", e);
                //throw new RemoteException("add user failed");
                return 0;
            }

            notifyUserStateChange(number, StateManager.USER_STATE_STARTED);
			System.runFinalization();
			System.gc();
            return 1;
        }

		@Override
		public boolean removeUser(String number) throws RemoteException {
			LogUtil.i(TAG, "removeUser, number = " + number);
			if (sdkStateMap == null) {
				return false;
			}

			String numberKey = NgccTextUtils.getNationalNumber(number);
			if (TextUtils.isEmpty(numberKey)) {
				numberKey = number;
			}
			Sdk.SdkState sdkState = sdkStateMap.remove(numberKey);
			if (sdkState == null) {
				//throw new RemoteException("No such user: " + number + " exist");
				return false;
			}

			notifyUserStateChange(number, StateManager.USER_STATE_NOT_STOPPED);
			return true;
		}

		@Override
		public String[] getAllUsers() throws RemoteException {
			LogUtil.i(TAG, "getAllUsers");
			if (sdkStateMap == null) {
				return null;
			}
			Set<String> keys = sdkStateMap.keySet();
			String[] users = new String[keys.size()];
			keys.toArray(users);
			return users;
		}

		@Override
		public void setDmUrl(String owner, String ip, String port, String sslPort) throws RemoteException {
			LogUtil.i(TAG, "setDmUrl, url = " + ip + ",port:" + port);
			try {
				SdkApi.setDmUrl(getSdkState(owner), ip, port, sslPort);
			} catch (LuaException e) {
				LogUtil.e(TAG, e);
			}
		}

		@Override
		public void notifyConnection(int state) throws RemoteException {
			LogUtil.i(TAG, "notifyConnection, state = " + state);
			Intent intent = new Intent(StateManager.ACTION_SERVICE_STATE);
			Bundle bundle = new Bundle();
			bundle.putInt(StateManager.EXTRA_SERVICE_STATE, state);
			intent.putExtras(bundle);
			getBaseContext().sendBroadcast(intent);
		}

		@Override
		public void setClientInfo(String clientVendor, String clientVersion, String terminalVendor, String terminalMode, String terminalSWVersion) throws RemoteException {
			LogUtil.i(TAG, "setClientInfo");
		}

		public void setProvisionUrl(String owner, String getSMSCodeUrl, String provisionUrl) throws RemoteException {
			LogUtil.i(TAG, "setProvisionUrl");
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.setProvisionUrl(sdkState, getSMSCodeUrl, provisionUrl);
		}

		@Override
        @Deprecated // 过期
		public void getSMSCode(String owner, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "getSMSCode");
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<GetSmsResult> callback = new ActionCallback<GetSmsResult>(getApplicationContext(),
                    intent, com.feinno.sdk.BroadcastActions.ACTION_GETSMSCODE_RESULT, "GetSmsCallback");
			SdkApi.getsmscode(sdkState, owner, callback);
		}
        
        @Override
		public void getSMSCode1(String owner, String number, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "getSMSCode1 number=" + number);
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<GetSmsResult> callback = new ActionCallback<GetSmsResult>(getApplicationContext(),
                    intent, com.feinno.sdk.BroadcastActions.ACTION_GETSMSCODE_RESULT, "GetSmsCallback");
			SdkApi.getsmscode(sdkState, number, callback);
		}
		@Override
        @Deprecated // 过期
		public void getSMSCode2(String owner, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "getSMSCode");
			Sdk.SdkState sdkState = getSdkState(owner);
			String nationalNumber = NgccTextUtils.getNationalNumber(owner);
            ActionCallback<GetSmsResult> callback = new ActionCallback<GetSmsResult>(getApplicationContext(),
                    intent, com.feinno.sdk.BroadcastActions.ACTION_GETSMSCODE_RESULT, "GetSmsCallback");
			SdkApi.getsmscode2(sdkState, nationalNumber);
		}

		@Override
        @Deprecated // 过期
		public void provision(String owner, String password, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "provision, user = " + owner);
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<ProvisionResult> callback = new ActionCallback<ProvisionResult>(getApplicationContext(),
                    intent, com.feinno.sdk.BroadcastActions.ACTION_PROVISION_RESULT, "ProvisionCallback");
			SdkApi.provision(sdkState, owner, password, callback);
		}

		@Override
		public void provision1(String owner, String number, String password, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "provision1, user = " + owner + " number " + number);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ProvisionResult> callback = new ActionCallback<ProvisionResult>(getApplicationContext(),
					intent, com.feinno.sdk.BroadcastActions.ACTION_PROVISION_RESULT, "ProvisionCallback");
			SdkApi.provision(sdkState, number, password, callback);
		}

		@Override
        @Deprecated // 过期
		public void provision2(String owner, int sessionId, String password, String smsCode, String imsi, String imei, String deviceModel, String deviceType, String deviceOS, String subHost) throws RemoteException {
			LogUtil.i(TAG, "provision2, sms code = " + smsCode);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.provision2(sdkState, sessionId, owner, password, smsCode, imsi, imei, deviceModel, deviceType, deviceOS, subHost);
		}

        @Override
        public void provisionOtp(String owner, String sessId, String number, String smsCode, String otp, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "provisionOtp, user = " + owner);
            Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<ProvisionResult> callback = new ActionCallback<ProvisionResult>(getApplicationContext(),
                    intent, com.feinno.sdk.BroadcastActions.ACTION_PROVISION_RESULT, "ProvisionCallback");
            SdkApi.provisionOtp(sdkState, smsCode, number, otp, sessId, callback);
        }

		@Override
		public void provisionIot(String owner, String password, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "provisionIot, user = " + owner);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ProvisionResult> callback = new ActionCallback<ProvisionResult>(getApplicationContext(),
					intent, com.feinno.sdk.BroadcastActions.ACTION_PROVISION_RESULT, "ProvisionCallback");
			SdkApi.provisioIot(sdkState, owner, password, callback);
		}


		@Override
		public void login(String owner, String password, String dmToken, PendingIntent pendingIntent) throws RemoteException {
            LogUtil.i(TAG, "login");
            Sdk.SdkState sdkState = getSdkState(owner);

			String imei = sdkState.getConf().getImei();
			String imsi = sdkState.getConf().getImsi();
			LogUtil.i(TAG, "login, number = " + owner + ", imsi = " + imsi + ", imei = " + imei);
            ActionCallback<LoginResult> callback = new ActionCallback<LoginResult>(getApplicationContext(),
                    pendingIntent, com.feinno.sdk.BroadcastActions.ACTION_LOGIN_RESULT, "LoginCallback");
			SdkApi.login(sdkState, owner, password, "", "", "", "", "", imsi, imei, dmToken, callback);

//            if (sdkState.getLoginStatus() == LoginStates.LOGIN_SUCCESS.value()) {
//                //send current login status
//                LoginCallback cb = new LoginCallback(RCSWorkingService.this, pendingIntent);
//                LoginSession session = new LoginSession();
//                session.state = sdkState.getLoginStatus();
//                cb.run(session);
//            } else {
//                String imei = sdkState.getConf().getImei();
//                String imsi = sdkState.getConf().getImsi();
//                LogUtil.i(TAG, "login, nationalNumber = " + nationalNumber + ", imsi = " + imsi + ", imei = " + imei);
//                SdkApi.login(sdkState, nationalNumber, password, "", "", "", "", "", imsi, imei, dmToken, new LoginCallback(getBaseContext(), pendingIntent));
//            }
        }

		@Override
		public void logout(String owner, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "logout");
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<LogoutResult> callback = new ActionCallback<LogoutResult>(getApplicationContext(),
                    pendingIntent, com.feinno.sdk.BroadcastActions.ACTION_LOGOUT_RESULT, "LogoutCallback");
			SdkApi.logout(sdkState, callback);
		}

		@Override
		public void keepAlive(String owner) throws RemoteException {
			LogUtil.i(TAG, "keepAlive");

		}

		@Override
		public void avInvite(String owner, String number, boolean isAudio, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "avInvite, number = " + number);
			Sdk.SdkState sdkState = getSdkState(owner);
			String internationalNumber = NgccTextUtils.getInternationalNumber(number);
			SdkApi.invite(sdkState, internationalNumber, isAudio, new AvCallback(getBaseContext(), pendingIntent));
		}

		@Override
		public void avMultiInvite(String owner, String numbers, boolean isAudio, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "avMultiInvite");
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.avMultiInvite(sdkState, numbers, isAudio, new AvCallback(getBaseContext(), pendingIntent));
		}

		@Override
		public void avInviteUser(String owner, int id, String number, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "avInviteUser");
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.avInviteUser(sdkState, id, number);
		}
		@Override
		public void avAnswer(String owner, int id, boolean asAudio) throws RemoteException {
			LogUtil.i(TAG, "avAnswer, id = " + id);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.answer(sdkState, id, asAudio);
		}

		@Override
		public void avRing(String owner, int id) throws RemoteException {
			LogUtil.i(TAG, "avRing, id = " + id);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.ring(sdkState, id);
		}

		@Override
		public void avHungUp(String owner, int id) throws RemoteException {
			LogUtil.i(TAG, "avHungUp, id = " + id);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.hangUp(sdkState, id);
		}

		@Override
		public void avMute(String owner, int id) throws RemoteException {
			LogUtil.i(TAG, "avMute, id = " + id);

		}

		@Override
		public void avToggle(String owner, int id) throws RemoteException {
			LogUtil.i(TAG, "avToggle, id = " + id);

		}

		@Override
		public void getAvSession(String owner, int id, IGetAvSessionCallback cb) throws RemoteException {
			LogUtil.i(TAG, "getAvSession, id = " + id);

		}

		@Override
		public void msgSendReport(String owner, String number, String messageId, int reportType, int directedType, String target, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "sendReport, number = " + number + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
					pendingIntent , com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgSendReport(sdkState, number, messageId, reportType, directedType, target, callback);
		}

		@Override
		public void msgSetStatus(String owner, String number, String messageId, int msgState, int chatType, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "msgSetStatus, number = " + number + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),
					pendingIntent, BroadcastActions.ACTION_MESSAGE_SETSTATES_RESULT, "MessageSetStatesCallback");
			SdkApi.msgSetStatus(sdkState, number, messageId, msgState, chatType, callback);
		}

		@Override
		public void msgSetConvStatus(String owner, String convId, String messageId, int convState, int chatType, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "msgSetConvStatus, convId = " + convId + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),
					pendingIntent, BroadcastActions.ACTION_MESSAGE_SETCONV_RESULT, "MessageSetConvCallback");
			SdkApi.msgSetConvStatus(sdkState, convId, messageId, convState, chatType, callback);
		}

		@Override
		public void msgSendText(String owner, String number, String messageId, String content, boolean needReport, boolean isBurn, int directedType, boolean needReadReport, String extension, int contentType,
								PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "sendMessage, number = " + number + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
                    sentIntent, com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgSendText(sdkState, number, messageId, content, needReport, isBurn, directedType, needReadReport, extension, contentType, callback);
		}

		@Override
		public void msgSendFile(String owner, String number, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail, int duration, boolean isBurn, int directedType, boolean needReadReport, String extension,
								PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "sendFile, number = " + number + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
                    sentIntent , com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgSendFile(sdkState, number, messageId, filePath, contentType, fileName, needReport, start, thumbnail, duration, isBurn, directedType, needReadReport, extension, callback);
		}

		@Override
		public void msgSendVemoticon(String owner, String number, String messageId, String vemoticonId, String vemoticonName, boolean needReport, boolean isBurn, int directedType, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "sendVemoticon, number = " + number + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
                    sentIntent , com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgSendVemoticon(sdkState, number, messageId, vemoticonId, vemoticonName, needReport, isBurn, directedType, callback);
		}

		@Override
		public void msgSendCloudfile(String owner, String number, String messageId, String fileName, String fileSize, String fileUrl, boolean needReport, boolean isBurn, int directedType, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "sendCloudfile, number = " + number + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
                    sentIntent , com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgSendCloudfile(sdkState, number, messageId, fileName, fileSize, fileUrl, needReport, isBurn, directedType, callback);
		}

		@Override
		public void msgFetchFile(String owner, String number, String messageId, int chatType, String filePath, int contentType, String fileName, String transferId, int start, int fileSize, String hash, boolean isBurn, PendingIntent resultIntent) throws RemoteException {
			LogUtil.i(TAG, "fetchFile, number = " + number + ", message id = " + messageId + ", transferId = " + transferId);
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<FetchFileResult> callback = new ActionCallback<FetchFileResult>(getApplicationContext(),
                   resultIntent , BroadcastActions.ACTION_MESSAGE_FETCH_RESULT, "FetchFileCallback");
			SdkApi.msgFetchFile(sdkState, number, messageId, chatType, filePath, contentType, fileName, transferId, start, fileSize, hash, isBurn, callback);
		}

		@Override
		public void msgHttpFetch(String owner, String messageId, int contentType, String filePath, int start, int fileSize, String originalLink, PendingIntent resultIntent) throws RemoteException {
			LogUtil.i(TAG, "fetchHttpFile, message id = " + messageId + ", originalLink = " + originalLink);
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<FetchFileResult> callback = new ActionCallback<FetchFileResult>(getApplicationContext(),
                    resultIntent , BroadcastActions.ACTION_MESSAGE_FETCH_RESULT, "FetchFileCallback");
			SdkApi.msgHttpFetch(sdkState, messageId, contentType, filePath, start, fileSize, originalLink, callback);
		}

		@Override
		public void msgPubSendText(String owner, String uri, String messageId, String content, boolean needReport, PendingIntent sentIntent) throws RemoteException{
			LogUtil.i(TAG, "paSendMessage, uri = " + uri + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
                    sentIntent , com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgPubSendText(sdkState, uri, messageId, content, needReport, callback);
		}

		@Override
		public void msgPubSendFile(String owner, String uri, String messageId, String content, int contentType, String fileName, boolean needReport, int start, String thumbnail, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "paSendMessage, uri = " + uri + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
                    sentIntent , com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgPubSendFile(sdkState, uri, messageId, content, contentType, fileName, needReport, start, thumbnail, callback);
		}

		@Override
		public void msgGpSendText(String owner, String groupUri, String messageId, String content, boolean needReport, String ccNumber, boolean needReadReport, String extension, int contentType, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "gpSendMessage, group uri = " + groupUri + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
					sentIntent , com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgGpSendText(sdkState, groupUri, messageId, content, needReport, ccNumber, needReadReport, extension, contentType, callback);
		}

		@Override
		public void msgGpSendVemoticon(String owner, String groupUri, String messageId, String vemoticonId, String vemoticonName, boolean needReport, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "msgGpSendVemoticon, group uri = " + groupUri + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
					sentIntent , com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgGpSendVemoticon(sdkState, groupUri, messageId, vemoticonId, vemoticonName, needReport, callback);
		}

		@Override
		public void msgGpSendCloudfile(String owner, String groupUri, String messageId, String fileName, String fileSize, String fileUrl, boolean needReport, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "msgGpSendCloudfile, group uri = " + groupUri + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
					sentIntent , com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgGpSendCloudfile(sdkState, groupUri, messageId, fileName, fileSize, fileUrl, needReport, callback);
		}

		@Override
		public void msgGpSendFile(String owner, String groupUri, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail,
								  boolean needReadReport, String extension, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "msgGpSendFile, group uri = " + groupUri + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
					sentIntent , com.feinno.sdk.BroadcastActions.ACTION_MESSAGE_SEND_RESULT, "MessageCallback");
			SdkApi.msgGpSendFile(sdkState, groupUri, messageId, filePath, contentType, fileName, needReport, start, thumbnail, needReadReport, extension, callback);
		}

		@Override
		public void uploadShareFile(String owner, String targetId, String messageId, String filePath, String fileName, int expire, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "uploadShareFile, targetId = " + targetId + ", message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),
					sentIntent , com.feinno.sdk.BroadcastActions.ACTION_UPLOAD_SHARE_FILE_RESULT, "UploadShareFileCallback");
			SdkApi.uploadShareFile(sdkState, targetId, messageId, filePath, fileName, expire, callback);
		}

		@Override
		public void fetchShareFile(String owner, String targetId, String messageId, String fileId, String shareFileId, int start, int size, String filePath, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "fetchShareFile message id = " + messageId + ", fileId = " + fileId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
					sentIntent , com.feinno.sdk.BroadcastActions.ACTION_FETCH_SHARE_FILE_RESULT, "FetchShareFileCallback");
			SdkApi.fetchShareFile(sdkState, targetId, messageId, fileId, shareFileId, start, size, filePath, callback);
		}

		@Override
		public void deleteShareFile(String owner, String targetId, String fileId, String shareFileId, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "deleteShareFile, targetId = " + targetId + ", fileId = " + fileId + ", shareFileId = " + shareFileId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),
					sentIntent , com.feinno.sdk.BroadcastActions.ACTION_DELETE_SHARE_FILE_RESULT, "DeleteShareFileCallback");
			SdkApi.deleteShareFile(sdkState, targetId, fileId, shareFileId, callback);
		}

		@Override
		public void getShareFileList(String owner, String targetId, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "getShareFileList, targetId = " + targetId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ShareFileListResult> callback = new ActionCallback<ShareFileListResult>(getApplicationContext(),
					sentIntent , com.feinno.sdk.BroadcastActions.ACTION_GET_SHARE_FILE_LIST_RESULT, "GetShareFileListCallback");
			SdkApi.getShareFileList(sdkState, targetId, callback);
		}

		@Override
		public void cancelTransfer(String owner, String messageId, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "cancelTransfer, message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<MessageResult> callback = new ActionCallback<MessageResult>(getApplicationContext(),
					sentIntent , com.feinno.sdk.BroadcastActions.ACTION_CANCEL_TRANSFER_RESULT, "CancelTransferCallback");
			SdkApi.cancelTransfer(sdkState, messageId, callback);
		}

		@Override
		public void setNotifyRead(String owner, String messageId, PendingIntent sentIntent) throws RemoteException {
			LogUtil.i(TAG, "setNotifyRead, message id = " + messageId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),
					sentIntent , BroadcastActions.ACTION_SET_NOTIFY_READ_RESULT, "SetNotifyReadCallback");
			SdkApi.setNotifyRead(sdkState, messageId, callback);
		}

		@Override
		public void gpSubList(String owner, String version, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpSubList");
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.subGroupList(sdkState, version,
					new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpSubInfo(String owner, String groupUri, String infoVersion, String membersVersion, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpSubInfo, groupuri = " + groupUri);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.subGroupInfo(sdkState, groupUri, infoVersion, membersVersion,
					new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpJoin(String owner, String groupUri, String inviter, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpJoin, group uri = " + groupUri);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.joinGroup(sdkState, groupUri, inviter,
					new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpReject(String owner, String groupUri, String inviter, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpReject, group uri = " + groupUri);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.rejectGroup(sdkState, groupUri, inviter,
					new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpCreate(String owner, String resourceList, String subject, String introduce, String bulletin, int groupType, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpCreate, resourceList = " + resourceList);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.createGroup(sdkState, "", resourceList, subject, introduce, bulletin, groupType, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpInviteMember(String owner, String groupUri, String member, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpInviteMember, group uri = " + groupUri + ", member = " + member);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.inviteGroupMember(sdkState, groupUri, "", "", member, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpApply(String owner, String groupUri, String remark, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpApply, group uri = " + groupUri + ", remark = " + remark);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.applyGroup(sdkState, groupUri, "", remark, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpApproval(String owner, String groupUri, String applicant, int handleResult, String replyMsg, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpApproval, group uri = " + groupUri + ", applicant = " + applicant + "handleResult= " + handleResult);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.approvalGroup(sdkState, groupUri, "", applicant, handleResult, replyMsg, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpDelete(String owner, String groupUri, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpDelete, group uri = " + groupUri);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.deleteGroup(sdkState, groupUri, "", "", new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpExit(String owner, String groupUri, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpExit, group uri = " + groupUri);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.exitGroup(sdkState, groupUri, "", "", new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpSetInviteFlag(String owner, String groupUri, int flag, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpSetSubject, group uri = " + groupUri + ", flag = " + flag);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.gpSetInviteFlag(sdkState, groupUri, "", flag, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpSetExtra(String owner, String groupUri, String extra, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpSetSubject, group uri = " + groupUri + ", extra = " + extra);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.gpSetExtra(sdkState, groupUri, "", extra, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpSetSubject(String owner, String groupUri, String subject, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpSetSubject, group uri = " + groupUri + ", subject = " + subject);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.gpModifySubject(sdkState, groupUri, "", subject, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpSetIntroduce(String owner, String groupUri, String introduce, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpSetIntroduce, group uri = " + groupUri + ", introduce = " + introduce);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.gpModifyIntroduce(sdkState, groupUri, "", introduce, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpSetBulletin(String owner, String groupUri, String bulletin, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpSetBulletin, group uri = " + groupUri + ", bulletin = " + bulletin);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.gpModifyBulletin(sdkState, groupUri, "", bulletin, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpRemoveMember(String owner, String groupUri, String member, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpRemoveMember, group uri = " + groupUri + ", member = " + member);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.gpRemoveUser(sdkState, groupUri, "", member, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpAssignAdmin(String owner, String groupUri, String member, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpAssignAdmin, group uri = " + groupUri + ", member = " + member);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.gpChangeManager(sdkState, groupUri, "", member, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpModifyNickName(String owner, String groupUri, String name, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpModifyNickName, group uri = " + groupUri + ", nick name = " + name);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.gpModifyNickName(sdkState, groupUri, "", name, new ActionCallback<GroupOpResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_OP_RESULT, "GroupOpCallback"));
		}

		@Override
		public void gpSearchGroup(String owner, String subject, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpSearchGroup, group subject = " + subject);
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.searchGroup(sdkState, subject, new ActionCallback<GroupSearchResult>(getApplicationContext(), pendingIntent, BroadcastActions.ACTION_GROUP_SEARCH_RESULT, "GroupSearchCallback"));
		}

		@Override
		public void gpShareInfo(String owner, String groupUri, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpShareInfo, group uri = " + groupUri);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<GroupShareInfoResult> callback = new ActionCallback<GroupShareInfoResult>(getApplicationContext(),
					pendingIntent, com.feinno.sdk.BroadcastActions.ACTION_GROUP_SHARE_INFO_RESULT, "GroupShareInfoCallback");
			SdkApi.gpShareInfo(sdkState, groupUri, callback);
		}

		@Override
		public void gpSetDND(String owner, String groupUri, int flag, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "gpSetDND, group uri = " + groupUri);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),
					pendingIntent, com.feinno.sdk.BroadcastActions.ACTION_GROUP_SET_DND_RESULT, "GroupSetDNDCallback");
			SdkApi.gpSetDND(sdkState, groupUri, flag, callback);
		}

		@Override
		public void getCap(String owner, String number, PendingIntent pendingIntent) throws RemoteException {
			LogUtil.i(TAG, "getCap, number = " + number);
			Sdk.SdkState sdkState = getSdkState(owner);
			String internationalNumber = NgccTextUtils.getInternationalNumber(number);
            ActionCallback<CapsResult> callback = new ActionCallback<CapsResult>(getApplicationContext(),
                    pendingIntent, com.feinno.sdk.BroadcastActions.ACTION_CAPS_RESULT, "CapCallback");
			SdkApi.capsexchange(sdkState, internationalNumber, callback);
		}

        @Override
        public void buddyAdd(String owner, String user, String reason, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "buddyAdd, user = " + user);
            Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<BuddyResult> callback = new ActionCallback<BuddyResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_BUDDY_RESULT, "BuddyCallback");
            SdkApi.buddyAdd(sdkState, user,reason, callback);
        }

        @Override
        public void buddyDel(String owner, int userId, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "buddyDel, user = " + userId);
            Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<BuddyResult> callback = new ActionCallback<BuddyResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_BUDDY_RESULT, "BuddyCallback");
            SdkApi.buddyDel(sdkState, userId, callback);
        }

        @Override
        public void buddyMemo(String owner, int userId, String memo, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "buddyMemo, user = " + userId);
            Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<BuddyResult> callback = new ActionCallback<BuddyResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_BUDDY_RESULT, "BuddyCallback");
            SdkApi.buddyMemo(sdkState, userId, memo, callback);
        }

		@Override
		public void buddyDnd(String owner, int userId, int dndFlag, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "buddyDnd, user = " + userId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<BuddyResult> callback = new ActionCallback<BuddyResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_BUDDY_RESULT, "BuddyCallback");
			SdkApi.buddyDnd(sdkState, userId, dndFlag, callback);
		}

        @Override
        public void buddyAccept(String owner, int userId, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "buddyAccept, user = " + userId);
            Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<BuddyResult> callback = new ActionCallback<BuddyResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_BUDDY_RESULT, "BuddyCallback");
            SdkApi.buddyHandle(sdkState, userId, true, null, callback);
        }

        @Override
        public void buddyRefuse(String owner, int userId, String reason, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "buddyRefuse, user = " + userId);
            Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<BuddyResult> callback = new ActionCallback<BuddyResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_BUDDY_RESULT, "BuddyCallback");
            SdkApi.buddyHandle(sdkState, userId, false, reason, callback);
        }

        @Override
        public void userGetInfo(String owner, String user, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "userGetInfo, user = " + user);
            Sdk.SdkState sdkState = getSdkState(owner);

            ActionCallback<UserInfoResult> callback = new ActionCallback<UserInfoResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_USERINFO_RESULT, "UserInfoCallback");
            SdkApi.userGetInfo(sdkState, user, callback);
        }

        @Override
        public void userGetPortrait(String owner, int userId, boolean isSmall, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "userPortrait, user = " + userId);
            Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<UserPortraitResult> callback = new ActionCallback<UserPortraitResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_USERPROTRAIT_RESULT, "UserPortraitCallback");
            SdkApi.userGetPortrait(sdkState, userId, isSmall, callback);
        }

        @Override
        public void userSetPortrait(String owner, String filePath, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "userSetPortrait, user = " + owner);
            Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<UserPortraitResult> callback = new ActionCallback<UserPortraitResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_USERPROTRAIT_RESULT, "UserPortraitCallback");
            SdkApi.userSetPortrait(sdkState, filePath, callback);
        }

        @Override
        public void userGetProfile(String owner, String user, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "userGetProfile, user = " + user);
            Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<UserProfileResult> callback = new ActionCallback<UserProfileResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_USERPROFILE_RESULT, "UserProfileCallback");
            SdkApi.userGetProfile(sdkState, user, callback);
        }

        @Override
        public void userSetProfile(String owner, String nickname, String impresa, String firstname, String lastName, int genber,
                                String email, String birthday, String clientExtra, PendingIntent intent) throws RemoteException {
            LogUtil.i(TAG, "userSetProfile, user = " + owner);
            Sdk.SdkState sdkState = getSdkState(owner);
            ActionCallback<UserProfileResult> callback = new ActionCallback<UserProfileResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_USERPROFILE_RESULT, "UserProfileCallback");
            SdkApi.userSetProfile(sdkState, nickname, impresa, firstname, lastName, genber, email, birthday, clientExtra, callback);
        }

		@Override
		public void userSetCareer(String owner, String company, String position, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "userSetCareer, user = " + owner);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<UserProfileResult> callback = new ActionCallback<UserProfileResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_USERPROFILE_RESULT, "UserProfileCallback");
			SdkApi.userSetCareer(sdkState, company, position, callback);
		}

		@Override
		public void userSetDndFlag(String owner, int dndFlag, PendingIntent intent) throws RemoteException  {
			LogUtil.i(TAG, "userSetDndFlag, user = " + owner);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_DNDFLAG_RESULT, "UserDndFlagCallback");
			SdkApi.userSetDndFlag(sdkState, dndFlag, callback);
		}

		@Override
		public void userSetBuddyFlag(String owner, int buddydFlag, PendingIntent intent) throws RemoteException  {
			LogUtil.i(TAG, "userSetBuddyFlag, user = " + owner);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_BUDDYFLAG_RESULT, "UserBuddyFlagCallback");
			SdkApi.userSetBuddyFlag(sdkState, buddydFlag, callback);
		}

		@Override
		public void userSetPermissionUidFlag(String owner, int uidFlag, PendingIntent intent) throws RemoteException  {
			LogUtil.i(TAG, "userSetPermissionUidFlag, user = " + owner);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_BUDDYFLAG_RESULT, "UserBuddyFlagCallback");
			SdkApi.userSetPermissionUidFlag(sdkState, uidFlag, callback);
		}

		@Override
		public void userSetPermissionUnameFlag(String owner, int uanmeFlag, PendingIntent intent) throws RemoteException  {
			LogUtil.i(TAG, "userSetPermissionUnameFlag, user = " + owner);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, com.feinno.sdk.BroadcastActions.ACTION_BUDDYFLAG_RESULT, "UserBuddyFlagCallback");
			SdkApi.userSetPermissionUnameFlag(sdkState, uanmeFlag, callback);
		}

		private Sdk.SdkState getSdkState(String owner) throws RemoteException {
			LogUtil.i(TAG, "getSdkState, owner = " + owner);
			RemoteException exception  = new RemoteException();
			if (sdkStateMap == null) {
				exception.initCause(new Throwable("sdk state map is not init"));
				throw exception;
			} else {
				LogUtil.i(TAG, "sdkStateMap is not null");
			}
			String numberKey = NgccTextUtils.getNationalNumber(owner);
			if(TextUtils.isEmpty(numberKey)){
				numberKey = owner;
			}
			Sdk.SdkState sdkState = sdkStateMap.get(numberKey);
			if (sdkState == null) {
				exception.initCause(new Throwable("there is no such user " + owner));
				throw exception;
			} else {
				LogUtil.i(TAG, "sdkState is not null");
			}

			return sdkState;
		}

		@Override
		public void stop(String owner) throws RemoteException {
			LogUtil.i(TAG, "stop");
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.stop(sdkState);
		}

		@Override
		public void doConnect(String owner) throws RemoteException {
			LogUtil.i(TAG, "doConnect");
			Sdk.SdkState sdkState = getSdkState(owner);
			SdkApi.doConnect(sdkState);
		}

		@Override
		public void getUserStates(String owner, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "getUserStates");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<UserStateResult> callback = new ActionCallback<UserStateResult>(getApplicationContext(),intent, BroadcastActions.ACTION_GET_USERSTATES_RESULT, "GetUserStatesCallback");
			SdkApi.getUserStates(sdkState, callback);
		}

		@Override
		public void getEndpointList(String owner, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "getEndpointList ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<EndPointResult> callback = new ActionCallback<EndPointResult>(getApplicationContext(),intent, BroadcastActions.ACTION_ENDPOINTLIST_RESULT, "EndpointListCallback");
			SdkApi.getEndpointList(sdkState, callback);
		}

		@Override
		public void bootEndpoint(String owner, String clientId, int clientType, String clientVersion, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "userGetProfile, user = " + clientId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, BroadcastActions.ACTION_ENDPOINT_RESULT, "BootEndpointCallback");
			SdkApi.bootEndpoint(sdkState, clientId, clientType, clientVersion, callback);
		}

		@Override
		public void deleteEndpoint(String owner, String clientId, int clientType, String clientVersion, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "deleteEndpoint, clientId = " + clientId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, BroadcastActions.ACTION_ENDPOINT_RESULT, "DeleteEndpointCallback");
			SdkApi.deleteEndpoint(sdkState, clientId, clientType, clientVersion, callback);
		}

		@Override
		public void setEndpointDND(String owner, int flag, String beginTime, String endTime, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "setEndpointDND, flag = " + flag);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, BroadcastActions.ACTION_SET_ENDPOINT_DND_RESULT, "SetEndpointDNDCallback");
			SdkApi.setEndpointDND(sdkState, flag, beginTime, endTime, callback);
		}

		@Override
		public void getConvList(String owner, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "getConvList ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ConvListResult> callback = new ActionCallback<ConvListResult>(getApplicationContext(),intent, BroadcastActions.ACTION_GET_CONVLIST_RESULT, "GetConvListCallback");
			SdkApi.getConvList(sdkState, callback);
		}

		@Override
		public void getConvHistory(String owner, int convType, String convId, int pageLimit, String beginImdnId, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "getConvHistory ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ConvHistoryResult> callback = new ActionCallback<ConvHistoryResult>(getApplicationContext(), intent, BroadcastActions.ACTION_GET_CONV_HISTORY_RESULT, "GetConvHistoryCallback");
			SdkApi.getConvHistory(sdkState, convType, convId, pageLimit, beginImdnId, callback);
		}

		public void subPresence(String owner, String cids, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "subPresence, cids = " + cids);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(), intent, BroadcastActions.ACTION_PRESENCE_RESULT, "SubPresenceCallback");
			SdkApi.subPresence(sdkState, cids, callback);
		}

		@Override
		public void unSubPresence(String owner, String cids, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "unSubPresence, cids = " + cids);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(), intent, BroadcastActions.ACTION_PRESENCE_RESULT, "SubPresenceCallback");
			SdkApi.unSubPresence(sdkState, cids, callback);
		}

		public void getPresence(String owner, String contactId, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "getPresence, contact = " + contactId);
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(), intent, BroadcastActions.ACTION_GET_PRESENCE_RESULT, "GetPresenceCallback");
			SdkApi.getPresence(sdkState, contactId, callback);
		}

		@Override
		public void token(String owner, PendingIntent intent) throws RemoteException {
			LogUtil.i(TAG, "token ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<TokenResult> callback = new ActionCallback<TokenResult>(getApplicationContext(),intent, BroadcastActions.ACTION_TOKEN_RESULT, "TokenCallback");
			SdkApi.token(sdkState, callback);
		}

		public void bklistAdd(String owner, String userId, PendingIntent intent) throws RemoteException{
			LogUtil.i(TAG, "bklistAdd ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, BroadcastActions.ACTION_BKLIST_ADD_RESULT, "BklistAddCallback");
			SdkApi.bklistAdd(sdkState, userId, callback);
		}

		public void bklistRemove(String owner, String userId, PendingIntent intent) throws RemoteException{
			LogUtil.i(TAG, "bklistAdd ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, BroadcastActions.ACTION_BKLIST_REMOVE_RESULT, "BklistRemoveCallback");
			SdkApi.bklistRemove(sdkState, userId, callback);
		}

		public void bklistGet(String owner, PendingIntent intent) throws RemoteException{
			LogUtil.i(TAG, "bklistAdd ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<BklistGetResult> callback = new ActionCallback<BklistGetResult>(getApplicationContext(),intent, BroadcastActions.ACTION_BKLIST_GET_RESULT, "BklistGetCallback");
			SdkApi.bklistGet(sdkState, callback);
		}

		public void deviceAdd(String owner, String userId, String password, PendingIntent intent) throws RemoteException{
			LogUtil.i(TAG, "deviceAdd ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, BroadcastActions.ACTION_DEVICE_ADD_RESULT, "DeviceAddCallback");
			SdkApi.deviceAdd(sdkState, userId, password, callback);
		}

		public void deviceRemove(String owner, String userId, PendingIntent intent) throws RemoteException{
			LogUtil.i(TAG, "deviceRemove ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<ActionResult> callback = new ActionCallback<ActionResult>(getApplicationContext(),intent, BroadcastActions.ACTION_DEVICE_REMOVE_RESULT, "DeviceRemoveCallback");
			SdkApi.deviceRemove(sdkState, userId, callback);
		}

		public void deviceListGet(String owner, PendingIntent intent) throws RemoteException{
			LogUtil.i(TAG, "deviceListGet ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<DeviceListGetResult> callback = new ActionCallback<DeviceListGetResult>(getApplicationContext(),intent, BroadcastActions.ACTION_DEVICE_LIST_GET_RESULT, "DeviceListGetCallback");
			SdkApi.devicelistGet(sdkState, callback);
		}

		public void deviceStatusGet(String owner, String userId, PendingIntent intent) throws RemoteException{
			LogUtil.i(TAG, "deviceStatusGet ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<DeviceStatusGetResult> callback = new ActionCallback<DeviceStatusGetResult>(getApplicationContext(),intent, BroadcastActions.ACTION_DEVICE_STATUS_GET_RESULT, "DeviceStatusGetCallback");
			SdkApi.deviceStatusGet(sdkState, userId , callback);
		}

		public void msg2Shorturl(String owner,String imdnId, int msgType, String content, String filePath, PendingIntent intent) throws RemoteException{
			LogUtil.i(TAG, "msg2Shorturl ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<Msg2ShorturlResult> callback = new ActionCallback<Msg2ShorturlResult>(getApplicationContext(),intent, BroadcastActions.ACTION_MSG2SHORTURL_RESULT, "Msg2ShorturlCallback");
			SdkApi.msg2Shorturl(sdkState, imdnId, msgType, content, filePath, callback);
		}

		public void getFileId(String owner,String fileName, PendingIntent intent) throws RemoteException{
			LogUtil.i(TAG, "getFileId ");
			Sdk.SdkState sdkState = getSdkState(owner);
			ActionCallback<GetFileIdResult> callback = new ActionCallback<GetFileIdResult>(getApplicationContext(),intent, BroadcastActions.ACTION_GETFILEID_RESULT, "GetFileIdCallback");
			SdkApi.getFileId(sdkState, fileName, callback);
		}
	}
}
