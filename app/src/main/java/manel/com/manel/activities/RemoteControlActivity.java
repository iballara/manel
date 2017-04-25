package manel.com.manel.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import manel.com.manel.R;

public class RemoteControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
