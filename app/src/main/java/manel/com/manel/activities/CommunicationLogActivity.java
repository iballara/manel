package manel.com.manel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        textView = (TextView) findViewById(R.id.tv_log);
        textView.setText("LOG:");
        startService(new Intent(this, CommunicationService.class));
    }

    public static void addLogRow(final String message) {

        textView.append("\nLog: " + message);
    }
}
