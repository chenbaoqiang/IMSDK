package com.feinno.sdk.dapi;

import android.app.Application;
import android.app.ExpandableListActivity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.feinno.sdk.StateManager;
import com.feinno.sdk.session.IGetAvSessionCallback;
import com.feinno.sdk.utils.LogUtil;

public class WorkingServiceProxy {
	private static final String TAG = "WorkingServiceProxy";

	private long lastEnsureBroadcastTime = 0;

	private Application application;
	private IRCSWorkingService workingService;
	private WorkingConnection workingConnection = null;
	private static WorkingServiceProxy proxy;

	private String dmUrl;
	private boolean autoAddUser;

	private String deviceId = "";
	private String subscriberId = "";
	private String number = "";

	private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
		@Override
		public void binderDied() {
			workingService = null;
		}
	};

	private WorkingServiceProxy() {

	}

	public static WorkingServiceProxy instance() {
		if (proxy == null) {
			proxy = new WorkingServiceProxy();
		}

		return proxy;
	}

	public boolean startSvc(Application application, String dmUrl, boolean autoAddUser) {
		LogUtil.i(TAG, "startSvc");
		this.application = application;
		this.dmUrl = dmUrl;
		this.autoAddUser = autoAddUser;

		if (autoAddUser) {
			TelephonyManager telephonyManager =
					(TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
			try {
				deviceId = telephonyManager.getDeviceId();
			} catch (Exception e) {
				deviceId = "";
				LogUtil.e(TAG, e);
			}

			try {
				subscriberId = telephonyManager.getSubscriberId();
			} catch (Exception e) {
				subscriberId = "";
				LogUtil.e(TAG, e);
			}

			try {
				number = telephonyManager.getLine1Number();
			} catch (Exception e) {
				number = "";
				LogUtil.e(TAG, e);
			}
		}

		return bindService(application);
	}

    public boolean isConnected() {
        return null != workingService;
    }

	public void stopSvc(Context context) {
		LogUtil.i(TAG, "stopSvc");
		if (workingConnection != null) {
			try {
				workingService.notifyConnection(StateManager.SERVICE_STATE_STOP);
			} catch (RemoteException e) {
				LogUtil.e(TAG, e);
			}
			context.unbindService(workingConnection);
			Intent it = new Intent(context, RCSWorkingService.class);
			context.stopService(it);
			workingService = null;
		}
	}

	public int addUser(String number, String imsi, String storage, String clientVendor, String clientVersion, String sysPath, String appId) throws RemoteException {
		LogUtil.i(TAG, "startUser, number = " + number);
		ensureConnection();
		return workingService.addUser(number, imsi, storage, clientVendor, clientVersion, sysPath, appId);
	}

	public boolean removeUser(String number) throws RemoteException {
		LogUtil.i(TAG, "removeUser, number = " + number);
		ensureConnection();
		return workingService.removeUser(number);
	}

	public String[] getAllUsers() throws RemoteException {
		LogUtil.i(TAG, "getAllUsers");
		ensureConnection();
		return workingService.getAllUsers();
	}

	public void setProvisionUrl(String owner, String getSMSCodeUrl, String provisionUrl) throws RemoteException {
		ensureConnection();
		workingService.setProvisionUrl(owner, getSMSCodeUrl, provisionUrl);
	}

	public void getSMSCode(String owner) throws RemoteException {
		ensureConnection();
		workingService.getSMSCode(owner, null);
	}
    
	public void getSMSCode(String owner, String number) throws RemoteException {
		ensureConnection();
		workingService.getSMSCode1(owner, number, null);
	}

	public void getSMSCode2(String owner) throws RemoteException {
		ensureConnection();
		workingService.getSMSCode2(owner, null);
	}

	public void provision(String owner, String password, PendingIntent intent) throws RemoteException {
		ensureConnection();
		workingService.provision(owner, password, intent);
	}

	public void provision(String owner, String number, String password, PendingIntent intent) throws RemoteException {
		ensureConnection();
		workingService.provision1(owner, number, password, intent);
	}

	public void provision2(String owner, int sessionId, String password, String smsCode, String imsi, String imei, String deviceModel, String deviceType, String deviceOS, String subHost) throws RemoteException {
		ensureConnection();
		workingService.provision2(owner, sessionId, password, smsCode, imsi, imei, deviceModel, deviceType, deviceOS, subHost);
	}

	public void provisionOtp(String owner, String sessId, String number, String smsCode, String otp, PendingIntent intent) throws RemoteException {
		ensureConnection();
		workingService.provisionOtp(owner, sessId, number, smsCode, otp, intent);
	}

	public void provisionIot(String owner, String password, PendingIntent intent) throws RemoteException {
		ensureConnection();
		workingService.provisionIot(owner, password, intent);
	}

	public void login(String owner, String password, String dmToken, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.login(owner, password, dmToken, pendingIntent);
	}

	public void logout(String owner, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.logout(owner, pendingIntent);
	}

	public void stop(String owner) throws RemoteException {
		ensureConnection();
		workingService.stop(owner);
	}

	public void doConnect(String owner) throws RemoteException {
		ensureConnection();
		workingService.doConnect(owner);
	}

	public void getUserStates(String owner, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.getUserStates(owner, pendingIntent);
	}

	public void keepAlive(String owner) throws RemoteException {
		ensureConnection();
		workingService.keepAlive(owner);
	}

	public void avInvite(String owner, String number, boolean isAudio, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.avInvite(owner, number, isAudio, pendingIntent);
	}

	public void avMultiInvite(String owner, String numbers, boolean isAudio, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.avMultiInvite(owner, numbers, isAudio, pendingIntent);
	}

	public void avInviteUser(String owner, int id, String number, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.avInviteUser(owner, id, number, pendingIntent);
	}
	public void avAnswer(String owner, int id, boolean asAudio) throws RemoteException {
		ensureConnection();
		workingService.avAnswer(owner, id, asAudio);
	}

	public void avRing(String owner, int id) throws RemoteException {
		ensureConnection();
		workingService.avRing(owner, id);
	}

	public void avHungUp(String owner, int id) throws RemoteException {
		ensureConnection();
		workingService.avHungUp(owner, id);
	}

	public void avMute(String owner, int id) throws RemoteException {
		ensureConnection();
		workingService.avMute(owner, id);
	}

	public void avToggle(String owner, int id) throws RemoteException {
		ensureConnection();
		workingService.avToggle(owner, id);
	}

	public void getAvSession(String owner, int id, IGetAvSessionCallback cb) throws RemoteException {
		ensureConnection();
		workingService.getAvSession(owner, id, cb);
	}

	public void msgSendText(String owner, String number, String messageId, String content, boolean needReport, boolean isBurn, int directedType, boolean needReadReport, String extension, int contentType, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.msgSendText(owner, number, messageId, content, needReport, isBurn, directedType, needReadReport, extension, contentType, sentIntent);
	}

	public void msgSendReport(String owner, String number, String messageId, int reportType, int directedType, String target, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.msgSendReport(owner, number, messageId, reportType, directedType, target, pendingIntent);
	}

	public void msgSetStatus(String owner, String number, String messageId, int msgState, int chatType, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.msgSetStatus(owner, number, messageId, msgState, chatType, pendingIntent);
	}

	public void msgSetConvStatus(String owner, String convId, String messageId, int convState, int chatType, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.msgSetConvStatus(owner, convId, messageId, convState, chatType, pendingIntent);
	}


	public void msgSendFile(String owner, String number, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail, boolean isBurn, int directedType,
							boolean needReadReport, String extension, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.msgSendFile(owner, number, messageId, filePath, contentType, fileName, needReport, start, thumbnail, 0, isBurn, directedType,
				needReadReport, extension, sentIntent);
	}

	public void msgSendFile(String owner, String number, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail, int duration, boolean isBurn, int directedType,
							boolean needReadReport, String extension, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.msgSendFile(owner, number, messageId, filePath, contentType, fileName, needReport, start, thumbnail, duration, isBurn, directedType,
				needReadReport, extension, sentIntent);
	}

	public void msgFetchFile(String owner, String number, String messageId, int chatType, String filePath, int contentType, String fileName, String transferId, int start, int fileSize, String hash, boolean isBurn, PendingIntent resultIntent) throws RemoteException {
		ensureConnection();
		workingService.msgFetchFile(owner, number, messageId, chatType, filePath, contentType, fileName, transferId, start, fileSize, hash, isBurn, resultIntent);
	}

	public void msgHttpFetch(String owner, String messageId, int contentType, String filePath, int start, int fileSize, String originalLink, PendingIntent resultIntent) throws RemoteException {
		ensureConnection();
		workingService.msgHttpFetch(owner, messageId, contentType, filePath, start, fileSize, originalLink, resultIntent);
	}

	public void msgSendVemoticon(String owner, String number, String messageId, String vemoticonId, String vemoticonName, boolean needReport, boolean isBurn, int directedType, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.msgSendVemoticon(owner, number, messageId, vemoticonId, vemoticonName, needReport, isBurn, directedType, sentIntent);
	}

	public void msgSendCloudfile(String owner, String number, String messageId, String fileName, String fileSize, String fileUrl, boolean needReport, boolean isBurn, int directedType, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.msgSendCloudfile(owner, number, messageId, fileName, fileSize, fileUrl, needReport, isBurn, directedType, sentIntent);
	}

	public void msgPubSendText(String owner, String uri, String messageId, String content, boolean needReport, PendingIntent sentIntent) throws RemoteException{
		ensureConnection();
		workingService.msgPubSendText(owner, uri, messageId, content, needReport, sentIntent);
	}

	public void msgPubSendFile(String owner, String uri, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.msgPubSendFile(owner, uri, messageId, filePath, contentType, fileName, needReport, start, thumbnail, sentIntent);
	}

	public void msgGpSendText(String owner, String groupUri, String messageId, String content, boolean needReport, String ccNumber, boolean needReadReport, String extension, int contentType, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.msgGpSendText(owner, groupUri, messageId, content, needReport, ccNumber, needReadReport, extension, contentType, sentIntent);
	}

	public void msgGpSendFile(String owner, String groupUri, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail,
							  boolean needReadReport, String extension, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.msgGpSendFile(owner, groupUri, messageId, filePath, contentType, fileName, needReport, start, thumbnail, needReadReport, extension, sentIntent);
	}

	public void msgGpSendVemoticon(String owner, String groupUri, String messageId, String vemoticonId, String vemoticonName, boolean needReport, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.msgGpSendVemoticon(owner, groupUri, messageId, vemoticonId, vemoticonName, needReport, sentIntent);
	}

	public void msgGpSendCloudfile(String owner, String groupUri, String messageId, String fileName, String fileSize, String fileUrl, boolean needReport, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.msgGpSendCloudfile(owner, groupUri, messageId, fileName, fileSize, fileUrl, needReport, sentIntent);
	}

	public void uploadShareFile(String owner, String targetId, String messageId, String filePath, String fileName, int expire, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.uploadShareFile(owner, targetId, messageId, filePath, fileName, expire, sentIntent);
	}

	public void fetchShareFile(String owner, String targetId, String messageId, String fileId, String shareFileId, int start, int size, String filePath, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.fetchShareFile(owner, targetId, messageId, fileId, shareFileId, start, size, filePath, sentIntent);
	}

	public void deleteShareFile(String owner, String targetId, String fileId, String shareFileId, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.deleteShareFile(owner, targetId, fileId, shareFileId, sentIntent);
	}

	public void getShareFileList(String owner, String targetId, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.getShareFileList(owner, targetId, sentIntent);
	}

	public void cancelTransfer(String owner, String messageId, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.cancelTransfer(owner, messageId, sentIntent);
	}

	public void setNotifyRead(String owner, String messageId, PendingIntent sentIntent) throws RemoteException {
		ensureConnection();
		workingService.setNotifyRead(owner, messageId, sentIntent);
	}

	public void gpJoin(String owner, String groupUri, String inviter, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpJoin(owner, groupUri, inviter, pendingIntent);
	}

	public void gpReject(String owner, String groupUri, String inviter, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpReject(owner, groupUri, inviter, pendingIntent);
	}

	public void gpCreate(String owner, String resourceList, String subject, String introduce, String bulletin, int groupType, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpCreate(owner, resourceList, subject, introduce, bulletin, groupType, pendingIntent);
	}

	public void gpInviteMember(String owner, String groupUri, String member, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpInviteMember(owner, groupUri, member, pendingIntent);
	}

	public void gpApply(String owner, String groupUri, String remark, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpApply(owner, groupUri, remark, pendingIntent);
	}

	public void gpApproval(String owner, String groupUri, String applicant, int handleResult, String replyMsg, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpApproval(owner, groupUri, applicant, handleResult, replyMsg, pendingIntent);
	}

	public void gpDelete(String owner, String groupUri, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpDelete(owner, groupUri, pendingIntent);
	}

	public void gpExit(String owner, String groupUri, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpExit(owner, groupUri, pendingIntent);
	}

	public void gpSetSubject(String owner, String groupUri, String subject, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpSetSubject(owner, groupUri, subject, pendingIntent);
	}

	public void gpSetInviteFlag(String owner, String groupUri, int flag, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpSetInviteFlag(owner, groupUri, flag, pendingIntent);
	}

	public void gpSetExtra(String owner, String groupUri, String extra, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpSetExtra(owner, groupUri, extra, pendingIntent);
	}

	public void gpSetIntroduce(String owner, String groupUri, String introduce, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpSetIntroduce(owner, groupUri, introduce, pendingIntent);
	}

	public void gpSetBulletin(String owner, String groupUri, String bulletin, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpSetBulletin(owner, groupUri, bulletin, pendingIntent);
	}

	public void gpRemoveMember(String owner, String groupUri, String member, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpRemoveMember(owner, groupUri, member, pendingIntent);
	}

	public void gpAssignAdmin(String owner, String groupUri, String member, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpAssignAdmin(owner, groupUri, member, pendingIntent);
	}

	public void gpModifyNickName(String owner, String groupUri, String name, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpModifyNickName(owner, groupUri, name, pendingIntent);
	}

	public void gpSubList(String owner, String version, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpSubList(owner, version, pendingIntent);
	}

	public void gpSubInfo(String owner, String groupUri, String infoVersion, String membersVersion, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpSubInfo(owner, groupUri, infoVersion, membersVersion, pendingIntent);
	}

	public void gpSearchGroup(String owner, String subject, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpSearchGroup(owner, subject, pendingIntent);
	}

	public void gpShareInfo(String owner, String groupUri, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpShareInfo(owner, groupUri, pendingIntent);
	}

	public void gpSetDND(String owner, String groupUri, int flag, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.gpSetDND(owner, groupUri, flag, pendingIntent);
	}

	public void getCap(String owner, String number, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.getCap(owner, number, pendingIntent);
	}

	//buddies:
	public void buddyAdd(String owner, String user, String reason, PendingIntent intent) throws RemoteException {
		ensureConnection();
        workingService.buddyAdd(owner, user, reason, intent);
	}
	public void buddyDel(String owner, int userId, PendingIntent intent) throws RemoteException{
        ensureConnection();
        workingService.buddyDel(owner, userId, intent);
	}

	public void buddyMemo(String owner, int userId, String memo, PendingIntent intent) throws RemoteException {
        ensureConnection();
        workingService.buddyMemo(owner, userId, memo, intent);
	}

	public void dndBuddy(String owner, int userId, int dndFlag, PendingIntent intent) throws RemoteException {
		ensureConnection();
		workingService.buddyDnd(owner, userId, dndFlag, intent);
	}

	public void buddyAccept(String owner, int userId, PendingIntent intent) throws RemoteException {
		ensureConnection();
		workingService.buddyAccept(owner, userId, intent);
	}
	public void buddyRefuse(String owner, int userId, String reason, PendingIntent intent) throws RemoteException {
		ensureConnection();
		workingService.buddyRefuse(owner, userId, reason, intent);
	}

    public void userGetInfo(String owner, String ids, PendingIntent pendingIntent) throws RemoteException {
        ensureConnection();
        workingService.userGetInfo(owner, ids, pendingIntent);
    }
    public void userGetProfile(String owner, String user, PendingIntent pendingIntent) throws RemoteException {
        ensureConnection();
        workingService.userGetProfile(owner, user, pendingIntent);
    }

    public void userSetProfile(String owner, String nickname, String impresa, String firstname,
                               String lastName, int gender, String email, String birthday, String clientExtra
			,PendingIntent pendingIntent) throws RemoteException {

        ensureConnection();
        workingService.userSetProfile(owner, nickname, impresa, firstname, lastName,
				gender, email, birthday, clientExtra, pendingIntent);
    }

	public void userSetCareer(String owner, String company, String position,PendingIntent pendingIntent) throws RemoteException {

		ensureConnection();
		workingService.userSetCareer(owner, company, position, pendingIntent);
	}

	public void userSetDndFlag(String owner, int dndFlag, PendingIntent pendingIntent) throws RemoteException{
		ensureConnection();
		workingService.userSetDndFlag(owner, dndFlag, pendingIntent);
	}

	public void userSetBuddyFlag(String owner, int buddyFlag, PendingIntent pendingIntent) throws RemoteException{
		ensureConnection();
		workingService.userSetBuddyFlag(owner, buddyFlag, pendingIntent);
	}

	public void useretPermissionUidFlag(String owner, int uidFlag, PendingIntent pendingIntent) throws RemoteException{
		ensureConnection();
		workingService.userSetPermissionUidFlag(owner, uidFlag, pendingIntent);
	}

	public void useretPermissionUnameFlag(String owner, int unameFlag, PendingIntent pendingIntent) throws RemoteException{
		ensureConnection();
		workingService.userSetPermissionUnameFlag(owner, unameFlag, pendingIntent);
	}

    public void userGetPortrait(String owner, int userId, boolean isSmall, PendingIntent pendingIntent) throws RemoteException {
        ensureConnection();
        workingService.userGetPortrait(owner, userId, isSmall, pendingIntent);
    }

    public void userSetPortrait(String owner, String filePath, PendingIntent pendingIntent) throws RemoteException {
        ensureConnection();
        workingService.userSetPortrait(owner, filePath, pendingIntent);
    }

	public void getEndpointList(String owner, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.getEndpointList(owner, pendingIntent);
	}

	public void bootEndpoint(String owner, String clientId, int clientType, String clientVersion, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.bootEndpoint(owner, clientId, clientType, clientVersion, pendingIntent);
	}

	public void deleteEndpoint(String owner, String clientId, int clientType, String clientVersion, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.deleteEndpoint(owner, clientId, clientType, clientVersion, pendingIntent);
	}

	public void setEndpointDND(String owner, int flag, String beginTime, String endTime, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.setEndpointDND(owner, flag, beginTime, endTime, pendingIntent);
	}

	public void getConvList(String owner, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.getConvList(owner, pendingIntent);
	}

	public void getConvHistory(String owner, int convType, String convId, int pageLimit, String beginImdnId, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.getConvHistory(owner, convType, convId, pageLimit, beginImdnId, pendingIntent);
	}

	public void subPresence(String owner, String cids, PendingIntent pendingIntent) throws RemoteException{
		ensureConnection();
		workingService.subPresence(owner, cids, pendingIntent);
	}

	public void unSubPresence(String owner, String cids, PendingIntent pendingIntent) throws RemoteException{
		ensureConnection();
		workingService.unSubPresence(owner, cids, pendingIntent);
	}

	public void getPresence(String owner, String contactId, PendingIntent pendingIntent) throws RemoteException{
		ensureConnection();
		workingService.getPresence(owner, contactId, pendingIntent);
	}

	public void token(String owner, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.token(owner, pendingIntent);
	}

	public void bklistAdd(String owner, String userId, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.bklistAdd(owner, userId, pendingIntent);
	}

	public void bklistRemove(String owner, String userId, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.bklistRemove(owner, userId, pendingIntent);
	}

	public void bklistGet(String owner, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.bklistGet(owner, pendingIntent);
	}

	public void deviceAdd(String owner, String userId, String password, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.deviceAdd(owner, userId, password, pendingIntent);
	}

	public void deviceRemove(String owner, String userId, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.deviceRemove(owner, userId, pendingIntent);
	}

	public void deviceListGet(String owner, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.deviceListGet(owner, pendingIntent);
	}

	public void deviceStatusGet(String owner, String userId, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.deviceStatusGet(owner, userId, pendingIntent);
	}

	public void msg2Shorturl(String owner,String imdnId, int msgType, String content, String filePath, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.msg2Shorturl(owner, imdnId, msgType, content, filePath, pendingIntent);
	}

	public void getFileId(String owner, String fileName, PendingIntent pendingIntent) throws RemoteException {
		ensureConnection();
		workingService.getFileId(owner, fileName, pendingIntent);
	}

    private void ensureConnection() throws RemoteException {
		LogUtil.i(TAG, "ensureConnection");
		sendEnsureBootBroadcast(application);
		if(workingService == null && application != null) {
			LogUtil.i(TAG, "workingService is null");
			bindService(application);
		}

		if(workingService == null) {
			RemoteException exception = new RemoteException();
			exception.initCause(new Throwable("service connection is null, please make sure you have connected to the working service first"));
			throw exception;
		} else {
			LogUtil.i(TAG, "workingService is not null");
		}
	}

	private void sendEnsureBootBroadcast(Context context){
		long now = System.currentTimeMillis();
		if(now - lastEnsureBroadcastTime > 60 * 1000) {
			lastEnsureBroadcastTime = now;
			Intent intent = new Intent(RCSWorkingServiceBootReceiver.BOOT_ACTION);
			context.sendBroadcast(intent);
		}
	}

	private synchronized boolean bindService(Context context) {
		LogUtil.i(TAG, "bindService");
		Intent it = new Intent("com.feinno.uws.Action.RCSWorkingService");
        //it.setPackage("com.feinno.uws");
		try {
			context.getPackageManager().getServiceInfo(
					new ComponentName(context.getPackageName(), RCSWorkingService.class.getName()),
					PackageManager.GET_SERVICES);
			it.setPackage(context.getPackageName());
		} catch (PackageManager.NameNotFoundException e) {
			LogUtil.i(TAG, "PackageManager NameNotFoundException, set package name");
			it.setPackage("com.feinno.uws");
		}

		it.putExtra("PackageName", context.getPackageName());

		if (workingConnection != null) {
			try {
				context.unbindService(workingConnection);
			} catch (Exception e) {
				LogUtil.e(TAG, e);
			}
		}
		if (workingService != null) {
			workingService = null;
		}
		workingConnection = new WorkingConnection();
        try {
            boolean res = context.bindService(it, workingConnection, Context.BIND_AUTO_CREATE);
			LogUtil.d(TAG, "bind service res " + res);
			return res;
        } catch (Exception e) {
			LogUtil.e(TAG, e);
            return false;
        }
	}

	public void setDm(String owner, String ip, String port, String sslPort) throws RemoteException {
		ensureConnection();
		workingService.setDmUrl(owner, ip, port, sslPort);
	}



    private final class WorkingConnection implements ServiceConnection {
		public void onServiceConnected(ComponentName name, IBinder service) {
			LogUtil.i(TAG, "on service connected");
			workingService = IRCSWorkingService.Stub.asInterface(service);
			try {
				service.linkToDeath(deathRecipient, 0);

				//workingService.setDmUrl(dmUrl);
				workingService.notifyConnection(StateManager.SERVICE_STATE_START);

				if (autoAddUser &&
						!TextUtils.isEmpty(subscriberId) &&
						!TextUtils.isEmpty(deviceId) &&
						!TextUtils.isEmpty(number)) {
					// TODO: add user
					//startUser(number, deviceId, subscriberId);
				}
			} catch (RemoteException e) {
				LogUtil.e(TAG, "bind remote service error", e);
			}
		}

		public void onServiceDisconnected(ComponentName name) {
			LogUtil.i(TAG, "onServiceDisconnected");
			workingService = null;
			AsyncTask<Context, Void, Void> action = new AsyncTask<Context, Void, Void>() {
				@Override
				protected Void doInBackground(Context... params) {
					try{
						if(!bindService(params[0])){
							LogUtil.d(TAG, "bind service not ok");
							Thread.sleep(500);
							boolean res = bindService(params[0]);
							LogUtil.d(TAG, "bind 2 res " + res);
						}
					}catch (Exception ex){
						LogUtil.d(TAG, ex.toString());
					}
					return null;
				}
			};
			final Context param = application;
			action.execute(param);
		}
	}
}
