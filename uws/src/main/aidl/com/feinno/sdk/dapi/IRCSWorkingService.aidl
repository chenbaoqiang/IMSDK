// IRCSWorkingService.aidl
package com.feinno.sdk.dapi;

import android.app.PendingIntent;

import com.feinno.sdk.session.IGetAvSessionCallback;

interface IRCSWorkingService {
    int addUser(String number, String imsi, String storage, String clientVendor, String clientVersion, String sysPath, String appId);
    boolean removeUser(String number);
    String[] getAllUsers();
    void setDmUrl(String owner, String ip, String port, String sslPort);
    void notifyConnection(int state);
    void setClientInfo(String clientVendor, String clientVersion, String terminalVendor, String terminalMode, String terminalSWVersion);

    void setProvisionUrl(String owner, String getSMSCodeUrl, String provisionUrl);
    void getSMSCode(String owner, in android.app.PendingIntent pendingIntent);
    void getSMSCode1(String owner, String number, in android.app.PendingIntent pendingIntent);
    void getSMSCode2(String owner, in android.app.PendingIntent pendingIntent);
    void provision(String owner, String password, in android.app.PendingIntent pendingIntent);
    void provision1(String owner, String number, String password, in android.app.PendingIntent pendingIntent);
    void provision2(String owner, int sessionId, String password, String smsCode, String imsi, String imei, String deviceModel, String deviceType, String deviceOS, String subHost);
    void provisionOtp(String owner, String sessionId, String number, String smsCode, String otp,in android.app.PendingIntent intent);
    void provisionIot(String owner, String password, in android.app.PendingIntent intent);

    void login(String owner, String password, String dmToken, in android.app.PendingIntent pendingIntent);
    void logout(String owner, in android.app.PendingIntent pendingIntent);
    void keepAlive(String owner);

    void avInvite(String owner, String number, boolean isAudio, in android.app.PendingIntent pendingIntent);
    void avMultiInvite(String owner, String numbers, boolean isAudio, in android.app.PendingIntent pendingIntent);
    void avInviteUser(String owner, int id, String number, in android.app.PendingIntent pendingIntent);
    void avAnswer(String owner, int id, boolean asAudio);
    void avRing(String owner, int id);
    void avHungUp(String owner, int id);
    void avMute(String owner, int id);
    void avToggle(String owner, int id);
    void getAvSession(String owner, int id, in IGetAvSessionCallback cb);

    void msgSendText(String owner, String number, String messageId, String content, boolean needReport, boolean isBurn, int directedType, boolean needReadReport, String extension, int contentType, in android.app.PendingIntent sentIntent);
    void msgSendReport(String owner, String number, String messageId, int reportType, int directedType, String target, in android.app.PendingIntent pendingIntent);
    void msgSetStatus(String owner, String number, String messageId, int msgState, int chatType, in android.app.PendingIntent pendingIntent);
    void msgSetConvStatus(String owner, String convId, String messageId, int convState, int chatType, in android.app.PendingIntent pendingIntent);
    void msgSendFile(String owner, String number, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail, int duration, boolean isBurn, int directedType, boolean needReadReport, String extension, in android.app.PendingIntent sentIntent);
    void msgSendVemoticon(String owner, String number, String messageId, String vemoticonId, String vemoticonName, boolean needReport, boolean isBurn, int directedType, in android.app.PendingIntent sentIntent);
    void msgSendCloudfile(String owner, String number, String messageId, String fileName, String fileSize, String fileUrl, boolean needReport, boolean isBurn, int directedType, in android.app.PendingIntent sentIntent);
    void msgFetchFile(String owner, String number, String messageId, int chatType, String filePath, int contentType, String fileName, String transferId, int start, int fileSize, String hash, boolean isBurn, in android.app.PendingIntent resultIntent);
    void msgHttpFetch(String owner, String messageId, int contentType, String filePath, int start, int fileSize, String originalLink, in android.app.PendingIntent resultIntent);
    void msgPubSendText(String owner, String uri, String messageId, String content, boolean needReport, in android.app.PendingIntent sentIntent);
    void msgPubSendFile(String owner, String uri, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail, in android.app.PendingIntent sentIntent);
    void msgGpSendText(String owner, String groupUri, String messageId, String content, boolean needReport, String ccNumber, boolean needReadReport, String extension, int contentType, in android.app.PendingIntent sentIntent);
    void msgGpSendVemoticon(String owner, String groupUri, String messageId, String vemoticonId, String vemoticonName, boolean needReport, in android.app.PendingIntent sentIntent);
    void msgGpSendCloudfile(String owner, String groupUri, String messageId, String fileName, String fileSize, String fileUrl, boolean needReport, in android.app.PendingIntent sentIntent);
    void msgGpSendFile(String owner, String groupUri, String messageId, String filePath, int contentType, String fileName, boolean needReport, int start, String thumbnail,
     boolean needReadReport, String extension, in android.app.PendingIntent sentIntent);

