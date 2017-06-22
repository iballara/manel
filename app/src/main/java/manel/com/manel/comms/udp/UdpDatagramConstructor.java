package manel.com.manel.comms.udp;

import java.util.HashMap;

import manel.com.manel.activities.RemoteControlActivity;
import manel.com.manel.utils.Constants;

import static manel.com.manel.comms.CommunicationService.sendDatagram;

/**
 * This is a class for sending data through UDP.
 * When we want to modify the data we will send of a certain field, here is where it is modified.
 * The datagram we send to the robot is:
 *
 * (i.e.)   ang:00,mod:man,lig:0,vel:+0,shp:0,acc:0,str:1
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 3.0
 */
public class UdpDatagramConstructor {

    public static String str = Constants.RemoteControl.IDLE;
    public static String shape = RemoteControlActivity.DEFAULT_SHAPE;

    private final static int DATA_SIZE = 6;

    private static String drivingMode = Constants.RemoteControl.MANUAL;
    private static String lightsStatus = Constants.RemoteControl.LIGHTS_OFF;
    private static String angleToTurn = Constants.RemoteControl.ANGLE_ZERO;
    private static String speed = RemoteControlActivity.DEFAULT_SPEED;
    private static String acc = Constants.RemoteControl.IDLE;


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

    public static void sendLabyrinth(String labMode) {
        UdpDatagramConstructor.str = labMode;
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
        dataToSend.put(Constants.KEYS.TO_SEND.STR, str);
        String datagramToSend = UdpDatagramHelper.formatMessageToSend(dataToSend);
        sendDatagram(datagramToSend);
    }
}
