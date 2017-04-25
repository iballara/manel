package manel.com.manel.comms;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class CommunicationService extends Service {

    private final static String LOCAL_IP = "192.168.1.4";
    private final static int PORT =  8080;

    public static Handler handler = null;
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
        // PROVA
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

        public void run() {
            this.setName(INNER_TAG);
            Looper.prepare();

            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    // Used to receive message from activity.
                    // Haurem de retornar el missatge a les activitats des d'aquí
                }
            };
            Looper.loop();
        }
    }
}
