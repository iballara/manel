package manel.com.manel.comms.udp;

import java.util.ArrayList;
import java.util.HashMap;

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
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.BUMPER_LEFT;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.BUMPER_RIGHT;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.LIGHTS;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.TEMP;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.ULTRASOUND;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.getReceivingKeys;

/**
 * This is a class for receiving data through UDP.
 *
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class UdpDatagramDeconstructor {
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

        switch (key) {
            case TEMP:
                setTvTemperature(value + " ºC");
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
        }
    }
}
