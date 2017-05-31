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
 * @version 1.0
 */
public class CommunicationLogActivity extends AppCompatActivity {

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
        textView.setText("LOG:");
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

    public static void post(String message) {
        textView.append("\n" + message);
    }
}
