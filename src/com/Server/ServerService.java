package com.Server;
import com.Server.ServerConnent;
import com.Common.Message;
import com.Common.MessageType;
import com.Common.UserInformation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class ServerService {
    public static void main(String[]args){
        ServerService serverService = new ServerService();
        serverService.serverService();
    }
    private ServerSocket serverSocket;
    private final HashMap<String, UserInformation> serverCollection;

    public ServerService() {
        serverCollection = new HashMap<>();
        serverCollection.put("bili", new UserInformation("bili", "bili"));
        serverCollection.put("123", new UserInformation("bili", "123"));
        serverCollection.put("456", new UserInformation("bili", "456"));
        serverCollection.put("789", new UserInformation("bili", "789"));
    }

    public boolean checkUser(String userName, String password) {
        UserInformation userInformation = serverCollection.get(userName);
        return userInformation != null && userInformation.getPassword() != null &&
                userInformation.getPassword().equals(password);
    }

    public void serverService() {
        System.out.println("服务器运行...");
        try {
            serverSocket = new ServerSocket(9999);
            while (true) {
                System.out.println("等待客户端连接...");
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                // 接收客户端的消息
                UserInformation object = (UserInformation) objectInputStream.readObject();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();
                System.out.print("用户名："+object.getUserName()+"密码："+object.getPassword()+" ");
                if (checkUser(object.getUserName(), object.getPassword())) {
                    System.out.println("登录成功");
                    message.setMessageType(MessageType.message_succeed);
                    objectOutputStream.writeObject(message);
                    ServerConnent serverConnent = new ServerConnent(object.getUserName(), socket);
                    serverConnent.start();
                    // 将线程加入集合
                    ServerCollection.addServer(object.getUserName(), serverConnent);
                } else {
                    System.out.println("登录失败");
                    message.setMessageType(MessageType.message_login_fail);
                    objectOutputStream.writeObject(message);
                    socket.close(); // 关闭连接
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

