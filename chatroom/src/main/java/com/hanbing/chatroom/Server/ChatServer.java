package com.hanbing.chatroom.Server;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatServer {
    int port;
    ServerSocket server;
    public static Map<Socket, ConcurrentLinkedQueue<String>>allSocketQueue = new ConcurrentHashMap<Socket, ConcurrentLinkedQueue<String>>();

    public static void main(String[] args) {
        new ChatServer();
    }

    public ChatServer() {
        try {

            port = 12345;
            server = new ServerSocket(port);

            while (true) {
                Socket socket = server.accept();
                ConcurrentLinkedQueue<String> receiveQueue = new ConcurrentLinkedQueue<>();
                ConcurrentLinkedQueue<String> sendQueue = new ConcurrentLinkedQueue<>();
                allSocketQueue.put(socket, sendQueue);

                ReceiveClientThread receiveThread = new ReceiveClientThread(socket, receiveQueue);
                MsgProcessThread msgThread = new MsgProcessThread(socket, receiveQueue);
                SendClientThread sendThread = new SendClientThread(socket);
                receiveThread.start();
                msgThread.start();
                sendThread.start();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   
}
