package com.Common;

public interface MessageType {
    String message_login = "0";
    String message_succeed = "1";
    String message_login_fail = "2";
    String message_com_mes = "3";
    String message_get_onLineFriend = "4";
    String message_ret_onLineFriend = "5";
    String message_client_exit = "6";
    String message_sending_messsage = "7";//发送失败
    String message_com_mes_platform = "8";//平台聊天
    String message_ret_com_mes_platform = "9";//返回平台聊天
    String message_sendingFile = "10";//发送文件
    String message_ret_sendingFile = "11";//返回发送文件
    String message_register_succeed = "12";//注册成功
    String message_ret_userOnLine = "13";//用户上线
    String message_ret_userOffLine = "14";//用户下线
    String message_referUserInformation = "15";//请求用户信息
    String message_ret_referUserInformation = "16";//返回用户信息
    String message_modifyUserInformation = "17";//修改用户信息

}
