package com.feinno.sdk.args;

import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.DirectedType;

import java.util.ArrayList;
import java.util.List;

public class ChatUri {

    private ChatType mChatType = ChatType.SINGLE;
    private String mUri;
    private List<String> mUriList;
    private DirectedType mDirectedType = DirectedType.NONE;

    public ChatUri() {}

    /**
     * 构建一个一对一消息，群组消息，公众消息或者回执消息的ChatUri
     * @param chatType {@link ChatType} 聊天类型
     * @param uri 接收方uri
     */
    public ChatUri(ChatType chatType, String uri) {
        this.mChatType = chatType;
        this.mUri = uri;
        this.mUriList = new ArrayList<String>();
        mUriList.add(uri);
    }

    /**
     * 构建一个广播消息（一对多）业务使用的ChatUri
     * @param uriList 号码的list
     */
    public ChatUri(List<String> uriList) {
        this.mChatType = ChatType.BROADCAST;
        this.mUriList = uriList;
    }

    /**
     * 构建一个定向消息使用的ChatUri
     * @param type 定向类型，参见{@link DirectedType}
     */
    public ChatUri(DirectedType type) {
        this.mChatType = ChatType.DIRECTED;
        this.mDirectedType = type;
    }

    public ChatType getChatType() {
        return mChatType;
    }

    public String getUri() {
        return mUri;
    }

    public List<String> getUriList() {
        return mUriList;
    }

    public DirectedType getmDirectedType() {
        return mDirectedType;
    }
}
