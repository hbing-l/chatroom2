package com.hanbing.chatroom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReceiveClientThread extends Thread {
    
    Socket ssocket;
    private ConcurrentLinkedQueue<String> rQueue;

    public ReceiveClientThread(Socket s, ConcurrentLinkedQueue<String> receiveQueue) {
        ssocket = s;
        rQueue = receiveQueue;
    }

    public void run() {

        //获取客户端输入流
        BufferedReader bufferinput = null;
        InputStream input;
        
        while(true){
       
            try {
                input = ssocket.getInputStream();
                bufferinput = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            try {
                String line = null;
                line = bufferinput.readLine();
                rQueue.offer(line);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
                   
        }
        
    }   

}
