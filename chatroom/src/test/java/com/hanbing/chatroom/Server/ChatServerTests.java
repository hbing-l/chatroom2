package com.hanbing.chatroom.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChatServerTests {
    Socket socket;

    @BeforeEach
    public void beforeTest() {
        socket = mock(Socket.class);

    }
    
    @Test
    public void loginMsgTest() throws IOException{
      
        ConcurrentLinkedQueue<String> receiveQueue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<String> sendQueue = new ConcurrentLinkedQueue<>();
        ChatServer.allSocketQueue.put(socket, sendQueue);

        MsgProcessThread msgThread = new MsgProcessThread(socket, receiveQueue);
    
        // ByteArrayOutputStream out = new ByteArrayOutputStream();
        // when(socket.getOutputStream()).thenReturn(out);

        msgThread.selectMsg("/login a");
        
        String msg = ChatServer.allSocketQueue.get(socket).poll();

        assertEquals(msg, "You have logined");

    }

    @Test
    public void sayHiTest() throws IOException{

        ConcurrentLinkedQueue<String> receiveQueue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<String> sendQueue = new ConcurrentLinkedQueue<>();
        ChatServer.allSocketQueue.put(socket, sendQueue);

        MsgProcessThread msgThread = new MsgProcessThread(socket, receiveQueue);
        msgThread.selectMsg("//hi");

        String msg = ChatServer.allSocketQueue.get(socket).poll();
        assertEquals(msg, "你向大家打招呼，“Hi，大家好！我来咯~”");

    }

    @Test 
    public void sayHi2OtherTest() throws IOException {

        Socket bsocket = mock(Socket.class);

        ConcurrentLinkedQueue<String> asendQueue = new ConcurrentLinkedQueue<>();
        ChatServer.allSocketQueue.put(socket, asendQueue);

        ConcurrentLinkedQueue<String> receiveQueue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<String> bsendQueue = new ConcurrentLinkedQueue<>();
        ChatServer.allSocketQueue.put(bsocket, bsendQueue);

        MsgProcessThread msgThread = new MsgProcessThread(socket, receiveQueue);
        
        MsgProcessThread.userDB.put("a", socket);
        MsgProcessThread.userDB.put("b", bsocket);

        msgThread.selectMsg("//hi");
        String bmsg = ChatServer.allSocketQueue.get(bsocket).poll();
        assertEquals(bmsg, "a向大家打招呼，“Hi，大家好！我来咯~”");

        MsgProcessThread.userDB.remove("a");
        MsgProcessThread.userDB.remove("b");

    }

}
