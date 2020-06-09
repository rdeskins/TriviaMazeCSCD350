/*
* Name: Chris Dobbins
* Description: Tests for TriviaGame.java
*/

package tests;

import TriviaGame.*;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.reflect.*;

public class TriviaGameTests {
    private static String databaseName = "test.db";
    private static String saveName = "testSave.txt";
    private static Database db;

    @BeforeClass
    public static void beforeClass() throws SQLException {
        db = new Database(databaseName);
        db.initialize();
        db.insertQuestion(1, 1, new Question("question", "answer"));
    }

    @AfterClass
    public static void afterClass() {
        File file = new File (databaseName);
        if (file.exists()) {
            file.delete();
        }

        file = new File (saveName);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void checkAnswerTrue() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new TriviaGame(new Maze(4), db, 2);
        Method sut = TriviaGame.class.getDeclaredMethod("checkAnswer", String.class, Question.class);
        sut.setAccessible(true);

        boolean result = (boolean) sut.invoke(game, "answer", new Question("question", "answer"));

        assertTrue(result);
    }

    @Test
    public void checkAnswerFalse() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new TriviaGame(new Maze(4), db, 2);
        Method sut = TriviaGame.class.getDeclaredMethod("checkAnswer", String.class, Question.class);
        sut.setAccessible(true);

        boolean result = (boolean) sut.invoke(game, "wrong answer", new Question("question", "answer"));

