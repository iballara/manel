package manel.com.manel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import manel.com.manel.activities.supports.Labyrinth;
import manel.com.manel.activities.supports.LabyrinthView;

public class LabyrinthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();    //get the intent extras
        Labyrinth maze = (Labyrinth)extras.get("maze");  //retrieve the maze from intent extras
        LabyrinthView view = new LabyrinthView(this,maze);
        setContentView(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
