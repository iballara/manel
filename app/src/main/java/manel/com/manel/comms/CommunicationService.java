package manel.com.manel.comms;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import manel.com.manel.activities.MainMenuActivity;

import static android.R.attr.port;

public class CommunicationService extends Service {

    private final static String LOCAL_IP = "192.168.1.4";
    private final static int PORT =  8080;
    private static final int BYTES_BUFFER = 10000;

    public static Handler mUIhandler = null;
    public static Boolean isServiceRunning = false;

    private DatagramSocket socket;
    private DatagramPacket packet;
    private InetAddress localAddress;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        (new MyThread()).start();
        try {
            localAddress = InetAddress.getByName(LOCAL_IP);
            socket = new DatagramSocket(PORT);
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        isServiceRunning = true;
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceRunning = false;
    }

    private class MyThread extends Thread {

        private final static String INNER_TAG = "MyThread";

        public void run(){
            System.out.println("MyThread is running");
            this.setName(INNER_TAG);
            receiveUDPMessage();
        }

        private void receiveUDPMessage(){

            Looper.prepare();
            byte[] recvBuf = new byte[BYTES_BUFFER];
            System.out.println("MyThread run method is running.");

            if (socket == null || socket.isClosed()){
                try {
                    System.out.println("socket");
                    socket = new DatagramSocket(port);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(packet.getData()).trim();
            if(!message.equals("")){
                //CommunicationAPI.sendMessageToActivity(message);
                mUIhandler = new Handler(){

                    public void handleMessage(Message message1) {
                        MainMenuActivity.uiHandler.publish(
                                new LogRecord(Level.INFO, message1.toString())
                        );
                    }
                };
            }
            Looper.loop();

        }
    }
}
