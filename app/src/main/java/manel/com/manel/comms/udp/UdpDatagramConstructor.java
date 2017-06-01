package manel.com.manel.comms.udp;

import java.util.HashMap;

import manel.com.manel.utils.Constants;

import static manel.com.manel.comms.CommunicationService.sendDatagram;

public class UdpDatagramConstructor {

    private final static int DATA_SIZE = 6;
    private static String drivingMode = "";
    private static String lightsStatus = "";
    private static String angleToTurn = "";
    public static String shape = "";
    private static String speed = "";
    private static String acc = "";

    public static void sendAcc(String acc) {
        UdpDatagramConstructor.acc = acc;
        createAndSendDatagram();
    }

    public static void sendDrivingMode(String drivingMode) {
        UdpDatagramConstructor.drivingMode = drivingMode;
        createAndSendDatagram();
    }

    public static void sendLightsStatus(String lightsStatus) {
        UdpDatagramConstructor.lightsStatus = lightsStatus;
        createAndSendDatagram();
    }

    public static void sendAngleToTurn(String angleToTurn) {
        UdpDatagramConstructor.angleToTurn = angleToTurn;
        createAndSendDatagram();
    }

    public static void sendShape(String shape) {
        UdpDatagramConstructor.shape = shape;
        createAndSendDatagram();
    }

    public static void sendSpeed(String speed) {
        UdpDatagramConstructor.speed = speed;
        createAndSendDatagram();
    }

    private static void createAndSendDatagram(){

        HashMap<String, String> dataToSend = new HashMap<>(DATA_SIZE);
        dataToSend.put(Constants.KEYS.TO_SEND.MODE, drivingMode);
        dataToSend.put(Constants.KEYS.TO_SEND.LIGTHS, lightsStatus);
        dataToSend.put(Constants.KEYS.TO_SEND.ANGLE, angleToTurn);
        dataToSend.put(Constants.KEYS.TO_SEND.SHAPE, shape);
        dataToSend.put(Constants.KEYS.TO_SEND.SPEED, speed);
        dataToSend.put(Constants.KEYS.TO_SEND.ACC, acc);
        String datagramToSend = UdpDatagramHelper.formatMessageToSend(dataToSend);
        sendDatagram(datagramToSend);
    }
}
