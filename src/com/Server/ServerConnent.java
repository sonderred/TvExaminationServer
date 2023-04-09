package com.Server;

import com.Common.Message;
import com.Common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                    } else {
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

