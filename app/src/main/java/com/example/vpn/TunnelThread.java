package com.example.vpn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TunnelThread implements Runnable {

    private int tunFd;
    private String serverIp;
    private int serverPort;

    public TunnelThread(int tunFd, String serverIp, int serverPort) {
        this.tunFd = tunFd;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        try {
            FileInputStream fis = new FileInputStream(tunFd);
            FileOutputStream fos = new FileOutputStream(tunFd);
            DatagramSocket udp = new DatagramSocket();

            byte[] buf = new byte[2048];
            while(true){
                int len = fis.read(buf);
                if(len>0){
                    DatagramPacket p = new DatagramPacket(buf, len, InetAddress.getByName(serverIp), serverPort);
                    udp.send(p);
                }
                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                udp.receive(recv);
                fos.write(recv.getData(), 0, recv.getLength());
            }

        } catch(Exception e){ e.printStackTrace(); }
    }
}
