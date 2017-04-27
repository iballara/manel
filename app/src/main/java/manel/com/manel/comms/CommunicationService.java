package manel.com.manel.comms;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import manel.com.manel.activities.MainMenuActivity;

/**
 * This is the communication service.
 * It is in charge of receiving UDP packets and returning its messages to the view.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 1.0
 */
public class CommunicationService extends Service {

    private final static String LOCAL_IP = "192.168.1.219";
    private final static int PORT =  53056;
    private static final int BYTES_BUFFER = 10000;

    public static Handler mUIhandler;
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
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        (new MyThread()).start();
        try {
            localAddress = InetAddress.getByName(LOCAL_IP);
            socket = new DatagramSocket();
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

    /**
     * Inner class MyThread constantly runs and receives UDP packets
     * while sending data to the UI.
     */
    private class MyThread extends Thread {

        private final static String INNER_TAG = "MyThread";

        public void run(){
            System.out.println("MyThread is running");
            this.setName(INNER_TAG);
            receiveUDPMessage();
        }

        /**
         * Method in charge of receiving the UDP packets.
         */
        private void receiveUDPMessage(){

            Looper.prepare();
            byte[] recvBuf = new byte[BYTES_BUFFER];
            System.out.println("MyThread run method is running.");

            if (socket == null || socket.isClosed()){
                try {
                    socket = new DatagramSocket(PORT);
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
            final String message = new String(packet.getData()).trim();
            System.out.println(message);
            if(message.length() > 0){
                MainMenuActivity.uiHandler.publish(new LogRecord(Level.INFO, message));
            }
            Looper.loop();
        }
    }
}
