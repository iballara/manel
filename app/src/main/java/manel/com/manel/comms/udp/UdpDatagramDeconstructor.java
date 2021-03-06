package manel.com.manel.comms.udp;

import java.util.ArrayList;
import java.util.HashMap;

import manel.com.manel.activities.LabyrinthActivity;

import static manel.com.manel.activities.AccelerometerActivity.printAccX;
import static manel.com.manel.activities.AccelerometerActivity.printAccY;
import static manel.com.manel.activities.AccelerometerActivity.printAccZ;
import static manel.com.manel.activities.RemoteControlActivity.setBumperCenter;
import static manel.com.manel.activities.RemoteControlActivity.setBumperLeft;
import static manel.com.manel.activities.RemoteControlActivity.setBumperRight;
import static manel.com.manel.activities.RemoteControlActivity.setTvLights;
import static manel.com.manel.activities.RemoteControlActivity.setTvTemperature;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.ACC_X;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.ACC_Y;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.ACC_Z;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.ACTUAL_CELL;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.BUMPER_LEFT;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.BUMPER_RIGHT;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.EAST;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.LAST_CELL;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.LIGHTS;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.NORTH;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.SOUTH;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.TEMP;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.ULTRASOUND;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.WEST;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.getReceivingKeys;

/**
 * This is a class for receiving data through UDP.
 * The datagram we will receive when doing the labyrinth is as follows:
 * (i.e.)   act:44,ant:431,n:0,e:1,s:1,w:0
 *
 * When doing remote control:
 * (i.e.)   lig:1,be:1,bd:1,us:0,T:26,x:0.05,y:-0.05,z:1.09
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class UdpDatagramDeconstructor {

    private static final String TEMP = " ºC";

    /**
     * Method for trating the new data received.
     * @param data HashMap<String, String>
     */
    public static void receiveData(HashMap<String, String> data) {

        ArrayList<String> keys = getReceivingKeys();

        for (String key : keys) {
            updateValue(key, data.get(key));
        }
    }

    /**
     * This is the method that updates the views depending on the data received
     * through UDP packets.
     *
     * @param key String
     * @param value String
     */
    private static void updateValue(String key, String value) {

        if (value != null) {
            switch (key) {
                case TEMP:
                    setTvTemperature(value + TEMP);
                    break;
                case LIGHTS:
                    setTvLights(value);
                    break;
                case BUMPER_LEFT:
                    setBumperLeft(value);
                    break;
                case BUMPER_RIGHT:
                    setBumperRight(value);
                    break;
                case ULTRASOUND:
                    setBumperCenter(value);
                    break;
                case ACC_X:
                    printAccX(Double.valueOf(value));
                    break;
                case ACC_Y:
                    printAccY(Double.valueOf(value));
                    break;
                case ACC_Z:
                    printAccZ(Double.valueOf(value));
                    break;
                case ACTUAL_CELL:
                    LabyrinthActivity.setActualCell(value);
                    break;
                case LAST_CELL:
                    LabyrinthActivity.setLastCell(value);
                    break;
                case NORTH:
                    LabyrinthActivity.setNorth(value);
                    break;
                case SOUTH:
                    LabyrinthActivity.setSouth(value);
                    break;
                case EAST:
                    LabyrinthActivity.setEast(value);
                    break;
                case WEST:
                    LabyrinthActivity.setWest(value);
                    break;
            }
        }
    }
}
