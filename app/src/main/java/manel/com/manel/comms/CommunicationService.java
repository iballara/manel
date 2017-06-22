package manel.com.manel.comms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static manel.com.manel.comms.UDPrxAndtxThreads.runRxAndTxThreads;

/**
 * This is the communication service.
 * It is in charge of receiving UDP packets and returning its messages to the view.
 * This class is a gateway for controlling the threads from the outside.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class CommunicationService extends Service {

    public static long timeBetweenFrames;
    public final static String PHONE_IP = "192.168.0.100";
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

    /**
     * This method is invoked when some activity start the service.
     * It starts both receiving and transmitting threads for UDP packets.
     *
     * @param intent Intent
     * @param flags int
     * @param startId int
     * @return int
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            localAddress = InetAddress.getByName(PHONE_IP);
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

    /**
     * This method is in charge of telling to MyTransmiterThread what to send.
     * @param datagram String datagram to send.
     */
    public static void sendDatagram(String datagram){
        UDPrxAndtxThreads.datagramToSend = datagram;
    }
}
