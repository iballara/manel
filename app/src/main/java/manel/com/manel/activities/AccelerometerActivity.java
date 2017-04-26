package manel.com.manel.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import manel.com.manel.R;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView tvX, tvY, tvZ;
    private double ax,ay,az;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        setViews();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            printACCdata(event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /*
        PRIVATE METHODS
    */
    private void setViews(){
        tvX = (TextView) findViewById(R.id.tv_X);
        tvY = (TextView) findViewById(R.id.tv_Y);
        tvZ = (TextView) findViewById(R.id.tv_Z);
    }

    private void printACCdata(double x, double y, double z){
        tvX.setText(String.format("X: %s", x));
        tvY.setText(String.format("X: %s", y));
        tvZ.setText(String.format("X: %s", z));
    }
}