        assertFalse(result);
    }

    @Test
    public void checkAnswerUsingCheatTrue() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new TriviaGame(new Maze(4), db, 2);
        Method sut = TriviaGame.class.getDeclaredMethod("checkAnswer", String.class, Question.class);
        sut.setAccessible(true);

        boolean result = (boolean) sut.invoke(game, "cscd350", new Question("question", "answer"));

        assertTrue(result);
    }

    @Test
    public void askQuestionTrue() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("askQuestion");
        sut.setAccessible(true);

        boolean result = (boolean) sut.invoke(game);

        assertTrue(result);
    }

    @Test
    public void askQuestionFalse() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("wrong answer");
        Method sut = TriviaGame.class.getDeclaredMethod("askQuestion");
        sut.setAccessible(true);

        boolean result = (boolean) sut.invoke(game);

        assertFalse(result);
    }

    @Test
    public void askQuestionUsingCheatTrue() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("cscd350");
        Method sut = TriviaGame.class.getDeclaredMethod("askQuestion");
        sut.setAccessible(true);

        boolean result = (boolean) sut.invoke(game);

        assertTrue(result);
    }

    @Test
    public void endConditionMetFalse() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new TriviaGame(new Maze(4), db, 2);
        Method sut = TriviaGame.class.getDeclaredMethod("endConditionMet");
        sut.setAccessible(true);

        boolean result = (boolean) sut.invoke(game);

        assertFalse(result);
    }

    @Test
    public void endConditionMetLoseTrue() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Method sut = TriviaGame.class.getDeclaredMethod("endConditionMet");
        sut.setAccessible(true);
        Maze maze = new Maze(4);
        Field fieldMaze = Maze.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Room[][] testMaze = (Room[][]) fieldMaze.get(maze);
        testMaze[0][0].lockEastDoor();
        testMaze[0][0].lockSouthDoor();
        TriviaGame game = new TriviaGame(maze, db, 2);

        boolean result = (boolean) sut.invoke(game);

        assertTrue(result);
    }

    @Test
    public void endConditionMetWinTrue() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Method sut = TriviaGame.class.getDeclaredMethod("endConditionMet");
        sut.setAccessible(true);
        Maze maze = new Maze(4);
        Field playerRow = Maze.class.getDeclaredField("playerRow");
        Field playerColumn = Maze.class.getDeclaredField("playerColumn");
        playerRow.setAccessible(true);
        playerColumn.setAccessible(true);
        playerRow.set(maze, 3);
        playerColumn.set(maze, 3);
        TriviaGame game = new TriviaGame(maze, db, 2);

        boolean result = (boolean) sut.invoke(game);

        assertTrue(result);
    }

    @Test
    public void attemptMoveNorthNoChangeDoorAlreadyLocked() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveNorth");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        String expected = "*****************\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);
        Maze maze = (Maze) fieldMaze.get(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveNorthSuccess() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveNorth");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Maze maze = (Maze) fieldMaze.get(game);
        Field playerRow = Maze.class.getDeclaredField("playerRow");
        playerRow.setAccessible(true);
        playerRow.set(maze, 2);
        String expected = "*****************\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveNorthFailLockDoor() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("wrong answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveNorth");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Maze maze = (Maze) fieldMaze.get(game);
        Field playerRow = Maze.class.getDeclaredField("playerRow");
        playerRow.setAccessible(true);
        playerRow.set(maze, 2);
        String expected = "*****************\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*****---*---*---*\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveSouthNoChangeDoorAlreadyLocked() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveSouth");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Maze maze = (Maze) fieldMaze.get(game);
        Field playerRow = Maze.class.getDeclaredField("playerRow");
        playerRow.setAccessible(true);
        playerRow.set(maze, 3);
        String expected = "*****************\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "* P |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveSouthSuccess() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveSouth");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        String expected = "*****************\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);
        Maze maze = (Maze) fieldMaze.get(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveSouthFailLockDoor() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("wrong answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveSouth");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        String expected = "*****************\n" +
                          "* P |   |   |   *\n" +
                          "*****---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);
        Maze maze = (Maze) fieldMaze.get(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveWestNoChangeDoorAlreadyLocked() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveWest");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        String expected = "*****************\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);
        Maze maze = (Maze) fieldMaze.get(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveWestSuccess() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveWest");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Maze maze = (Maze) fieldMaze.get(game);
        Field playerColumn = Maze.class.getDeclaredField("playerColumn");
        playerColumn.setAccessible(true);
        playerColumn.set(maze, 2);
        String expected = "*****************\n" +
                          "*   | P |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveWestFailLockDoor() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("wrong answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveWest");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Maze maze = (Maze) fieldMaze.get(game);
        Field playerColumn = Maze.class.getDeclaredField("playerColumn");
        playerColumn.setAccessible(true);
        playerColumn.set(maze, 2);
        String expected = "*****************\n" +
                          "*   |   * P |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveEastNoChangeDoorAlreadyLocked() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveEast");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Maze maze = (Maze) fieldMaze.get(game);
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
        
        sut.invoke(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveEastSuccess() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveEast");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        String expected = "*****************\n" +
                          "*   | P |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);
        Maze maze = (Maze) fieldMaze.get(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void attemptMoveEastFailLockDoor() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("wrong answer");
        Method sut = TriviaGame.class.getDeclaredMethod("attemptMoveEast");
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        String expected = "*****************\n" +
                          "* P *   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";
        
        sut.invoke(game);
        Maze maze = (Maze) fieldMaze.get(game);

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void takeTurnQuitReturnTrue() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new TriviaGame(new Maze(4), db, 2);
        Method sut = TriviaGame.class.getDeclaredMethod("takeTurn", String.class);
        sut.setAccessible(true);

        boolean result = (boolean) sut.invoke(game, "q");

        assertTrue(result);
    }

    @Test
    public void takeTurnMoveNorthReturnFalse() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("takeTurn", String.class);
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        String expected = "*****************\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";

        boolean result = (boolean) sut.invoke(game, "w");
        Maze maze = (Maze) fieldMaze.get(game);

        assertFalse(result);
        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void takeTurnMoveSouthReturnFalse() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("takeTurn", String.class);
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        String expected = "*****************\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";

        boolean result = (boolean) sut.invoke(game, "s");
        Maze maze = (Maze) fieldMaze.get(game);

        assertFalse(result);
        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void takeTurnMoveWestReturnFalse() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("takeTurn", String.class);
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        String expected = "*****************\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";

        boolean result = (boolean) sut.invoke(game, "a");
        Maze maze = (Maze) fieldMaze.get(game);

        assertFalse(result);
        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void takeTurnMoveEastReturnFalse() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("takeTurn", String.class);
        sut.setAccessible(true);
        Field fieldMaze = TriviaGame.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        String expected = "*****************\n" +
                          "*   | P |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";

        boolean result = (boolean) sut.invoke(game, "d");
        Maze maze = (Maze) fieldMaze.get(game);

        assertFalse(result);
        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void takeTurnSaveGameReturnFalse() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("takeTurn", String.class);
        sut.setAccessible(true);

        boolean result = (boolean) sut.invoke(game, "e");

        assertFalse(result);
    }

    @Test
    public void takeTurnInvalidInputReturnFalse() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("takeTurn", String.class);
        sut.setAccessible(true);

        boolean result = (boolean) sut.invoke(game, "blah");

        assertFalse(result);
    }

    @Test
    public void saveGameSuccess() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new TriviaGame(new Maze(4), db, 2);
        Method sut = TriviaGame.class.getDeclaredMethod("saveGame");
        sut.setAccessible(true);
        Field field = TriviaGame.class.getDeclaredField("saveName");
        field.setAccessible(true);
        field.set(game, saveName);

        sut.invoke(game);

        Maze maze = null;

        try {
            FileInputStream file = new FileInputStream(saveName);
            ObjectInputStream in = new ObjectInputStream(file);

            maze = (Maze) in.readObject();

            in.close();
            file.close();
        }
        catch (Exception e) {
            fail();
        }

        String expected = "*****************\n" +
                          "* P |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";

        assertTrue(expected.equals(maze.toString()));
    }

    @Test
    public void saveGameDoorsSharedAfterLoadSuccess() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Maze maze = new Maze(4);
        Method lockDoor = Maze.class.getDeclaredMethod("lockEastDoor");
        lockDoor.setAccessible(true);
        lockDoor.invoke(maze);
        TriviaGame game = new TriviaGame(maze, db, 2);
        Method sut = TriviaGame.class.getDeclaredMethod("saveGame");
        sut.setAccessible(true);
        Field field = TriviaGame.class.getDeclaredField("saveName");
        field.setAccessible(true);
        field.set(game, saveName);
        
        sut.invoke(game);
        
        Maze maze2 = null;
        
        try {
            FileInputStream file = new FileInputStream(saveName);
            ObjectInputStream in = new ObjectInputStream(file);
            
            maze2 = (Maze) in.readObject();
            
            in.close();
            file.close();
        }
        catch (Exception e) {
            fail();
        }
        
        assertFalse(maze2.playerEastDoorUnlocked());
        Field fieldMaze = Maze.class.getDeclaredField("maze");
        fieldMaze.setAccessible(true);
        Room[][] internalMaze = (Room[][]) fieldMaze.get(maze2);
        Room room1 = internalMaze[0][0];
        Room room2 = internalMaze[0][1];
        Field eastDoor = Room.class.getDeclaredField("eastDoor");
        eastDoor.setAccessible(true);
        Field westDoor = Room.class.getDeclaredField("westDoor");
        westDoor.setAccessible(true);
        Door door1 = (Door) eastDoor.get(room1);
        Door door2 = (Door) westDoor.get(room2);
        assertEquals(door1, door2);
        assertFalse(door1.isUnlocked());
        assertFalse(door2.isUnlocked());
    }

    @Test
    public void saveGamePlayerLocationSaved() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        TriviaGame game = new MockTriviaGame("answer");
        Method sut = TriviaGame.class.getDeclaredMethod("saveGame");
        sut.setAccessible(true);
        Field field = TriviaGame.class.getDeclaredField("saveName");
        field.setAccessible(true);
        field.set(game, saveName);

        Method moveEast = TriviaGame.class.getDeclaredMethod("takeTurn", String.class);
        moveEast.setAccessible(true);
        moveEast.invoke(game, "d");

        sut.invoke(game);

        Maze maze = null;

        try {
            FileInputStream file = new FileInputStream(saveName);
            ObjectInputStream in = new ObjectInputStream(file);

            maze = (Maze) in.readObject();

            in.close();
            file.close();
        }
        catch (Exception e) {
            fail();
        }

        String expected = "*****************\n" +
                          "*   | P |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   |   *\n" +
                          "*---*---*---*---*\n" +
                          "*   |   |   | G *\n" +
                          "*****************";

        assertTrue(expected.equals(maze.toString()));
    }

    private class MockTriviaGame extends TriviaGame {
        private String userAnswer;

        private MockTriviaGame(String userAnswer) {
            super(new Maze(4), db, 2);
            this.userAnswer = userAnswer;
        }

        @Override
        protected String getInput() {
            return this.userAnswer;
        }
    }

}
