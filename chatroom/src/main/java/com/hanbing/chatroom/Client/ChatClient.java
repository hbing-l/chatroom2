package com.hanbing.chatroom.Client;
import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatClient {

    public int port = 12345;
    Socket socket = null;

    public static void main(String[] args) {
        new ChatClient();
    }

    public ChatClient() {

        try {
            socket = new Socket("localhost", port);
            //第一步 登录
            System.out.println("Please login");
            login();
           
            //登录成功后开启用户接收和发送线程
            SendServerThread send = new SendServerThread(socket);
            ReceiveServerThread receive = new ReceiveServerThread(socket);

            send.start();
            receive.start();
            
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(){

        try{

            while(true){

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
                String msg = null;
                msg = br.readLine();

                String pLogin = "/login\\s+(\\w+)";
                String pQuit = "/quit";

                Pattern patternLogin = Pattern.compile(pLogin);
                Pattern patternQuit = Pattern.compile(pQuit);

                Matcher matcherLogin = patternLogin.matcher(msg);
                Matcher matcherQuit = patternQuit.matcher(msg);

                if(matcherLogin.matches()){
                    msg += "\r\n";
                    OutputStream out = socket.getOutputStream();
                    out.write(msg.getBytes());
                    out.flush();
                    break;
                }else if(matcherQuit.matches()){
                    socket.close();
                    //终止程序
                    System.exit(0);
                    return;
                }else{
                    System.out.println("Invalid command");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}