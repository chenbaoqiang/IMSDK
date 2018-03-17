package com.feinno.sdk.args;

import android.text.TextUtils;
import com.feinno.sdk.enums.ContentType;

import java.io.File;

/**
 * 传输文件时的消息参数
 */
public class FTMessageArg extends MessageArg {
    protected File file;
    protected String fileName;
    protected String filePath;
    protected int start;
    protected String thumbnail;
    protected int duration;
	protected byte[] content;

    @Override
    public String toString() {
        return "[filePath=" + filePath + ",start=" + start + ",thumbnailPath=" + thumbnail + ",messageID=" + messageID +
                ", messageTo=" + chatUri.getUri() + ", contentType=" + contentType + ", needReport=" + needReport + "]";
    }

    /**
     * 构造一个用于文件传输的消息参数
     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
     * @param to 消息接收者
     * @param contentType 消息类型，参见{@link ContentType}
     * @param file 要传输的文件
	 * @param start 起始位置
	 * @param needReport 是否需要送达回执报告
     * @param thumbnail 文件缩略图路径
     */
    public FTMessageArg(String messageId, ChatUri to, ContentType contentType, File file, int start, boolean needReport, String thumbnail) {
        this(messageId, to, contentType, file, start, needReport, thumbnail, 0, false, null);
    }

    /**
     * 构造一个用于文件传输的消息参数
     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
     * @param to 消息接收者
     * @param contentType 消息类型，参见{@link ContentType}
     * @param file 要传输的文件
     * @param start 起始位置
     * @param needReport 是否需要送达回执报告
     * @param thumbnail 文件缩略图路径
     * @param extension 扩展字段（由客户端自定义,服务端透传）
     */
    public FTMessageArg(String messageId, ChatUri to, ContentType contentType, File file, int start, boolean needReport, String thumbnail, String extension) {
        this(messageId, to, contentType, file, start, needReport, thumbnail, 0, false, extension);
    }

    /**
     * 构造一个用于文件传输的消息参数
     */
    public FTMessageArg(String messageId, ChatUri to, ContentType contentType, File file, int start, boolean needReport, String thumbnail, boolean needReadReport, String extension) {
        //this.messageTo = to;
        this(messageId, to, contentType, file, start, needReport, thumbnail, 0, needReadReport, extension);
    }

    /**
     * 构造一个用于文件传输的消息参数
     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
     * @param to 消息接收者
     * @param contentType 消息类型，参见{@link ContentType}
     * @param file 要传输的文件
     * @param start 起始位置
     * @param needReport 是否需要送达回执报告
     * @param thumbnail 文件缩略图路径
     * @param needReadReport 是否需要阅读回执报告
     * @param duration 视频时长
     * @param extension 扩展字段（由客户端自定义,服务端透传）
     */
    public FTMessageArg(String messageId, ChatUri to, ContentType contentType, File file, int start, boolean needReport, String thumbnail, int duration, boolean needReadReport, String extension) {
        //this.messageTo = to;
        this.chatUri = to;
        this.file = file;
        this.contentType = contentType;
        this.start = start;
        this.needReport = needReport;
        this.thumbnail = thumbnail;
        this.needReadReport = needReadReport;
        this.extension = extension;
        this.duration = duration;
        if (TextUtils.isEmpty(messageId)) {
            this.messageID = String.valueOf(System.currentTimeMillis());
        } else {
            this.messageID = messageId;
        }

        if (file != null) {
            this.fileName = file.getName();
            this.filePath = file.getAbsolutePath();
        }
    }

//    /**
//     * 构造一个用于文件传输的消息参数
//     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
//     * @param to 消息接收者
//     * @param contentType 消息类型，参见{@link ContentType}
//     * @param file 要传输的文件
//     * @param fileName 要传输的文件名称
//     * @param start 起始位置
//     * @param needReport 是否需要回执报告
//     * @param thumbnail 文件缩略图路径
//     */
//    public FTMessageArg(String messageId, ChatUri to, ContentType contentType, File file, String fileName, int start, boolean needReport, String thumbnail) {
//        //this.messageTo = to;
//        this.chatUri = to;
//        this.file = file;
//        this.contentType = contentType;
//        this.start = start;
//        this.needReport = needReport;
//        this.thumbnail = thumbnail;
//        if (TextUtils.isEmpty(messageId)) {
//            this.messageID = String.valueOf(System.currentTimeMillis());
//        } else {
//            this.messageID = messageId;
//        }
//
//        if(TextUtils.isEmpty(fileName)){
//            if (file != null) {
//                this.fileName = file.getName();
//            }
//        } else {
//            this.fileName = fileName;
//        }
//        if(file != null){
//            this.filePath = file.getAbsolutePath();
//        }
//    }

