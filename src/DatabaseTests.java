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
        db.initialize();
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
            Question q = new Question("question", "answer");
            db.insertQuestion(0, 0, q);
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
    public void deleteAllQuestionsTest() {
        try {
            Question q = new Question("q", "a");
            db.insertQuestion(0, 0, q);
            db.insertQuestion(0, 0, q);
            db.deleteAllQuestions();
            String sql = "SELECT COUNT(*) FROM Questions;";
            ResultSet rs = stmt.executeQuery(sql);
            int total = rs.getInt("COUNT(*)");
            assertEquals(0, total);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getRandomQuestionTest() {
        try {
            for (int i = 0; i < 5; i ++) {
                Question q = new Question("question" + i, "answer" + i);
                db.insertQuestion(0, 0, q);
            }

            Question q = db.getRandomQuestion();
            String actualQ = q.getQuestion();
            assertTrue(actualQ.contains("question"));
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getRandomQuestionWithDifficultyTest() {
        try {
            db.insertQuestion(0, 2, new Question("Easy", "Easy"));
            db.insertQuestion(1, 2, new Question("Medium", "Medium"));
            db.insertQuestion(2, 2, new Question("Hard", "Hard"));
            String expectedQ = "Easy";
            Question actualQ = db.getRandomQuestion(0);
            assertEquals(expectedQ, actualQ.getQuestion());
            
            expectedQ = "Medium";
            actualQ = db.getRandomQuestion(1);
            assertEquals(expectedQ, actualQ.getQuestion());

            expectedQ = "Hard";
            actualQ = db.getRandomQuestion(2);
            assertEquals(expectedQ, actualQ.getQuestion());
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
            Question q = new Question("q", "a");
            db.insertQuestion(0, 0, q);
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
                Question q = new Question( "question" + i, "answer" + i);
                db.insertQuestion(0, 0, q);
            }

            for (int i = 0; i < 5; i++) {
                Question actualQ  = db.getQuestion(i);
                assertEquals("question"+i, actualQ.getQuestion());
            }
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getQuestionTotalTest() {
        try {
            Question q = new Question("q", "a");
            assertEquals(0, db.getQuestionTotal());
            db.insertQuestion(0, 0, q);
            assertEquals(1, db.getQuestionTotal());
            db.insertQuestion(0, 0, q);
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
