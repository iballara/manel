package manel.com.manel.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import manel.com.manel.R;
import manel.com.manel.comms.CommunicationService;
import manel.com.manel.comms.udp.UdpDatagramConstructor;
import manel.com.manel.utils.Constants;

/**
 * An Activity with which we will keep track of the robot solving the labyrinth.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class LabyrinthActivity extends AppCompatActivity {

    private final static String LAST_CELL_OK = "441";
    private static LinearLayout layout;
    private static View actualCell;
    private static Button secondRideBtn;

    /**
     * OnCreate Method from Activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinth);
        ActionBar actBar = getSupportActionBar();
        actBar.setHomeButtonEnabled(true);
        setViews();
        if (!CommunicationService.isServiceRunning) {
            startService(new Intent(this, CommunicationService.class));
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static View getInnerCell(int x, int y) {
        return getCell(x, y).findViewById(R.id.cell);
    }

    public static View getNorthernWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.north_wall);
    }

    public static View getSouthernWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.south_wall);
    }

    public static View getEasternWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.east_wall);
    }

    public static View getWesternWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.west_wall);
    }

    public static View getCell(int x, int y) {
        return getCellFromRow(getRow(x), y);
    }

    public static void setActualCell(String value) {

        int x = Integer.parseInt(value.substring(0,1));
        int y = Integer.parseInt(value.substring(1,2));

        View view = getCell(x, y);

        if (view != null) {
            actualCell.setBackgroundColor(Color.WHITE);
            actualCell = view;
            view.setBackgroundColor(Color.CYAN);
        }

        if (x == 4 && y == 4) {
            secondRideBtn.setEnabled(true);
            setLastCell(LAST_CELL_OK);
        }
    }

    public static void setLastCell(String value) {

        View view = getCell(
                Integer.parseInt(value.substring(0,1)),
                Integer.parseInt(value.substring(1,2))
        );

        if (view != null) {
            if (value.substring(2,3).equals("1"))
                view.findViewById(R.id.cell).setBackgroundColor(Color.GREEN);
            else if (value.substring(2,3).equals("0"))
                view.findViewById(R.id.cell).setBackgroundColor(Color.RED);
        }
    }

    public static void setNorth(String value) {
        if (actualCell != null) {
            if (value.equals("1")) {
                actualCell.findViewById(R.id.north_wall).setBackgroundColor(Color.BLACK);
            }
        }
    }

    public static void setSouth(String value) {
        if (actualCell != null) {
            if (value.equals("1")) {
                actualCell.findViewById(R.id.south_wall).setBackgroundColor(Color.BLACK);
            }
        }
    }

    public static void setEast(String value) {
        if (actualCell != null) {
            if (value.equals("1")) {
                actualCell.findViewById(R.id.east_wall).setBackgroundColor(Color.BLACK);
            }
        }
    }

    public static void setWest(String value) {
        if (actualCell != null) {
            if (value.equals("1")) {
                actualCell.findViewById(R.id.west_wall).setBackgroundColor(Color.BLACK);
            }
        }
    }

    /*
    **  PRIVATE FUNCTIONS
    */

    private void setViews() {
        layout = (LinearLayout) findViewById(R.id.labyrinth_layout);
        actualCell = getCell(0,0);
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++){
                getNorthernWall(i,j).setBackgroundColor(Color.WHITE);
                getSouthernWall(i,j).setBackgroundColor(Color.WHITE);
                getEasternWall(i,j).setBackgroundColor(Color.WHITE);
                getWesternWall(i,j).setBackgroundColor(Color.WHITE);
            }
        }
        getCell(0,0).setBackgroundColor(Color.CYAN);
        getInnerCell(0,0).setBackgroundColor(Color.GREEN);

        Button firstRideBtn = (Button) findViewById(R.id.first_ride_btn);
        secondRideBtn = (Button) findViewById(R.id.second_ride_btn);
        secondRideBtn.setEnabled(false);

        firstRideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpDatagramConstructor.sendLabyrinth(Constants.RemoteControl.FIRST_RIDE);
            }
        });

        secondRideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpDatagramConstructor.sendLabyrinth(Constants.RemoteControl.SECOND_RIDE);
            }
        });
    }

    private static View getRow(int x) {
        View view;
        switch (x){
            case 0:
                view = layout.findViewById(R.id.row_0);
            break;
            case 1:
                view = layout.findViewById(R.id.row_1);
            break;
            case 2:
                view = layout.findViewById(R.id.row_2);
            break;
            case 3:
                view =  layout.findViewById(R.id.row_3);
            break;
            case 4:
                view = layout.findViewById(R.id.row_4);
            break;
            default:
                view = null;
            break;
        }
        return view;
    }

    private static View getCellFromRow(View row, int y) {
        View view;
        if (row != null) {
            switch (y) {
                case 0:
                    view = row.findViewById(R.id.cell_0);
                    break;
                case 1:
                    view = row.findViewById(R.id.cell_1);
                    break;
                case 2:
                    view = row.findViewById(R.id.cell_2);
                    break;
                case 3:
                    view = row.findViewById(R.id.cell_3);
                    break;
                case 4:
                    view = row.findViewById(R.id.cell_4);
                    break;
                default:
                    view = null;
                    break;
            }
            return view;
        } else {
            return null;
        }
    }
}
