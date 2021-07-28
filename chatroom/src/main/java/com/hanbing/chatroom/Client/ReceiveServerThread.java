package com.hanbing.chatroom.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveServerThread extends Thread {

    Socket ssocket;

    public ReceiveServerThread(Socket s) {
        ssocket = s;
    }
 
    public void run() {
        try {
            //获取客户端输入流
            BufferedReader bufferinput;
            InputStream input = ssocket.getInputStream();
            bufferinput = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String line;
        
            while(true){
                //获取消息
                if((line=bufferinput.readLine())!=null){
                    System.out.println(line);
                    if(line.equals("You have quited.")){
                        ssocket.close();
                        //终止程序
                        System.exit(0);
                        return;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
