package com.Server;

import com.Common.UserInformation;

import java.util.Arrays;
import java.util.HashMap;

//和客户端保持连接线程的集合
public class ServerCollection {
    private static HashMap<String, ServerConnent> servers = new HashMap<>();

    public static void addServer(String userName, ServerConnent serverConnent) {
        servers.put(userName, serverConnent);//将客户端的信息保存在集合中
    }
//获取realName和birthPlace

    public static ServerConnent getServer(String userName) {//获取客户端的信息
        return servers.get(userName);
    }



    public static void removeServer(String userName) {//移除客户端的信息
        servers.remove(userName);
    }
    public static String[] getOnLineFriend() {
        String[] onLineFriend = new String[servers.size()];
        int i = 0;
        for (String s : servers.keySet()) {
            onLineFriend[i] = s;
            i++;

        }
        System.out.println("在线好友列表：" + Arrays.toString(onLineFriend));
        return onLineFriend;
    }

    public static boolean isOnline(String receiver) {
        boolean flag = false;
        for (int i = 0; i < servers.size(); i++) {
            if (receiver.equals(servers.keySet().toArray()[i])) {
                flag = true;
            }
        }
        return flag;
    }

}
