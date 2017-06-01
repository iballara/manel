package manel.com.manel.comms.udp;

import java.util.HashMap;

import manel.com.manel.utils.Constants;

import static manel.com.manel.comms.CommunicationService.sendDatagram;

/**
 * This is a class for sending data through UDP.
 * When we want to modify the data we will send of a certain field, here is where it is modified.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class UdpDatagramConstructor {

    private final static int DATA_SIZE = 6;
    private static String drivingMode = "";
    private static String lightsStatus = "";
    private static String angleToTurn = "";
    public static String shape = "";
    private static String speed = "";
    private static String acc = "";

    /* ************
    *** SETTERS
    ***************/

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

    /**
     * Method that adds the key to each field.
     */
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
