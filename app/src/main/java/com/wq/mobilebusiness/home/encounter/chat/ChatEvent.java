package com.wq.mobilebusiness.home.encounter.chat;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;

/**
 * Created by 王铨 on 2016/4/16.
 */
public class ChatEvent {
    private AVIMMessage message;
    private AVIMConversation conversation;
    private AVIMClient client;

    public ChatEvent(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        this.message = message;
        this.conversation = conversation;
        this.client = client;
    }

    public AVIMMessage getMessage() {
        return message;
    }

    public void setMessage(AVIMMessage message) {
        this.message = message;
    }

    public AVIMConversation getConversation() {
        return conversation;
    }

    public void setConversation(AVIMConversation conversation) {
        this.conversation = conversation;
    }

    public AVIMClient getClient() {
        return client;
    }

    public void setClient(AVIMClient client) {
        this.client = client;
    }
}
