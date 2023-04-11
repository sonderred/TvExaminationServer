package com.Common;

import java.io.Serializable;

//编写用户信息
    public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender;//发送者
    private String receiver;//接收者
    private String content;//消息内容
    private String[] userlist;//用户列表
    private String messageType;//定义消息类型
    private String sendTime;//发送时间
    private String srcFilePath;//源文件路径
    private String destFilePath;//目标文件路径
    private byte[] fileBytes;//文件数据
    private String realName;//真实姓名
    private String birthPlace;//出生地
    private String newUserName;//用户名
    private String newPassword;//密码
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

    public String getSrcFilePath() {
        return srcFilePath;
    }

    public String getDestFilePath() {
        return destFilePath;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setSrcFilePath(String srcFilePath) {
        this.srcFilePath = srcFilePath;
    }

    public void setDestFilePath(String destFilePath) {
        this.destFilePath = destFilePath;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
    public String getRealName() {
        return realName;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNewUserName() {
        return newUserName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}