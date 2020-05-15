import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/*
* Name: Robin Deskins
* Descrioption: Code related to creating the database and adding/retrieving data from the database.
*/
public class Database {

    public static boolean createDatabase() {
        Statement stmt = null;
        Connection c = connect();
        try {
            stmt = c.createStatement();
            //Create tables
            String sql = "CREATE TABLE Questions " +
	            "(ID INT PRIMARY KEY NOT NULL," +
	            " difficulty_ID INT NOT NULL, " + 
                " type_ID INT NOT NULL, " +
                " question_text VARCHAR(255) NOT NULL, " +
                " answer_text VARCHAR(255) NOT NULL)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE Difficulties " +
                "(difficulty_ID INT PRIMARY KEY NOT NULL," +
                "difficulty VARCHAR(255) NOT NULL)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE Types " +
                "(type_ID INT PRIMARY KEY NOT NULL," +
                "type VARCHAR(255) NOT NULL)";
            stmt.executeUpdate(sql);

            //Add basic difficulites. 0 = Easy, 1 = Medium, 2 = Hard
            sql = "INSERT INTO Difficulties (difficulty_ID, difficulty) " +
                    "VALUES (0, 'Easy');";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Difficulties (difficulty_ID, difficulty) " +
                    "VALUES (1, 'Medium');";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Difficulties (difficulty_ID, difficulty) " +
                    "VALUES (2, 'Hard');";
            stmt.executeUpdate(sql);

            //Add basic question types. 0 = T/F, 1 = Mult. Choice, 2 = Short Ans.
            sql = "INSERT INTO Types (Type_ID, type) " +
                "VALUES (0, 'True/False');";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Types (Type_ID, type) " +
                "VALUES (1, 'Multiple Choice');";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Types (Type_ID, type) " +
                "VALUES (2, 'Short Answer');";
            stmt.executeUpdate(sql);

            //Close connection
	        stmt.close();
	        c.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertQuestion(int diffID, int typeID, String question, String answer) {
        Statement stmt = null;
        Connection c = connect();
        try {
            stmt = c.createStatement();
            int qID = getQuestionTotal();
            String sql = "INSERT INTO Questions (ID, difficulty_ID, type_ID, question_text, answer_text) " +
                "VALUES (" + qID + ", " + diffID + ", " + typeID + ", " +
                    "'" + question +"', '" + answer + "');";
            stmt.executeUpdate(sql);

            c.close();
            stmt.close();
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
	        return false;
        }
    }

    public static String[] getRandomQuestion() {
        Random random = new Random();
        int randomID = random.nextInt(getQuestionTotal());
        return getQuestion(randomID);
    }

    private static String[] getQuestion(int qID) {
        Statement stmt = null;
        Connection c = connect();
        try {
            stmt = c.createStatement();
            String sql = "SELECT question_text, answer_text FROM Questions WHERE ID = " + qID;
            ResultSet rs = stmt.executeQuery(sql);
            String[] result = {rs.getString("question_text"), rs.getString("answer_text")};
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Return -1 if there is an error
    public static int getQuestionTotal() {
        Statement stmt = null;
        Connection c = connect();
        try {
            stmt = c.createStatement();
            String sql = "SELECT COUNT(*) FROM Questions;";
            ResultSet rs = stmt.executeQuery(sql);
            int total = rs.getInt("COUNT(*)");
            c.close();
            stmt.close();
            return total;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //Returns empty array list if error occurs
    public static ArrayList<String> getQuestionTypes() {
        ArrayList<String> results = new ArrayList<String>();
        Statement stmt = null;
        Connection c = connect();
        try {
            stmt = c.createStatement();
            String sql = "SELECT type_ID, type FROM Types;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                results.add(rs.getString("type_ID") + ". " + rs.getString("type"));
            }
            return results;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    //Returns empty array list if error occurs
    public static ArrayList<String> getDifficulties() {
        ArrayList<String> results = new ArrayList<String>();
        Statement stmt = null;
        Connection c = connect();
        try {
            stmt = c.createStatement();
            String sql = "SELECT difficulty_ID, difficulty FROM Difficulties;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                results.add(rs.getString("difficulty_ID") + ". " + rs.getString("difficulty"));
            }
            return results;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:trivia.db");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
