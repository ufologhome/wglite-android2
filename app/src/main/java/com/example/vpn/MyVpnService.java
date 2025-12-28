package com.example.vpn;

import android.net.VpnService;
import android.os.ParcelFileDescriptor;

public class MyVpnService extends VpnService {

    private ParcelFileDescriptor tun;

    @Override
    public void onCreate() {
        Builder builder = new Builder();
        builder.addAddress("10.0.0.2", 32);
        builder.addRoute("0.0.0.0", 0);
        builder.setMtu(1400);

        tun = builder.establish();

        new Thread(new TunnelThread(tun.getFd(), "192.168.0.150", 51820)).start();
    }

    @Override
    public void onDestroy() {
        try { if(tun!=null) tun.close(); } catch(Exception e){}
        super.onDestroy();
    }
}
