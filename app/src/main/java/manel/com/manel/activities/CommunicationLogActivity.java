package manel.com.manel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import manel.com.manel.R;
import manel.com.manel.comms.CommunicationService;

/**
 * This Activity is in charge of Logging the UDP packets received.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class CommunicationLogActivity extends AppCompatActivity {

    private static final long timeBetweenFrames = 500;
    private static String entireMessage = "";
    private static TextView textView;

    /**
     * OnCreate Method from Activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ActionBar actBar = getSupportActionBar();
        actBar.setHomeButtonEnabled(true);
        textView = (TextView) findViewById(R.id.tv_log);
        textView.setText(R.string.log);
        CommunicationService.timeBetweenFrames = timeBetweenFrames;
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

    @Override
    public void onResume() {
        super.onResume();
        if (textView != null)
            textView.append(entireMessage);
    }

    /**
     * Method for adding a new row to the log register.
     * @param message String
     */
    public static void addLogRow(String message) {
        entireMessage += message;
        if (textView != null)
            textView.append("\n" + message);
    }
}
