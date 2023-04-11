package com.Server;

import com.Common.Message;
import com.Common.MessageType;
import com.Common.UserInformation;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

//与客户端保持连接
public class ServerConnent extends Thread {
    private Socket socket;
    private String userName;
    public ServerConnent(String userName, Socket socket) {
        this.userName = userName;
        this.socket = socket;
    }
    public Socket getSocket() {
        return socket;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void run() {
        //接受客户端的消息
        try {
            while (true) {
                try {
                    //接受客户端的消息
                    // System.out.println("接收客户端的信息...");
                    ObjectInputStream objectInputstream = new ObjectInputStream(socket.getInputStream());
                    Message message = (Message) objectInputstream.readObject();
                    //发送消息给客户端(获取好友在线列表)
                    if (message.getMessageType().equals(MessageType.message_get_onLineFriend)) {
                        String[] onLineFriend = ServerCollection.getOnLineFriend();
                        System.out.println(message.getSender()+ " 请求在线好友列表");
                        for (String s : onLineFriend) {
                            System.out.println("用户 " + s);
                        }
                        //发送消息给客户端
                        Message messageUser = new Message();
                        messageUser.setMessageType(MessageType.message_ret_onLineFriend);
                        messageUser.setUserlist(onLineFriend);
                        messageUser.setReceiver(message.getSender());
                        //发送消息给客户端
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(messageUser);
                    }
                    //私聊消息，通过服务器端将消息发送给receiver
                    else if (message.getMessageType().equals(MessageType.message_com_mes)) {
                        if (ServerCollection.isOnline(message.getReceiver())) {
                            System.out.println(message.getSender() + " 给 " + message.getReceiver() + " 发送消息：" + message.getContent());
                            //发送消息给客户端
                            Message privateMessage = new Message();
                            privateMessage.setMessageType(MessageType.message_com_mes);
                            privateMessage.setSender(message.getSender());
                            privateMessage.setReceiver(message.getReceiver());
                            privateMessage.setContent(message.getContent());
                            //发送消息给对方客户端
                            ObjectOutputStream objectOutputStream1 =
                                    new ObjectOutputStream(ServerCollection.getServer(message.getReceiver()).getSocket().getOutputStream());
                            objectOutputStream1.writeObject(privateMessage);
                            //发送消息给自己客户端，发送成功
                            Message sendingSuccessMessage = new Message();
                            sendingSuccessMessage.setMessageType(MessageType.message_sending_messsage);
                            sendingSuccessMessage.setSender(message.getSender());
                            String sendingSuccessMessageContent = "发送成功";
                            sendingSuccessMessage.setContent(sendingSuccessMessageContent);
                            //发送消息给客户端发送失败
                            ObjectOutputStream objectOutputStream2 =
                                    new ObjectOutputStream(ServerCollection.getServer(message.getSender()).getSocket().getOutputStream());
                            objectOutputStream2.writeObject(sendingSuccessMessage);
                        } else {
                            System.out.println("该用户不在线或不存在");
                            Message sendingFaliureMessage = new Message();
                            sendingFaliureMessage.setMessageType(MessageType.message_sending_messsage);
                            sendingFaliureMessage.setSender(message.getSender());
                            String sendingFaliureMessageContent = "该用户不在线或不存在，请检查用户名是否正确";
                            sendingFaliureMessage.setContent(sendingFaliureMessageContent);
                            //发送消息给客户端发送失败
                            ObjectOutputStream objectOutputStream =
                                    new ObjectOutputStream(ServerCollection.getServer(message.getSender()).getSocket().getOutputStream());
                            objectOutputStream.writeObject(sendingFaliureMessage);
                        }
                    }
                    //平台聊天消息，通过服务器把消息发送给所有在线用户
                    else if (message.getMessageType().equals(MessageType.message_com_mes_platform)) {
                        //发送消息给客户端
                        Message platformMessage = new Message();
                        platformMessage.setMessageType(MessageType.message_ret_com_mes_platform);
                        platformMessage.setSender(message.getSender());
                        platformMessage.setContent(message.getContent());
                        //发送消息给所有在线用户
                        for (String s : ServerCollection.getOnLineFriend()) {
                            ObjectOutputStream objectOutputStream =
                                    new ObjectOutputStream(ServerCollection.getServer(s).getSocket().getOutputStream());
                            objectOutputStream.writeObject(platformMessage);
                        }
                        System.out.println(message.getSender() + " 艾特所有人：" + message.getContent());
                    }
                    //传送文件给receiver
                    else if(message.getMessageType().equals(MessageType.message_sendingFile)){
                        if (ServerCollection.isOnline(message.getReceiver())) {
                            System.out.println(message.getSender() + " 给 " + message.getReceiver() + " 发送文件：" +message.getDestFilePath());
                            ObjectOutputStream objectOutputStream1 =
                                    new ObjectOutputStream(ServerCollection.getServer(message.getReceiver()).getSocket().getOutputStream());
                          message.setMessageType(MessageType.message_ret_sendingFile);
                           objectOutputStream1.writeObject(message);
                            System.out.println("文件发送成功");
                            System.out.println("字节数："+message.getFileBytes().length);
                            //发送消息给自己客户端，发送成功
                            Message sendingSuccessMessage = new Message();
                            sendingSuccessMessage.setMessageType(MessageType.message_sending_messsage);
                            sendingSuccessMessage.setSender(message.getSender());
                            String sendingSuccessMessageContent = "发送成功";
                            sendingSuccessMessage.setContent(sendingSuccessMessageContent);
                            //发送消息给客户端发送失败
                            ObjectOutputStream objectOutputStream2 =
                                    new ObjectOutputStream(ServerCollection.getServer(message.getSender()).getSocket().getOutputStream());
                            objectOutputStream2.writeObject(sendingSuccessMessage);
                        } else {
                            System.out.println("该用户不在线或不存在");
                            Message sendingFaliureMessage = new Message();
                            sendingFaliureMessage.setMessageType(MessageType.message_sending_messsage);
                            sendingFaliureMessage.setSender(message.getSender());
                            String sendingFaliureMessageContent = "该用户不在线或不存在，请检查用户名是否正确";
                            sendingFaliureMessage.setContent(sendingFaliureMessageContent);
                            //发送消息给客户端发送失败
                            ObjectOutputStream objectOutputStream =
                                    new ObjectOutputStream(ServerCollection.getServer(message.getSender()).getSocket().getOutputStream());
                            objectOutputStream.writeObject(sendingFaliureMessage);

                        }
                    }
                    //服务器端接收用户上下线消息,并将消息发送给所有在线用户
                    else if (message.getMessageType().equals(MessageType.message_ret_userOnLine)) {
                        //发送消息给客户端
                        Message platformMessage = new Message();
                        platformMessage.setMessageType(MessageType.message_ret_userOnLine);
                        platformMessage.setSender(message.getSender());
                        System.out.println(message.getSender() + " 已上线");
                        String[] onLineFriend = ServerCollection.getOnLineFriend();
                        platformMessage.setUserlist(onLineFriend);
                        //发送消息给所有在线用户
                        for (String s :onLineFriend) {
                            ObjectOutputStream objectOutputStream =
                                    new ObjectOutputStream(ServerCollection.getServer(s).getSocket().getOutputStream());
                            objectOutputStream.writeObject(platformMessage);
                         }
                    }
                    //查询个人信息
                    else if (message.getMessageType().equals(MessageType.message_referUserInformation)) {
                        //发送消息给客户端
                        Message requestMessage = new Message();
                        requestMessage.setMessageType(MessageType.message_ret_referUserInformation);
                        requestMessage.setSender(message.getSender());
                        System.out.println(message.getSender() + " 查询个人信息");
                        //从HashMap中获取用户信息
                        requestMessage.setRealName(ServerService.getUserInformation(message.getSender()).getRealName());
                        requestMessage.setBirthPlace(ServerService.getUserInformation(message.getSender()).getBirthPlace());
                        //发送消息给所有在线用户
                        ObjectOutputStream objectOutputStream =
                                new ObjectOutputStream(ServerCollection.getServer(message.getSender()).getSocket().getOutputStream());
                        objectOutputStream.writeObject(requestMessage);
                    }
                    //修改个人信息
                    else if (message.getMessageType().equals(MessageType.message_modifyUserInformation)) {
                        //发送消息给客户端
                        Message requestMessage = new Message();
                        requestMessage.setMessageType(MessageType.message_modifyUserInformation);
                        requestMessage.setSender(message.getSender());
                        requestMessage.setNewUserName(message.getNewUserName());
                        requestMessage.setNewPassword(message.getNewPassword());
                        System.out.println(message.getSender() + " 修改个人信息:");
                        //更新在线用户列表
                        ServerCollection.addServer(message.getNewUserName(),ServerCollection.getServer(message.getSender()));
                        ServerCollection.removeServer(message.getSender());
                        //从HashMap中修改用户信息,删除原来的用户信息，添加新的用户信息
                            ServerService.modifyUserInformation(message.getSender(),message.getNewUserName(),message.getNewPassword());
                        requestMessage.setContent("修改成功");
                        //发送消息给所有在线用户
                        ObjectOutputStream objectOutputStream =
                                new ObjectOutputStream(ServerCollection.getServer(message.getSender()).getSocket().getOutputStream());
                        objectOutputStream.writeObject(requestMessage);
                        //修改成功后，关闭线程，重新登录
                        sleep(1000);
                        socket.close();
                        break;//退出线程
                    }
                    //发送客户端安全退出指令
                    else if (message.getMessageType().equals(MessageType.message_client_exit)) {
                        System.out.println(message.getSender() + " 安全退出");
                        Message platformMessage = new Message();
                        platformMessage.setMessageType(MessageType.message_ret_userOffLine);
                        platformMessage.setSender(message.getSender());
                        System.out.println(message.getSender() + " 已下线");
                        ServerCollection.removeServer(message.getSender());
                        String[] onLineFriend = ServerCollection.getOnLineFriend();
                        platformMessage.setUserlist(onLineFriend);
                        //发送消息给所有在线用户
                        for (String s :onLineFriend) {
                            ObjectOutputStream objectOutputStream =
                                    new ObjectOutputStream(ServerCollection.getServer(s).getSocket().getOutputStream());
                            objectOutputStream.writeObject(platformMessage);
                        }
                        socket.close();
                        break;//退出线程

                    }
                    else {
                        System.out.println("无用输出");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //发送消息给客户端

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