    void uploadShareFile(String owner, String targetId, String messageId, String filePath, String fileName, int expire, in android.app.PendingIntent sentIntent);
    void fetchShareFile(String owner, String targetId, String messageId, String fileId, String shareFileId, int start, int size, String filePath, in android.app.PendingIntent sentIntent);
    void deleteShareFile(String owner, String targetId, String fileId, String shareFileId, in android.app.PendingIntent sentIntent);
    void getShareFileList(String owner, String targetId, in android.app.PendingIntent sentIntent);
    void cancelTransfer(String owner, String messageId, in android.app.PendingIntent sentIntent);
    void setNotifyRead(String owner, String messageId, in android.app.PendingIntent sentIntent);

    void gpSubList(String owner, String version, in android.app.PendingIntent pendingIntent);
    void gpSubInfo(String owner, String groupUri, String infoVersion, String membersVersion, in android.app.PendingIntent pendingIntent);
    void gpJoin(String owner, String groupUri, String inviter, in android.app.PendingIntent pendingIntent);
    void gpReject(String owner, String groupUri, String inviter, in android.app.PendingIntent pendingIntent);
    void gpCreate(String owner, String resourceList, String subject, String introduce, String bulletin, int groupType, in android.app.PendingIntent pendingIntent);
    void gpInviteMember(String owner, String groupUri, String member, in android.app.PendingIntent pendingIntent);
    void gpApply(String owner, String groupUri, String remark, in android.app.PendingIntent pendingIntent);
    void gpApproval(String owner, String groupUri, String applicant, int handleResult, String replyMsg, in android.app.PendingIntent pendingIntent);

    void gpDelete(String owner, String groupUri, in android.app.PendingIntent pendingIntent);
    void gpExit(String owner, String groupUri, in android.app.PendingIntent pendingIntent);
    void gpSetSubject(String owner, String groupUri, String subject, in android.app.PendingIntent pendingIntent);
    void gpSetIntroduce(String owner, String groupUri, String introduce, in android.app.PendingIntent pendingIntent);
    void gpSetInviteFlag(String owner, String groupUri, int flag, in android.app.PendingIntent pendingIntent);
    void gpSetExtra(String owner, String groupUri, String extra, in android.app.PendingIntent pendingIntent);
    void gpSetBulletin(String owner, String groupUri, String bulletin, in android.app.PendingIntent pendingIntent);
    void gpRemoveMember(String owner, String groupUri, String member, in android.app.PendingIntent pendingIntent);
    void gpAssignAdmin(String owner, String groupUri, String member, in android.app.PendingIntent pendingIntent);
    void gpModifyNickName(String owner, String groupUri, String name, in android.app.PendingIntent pendingIntent);
    void gpSearchGroup(String owner, String subject, in android.app.PendingIntent pendingIntent);

