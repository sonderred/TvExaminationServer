package com.Server;

import com.Common.Message;
import java.io.ObjectInputStream;
import java.net.Socket;

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
        while (true) {
            try {
                //接受客户端的消息
                System.out.println("接收客户端的信息...");
                ObjectInputStream objectInputstream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputstream.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //发送消息给客户端
        }
    }
}
