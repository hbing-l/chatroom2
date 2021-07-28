package com.hanbing.chatroom.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SendClientThread extends Thread {
    
    Socket ssocket;

    public SendClientThread(Socket s) {
        ssocket = s;
    }

    public void run() {
        try {
            while(true){
                String msg = null;
                msg = ChatServer.allSocketQueue.get(ssocket).poll();
                
                if(msg == null){
                    continue;
                }
                
                OutputStream out = ssocket.getOutputStream();

                msg += "\r\n";
                out.write(msg.getBytes());
                out.flush();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