    void gpShareInfo(String owner, String groupUri, in android.app.PendingIntent pendingIntent);
    void gpSetDND(String owner, String groupUri, int flag, in android.app.PendingIntent pendingIntent);

    void getCap(String owner, String number, in android.app.PendingIntent pendingIntent);

    void buddyAdd(String owner, String user, String reason, in android.app.PendingIntent pendingIntent);
    void buddyDel(String owner, int userId, in android.app.PendingIntent pendingIntent);
    void buddyMemo(String owner, int userId, String memo, in android.app.PendingIntent pendingIntent);
    void buddyDnd(String owner, int userId, int dndFlag, in android.app.PendingIntent pendingIntent);
    void buddyAccept(String owner, int userId, in android.app.PendingIntent pendingIntent);
    void buddyRefuse(String owner, int userId, String reason, in android.app.PendingIntent pendingIntent);

    void userGetInfo(String owner, String ids,in android.app.PendingIntent pendingIntent);
    void userGetProfile(String owner, String user,in android.app.PendingIntent pendingIntent);
    void userSetProfile(String owner, String nickname, String impresa, String firstname, String lastName, int gender, String email, String birthday, String clientExtra,in android.app.PendingIntent pendingIntent);
    void userSetCareer(String owner, String company, String position,in android.app.PendingIntent pendingIntent);
    void userSetDndFlag(String owner, int dndFlag,in android.app.PendingIntent pendingIntent);
    void userSetBuddyFlag(String owner, int buddyFlag,in android.app.PendingIntent pendingIntent);
    void userSetPermissionUidFlag(String owner, int uidFlag,in android.app.PendingIntent pendingIntent);
    void userSetPermissionUnameFlag(String owner, int unameFlag,in android.app.PendingIntent pendingIntent);
    void userGetPortrait(String owner, int userId, boolean isSmall, in android.app.PendingIntent pendingIntent);
    void userSetPortrait(String owner, String filePath, in android.app.PendingIntent pendingIntent);

    void stop(String owner);
    void doConnect(String owner);
    void getUserStates(String owner, in android.app.PendingIntent intent);
    void getEndpointList(String owner, in android.app.PendingIntent pendingIntent);
    void bootEndpoint(String owner, String clientId, int clientType, String clientVersion, in android.app.PendingIntent pendingIntent);
    void deleteEndpoint(String owner, String clientId, int clientType, String clientVersion, in android.app.PendingIntent pendingIntent);
    void setEndpointDND(String owner, int flag, String beginTime, String endTime, in android.app.PendingIntent pendingIntent);
    void getConvList(String owner, in android.app.PendingIntent pendingIntent);
    void getConvHistory(String owner, int convType, String convId, int pageLimit, String beginImdnId, in android.app.PendingIntent pendingIntent);
    void subPresence(String owner, String cids, in android.app.PendingIntent pendingIntent);
    void unSubPresence(String owner, String cids, in android.app.PendingIntent pendingIntent);
    void token(String owner, in android.app.PendingIntent pendingIntent);
    void bklistAdd(String owner, String userId, in android.app.PendingIntent pendingIntent);
    void bklistRemove(String owner, String userId, in android.app.PendingIntent pendingIntent);
    void bklistGet(String owner, in android.app.PendingIntent pendingIntent);
    void deviceAdd(String owner, String userId, String password, in android.app.PendingIntent pendingIntent);
    void deviceRemove(String owner, String userId, in android.app.PendingIntent pendingIntent);
    void deviceListGet(String owner, in android.app.PendingIntent pendingIntent);
    void deviceStatusGet(String owner, String userId, in android.app.PendingIntent pendingIntent);
    void msg2Shorturl(String owner,String imdnId, int msgType, String content, String filePath, in android.app.PendingIntent pendingIntent);
    void getPresence(String owner, String contactId, in android.app.PendingIntent pendingIntent);
    void getFileId(String owner, String fileId, in android.app.PendingIntent pendingIntent);
}
