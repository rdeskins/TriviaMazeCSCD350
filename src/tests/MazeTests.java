/*
* Name: Chris Dobbins
* Description: Tests for Maze.java
*/

package tests;

import TriviaGame.*;
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
    public void playerMoveNorthFail() {
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
    public void playerMoveNorthSuccess() throws NoSuchFieldException, IllegalAccessException {
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
    public void playerMoveSouthFail() throws NoSuchFieldException, IllegalAccessException {
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
    public void playerMoveSouthSuccess() {
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
    public void playerMoveWestFail() {
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
    public void playerMoveWestSuccess() throws NoSuchFieldException, IllegalAccessException {
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
    public void playerMoveEastFail() throws NoSuchFieldException, IllegalAccessException {
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
    public void playerMoveEastSuccess() {
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

    @Test
    public void pathToGoalSuccess() {
        Maze maze = new Maze(4);
        assertTrue(maze.pathToGoalExists());
    }

    @Test
    public void pathToGoalLockedDoorsSuccess() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field fieldMaze = Maze.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Room[][] testMaze = (Room[][]) fieldMaze.get(maze);
        testMaze[0][0].lockEastDoor();
        testMaze[3][3].lockNorthDoor();
        testMaze[3][2].lockNorthDoor();
        testMaze[3][1].lockNorthDoor();
        testMaze[1][0].lockSouthDoor();
        testMaze[1][1].lockSouthDoor();

        String expected = "*****************\n" +
                          "* P *   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*********---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*************\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());

        assertTrue(maze.pathToGoalExists());
    }

    @Test
    public void pathToGoalLockedDoorsFail() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field fieldMaze = Maze.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Room[][] testMaze = (Room[][]) fieldMaze.get(maze);
        testMaze[0][0].lockEastDoor();
        testMaze[0][0].lockSouthDoor();

        assertFalse(maze.pathToGoalExists());
    }

    @Test
    public void pathToGoalResetsVisitedStatus() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field fieldMaze = Maze.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Room[][] testMaze = (Room[][]) fieldMaze.get(maze);
        testMaze[0][0].lockSouthDoor();
        testMaze[0][1].lockSouthDoor();
        testMaze[0][2].lockSouthDoor();
        testMaze[1][3].lockWestDoor();
        testMaze[2][3].lockWestDoor();
        testMaze[3][3].lockWestDoor();

        String expected = "*****************\n" +
                          "* P |   |   |   *\n" +
                          "*************---*\n" +
                          "*   |   |   *   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   *   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   * G *\n" +
                          "*****************";
        assertEquals(expected, maze.toString());

        assertTrue(maze.pathToGoalExists());
        assertTrue(maze.pathToGoalExists());
    }

    @Test
    public void goalNotReached() {
        Maze maze = new Maze(4);
        assertFalse(maze.goalReached());
    }

    @Test
    public void goalReached() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field playerRow = Maze.class.getDeclaredField("playerRow");
        Field playerColumn = Maze.class.getDeclaredField("playerColumn");
        playerRow.setAccessible(true);
        playerColumn.setAccessible(true);
        playerRow.set(maze, 3);
        playerColumn.set(maze, 3);

        assertTrue(maze.goalReached());
    }

    @Test
    public void playerNorthDoorUnlockedFalse() {
        Maze maze = new Maze(4);

        assertFalse(maze.playerNorthDoorUnlocked());
    }

    @Test
    public void playerNorthDoorUnlockedTrue() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field playerRow = Maze.class.getDeclaredField("playerRow");
        playerRow.setAccessible(true);
        playerRow.set(maze, 1);

        assertTrue(maze.playerNorthDoorUnlocked());
    }
    
    @Test
    public void playerSouthDoorUnlockedFalse() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field playerRow = Maze.class.getDeclaredField("playerRow");
        playerRow.setAccessible(true);
        playerRow.set(maze, 3);

        assertFalse(maze.playerSouthDoorUnlocked());
    }

    @Test
    public void playerSouthDoorUnlockedTrue() {
        Maze maze = new Maze(4);

        assertTrue(maze.playerSouthDoorUnlocked());
    }

    @Test
    public void playerWestDoorUnlockedFalse() {
        Maze maze = new Maze(4);

        assertFalse(maze.playerWestDoorUnlocked());
    }

    @Test
    public void playerWestDoorUnlockedTrue() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field playerColumn = Maze.class.getDeclaredField("playerColumn");
        playerColumn.setAccessible(true);
        playerColumn.set(maze, 1);

        assertTrue(maze.playerWestDoorUnlocked());
    }

    @Test
    public void playerEastDoorUnlockedFalse() throws NoSuchFieldException, IllegalAccessException {
        Maze maze = new Maze(4);
        Field playerColumn = Maze.class.getDeclaredField("playerColumn");
        playerColumn.setAccessible(true);
        playerColumn.set(maze, 3);

        assertFalse(maze.playerEastDoorUnlocked());
    }

    @Test
    public void playerEastDoorUnlockedTrue() {
        Maze maze = new Maze(4);

        assertTrue(maze.playerEastDoorUnlocked());
    }

}
