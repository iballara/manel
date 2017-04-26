package manel.com.manel.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import manel.com.manel.R;

public class CommunicationLogActivity extends AppCompatActivity {

    private static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        textView = (TextView) findViewById(R.id.tv_log);
    }

    public static void post(String message) {
        textView.append("\n" + message);
    }
}
