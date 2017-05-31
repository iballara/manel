package manel.com.manel.comms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static manel.com.manel.comms.MyThreads.runRxAndTxThreads;

/**
 * This is the communication service.
 * It is in charge of receiving UDP packets and returning its messages to the view.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 1.0
 */
public class CommunicationService extends Service {

    public final static String LOCAL_IP = "192.168.0.105";
    final static int PHONE_PORT =  58723;
    static final int BYTES_BUFFER = 10000;
    public static Boolean isServiceRunning = false;

    static DatagramSocket socket;
    static InetAddress localAddress;

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
        try {
            localAddress = InetAddress.getByName(LOCAL_IP);
            socket = new DatagramSocket(PHONE_PORT, localAddress);
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        runRxAndTxThreads();
        isServiceRunning = true;
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceRunning = false;
    }

    public static void sendDatagram(String datagram){
        MyThreads.datagramToSend = datagram;
    }
}
