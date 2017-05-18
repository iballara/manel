package manel.com.manel.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import manel.com.manel.R;

/**
 * This Activity will record the values of the accelerometer of the robot.
 * For this version, we only show the values of the Smartphone acc as an example.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 1.0
 */
public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView tvX, tvY, tvZ;

    /**
     * OnCreate Method from Activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);
        ActionBar actBar = getSupportActionBar();
        actBar.setHomeButtonEnabled(true);
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
        tvY.setText(String.format("Y: %s", y));
        tvZ.setText(String.format("Z: %s", z));
    }
}
