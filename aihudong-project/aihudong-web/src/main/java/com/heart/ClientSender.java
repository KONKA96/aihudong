package com.heart;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.servlet.http.HttpServletRequest;

public class ClientSender {
	private ClientSender() {
    }

    Socket sender = null;
    private static ClientSender instance;

    public static ClientSender getInstance() {
        if (instance == null) {
            synchronized (ClientHeart.class) {
                instance = new ClientSender();
            }
        }
        return instance;
    }

    public void send() {
        try {
            sender = new Socket(InetAddress.getLocalHost(), 12306);
            while (true) {
                ObjectOutputStream out = new ObjectOutputStream(sender.getOutputStream());
                /*Err obj = new Err();
                obj.setErrcode(1001);
                obj.setMsg("O(∩_∩)O");*/
                String aaa=new String("O(∩_∩)O");
                out.writeObject(aaa);
                out.flush();

                System.out.println("已发送...");
                Thread.sleep(5000);
            }
        } catch (Exception e) {

        }
    }
   
}
