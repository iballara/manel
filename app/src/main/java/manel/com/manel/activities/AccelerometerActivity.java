package manel.com.manel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import manel.com.manel.R;
import manel.com.manel.comms.CommunicationService;
import manel.com.manel.comms.udp.UdpDatagramConstructor;

/**
 * This Activity will record the values of the accelerometer of the robot.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class AccelerometerActivity extends AppCompatActivity //implements SensorEventListener
{

    private static final String ACC_ON = "1";
    private static final String ACC_OFF = "0";
    private static final long timeBetweenFrames = 500;

    private static TextView tvX, tvY, tvZ;

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
        UdpDatagramConstructor.sendAcc(ACC_ON);
        setViews();
        CommunicationService.timeBetweenFrames = timeBetweenFrames;
        if (!CommunicationService.isServiceRunning) {
            startService(new Intent(this, CommunicationService.class));
        }
    }

    /**
     * The regular onBackPressed from Activity Class
     */
    @Override
    public void onBackPressed() {
        UdpDatagramConstructor.sendAcc(ACC_OFF);
        this.finish();
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
                UdpDatagramConstructor.sendAcc(ACC_OFF);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Printing the value of the robot's acc X value
     * @param x double
     */
    public static void printAccX(double x) {
        if (tvX != null)
            tvX.setText(String.format("X: %s", x));
    }

    /**
     * Printing the value of the robot's acc Y value
     * @param y double
     */
    public static void printAccY(double y) {
        if (tvY != null)
            tvY.setText(String.format("Y: %s", y));
    }

    /**
     * Printing the value of the robot's acc Z value
     * @param z double
     */
    public static void printAccZ(double z) {
        if (tvX != null)
            tvZ.setText(String.format("Z: %s", z));
    }

    /* ************************
    **      PRIVATE METHODS
    ***************************/

    /**
     * Regular method for initializing the views.
     */
    private void setViews(){
        tvX = (TextView) findViewById(R.id.tv_X);
        tvY = (TextView) findViewById(R.id.tv_Y);
        tvZ = (TextView) findViewById(R.id.tv_Z);
    }
}
