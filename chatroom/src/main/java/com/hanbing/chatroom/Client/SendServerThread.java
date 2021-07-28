package com.hanbing.chatroom.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SendServerThread extends Thread {

    Socket ssocket;

    public SendServerThread(Socket s) {
        ssocket = s;
    }
    
    public void run() {
        try {
            while(true){
                //获取客户端的输出流
                OutputStream out = ssocket.getOutputStream();
                //从键盘中输入信息
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

                String msg = null;
                msg = br.readLine()+"\r\n";
                // String s = new String(msg.getBytes("iso8859-1"),"UTF-8");
                out.write(msg.getBytes());
                out.flush();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
