package manel.com.manel.activities.support;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class Cell extends LinearLayout {

    private View nortWall;
    private View eastWall;
    private View westWall;
    private View southWall;
    private View cell;


    public Cell(Context context) {
        super(context);
        setViews();
    }

    private void setViews() {

    }
}
