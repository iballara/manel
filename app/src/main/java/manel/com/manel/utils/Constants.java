package manel.com.manel.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The scope of this class is just being a constants repository,
 * for all the different constants we will use in this project.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class Constants {
    
    public static class RemoteControl{

        // Direction.
        public static final String STOPPED = "0";

        // Angle detected from accelerometer.
        public static final String ANGLE_ZERO = "00";
        public static final String ANGLE_RIGHT_1 = "11";
        public static final String ANGLE_RIGHT_2 = "12";
        public static final String ANGLE_LEFT_1 = "21";
        public static final String ANGLE_LEFT_2 = "22";

        // Driving Mode
        public static final String MANUAL = "man";
        public static final String AUTOMATIC = "aut";

        // Lights
        public static final String LIGHTS_ON = "1";
        public static final String LIGHTS_OFF = "0";

        // Shapes
        public static final String SQUARE = "S";
        public static final String TRIANGLE = "T";
        public static final String CIRCLE = "C";

        // Gears
        public static final int MAX_GEAR = 3;
        public static final int MIN_GEAR = -3;
    }

    public static class KEYS {

        public static class TO_SEND {
            public static final String ANGLE = "ang";
            public static final String MODE = "mod";
            public static final String LIGTHS = "lig";
            public static final String SPEED = "vel";
            public static final String SHAPE = "shp";
            public static final String ACC = "acc";

            public static ArrayList<String> getSendingKeys() {
                List<String> keys = new ArrayList<>();
                keys.add(ANGLE);
                keys.add(MODE);
                keys.add(LIGTHS);
                keys.add(SPEED);
                keys.add(SHAPE);
                keys.add(ACC);
                return (ArrayList<String>) keys;
            }
        }

        public static class TO_RECEIVE {
            public static final String TEMP = "tmp";
            public static final String LIGHTS = "lig";
            public static final String BUMPER_LEFT = "bl";
            public static final String BUMPER_RIGHT = "br";
            public static final String ULTRASOUND = "us";
            public static final String ACC_X = "x";
            public static final String ACC_Y = "y";
            public static final String ACC_Z = "z";

            public static ArrayList<String> getReceivingKeys() {
                List<String> keys = new ArrayList<>();
                keys.add(TEMP);
                keys.add(LIGHTS);
                keys.add(BUMPER_LEFT);
                keys.add(BUMPER_RIGHT);
                keys.add(ULTRASOUND);
                keys.add(ACC_X);
                keys.add(ACC_Y);
                keys.add(ACC_Z);
                return (ArrayList<String>) keys;
            }
        }
    }
}
