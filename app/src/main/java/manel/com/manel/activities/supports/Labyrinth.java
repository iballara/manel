package manel.com.manel.activities.supports;

import java.io.Serializable;


public class Labyrinth implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;

    private boolean[][] verticalLines;
    private boolean[][] horizontalLines;
    private int sizeX, sizeY;         //stores the width and height of the maze
    private int currentX, currentY;   //stores the current location of the ball
    private int startX, startY;
    private int finalX, finalY;       //stores the finishing point of the maze
    private boolean gameComplete;

    public boolean[][] getVerticalLines() {
        return verticalLines;
    }

    public void setVerticalLines(boolean[][] verticalLines) {
        this.verticalLines = verticalLines;
    }

    public boolean[][] getHorizontalLines() {
        return horizontalLines;
    }

    public void setHorizontalLines(boolean[][] horizontalLines) {
        this.horizontalLines = horizontalLines;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getFinalX() {
        return finalX;
    }

    public void setFinalX(int finalX) {
        this.finalX = finalX;
    }

    public int getFinalY() {
        return finalY;
    }

    public void setFinalY(int finalY) {
        this.finalY = finalY;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public void setGameComplete(boolean gameComplete) {
        this.gameComplete = gameComplete;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }


    public void setStartPosition(int i, int j) {
        startX = i;
        startY = j;
    }


    public void setFinalPosition(int i, int j) {
        finalX = i;
        finalY = j;
    }
}
