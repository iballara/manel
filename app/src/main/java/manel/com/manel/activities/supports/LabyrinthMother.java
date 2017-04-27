package manel.com.manel.activities.supports;

public class LabyrinthMother {

    public static Labyrinth getLabyrinth(int num) {
        Labyrinth labyrinth = null;
        if(num == 1) {
            labyrinth = new Labyrinth();
            boolean[][] vLines = new boolean[][]{
                    {true ,false,false,false,true ,false,false},
                    {true ,false,false,true ,false,true ,true },
                    {false,true ,false,false,true ,false,false},
                    {false,true ,true ,false,false,false,true },
                    {true ,false,false,false,true ,true ,false},
                    {false,true ,false,false,true ,false,false},
                    {false,true ,true ,true ,true ,true ,false},
                    {false,false,false,true ,false,false,false}
            };
            boolean[][] hLines = new boolean[][]{
                    {false,false,true ,true ,false,false,true ,false},
                    {false,false,true ,true ,false,true ,false,false},
                    {true ,true ,false,true ,true ,false,true ,true },
                    {false,false,true ,false,true ,true ,false,false},
                    {false,true ,true ,true ,true ,false,true ,true },
                    {true ,false,false,true ,false,false,true ,false},
                    {false,true ,false,false,false,true ,false,true }
            };
            labyrinth.setVerticalLines(vLines);
            labyrinth.setHorizontalLines(hLines);
            labyrinth.setStartPosition(0, 0);
            labyrinth.setFinalPosition(7, 7);
        }
        return labyrinth;
    }
}
