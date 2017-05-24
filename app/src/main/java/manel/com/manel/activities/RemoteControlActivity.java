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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import manel.com.manel.R;
import manel.com.manel.utils.Constants;

import static java.util.Arrays.asList;
import static manel.com.manel.utils.Constants.RemoteControl.ANGLE_LEFT_1;
import static manel.com.manel.utils.Constants.RemoteControl.ANGLE_RIGHT_1;
import static manel.com.manel.utils.Constants.RemoteControl.ANGLE_RIGHT_2;
import static manel.com.manel.utils.Constants.RemoteControl.ANGLE_ZERO;
import static manel.com.manel.utils.Constants.RemoteControl.AUTOMATIC_MODE;
import static manel.com.manel.utils.Constants.RemoteControl.MANUAL_MODE;
import static manel.com.manel.utils.Constants.RemoteControl.STOPPED;

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
    private Boolean ligthsOn;

    // Views
    TextView tvTemperature, tvSpeed;
    Button btnGearsPlus, btnGearsMinus, btnAutMan, btnLights, btnAccelerate;
    View bumperLeft, bumperCenter, bumperRight;

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
        this.ligthsOn = false;
        setContentView(gestureOverlayView);
        setViews();
    }

    public static void writeData() {

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
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            double y = event.values[1];
            int angle;
            if (y < -6) {
                angle = Constants.RemoteControl.ANGLE_LEFT_2;
            } else if (-6 < y && y < -3) {
                angle = ANGLE_LEFT_1;
            } else if (-3 < y && y < 3) {
                angle = ANGLE_ZERO;
            } else if (3 <  y && y < 6) {
                angle = ANGLE_RIGHT_1;
            } else {
                angle = ANGLE_RIGHT_2;
            }

            //TODO: enviar al robot.

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

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
                currentGear++;
            }
        });

        btnGearsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentGear--;
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
            }
        });


    }
}
