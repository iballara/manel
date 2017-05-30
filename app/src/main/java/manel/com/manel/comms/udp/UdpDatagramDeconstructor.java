package manel.com.manel.comms.udp;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.HashMap;

import manel.com.manel.R;
import manel.com.manel.activities.AccelerometerActivity;
import manel.com.manel.activities.RemoteControlActivity;
import manel.com.manel.utils.Constants;

import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.ACC_X;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.ACC_Y;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.ACC_Z;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.BUMPER_LEFT;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.BUMPER_RIGHT;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.LIGHTS;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.TEMP;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.ULTRASOUND;
import static manel.com.manel.utils.Constants.KEYS.TO_RECEIVE.getReceivingKeys;

public class UdpDatagramDeconstructor {
    public static void receiveData(HashMap<String, String> data) {

        ArrayList<String> keys = getReceivingKeys();

        for (String key : keys) {
            updateValue(key, data.get(key));
        }
    }

    private static void updateValue(String key, String value) {

        if (key.equals(TEMP)) {
            RemoteControlActivity.tvTemperature.setText(value);
        } else if (key.equals(LIGHTS)) {

            if (value.equals("0"))
                RemoteControlActivity.tvLights.setText("Ligths: OFF");
            else
                RemoteControlActivity.tvLights.setText("Ligths: ON");

        } else if (key.equals(BUMPER_LEFT)) {
            if (value.equals("0"))
                RemoteControlActivity.bumperLeft.setBackgroundColor(Color.WHITE);
            else
                RemoteControlActivity.bumperLeft.setBackgroundColor(Color.BLUE);
        } else if (key.equals(BUMPER_RIGHT)) {
            if (value.equals("0"))
                RemoteControlActivity.bumperLeft.setBackgroundColor(Color.WHITE);
            else
                RemoteControlActivity.bumperLeft.setBackgroundColor(Color.BLUE);
        } else if (key.equals(ULTRASOUND)) {
            if (value.equals("0"))
                RemoteControlActivity.bumperLeft.setBackgroundColor(Color.WHITE);
            else
                RemoteControlActivity.bumperLeft.setBackgroundColor(Color.BLUE);
        } else if (key.equals(ACC_X)) {

        } else if (key.equals(ACC_Y)) {

        } else if (key.equals(ACC_Z)) {

        }

    }
}
