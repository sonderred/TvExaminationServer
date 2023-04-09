package com.Common;

import java.io.Serializable;

//编写用户信息
    public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender;
    private String receiver;
    private String content;//消息内容
    private String[] userlist;
    private String messageType;//定义消息类型
    private String sendTime;//发送时间

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public String getMessageType() {
        return messageType;
    }


    public void setUserlist(String[] userlist) {
        this.userlist = userlist;
    }

    public String[] getUserlist() {
        return userlist;
    }
}