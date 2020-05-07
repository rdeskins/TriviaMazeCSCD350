
import static org.junit.Assert.*;
import org.junit.Test;
import java.lang.reflect.*;

public class MazeTests {

    @Test
    public void create4x4MazeSuccess() {
        Maze maze = new Maze(4);
        assertNotNull(maze);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create3x3MazeFail() {
        Maze maze = new Maze(3);
    }

    @Test
    public void initializeMazeSuccess() throws NoSuchFieldException, IllegalAccessException {
        int mazeSize = 4;
        Maze maze = new Maze(mazeSize);
        Field fieldMaze = Maze.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Room[][] testMaze = (Room[][]) fieldMaze.get(maze);
        
        for (int i = 0; i < mazeSize; i++) {
            assertFalse(testMaze[0][i].northDoorStatus());
            assertFalse(testMaze[mazeSize - 1][i].southDoorStatus());
        }

        for (int i = 0; i < mazeSize; i++) {
            assertFalse(testMaze[i][0].westDoorStatus());
            assertFalse(testMaze[i][mazeSize - 1].eastDoorStatus());
        }

        for (int i = 0; i < mazeSize - 1; i++) {
            for (int j = 0; j < mazeSize - 1; j++) {
                assertTrue(testMaze[i][j].eastDoorStatus() == testMaze[i][j + 1].westDoorStatus());
                assertTrue(testMaze[i][j].southDoorStatus() == testMaze[i + 1][j].northDoorStatus());
            }
        }
    }

    @Test
    public void maze4x4ToString() {
        Maze maze = new Maze(4);
        String expected = "*****************\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void maze5x5ToString() {
        Maze maze = new Maze(5);
        String expected = "*********************\n" +
                          "*   |   |   |   |   *\n" +
                          "*---*---*---*---*---*\n" +
                          "*   |   |   |   |   *\n" +
                          "*---*---*---*---*---*\n" +
                          "*   |   |   |   |   *\n" +
                          "*---*---*---*---*---*\n" +
                          "*   |   |   |   |   *\n" +
                          "*---*---*---*---*---*\n" +
                          "*   |   |   |   |   *\n" +
                          "*********************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void maze4x4ToStringFirstRoomLocked() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field fieldMaze = Maze.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Room[][] testMaze = (Room[][]) fieldMaze.get(maze);
        testMaze[0][0].lockEastDoor();
        testMaze[0][0].lockSouthDoor();

        String expected = "*****************\n" +
                          "*   *   |   |   *\n" +
                          "*****---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }
}
