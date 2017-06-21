package manel.com.manel.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import manel.com.manel.R;
import manel.com.manel.activities.support.Cell;

/**
 * An Activity with which we will keep track of the robot solving the labyrinth.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 1.0
 */
public class LabyrinthActivity extends AppCompatActivity {

    GridLayout grid;

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

    /*
    **  PRIVATE METHODS
    */
    private void setViews() {

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
        getWesternWall(2,3).setBackgroundColor(Color.BLACK);
        getEasternWall(2,2).setBackgroundColor(Color.BLACK);
    }

    public View getInnerCell(int x, int y) {
        return getCell(x, y).findViewById(R.id.cell);
    }

    public View getNorthernWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.north_wall);
    }

    public View getSouthernWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.south_wall);
    }

    public View getEasternWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.east_wall);
    }

    public View getWesternWall(int x, int y) {
        return getCell(x,y).findViewById(R.id.west_wall);
    }

    public View getCell(int x, int y) {
        return getCellFromRow(getRow(x), y);
    }

    private View getRow(int x) {
        View view = new View(this);
        switch (x){
            case 0:
                view = findViewById(R.id.row_0);
            break;
            case 1:
                view = findViewById(R.id.row_1);
            break;
            case 2:
                view = findViewById(R.id.row_2);
            break;
            case 3:
                view =  findViewById(R.id.row_3);
            break;
            case 4:
                view = findViewById(R.id.row_4);
            break;
            default:
                view = null;
            break;
        }
        return view;
    }

    private View getCellFromRow(View row, int y) {
        View view = new View(this);
        switch (y){
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
                view =  row.findViewById(R.id.cell_3);
                break;
            case 4:
                view = row.findViewById(R.id.cell_4);
                break;
            default:
                view = null;
                break;
        }
        return view;
    }
}
