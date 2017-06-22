package manel.com.manel.comms;

import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

import manel.com.manel.activities.CommunicationLogActivity;
import manel.com.manel.comms.udp.UdpDatagramConstructor;
import manel.com.manel.comms.udp.UdpDatagramDeconstructor;

import static java.net.InetAddress.getByName;
import static manel.com.manel.activities.MainMenuActivity.uiHandler;
import static manel.com.manel.activities.RemoteControlActivity.DEFAULT_SHAPE;
import static manel.com.manel.comms.CommunicationService.BYTES_BUFFER;
import static manel.com.manel.comms.CommunicationService.PHONE_PORT;
import static manel.com.manel.comms.CommunicationService.socket;
import static manel.com.manel.comms.udp.UdpDatagramHelper.parseReceivedMessage;

/**
 * This is a package-protected class that handles 2 threads, one for receiving UDP packets and
 * another one for sending UDP packets.
 *
 * Each of these threads is a static class and can only be accessed from the CommunicationService
 * sevice, which is a like an interface for controlling the threads.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
class UDPrxAndtxThreads {

    private static TransmiterThread txThread;
    private static ReceivingThread rxThread;
    static String datagramToSend;

    /**
     * Gateway method for starting the threads only from the CommunicationService service.
     */
    static void runRxAndTxThreads() {
        txThread = new TransmiterThread();
        rxThread = new ReceivingThread();
        txThread.start();
        rxThread.start();
    }

    /**
     * Method for stopping both threads.
     */
    static void stopRxAndTxThreads() {
        if (txThread.isAlive()) {
            txThread.interrupt();
        }

        if (rxThread.isAlive()) {
            rxThread.interrupt();
        }
    }

    /**
     * Class for receiving UDP packets.
     */
    private static class ReceivingThread extends Thread {

        private final static String INNER_TAG = "ReceivingThread";

        @Override
        public void run(){
            System.out.println("ReceivingThread is running");
            this.setName(INNER_TAG);
            receiveUDPMessage();
        }

        /**
         * Method in charge of receiving the UDP packets.
         * It's constantly looping.
         */
        private void receiveUDPMessage(){

            while (!this.isInterrupted()) {

                Message message1 = new Message();
                byte[] recvBuf = new byte[BYTES_BUFFER];
                System.out.println("ReceivingThread run method is running.");

                if (socket == null || socket.isClosed()){
                    try {
                        socket = new DatagramSocket(PHONE_PORT);
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
                Log.i("Packet received: ", message);
                System.out.println(message);
                if(message.length() > 0){
                    message1.obj = message;
                    uiHandler.sendMessage(message1);
                }
            }
        }
    }

    /**
     * Class for sending UDP packets.
     */
    private static class TransmiterThread extends Thread {

        static final String INNER_TAG = "TransmiterThread";
        static final String MANEL_IP = "192.168.0.255";
        static final int MANEL_PORT = 2390;

        @Override
        public void run() {
            System.out.println("TransmiterThread is running");
            this.setName(INNER_TAG);
            sendUDPMessage();
        }

        /**
         * Method for sending the UDP packets.
         * It's constantly looping.
         */
        private void sendUDPMessage() {

            while (!this.isInterrupted()) {
                try {
                    if (datagramToSend != null) {
                        System.out.println("TransmiterThread run method is running.");
                        byte[] message = datagramToSend.getBytes();
                        Log.i("Packet sent: ", datagramToSend);
                        DatagramPacket packet = new DatagramPacket(
                                message,
                                message.length,
                                getByName(MANEL_IP),
                                MANEL_PORT
                        );
                        DatagramSocket dsocket = new DatagramSocket();
                        dsocket.send(packet);
                        dsocket.close();
                        UdpDatagramConstructor.shape = DEFAULT_SHAPE;
                    } else {
                        Log.i("Packet sent: ", "is null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
