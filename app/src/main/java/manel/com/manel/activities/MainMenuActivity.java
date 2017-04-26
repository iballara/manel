package manel.com.manel.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import manel.com.manel.R;
import manel.com.manel.comms.CommunicationService;
import manel.com.manel.utils.OverviewCardsAdapter;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommunicationService service;
    private Button accButton, logButton, remConButton, labyrinthButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setViews();
        startService(new Intent(this, CommunicationService.class));
    }

    /*
        PRIVATE METHODS
     */

    private void setViews() {

        ActionBar actBar = getSupportActionBar();
        actBar.setTitle("MANEL");
        // FIXME: Queda pendent mirar com es fa ara, sense deprecar
        actBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(
                R.color.home_page)));

        // RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<Integer> myDataSet = new ArrayList<Integer>();
        myDataSet.add(OverviewCardsAdapter.REMOTE_CONTROL_CASE);
        myDataSet.add(OverviewCardsAdapter.LABYRINTH_CASE);
        myDataSet.add(OverviewCardsAdapter.ACCELEROMETER_CASE);
        myDataSet.add(OverviewCardsAdapter.LOG_CASE);
        mAdapter = new OverviewCardsAdapter(this, myDataSet);
        mRecyclerView.setAdapter(mAdapter);

        //accButton = (Button) findViewById(R.id.)

    }

    private void startIntent(Class<?> clazz) {
        Intent intent = new Intent(MainMenuActivity.this, clazz);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }
}
