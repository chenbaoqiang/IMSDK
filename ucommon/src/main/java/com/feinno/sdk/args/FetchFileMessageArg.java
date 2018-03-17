package com.feinno.sdk.args;

import android.text.TextUtils;

import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.ContentType;
import com.feinno.sdk.session.FTMessageSession;

import java.io.File;

/**
 * 拉取文件时的消息参数
 */
public class FetchFileMessageArg extends MessageArg {
	private File file;
    private String fileName;
    private String filePath;
    private int start;
	private String transferId;
	private int fileSize;
	private String fileHash;
	private ChatType chatType;
	private String originalLink;

    @Override
    public String toString() {
        return "FetchFileMessageArg{" +
                "file=" + file +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", start=" + start +
                ", transferId='" + transferId + '\'' +
                ", fileSize=" + fileSize +
                ", fileHash='" + fileHash + '\'' +
                ", chatType=" + chatType + '\'' +
				", originalLink=" + originalLink + '\'' +
                "} " + super.toString();
    }

	/**
	 * 用于拉取文件的参数
	 * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
	 * @param to 消息接收者 {@link ChatUri}
	 * @param contentType 消息内容类型，参见{@link ContentType}
	 * @param file 文件
	 * @param transferId 文件传输的ID，可以通过{@link FTMessageSession#transferId}获得
	 * @param start 起始位置
	 * @param fileSize 文件大小
	 * @param fileHash 文件哈希值
	 */
	public FetchFileMessageArg(String messageId, ChatUri to, ContentType contentType, File file, String transferId, int start, int fileSize, String fileHash) {
		this.chatUri = to;
		this.contentType = contentType;
		this.transferId = transferId;
		this.start = start;
		this.fileSize = fileSize;
		this.fileHash = fileHash;
		if (TextUtils.isEmpty(messageId)) {
			this.messageID = String.valueOf(System.currentTimeMillis());
		} else {
			this.messageID = messageId;
		}

		this.file = file;
		if (file != null) {
			this.fileName = file.getName();
			this.filePath = file.getAbsolutePath();
		}
	}

	/**
	 * 用于下载一个文件的参数（用于下载公众帐号发送的文件http链接）
	 * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
	 * @param contentType 消息内容类型，参见{@link ContentType}
	 * @param file 文件
	 * @param start 起始位置
	 * @param fileSize 文件大小
	 */
	public FetchFileMessageArg(String messageId, ContentType contentType, File file, int start, int fileSize, String originalLink) {
		this.contentType = contentType;
		this.start = start;
		this.fileSize = fileSize;
		if (TextUtils.isEmpty(messageId)) {
			this.messageID = String.valueOf(System.currentTimeMillis());
		} else {
			this.messageID = messageId;
		}

		this.file = file;
		if (file != null) {
			this.fileName = file.getName();
			this.filePath = file.getAbsolutePath();
		}
		this.originalLink = originalLink;
	}

	/***
	 * 用于拉取文件的参数
	 * @param messageId 消息ID，唯一标记一条消息，推荐使用UUID
	 * @param transferId 文件传输的ID，可以通过{@link FTMessageSession#transferId}获得
	 * @param filePath 本地存储路径
	 * @param start 起始位置
     * @param fileSize 文件大小
     */
	public FetchFileMessageArg(String messageId, String transferId, String filePath, int start, int fileSize) {
		this.chatUri = new ChatUri();
		this.start = start;
		this.fileSize = fileSize;
		if (TextUtils.isEmpty(messageId)) {
			this.messageID = String.valueOf(System.currentTimeMillis());
		} else {
			this.messageID = messageId;
		}

		this.transferId = transferId;
		this.filePath = filePath;
	}

    public int getStart() {
        return start;
    }

    @Override
    public Object getPayload() {
        return filePath;
    }

    public  String getFileName() {
        return fileName;
    }

	public String getTransferId() {
		return transferId;
	}

	public int getFileSize() {
		return fileSize;
	}

	public String getFileHash() {
		return fileHash;
	}

	public ChatType getChatType() {
		return chatType;
	}

	public String getFilePath() {
		return filePath;
	}

	public File getFile() {
		return file;
	}

	public String getOriginalLink() { return originalLink; }
}
