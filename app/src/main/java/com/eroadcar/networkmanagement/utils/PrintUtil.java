package com.eroadcar.networkmanagement.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class PrintUtil {
    public static String IPADDRESS;
    public Socket socket;
    public static final int POS_OPEN_NETPORT = 9100;
    public static String IPADRESS;
    public int Net_ReceiveTimeout = 2500;
    Context context;
    String path;

    public PrintUtil(Context context, String path) {
        super();
        this.context = context;
        this.path = path;
        IPADDRESS = getIPAddress(context);
        RunThread run = new RunThread();
        run.start();
    }     /*      * 获取当前手机所连wifi的ip地址      */

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {// 当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {// 当前使用无线网络
                WifiManager wifiManager = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());// 得到IPV4地址
                return ipAddress;
            }
        } else {              // 当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型,截取ip的前三段
     * //   *      * @param ip      * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + ".";
    }

    ScheduledExecutorService pool;
    boolean isHave = false;

    private class RunThread extends Thread {
        public RunThread() {
        }

        @Override
        public void run() {
            if (pool == null) {
                pool = Executors.newScheduledThreadPool(100);
            }
            for (int i = 2; i < 255; ++i) {     //0~255循环找到打印机ip
                final int index = i;
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String ipaddress = IPADDRESS + index;
                            NetPrinter printer = new NetPrinter();
                            printer.Open(ipaddress, NetPrinter.POS_OPEN_NETPORT);
                            if (printer.IFOpen) {   //找到打印机

                                isHave = true;
                                pos(ipaddress);
                                handler.sendEmptyMessage(0);
                                return;
                            } else {
                                if (index == 254 && isHave == false) {
                                    handler.sendEmptyMessage(1);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(context, "打印完成！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(context, "未找到打印机，请确认打印机在同一局域网内，谢谢！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private void pos(final String ip) {
        new Thread() {
            public void run() {
                try { // 打印机网口IP 打印机端口号 编码方式
                    printImage(ip);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    Log.d("tag", "错误信息1：" + e.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("tag", "错误信息2：" + e.toString());
                }
            }
        }.start();
    }    //根据IP地址使用打印机打印图片

    public void printImage(String ip) throws IOException {
        DataOutputStream outToServer = null;
        Socket clientSocket;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(path);
            InputStream is = fileInputStream;
            clientSocket = new Socket(ip, 9100);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            byte[] buffer = new byte[3000];
            while (is.read(buffer) != -1) {
                outToServer.write(buffer);
            }
            outToServer.flush();
        } catch (ConnectException connectException) {
            Log.e("msg", connectException.toString(), connectException);
        } catch (UnknownHostException e1) {
            Log.e("msg", e1.toString(), e1);
        } catch (IOException e1) {
            Log.e("msg", e1.toString(), e1);
        } finally {
            outToServer.close();
        }
    }

    public void deleteAll(File file) {
        if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            for (File f : file.listFiles()) {
                deleteAll(f); // 递归删除每一个文件
            }
            file.delete(); // 删除文件夹
        }
    }


}
