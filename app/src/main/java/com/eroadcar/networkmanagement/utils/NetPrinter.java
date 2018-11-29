package com.eroadcar.networkmanagement.utils;

import android.util.Log;

import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class NetPrinter {
    public Socket socket;
    private static final String DEBUG_TAG = "NetPrinter";
    public static final int POS_OPEN_NETPORT = 9100;
    public boolean IFOpen = false;
    public int Net_ReceiveTimeout = 2500;    //网络打印机 打开网络打印机

    public boolean Open(String ipaddress, int netport) {
        if (socket == null) {
            try {
                SocketAddress ipe = new InetSocketAddress(Inet4Address.getByName(ipaddress), netport);
                Log.e(DEBUG_TAG, Inet4Address.getByName(ipaddress) + "|" + netport);
                socket = new Socket();
                socket.connect(ipe, Net_ReceiveTimeout);  //2500ms连接时间，连接不上返回false
                IFOpen = true;
            } catch (Exception e) {
                IFOpen = false;
            }
        } else {
            try {
                socket.close();
                SocketAddress ipe = new InetSocketAddress(Inet4Address.getByName(ipaddress), netport);
                Log.e(DEBUG_TAG, Inet4Address.getByName(ipaddress) + "|" + netport);
                socket = new Socket();
                socket.connect(ipe, Net_ReceiveTimeout);
                IFOpen = true;
            } catch (Exception e) {
                IFOpen = false;
            }
        }
        if (IFOpen) {
            Close();
        }
        return IFOpen;
    }    //网络打印机 关闭

    public void Close() {
        try {
            socket.close();
            socket = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
