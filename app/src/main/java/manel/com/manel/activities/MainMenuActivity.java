package manel.com.manel.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import manel.com.manel.R;
import manel.com.manel.comms.udp.UdpDatagramDeconstructor;
import manel.com.manel.utils.ui.OverviewCardsAdapter;

import static manel.com.manel.comms.udp.UdpDatagramHelper.parseReceivedMessage;

/**
 * Main Activity. It is just a menu in order to decide what to do next.
 * It show the four main options and working modes of the app.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class MainMenuActivity extends AppCompatActivity{

    public static Handler uiHandler;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * OnCreate Method from Activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setViews();
        uiHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                CommunicationLogActivity.addLogRow((String) msg.obj);
                HashMap<String, String> data = parseReceivedMessage((String) msg.obj);
                UdpDatagramDeconstructor.receiveData(data);
            }
        };
    }

    /* *************************
    **    PRIVATE METHODS
    ****************************/

    /**
     * Sets the initial configuration of the views.
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
    }
}
