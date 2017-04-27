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
        Bundle extras = intent.getExtras();
        Labyrinth labyrinth = (Labyrinth)extras.get("labyrinth");
        LabyrinthView view = new LabyrinthView(this, labyrinth);
        setContentView(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
