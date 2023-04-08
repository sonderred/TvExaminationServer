package com.Server;

import java.util.HashMap;

//和客户端保持连接线程的集合
public class ServerCollection {
    private static HashMap<String, ServerConnent> servers = new HashMap<>();
    public static void addServer(String userName, ServerConnent serverConnent) {
        servers.put(userName, serverConnent);//将客户端的信息保存在集合中
    }
    public static ServerConnent getServer(String userName) {//获取客户端的信息
        return servers.get(userName);
    }
    public static void removeServer(String userName) {//移除客户端的信息
        servers.remove(userName);
    }
}
