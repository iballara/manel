package manel.com.manel.comms.udp;

import java.util.ArrayList;
import java.util.HashMap;

import manel.com.manel.activities.RemoteControlActivity;
import manel.com.manel.utils.Constants;

import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.BUMPER_LEFT;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.LIGHTS;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.TEMP;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.getReceivingKeys;

public class UdpDatagramDeconstructor {
    public static void receiveData(HashMap<String, String> data) {

        ArrayList<String> keys = getReceivingKeys();

        for (String key : keys) {
            updateValue(key, data.get(key));
        }
    }

    private static void updateValue(String key, String value) {

        // TODO: PENSAR COM FER.
//        if (key.equals(TEMP)) {
//            RemoteControlActivity.tvTemperature.setText(value);
//        } else if (key.equals(LIGHTS)) {
//            RemoteControlActivity.tvLights.setText(value);
//        } else if (key.equals(BUMPER_LEFT)) {
//            // S'hauria de pintar.
//            RemoteControlActivity.bumperLeft.
//        }

    }
}
