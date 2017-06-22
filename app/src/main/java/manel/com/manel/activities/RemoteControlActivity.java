package manel.com.manel.activities;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import manel.com.manel.R;
import manel.com.manel.comms.CommunicationService;
import manel.com.manel.comms.udp.UdpDatagramConstructor;

import static java.util.Arrays.asList;
import static manel.com.manel.comms.udp.UdpDatagramConstructor.*;
import static manel.com.manel.utils.Constants.RemoteControl.*;
import static manel.com.manel.utils.Constants.RemoteControl.ANGLE_LEFT_1;
import static manel.com.manel.utils.Constants.RemoteControl.ANGLE_RIGHT_1;
import static manel.com.manel.utils.Constants.RemoteControl.ANGLE_RIGHT_2;
import static manel.com.manel.utils.Constants.RemoteControl.ANGLE_ZERO;
import static manel.com.manel.utils.Constants.RemoteControl.CIRCLE;
import static manel.com.manel.utils.Constants.RemoteControl.MAX_GEAR;
import static manel.com.manel.utils.Constants.RemoteControl.MIN_GEAR;
import static manel.com.manel.utils.Constants.RemoteControl.SQUARE;
import static manel.com.manel.utils.Constants.RemoteControl.STOPPED;
import static manel.com.manel.utils.Constants.RemoteControl.TRIANGLE;

