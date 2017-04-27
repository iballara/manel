package manel.com.manel.activities.supports;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import manel.com.manel.R;

/**
 * This class is in charge to draw the labyrinth to the View.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 1.0
 */
public class LabyrinthView extends View {

    private int width, height, lineWidth;
    private int labyrinthSizeX, labyrinthSizeY;
    float cellWidth, cellHeight;
    float totalCellWidth, totalCellHeight;
    private int labyrinthFinishX, labyrinthFinishY;
    private Labyrinth labyrinth;
    private Activity context;
    private Paint line, red, background;


    public LabyrinthView(Context context, Labyrinth labyrinth) {
        super(context);
        this.context = (Activity)context;
        this.labyrinth = labyrinth;
        labyrinthFinishX = labyrinth.getFinalX();
        labyrinthFinishY = labyrinth.getFinalY();
        labyrinthSizeX = labyrinth.getSizeX();
        labyrinthSizeY = labyrinth.getSizeY();
        line = new Paint();
        line.setColor(getResources().getColor(R.color.line));
        red = new Paint();
        red.setColor(getResources().getColor(R.color.position));
        background = new Paint();
        background.setColor(getResources().getColor(R.color.game_bg));
        setFocusable(true);
        this.setFocusableInTouchMode(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //fill in the background
        canvas.drawRect(0, 0, width, height, background);

        boolean[][] hLines = labyrinth.getHorizontalLines();
        boolean[][] vLines = labyrinth.getVerticalLines();
        //iterate over the boolean arrays to draw walls
        for(int i = 0; i < labyrinthSizeX; i++) {
            for(int j = 0; j < labyrinthSizeY; j++){
                float x = j * totalCellWidth;
                float y = i * totalCellHeight;
                if(j < labyrinthSizeX - 1 && vLines[i][j]) {
                    //we'll draw a vertical line
                    canvas.drawLine(x + cellWidth,   //start X
                            y,               //start Y
                            x + cellWidth,   //stop X
                            y + cellHeight,  //stop Y
                            line);
                }
                if(i < labyrinthSizeY - 1 && hLines[i][j]) {
                    //we'll draw a horizontal line
                    canvas.drawLine(x,               //startX
                            y + cellHeight,  //startY
                            x + cellWidth,   //stopX
                            y + cellHeight,  //stopY
                            line);
                }
            }
        }
        int currentX = labyrinth.getCurrentX(),currentY = labyrinth.getCurrentY();
        //draw the ball
        canvas.drawCircle((currentX * totalCellWidth)+(cellWidth/2),   //x of center
                (currentY * totalCellHeight)+(cellWidth/2),  //y of center
                (cellWidth*0.45f),                           //radius
                red);
        //draw the finishing point indicator
        canvas.drawText("F",
                (labyrinthFinishX * totalCellWidth)+(cellWidth*0.25f),
                (labyrinthFinishY * totalCellHeight)+(cellHeight*0.75f),
                red);
    }
}
