package com.feinno.sdk.api;

/**
 * 该类提供消息的相关接口
 */
@Deprecated
public class Message {
//    /**
//     * 发送一条消息，包括文本消息，文件等
//     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
//     * @param arg 消息参数，参见{@link MessageArg}
//     * @param cb 用于回调的Callback接口
//     */
//    public static void sendMessage(Sdk.SdkState sdkState, MessageArg arg, Callback<MessageSession> cb) {
//        if (arg instanceof GroupTextMessageArg || arg instanceof GroupFTMessageArg) {
//            sendGroupChat(sdkState, arg, cb);
//        }
//        else if(arg instanceof TransientMessageArg) {
//            LogUtil.i("TransientMessageArg", "TransientMessageArg");
//            TransientMessageArg transientArg = (TransientMessageArg) arg;
//            MessageArg tmpArg = transientArg.getInnerMessage();
//            sendChat(sdkState, tmpArg, true, cb);
//        }
//        else {
//            sendChat(sdkState, arg, false, cb);
//        }
//    }
//
//    private static int sendChat(Sdk.SdkState sdkState, MessageArg arg, boolean isTransient, Callback<MessageSession> cb) {
//        int ret = -1;
////        if (arg instanceof ReportMessageArg) {
////            ReportMessageArg reportArg = (ReportMessageArg) arg;
////            ret = SdkApi.pmSendReport(sdkState,
////                    reportArg.getMessageTo(),
////                    reportArg.getMessageID(),
////                    reportArg.getImdnID(),
////                    isTransient,
////                    reportArg.getReportType().value(),
////                    reportArg.getReportValue().value(),
////                    cb);
////        } else
//        if (arg instanceof TextMessageArg) {
//            TextMessageArg textArg = (TextMessageArg) arg;
//            ret = SdkApi.msgSendText(sdkState,
//                    textArg.getMessageTo(),
//                    textArg.getMessageID(),
//                    textArg.getContent(),
//                    textArg.needReport(),
//                    isTransient,
//                    "",
//                    cb);
//        } else if (arg instanceof BroadcastMessageArg) {
//            BroadcastMessageArg textArg = (BroadcastMessageArg) arg;
//            ret = SdkApi.msgSendText(sdkState,
//                    textArg.getMessageTo(),
//                    textArg.getMessageID(),
//                    textArg.getContent(),
//                    textArg.needReport(),
//                    isTransient,
//                    textArg.getRecipients(),
//                    cb);
//        } else if (arg instanceof FTMessageArg) {
//            FTMessageArg ftArg = (FTMessageArg) arg;
//            ret = SdkApi.msgSendFile(sdkState,
//                    ftArg.getMessageTo(),
//                    ftArg.getMessageID(),
//                    ftArg.getFilePath(),
//                    String.valueOf(ftArg.getContentType().value()),
//                    ftArg.getFileName(),
//                    ftArg.needReport(),
//                    ftArg.isFile(),
//                    ftArg.getStart(),
//                    isTransient,
//					ftArg.getThumbnail(),
//                    0,
//                    cb);
//        }
//        return ret;
//    }
//
//    private static void sendGroupChat(Sdk.SdkState sdkState, MessageArg arg, Callback<MessageSession> cb) {
//        if (arg instanceof GroupTextMessageArg) {
//            GroupTextMessageArg textArg = (GroupTextMessageArg) arg;
//            SdkApi.gpsend(sdkState, textArg.getMessageTo(),
//                    "",
//                    "",
//                    textArg.getMessageID(),
//                    textArg.getContent(),
//                    String.valueOf(textArg.getContentType().value()),
//                    textArg.needReport(),
//                    "",
//                    cb);
//        } else if (arg instanceof GroupFTMessageArg) {
//            GroupFTMessageArg ftArg = (GroupFTMessageArg) arg;
//            SdkApi.gpft(sdkState, ftArg.getMessageTo(),
//                    "",
//                    "",
//                    ftArg.getMessageID(),
//                    ftArg.getFilePath(),
//                    String.valueOf(ftArg.getContentType().value()),
//                    ftArg.getFileName(),
//                    ftArg.needReport(),
//                    true,
//                    ftArg.getStart(),
//                    ftArg.getThumbnail(),
//                    cb);
//        } else {
//
//        }
//    }
//
//	/**
//	 * 断点下载文件，包括图片，视频等文件
//	 * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
//	 * @param arg 消息参数，参见{@link MessageArg}
//	 * @param cb 用于回调的Callback接口
//	 */
//	public static void fetchFile(Sdk.SdkState sdkState, MessageArg arg, Callback<MessageSession> cb) {
//		if (arg instanceof FetchFileMessageArg) {
//            FetchFileMessageArg ftArg = (FetchFileMessageArg) arg;
//			SdkApi.msgFetchFile(sdkState,
//					ftArg.getMessageID(),
//					ftArg.getMessageTo(),
//					ftArg.getChatType().value(),
//                    "",
//					String.valueOf(ftArg.getContentType().value()),
//					ftArg.getFileName(),
//					ftArg.getTransferId(),
//					ftArg.getStart(),
//					ftArg.getFileSize(),
//					ftArg.getFileHash(),
//					false,
//					cb);
//
//		} else if(arg instanceof TransientMessageArg){
//			TransientMessageArg transientArg = (TransientMessageArg) arg;
//			MessageArg tmpArg = transientArg.getInnerMessage();
//			if (tmpArg instanceof FetchFileMessageArg) {
//                FetchFileMessageArg ftArg = (FetchFileMessageArg) tmpArg;
//				SdkApi.msgFetchFile(sdkState,
//						ftArg.getMessageID(),
//						ftArg.getMessageTo(),
//						ftArg.getChatType().value(),
//                        "",
//						String.valueOf(ftArg.getContentType().value()),
//						ftArg.getFileName(),
//						ftArg.getTransferId(),
//						ftArg.getStart(),
//						ftArg.getFileSize(),
//						ftArg.getFileHash(),
//						true,
//						cb);
//			}
//		}
//	}
}
