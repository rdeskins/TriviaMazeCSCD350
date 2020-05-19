import static org.junit.Assert.*;

import java.io.File;
import java.sql.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

public class DatabaseTests {
    private static final String databaseName = "triviaTest.db";

    @BeforeClass
    public static void deleteOldTest() {
        deleteDatabase();
    }

    private static void deleteDatabase() {
        File file = new File (databaseName);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    public void tearDown() {
        deleteDatabase();
    }
    
    @Test
    public void insertQuestionTest() {
        try {
            Database db = new Database(databaseName);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getRandomQuestionTest() {
        try {
            Database db = new Database(databaseName);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getSpecificQuestionTest() {
        fail("Not yet implemented.");
    }

    @Test
    public void getQuestionTotalTest() {
        fail("Not yet implemented.");
    }

    @Test
    public void getQuestionTypesTest() {
        fail("Not yet implemented.");
    }

    @Test
    public void getDifficultiesTest() {
        fail("Not yet implemented.");
    }
}