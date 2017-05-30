package manel.com.manel.comms.udp;

import java.util.HashMap;

import manel.com.manel.comms.CommunicationService;
import manel.com.manel.utils.Constants;

public class UdpDatagramConstructor {

    private final static int DATA_SIZE = 5;
    private final static String TRIANGLE = "Triangle";
    private final static String CIRCLE = "Circle";
    private final static String SQUARE = "Square";
    private static String drivingMode = "";
    private static String lightsStatus = "";
    private static String angleToTurn = "";
    private static String shape = "";
    private static String speed = "";
    private static String acc = "";

    public static void setAcc(String acc) {
        UdpDatagramConstructor.acc = acc;
        createAndSendDatagram();
    }

    public static void setDrivingMode(String drivingMode) {
        UdpDatagramConstructor.drivingMode = drivingMode;
        createAndSendDatagram();
    }

    public static void setLightsStatus(String lightsStatus) {
        UdpDatagramConstructor.lightsStatus = lightsStatus;
        createAndSendDatagram();
    }

    public static void setAngleToTurn(String angleToTurn) {
        UdpDatagramConstructor.angleToTurn = angleToTurn;
        createAndSendDatagram();
    }

    public static void setShape(String shape) {
        UdpDatagramConstructor.shape = shape;
        createAndSendDatagram();
        UdpDatagramConstructor.shape = "0";
    }

    public static void setSpeed(String speed) {
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
        CommunicationService.sendDatagram(datagramToSend);
    }
}
