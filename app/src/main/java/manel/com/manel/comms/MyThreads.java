package manel.com.manel.comms;

import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import manel.com.manel.comms.udp.UdpDatagramConstructor;

import static java.net.InetAddress.getByName;
import static manel.com.manel.activities.MainMenuActivity.uiHandler;
import static manel.com.manel.activities.RemoteControlActivity.DEFAULT_SHAPE;
import static manel.com.manel.comms.CommunicationService.BYTES_BUFFER;
import static manel.com.manel.comms.CommunicationService.PHONE_PORT;
import static manel.com.manel.comms.CommunicationService.socket;

class MyThreads {

    private static MyTransmiterThread txThread;
    private static MyReceivingThread rxThread;
    static String datagramToSend = "";

    static void runRxAndTxThreads() {
        txThread = new MyTransmiterThread();
        rxThread = new MyReceivingThread();
        txThread.start();
        rxThread.start();
    }

    static void stopRxAndTxThreads() {
        if (txThread.isAlive()) {
            txThread.interrupt();
        }

        if (rxThread.isAlive()) {
            rxThread.interrupt();
        }
    }

    private static class MyReceivingThread extends Thread {

        private final static String INNER_TAG = "MyReceivingThread";

        @Override
        public void run(){
            System.out.println("MyReceivingThread is running");
            this.setName(INNER_TAG);
            receiveUDPMessage();
        }

        /**
         * Method in charge of receiving the UDP packets.
         */
        private void receiveUDPMessage(){

            while (!this.isInterrupted()) {

                Message message1 = new Message();
                byte[] recvBuf = new byte[BYTES_BUFFER];
                System.out.println("MyReceivingThread run method is running.");

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

    private static class MyTransmiterThread extends Thread {

        private static final int TIME_BETWEEN_FRAMES = 300;
        static final String INNER_TAG = "MyTransmiterThread";
        static final String MANEL_IP = "192.168.0.255";
        static final int MANEL_PORT = 2390;

        @Override
        public void run() {
            System.out.println("MyTransmiterThread is running");
            this.setName(INNER_TAG);
            sendUDPMessage();
        }

        private void sendUDPMessage() {

            while (!this.isInterrupted()) {
                try {
                    if (datagramToSend != null) {
                        System.out.println("MyTransmiterThread run method is running.");
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
                        Thread.sleep(TIME_BETWEEN_FRAMES);
                        UdpDatagramConstructor.shape = DEFAULT_SHAPE;
                    } else {
                        Log.i("Packet sent: ", "is null");
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
