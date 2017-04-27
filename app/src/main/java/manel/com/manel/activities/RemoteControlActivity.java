package manel.com.manel.activities;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import manel.com.manel.R;

/**
 * Activty to control the robot. It also receives information from it
 * like temperature or velocity.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 1.0
 */
public class RemoteControlActivity extends AppCompatActivity
        implements GestureOverlayView.OnGesturePerformedListener{

    private GestureLibrary gestureLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GestureOverlayView gestureOverlayView = new GestureOverlayView(this);
        View inflate = getLayoutInflater().inflate(R.layout.activity_remote_control, null);
        gestureOverlayView.addView(inflate);
        gestureOverlayView.addOnGesturePerformedListener(this);
        gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gestureLib.load()) {
            System.out.println("GestureLib not loaded");
            finish();
        }
        setContentView(gestureOverlayView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
        for (Prediction prediction : predictions) {
            if (prediction.score > 1.0 && prediction.name.length()>1) {
                Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
