package manel.com.manel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import manel.com.manel.R;
import manel.com.manel.activities.supports.Labyrinth;
import manel.com.manel.activities.supports.LabyrinthMother;
import manel.com.manel.activities.supports.LabyrinthView;

/**
 * An Activity with which we will keep track of the robot solving the labyrinth.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 1.0
 */
public class LabyrinthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinth);
        Labyrinth labyrinth = LabyrinthMother.getLabyrinth(1);
        LabyrinthView view = new LabyrinthView(this, labyrinth);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        layout.addView(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