	/**
	 * 构造一个用于文件传输的消息参数
	 * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
	 * @param to 消息接收者
	 * @param contentType 消息类型，参见{@link ContentType}
	 * @param fileName 文件名称
     * @param filePath 文件路径
	 * @param start 起始位置
	 * @param needReport 是否需要回执报告
	 * @param thumbnail 文件缩略图路径
	 */
	protected FTMessageArg(String messageId, ChatUri to, ContentType contentType, String fileName, String filePath, int start, boolean needReport, String thumbnail) {
       this(messageId, to, contentType, fileName, filePath, start, needReport, thumbnail, 0, false, null);
	}

    /**
     * 构造一个用于文件传输的消息参数
     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
     * @param to 消息接收者
     * @param contentType 消息类型，参见{@link ContentType}
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param start 起始位置
     * @param needReport 是否需要回执报告
     * @param thumbnail 文件缩略图路径
     * @param extension 扩展字段（由客户端自定义,服务端透传）
     */
    protected FTMessageArg(String messageId, ChatUri to, ContentType contentType, String fileName, String filePath, int start, boolean needReport, String thumbnail, String extension) {
        this(messageId, to, contentType, fileName, filePath, start, needReport, thumbnail, 0, false, extension);
    }

    /**
     * 构造一个用于文件传输的消息参数
     */
    protected FTMessageArg(String messageId, ChatUri to, ContentType contentType, String fileName, String filePath, int start, boolean needReport, String thumbnail,  boolean needReadReport, String extension) {
        this(messageId, to, contentType, fileName, filePath, start, needReport, thumbnail, 0, needReadReport, extension);
    }

    /**
     * 构造一个用于文件传输的消息参数
     * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
     * @param to 消息接收者
     * @param contentType 消息类型，参见{@link ContentType}
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param start 起始位置
     * @param needReport 是否需要送达回执报告
     * @param thumbnail 文件缩略图路径
     * @param needReadReport 是否需要已读回执报告
     * @param extension 扩展字段（由客户端自定义,服务端透传）
     */
    protected FTMessageArg(String messageId, ChatUri to, ContentType contentType, String fileName, String filePath, int start, boolean needReport, String thumbnail, int duration, boolean needReadReport, String extension) {
        this.chatUri = to;
        this.contentType = contentType;
        this.needReport = needReport;
        this.fileName = fileName;
        this.filePath = filePath;
        this.start = start;
        this.thumbnail = thumbnail;
        this.needReadReport = needReadReport;
        this.duration = duration;
        this.extension = extension;
        if (TextUtils.isEmpty(messageId)) {
            this.messageID = String.valueOf(System.currentTimeMillis());
        } else {
            this.messageID = messageId;
        }
    }

    @Override
    public Object getPayload() {
        return content;
    }

    public int getStart() {
        return start;
    }

	public String getThumbnail(){
		return thumbnail;
	}

    public  String getFileName() {
        return fileName;
    }

	public String getFilePath() {
		return filePath;
	}

	public byte[] getContent() {
		return content;
	}

    public File getFile() {
        return file;
    }

    public int getDuration(){
        return duration;
    }
}
