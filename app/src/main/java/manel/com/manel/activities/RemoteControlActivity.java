package manel.com.manel.activities;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import manel.com.manel.R;
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
        this.currentGear = STOPPED;
        this.lightsOn = false;
        setContentView(gestureOverlayView);
        setViews();
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

        ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
        Prediction prediction = predictions.get(0);

        if (prediction.score > 1.0 && prediction.name.length()>1) {
            Toast.makeText(this, prediction.name , Toast.LENGTH_SHORT).show();

            String shapeString = shapeMapper(prediction.name);

            if (shapeString != null)
                UdpDatagramConstructor.setShape(shapeString);
        }
    }

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
            setAngleToTurn(angle);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     *  PRIVATE METHODS
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

        final List<Button> viewsToDeactivate = asList(btnGearsPlus, btnGearsMinus,
                btnAccelerate, btnLights);

        btnGearsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentGear < MAX_GEAR) {
                    currentGear++;
                    if (currentGear >= 0)
                        setSpeed("+" + currentGear.toString());
                    else
                        setSpeed(currentGear.toString());
                }
            }
        });

        btnGearsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentGear > MIN_GEAR) {
                    currentGear--;
                    if (currentGear >= 0)
                        setSpeed("+" + currentGear.toString());
                    else
                        setSpeed(currentGear.toString());
                }
            }
        });

        btnAutMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drivingModeManual = !drivingModeManual;

                // We deactivate some buttons if we are
                // riding in automatic and viceversa.
                for (Button button : viewsToDeactivate)
                    button.setEnabled(drivingModeManual);

                setDrivingMode(drivingModeManual ? MANUAL : AUTOMATIC);
            }
        });

        btnLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightsOn = !lightsOn;
                setLightsStatus(lightsOn ? LIGHTS_ON : LIGHTS_OFF);
            }
        });

    }

    private String shapeMapper(String name) {

        switch (name) {
            case TRIANGLE:
                return TRIANGLE;
            case CIRCLE:
                return CIRCLE;
            case SQUARE:
                return SQUARE;
            default:
                return null;
        }
    }
}
