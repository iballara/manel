package manel.com.manel.comms;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import manel.com.manel.activities.MainMenuActivity;

import static java.net.InetAddress.*;
import static manel.com.manel.activities.MainMenuActivity.uiHandler;
import static manel.com.manel.comms.CommunicationService.BYTES_BUFFER;
import static manel.com.manel.comms.CommunicationService.PORT;
import static manel.com.manel.comms.CommunicationService.socket;

class MyThreads {

    private static MyTransmiterThread txThread;
    private static MyReceivingThread rxThread;
    static String datagramToSend;

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

            Message message1 = new Message();
            Looper.prepare();
            byte[] recvBuf = new byte[BYTES_BUFFER];
            System.out.println("MyReceivingThread run method is running.");

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
                message1.obj = message;
                uiHandler.sendMessage(message1);
            }
            Looper.loop();
        }
    }

    private static class MyTransmiterThread extends Thread {

        public static final String INNER_TAG = "MyTransmiterThread";
        static final String MANEL_IP = "192.168.0.0";
        static final int MANEL_PORT = 2370;

        @Override
        public void run() {
            System.out.println("MyTransmiterThread is running");
            this.setName(INNER_TAG);
            sendUDPMessage();
        }

        private void sendUDPMessage() {

            try {
                byte[] message = datagramToSend.getBytes();
                DatagramPacket packet = new DatagramPacket(
                        message,
                        message.length,
                        getByName(MANEL_IP),
                        MANEL_PORT
                );
                DatagramSocket dsocket = new DatagramSocket();
                dsocket.send(packet);
                dsocket.close();
            } catch (UnknownHostException e) {
                Log.e("Error", e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