/**
 * Activty to control the robot. It also receives information from it
 * like temperature or velocity.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class RemoteControlActivity extends AppCompatActivity
        implements GestureOverlayView.OnGesturePerformedListener, SensorEventListener {

    private static final String ZERO = "0";
    private static final String ONE = "1";
    private static final String LIGHTS_OFF_TEXT = "Ligths: OFF";
    private static final String LIGHTS_ON_TEXT = "Lights: ON";
    private static final String GEAR = "Gear: ";
    public static final String DEFAULT_SPEED = "+0";
    public static final String DEFAULT_SHAPE = "0";
    private static final String PLUS = "+";

    private Boolean isButtonPressed;
    private GestureLibrary gestureLib;
    private Boolean drivingModeManual;
    private Integer currentGear;
    private Boolean lightsOn;

    // Views
    public static TextView tvTemperature, tvSpeed, tvLights;
    private Button btnGearsPlus, btnGearsMinus, btnAutMan, btnLights, btnAccelerate;
    public static View bumperLeft, bumperCenter, bumperRight;

    /**
     * OnCreate Method from Activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actBar = getSupportActionBar();
        actBar.setHomeButtonEnabled(true);
        GestureOverlayView gestureOverlayView = new GestureOverlayView(this);
        View inflate = getLayoutInflater().inflate(R.layout.activity_remote_control, null);
        gestureOverlayView.addView(inflate);
        gestureOverlayView.addOnGesturePerformedListener(this);
        gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL
        );
        if (!gestureLib.load()) {
            System.out.println("GestureLib not loaded");
            finish();
        }
        this.drivingModeManual = true;
        this.currentGear = 0;
        this.lightsOn = false;
        this.isButtonPressed = false;
        sendDrivingMode(MANUAL);
        sendAcc(STOPPED);
        sendSpeed(DEFAULT_SPEED);
        sendLightsStatus(LIGHTS_OFF);
        sendShape(DEFAULT_SHAPE);
        setContentView(gestureOverlayView);
        setViews();
        if (!CommunicationService.isServiceRunning) {
            startService(new Intent(this, CommunicationService.class));
        }
    }

    /**
     * This method is executed when we click the arrow to
     * go to the parent activity.
     *
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * The regular onBackPressed from Activity class.
     * It just finishes this activity.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * It detects the gesture the user has drawn on the screen.
     *
     * @param overlay GestureOverlayView
     * @param gesture Gesture
     */
    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

        ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
        Prediction prediction = predictions.get(0);

        if (prediction.score > 1.0 && prediction.name.length()>1) {
            Toast.makeText(this, prediction.name , Toast.LENGTH_SHORT).show();

            String shapeString = shapeMapper(prediction.name);

            if (shapeString != null)
                UdpDatagramConstructor.sendShape(shapeString);
        }
    }

    /**
     * This method will be executed everytime that the values
     * of the accelerometer of the smartphone change.
     *
     * In this case, it also calculates the angle the robot will turn.
     *
     * @param event SensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            double y = event.values[1];
            String angle;
            if (y < -6) {
                angle = ANGLE_LEFT_2;
            } else if (-6 < y && y < -3) {
                angle = ANGLE_LEFT_1;
            } else if (-3 < y && y < 3) {
                angle = ANGLE_ZERO;
            } else if (3 <  y && y < 6) {
                angle = ANGLE_RIGHT_1;
            } else {
                angle = ANGLE_RIGHT_2;
            }
            sendAngleToTurn(angle);
        }
    }

    public static void setTvTemperature(String value) {
        if (tvTemperature != null) {
            tvTemperature.setText(value);
        }
    }

    public static void setTvSpeed(String value) {
        if (tvSpeed != null) {
            tvSpeed.setText(String.format("%s%s", GEAR, value));
        }
    }

    public static void setTvLights(String value) {
        if (tvLights != null) {
            if (value.equals(ZERO))
                tvLights.setText(LIGHTS_OFF_TEXT);
            else
                tvLights.setText(LIGHTS_ON_TEXT);
        }
    }

    public static void setBumperLeft(String value) {
        if (bumperLeft != null) {
            if (value.equals("1"))
                bumperLeft.setBackgroundColor(Color.RED);
            else
                bumperLeft.setBackgroundColor(Color.CYAN);
        }
    }
    public static void setBumperCenter(String value) {
        if (bumperCenter != null) {
            if (value.equals(ZERO))
                bumperCenter.setBackgroundColor(Color.CYAN);
            else
                bumperCenter.setBackgroundColor(Color.RED);
        }
    }

    public static void setBumperRight(String value) {
        if (bumperRight != null) {
            if (value.equals(ONE))
                bumperRight.setBackgroundColor(Color.RED);
            else
                bumperRight.setBackgroundColor(Color.CYAN);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /* *********************
    **  PRIVATE METHODS
    *************************/

    /**
     * Method for initializing the views and setting the
     * listeners.
     */
    private void setViews() {

        tvTemperature = (TextView) findViewById(R.id.tv_temperature);
        btnGearsPlus = (Button) findViewById(R.id.button_gears_plus);
        btnGearsMinus = (Button) findViewById(R.id.button_gears_minus);
        tvSpeed = (TextView) findViewById(R.id.tv_velocimeter);
        btnAutMan = (Button) findViewById(R.id.btn_automatic_manual);
        btnLights = (Button) findViewById(R.id.btn_light_control);
        bumperLeft = findViewById(R.id.bumper_left);
        bumperCenter = findViewById(R.id.bumper_collision);
        bumperRight = findViewById(R.id.bumper_right);
        btnAccelerate = (Button) findViewById(R.id.acc_button);
        tvLights = (TextView) findViewById(R.id.tv_lights);

        final List<Button> viewsToDeactivate = asList(btnGearsPlus, btnGearsMinus,
                btnAccelerate, btnLights);

        btnGearsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentGear < MAX_GEAR) {
                    currentGear++;
                    setTvSpeed(currentGear.toString());
                    // If the accelerate button is not pressed, the gear will
                    // increase but the robot won't move.
                    if (isButtonPressed)
                        prepareSpeedToSend(currentGear);
                    else
                        prepareSpeedToSend(0);
                }
            }
        });

        btnGearsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentGear > MIN_GEAR) {
                    currentGear--;
                    setTvSpeed(currentGear.toString());
                    if (isButtonPressed)
                        prepareSpeedToSend(currentGear);
                    else
                        prepareSpeedToSend(0);
                }
            }
        });

        btnAutMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // We change the driving mode
                drivingModeManual = !drivingModeManual;

                // We deactivate some buttons if we are
                // riding in automatic and viceversa.
                for (Button button : viewsToDeactivate)
                    button.setEnabled(drivingModeManual);

                // If we are driving in automatic mode,
                // we will send speed as 0
                if (!drivingModeManual)
                    sendSpeed(DEFAULT_SPEED);
                 else
                    prepareSpeedToSend(0);

                sendDrivingMode(drivingModeManual ? MANUAL : AUTOMATIC);
            }
        });

        btnLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightsOn = !lightsOn;
                sendLightsStatus(lightsOn ? LIGHTS_ON : LIGHTS_OFF);
            }
        });

        btnAccelerate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    prepareSpeedToSend(currentGear);
                    isButtonPressed = true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    prepareSpeedToSend(0);
                    isButtonPressed = false;
                }
                return true;
            }
        });
    }

    /**
     * Particular method for adding the "+" symbol if the currentGear
     * is >= 0, for sending tho the robot.
     *
     * @param internalSpeed Integer
     */
    private void prepareSpeedToSend(Integer internalSpeed) {
        if (internalSpeed >= 0){
            sendSpeed(PLUS + internalSpeed);
        } else {
            sendSpeed(internalSpeed.toString());
        }
    }

    /**
     * This method deduces what letter to send depending
     * on the drawn shape.
     *
     * @param name String
     * @return String with the symbol of the shape to send.
     */
    private String shapeMapper(String name) {
        switch (name.charAt(0)) {
            case 'T':
                return TRIANGLE;
            case 'C':
                return CIRCLE;
            case 'S':
                return SQUARE;
            default:
                return null;
        }
    }
}
