package manel.com.manel.activities.supports;

/**
 * This class is used to return an instance of Labyrinth.
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 1.0
 */
public class LabyrinthMother {

    /**
     * Returns an instance of Labyrinth.
     * @param num int
     * @return Labyrinth
     */
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
