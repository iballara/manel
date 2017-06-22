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
 * We paint the labyrinth as the robot simultaenously solves it.
 * Valid cells are painted green while invalid cells are painted red.
 * The background of the actual cell in which the robot is at each moment is painted cyan.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class LabyrinthActivity extends AppCompatActivity {

    private final static String LAST_CELL_OK = "441";
    private static final long timeBetweenFrames = 5000;
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
        CommunicationService.timeBetweenFrames = timeBetweenFrames;
        if (!CommunicationService.isServiceRunning) {
            startService(new Intent(this, CommunicationService.class));
        }
    }

    /**
     * Setting bar arrow to return to MainMenu when clicked.
     *
     * @param item MenuItem
     * @return boolean
     */
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

    /**
     * Returns the central square within a cell, without the walls.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getInnerCell(int x, int y) {
        return getCell(x, y).findViewById(R.id.cell);
    }

    /**
     * Returns a cell, with the 4 walls and the inner rectangle.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getCell(int x, int y) {
        return getCellFromRow(getRow(x), y);
    }

    /**
     * Returns the top wall of the cell specified by x and y.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getNorthernWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.north_wall);
    }

    /**
     * Returns the south wall of the cell specified by x and y.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getSouthernWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.south_wall);
    }

    /**
     * Returns the left wall of the cell specified by x and y.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getEasternWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.east_wall);
    }

    /**
     * Returns the right wall of the cell specified by x and y.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getWesternWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.west_wall);
    }

    /**
     * Paints the cell in which Manel is currently on.
     *
     * @param value String
     */
    public static void setActualCell(String value) {

        int x = Integer.parseInt(value.substring(0,1));
        int y = Integer.parseInt(value.substring(1,2));

        View view = getCell(x, y);

        if (view != null) {
            actualCell.setBackgroundColor(Color.WHITE);
            actualCell = view;
            view.setBackgroundColor(Color.CYAN);
        }

        // Setting the last cell (4,4) to valid.
        if (x == 4 && y == 4) {
            secondRideBtn.setEnabled(true);
            setLastCell(LAST_CELL_OK);
        }
    }

    /**
     * Paints the last cell in which Manel has been.
     *
     * @param value String
     */
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

    /**
     * Sets the northern wall of the actual cell to visible or not, depending on "value" string.
     * @param value String -> "1" means there is a wall.
     */
    public static void setNorth(String value) {
        if (actualCell != null) {
            if (value.equals("1")) {
                actualCell.findViewById(R.id.north_wall).setBackgroundColor(Color.BLACK);
            }
        }
    }

    /**
     * Sets the southern wall of the actual cell to visible or not, depending on "value" string.
     * @param value String -> "1" means there is a wall.
     */
    public static void setSouth(String value) {
        if (actualCell != null) {
            if (value.equals("1")) {
                actualCell.findViewById(R.id.south_wall).setBackgroundColor(Color.BLACK);
            }
        }
    }

    /**
     * Sets the eastern wall of the actual cell to visible or not, depending on "value" string.
     * @param value String -> "1" means there is a wall.
     */
    public static void setEast(String value) {
        if (actualCell != null) {
            if (value.equals("1")) {
                actualCell.findViewById(R.id.east_wall).setBackgroundColor(Color.BLACK);
            }
        }
    }

    /**
     * Sets the western wall of the actual cell to visible or not, depending on "value" string.
     * @param value String -> "1" means there is a wall.
     */
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

    /**
     * Classic method for setting up the views.
     */
    private void setViews() {
        layout = (LinearLayout) findViewById(R.id.labyrinth_layout);

        //We set the first cell to (0,0).
        actualCell = getCell(0,0);

        // We first paint all the walls BLACK.
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

    /**
     * This method encapsulates the logic for getting a row 1x5 og the labyrinth.
     *
     * @param x int -> referring to the row.
     * @return View -> The row.
     */
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

    /**
     * This method encapsulates the logic for getting a cell within a row.
     *
     * @param row View -> The row in which we'll search for the cell.
     * @param y int -> The column we select.
     * @return View -> The cell we return.
     */
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
