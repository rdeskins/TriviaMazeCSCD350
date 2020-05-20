/*
* Name: Robin Deskins
* Description: Tests for Database.java
*/

import static org.junit.Assert.*;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseTests {
    private static final String databaseName = "triviaTest.db";
    private static Database db;
    private static Connection c;
    private static Statement stmt;

    @BeforeClass
    public static void setUp() throws SQLException {
        deleteDatabase();
        db = new Database(databaseName);
        c = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
        stmt = c.createStatement();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        deleteDatabase();
        stmt.close();
        c.close();
    }

    public static void deleteDatabase() {
        File file = new File (databaseName);
        if (file.exists()) {
            file.delete();
        }
    }

    @After
    public void clearQuestions() throws SQLException {
        String sql = "DELETE FROM Questions;";
        stmt.executeUpdate(sql);
    }
    
    @Test
    public void insertQuestionTest() {
        try {
            db.insertQuestion(0,0,"question","answer");
            String sql = "SELECT question_text, answer_text FROM Questions;";
            ResultSet rs = stmt.executeQuery(sql);
            assertEquals("question", rs.getString("question_text"));
            assertEquals("answer", rs.getString("answer_text"));
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getRandomQuestionTest() {
        try {
            for (int i = 0; i < 5; i ++) {
                db.insertQuestion(0, 0, "question" + i, "answer" + i);
            }

            String[] result = db.getRandomQuestion();
            assertTrue(result[0].contains("question"));
            assertTrue(result[1].contains("answer"));
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void emptyDatabaseRandomQuestionTest() {
        try {
            db.getRandomQuestion();
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void getInvalidQuestion() {
        try {
            db.insertQuestion(0, 0, "q", "a");
            db.getQuestion(10);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getSpecificQuestionTest() {
        try {
            for (int i = 0; i < 5; i++) {
                db.insertQuestion(0, 0, "question" + i, "answer" + i);
            }

            for (int i = 0; i < 5; i++) {
                String[] result  = db.getQuestion(i);
                assertEquals("question"+i,result[0]);
                assertEquals("answer"+i,result[1]);
            }
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getQuestionTotalTest() {
        try {
            assertEquals(0, db.getQuestionTotal());
            db.insertQuestion(0, 0, "q", "a");
            assertEquals(1, db.getQuestionTotal());
            db.insertQuestion(0, 0, "q", "a");
            assertEquals(2, db.getQuestionTotal());
            clearQuestions();
            assertEquals(0, db.getQuestionTotal());
        }
        catch (SQLException e) {

        }
    }

    @Test
    public void getQuestionTypesTest() {
        try {
            ArrayList<String> list = db.getQuestionTypes();
            String sql = "SELECT type FROM Types;";
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                String expectedType = rs.getString("type");
                String result = list.get(i);
                assertTrue(result.contains(expectedType));
                i++;
            }
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getDifficultiesTest() {
        try {
            ArrayList<String> list = db.getDifficulties();
            String sql = "SELECT difficulty FROM Difficulties;";
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                String expectedDiff = rs.getString("difficulty");
                String result = list.get(i);
                assertTrue(result.contains(expectedDiff));
                i++;
            }
        }
        catch (SQLException e) {
            fail();
        }
    }
}
