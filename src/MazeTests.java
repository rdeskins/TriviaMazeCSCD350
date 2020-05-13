
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
            assertFalse(testMaze[0][i].northDoorUnlocked());
            assertFalse(testMaze[mazeSize - 1][i].southDoorUnlocked());
        }

        for (int i = 0; i < mazeSize; i++) {
            assertFalse(testMaze[i][0].westDoorUnlocked());
            assertFalse(testMaze[i][mazeSize - 1].eastDoorUnlocked());
        }

        for (int i = 0; i < mazeSize - 1; i++) {
            for (int j = 0; j < mazeSize - 1; j++) {
                assertTrue(testMaze[i][j].eastDoorUnlocked() == testMaze[i][j + 1].westDoorUnlocked());
                assertTrue(testMaze[i][j].southDoorUnlocked() == testMaze[i + 1][j].northDoorUnlocked());
            }
        }
    }

    @Test
    public void maze4x4ToString() {
        Maze maze = new Maze(4);
        String expected = "*****************\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void maze5x5ToString() {
        Maze maze = new Maze(5);
        String expected = "*********************\n" +
                          "* P |   |   |   |   *\n" +
                          "*---*---*---*---*---*\n" +
                          "*   |   |   |   |   *\n" +
                          "*---*---*---*---*---*\n" +
                          "*   |   |   |   |   *\n" +
                          "*---*---*---*---*---*\n" +
                          "*   |   |   |   |   *\n" +
                          "*---*---*---*---*---*\n" +
                          "*   |   |   |   | G *\n" +
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
                          "* P *   |   |   *\n" +
                          "*****---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void maze4x4ToStringPlayerAtRow0Column3() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field playerColumn = Maze.class.getDeclaredField("playerColumn");
        playerColumn.setAccessible(true);
        playerColumn.set(maze, 3);

        String expected = "*****************\n" +
                          "*   |   |   | P *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void PlayerMoveNorthFail() {
        Maze maze = new Maze(4);
        maze.moveNorth();
        String expected = "*****************\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void PlayerMoveNorthSuccess() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field playerRow = Maze.class.getDeclaredField("playerRow");
        playerRow.setAccessible(true);
        playerRow.set(maze, 2);
        maze.moveNorth();
        String expected = "*****************\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void PlayerMoveSouthFail() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field playerRow = Maze.class.getDeclaredField("playerRow");
        playerRow.setAccessible(true);
        playerRow.set(maze, 3);
        maze.moveSouth();
        String expected = "*****************\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "* P |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void PlayerMoveSouthSuccess() {
        Maze maze = new Maze(4);
        maze.moveSouth();
        String expected = "*****************\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void PlayerMoveWestFail() {
        Maze maze = new Maze(4);
        maze.moveWest();
        String expected = "*****************\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void PlayerMoveWestSuccess() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field playerColumn = Maze.class.getDeclaredField("playerColumn");
        playerColumn.setAccessible(true);
        playerColumn.set(maze, 3);
        maze.moveWest();
        String expected = "*****************\n" +
                          "*   |   | P |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void PlayerMoveEastFail() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field playerColumn = Maze.class.getDeclaredField("playerColumn");
        playerColumn.setAccessible(true);
        playerColumn.set(maze, 3);
        maze.moveEast();
        String expected = "*****************\n" +
                          "*   |   |   | P *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }

    @Test
    public void PlayerMoveEastSuccess() {
        Maze maze = new Maze(4);
        maze.moveEast();
        String expected = "*****************\n" +
                          "*   | P |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());
    }
}
