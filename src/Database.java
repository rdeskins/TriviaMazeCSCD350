import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/*
* Name: Robin Deskins
* Description: Code related to creating the database and adding/retrieving data from the database.
*/
public class Database {
    private String databaseName;

    public Database(String fileName) throws SQLException {
        this.databaseName = fileName;
        Connection c = connect();
	    c.close();
    }

    public void initialize() throws SQLException {
        Connection c = connect();
        Statement stmt = c.createStatement();
        this.createTables(c, stmt);
        this.insertDefaultDifficulties(c, stmt);
        this.insertDefaultQuestionTypes(c, stmt);
        stmt.close();
        c.close();
    }

    private void createTables(Connection c, Statement stmt) throws SQLException {
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
    }

    private void insertDefaultDifficulties(Connection c, Statement stmt) throws SQLException {
        String sql = "INSERT INTO Difficulties (difficulty_ID, difficulty) " +
                "VALUES (0, 'Easy');";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO Difficulties (difficulty_ID, difficulty) " +
                "VALUES (1, 'Medium');";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO Difficulties (difficulty_ID, difficulty) " +
                "VALUES (2, 'Hard');";
        stmt.executeUpdate(sql);
    }

    private void insertDefaultQuestionTypes(Connection c, Statement stmt) throws SQLException {
        String sql = "INSERT INTO Types (Type_ID, type) " +
            "VALUES (0, 'True/False');";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO Types (Type_ID, type) " +
            "VALUES (1, 'Multiple Choice');";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO Types (Type_ID, type) " +
            "VALUES (2, 'Short Answer');";
        stmt.executeUpdate(sql);
    }

    public boolean insertQuestion(int diffID, int typeID, String question, String answer) throws SQLException{
        Connection c = connect();
        Statement stmt = c.createStatement();
        int qID = getQuestionTotal();
        String sql = "INSERT INTO Questions (ID, difficulty_ID, type_ID, question_text, answer_text) " +
            "VALUES (" + qID + ", " + diffID + ", " + typeID + ", " +
            "'" + question +"', '" + answer + "');";
        stmt.executeUpdate(sql);

        c.close();
        stmt.close();
        return true;
    }

    public void deleteAllQuestions() throws SQLException {
        Connection c = connect();
        Statement stmt = c.createStatement();
        String sql = "DELETE FROM Questions;";
        stmt.executeUpdate(sql);
        stmt.close();
        c.close();
    }

    public String[] getRandomQuestion() throws SQLException, IllegalArgumentException {
        if (getQuestionTotal() == 0) {
            throw new IllegalArgumentException();
        }
        Connection c = connect();
        Statement stmt = c.createStatement();
        String sql = "SELECT question_text, answer_text FROM Questions ORDER BY RANDOM() LIMIT 1;";
        ResultSet rs = stmt.executeQuery(sql);
        String[] result = {rs.getString("question_text"), rs.getString("answer_text")};
        stmt.close();
        c.close();
        return result;
    }

    public String[] getRandomQuestion(int difficultyID) throws SQLException, IllegalArgumentException {
        if (getQuestionTotal() == 0) {
            throw new IllegalArgumentException();
        }
        Connection c = connect();
        Statement stmt = c.createStatement();
        String sql = "SELECT question_text, answer_text FROM Questions " +
            "WHERE difficulty_ID = " + difficultyID + " " +
            "ORDER BY RANDOM() LIMIT 1;";
        ResultSet rs = stmt.executeQuery(sql);
        String[] result = {rs.getString("question_text"), rs.getString("answer_text")};
        stmt.close();
        c.close();
        return result;
    }

    public String[] getQuestion(int qID) throws SQLException, IllegalArgumentException {
        if (qID >= getQuestionTotal()) {
            throw new IllegalArgumentException();
        }
        Connection c = connect();
        Statement stmt = c.createStatement();
        String sql = "SELECT question_text, answer_text FROM Questions WHERE ID = " + qID;
        ResultSet rs = stmt.executeQuery(sql);
        String[] result = {rs.getString("question_text"), rs.getString("answer_text")};
        c.close();
        stmt.close();
        return result;
    }

    public int getQuestionTotal() throws SQLException {
        Connection c = connect();
        Statement stmt = c.createStatement();
        String sql = "SELECT COUNT(*) FROM Questions;";
        ResultSet rs = stmt.executeQuery(sql);
        int total = rs.getInt("COUNT(*)");
        c.close();
        stmt.close();
        return total;
    }

    public ArrayList<String> getQuestionTypes() throws SQLException {
        ArrayList<String> results = new ArrayList<String>();
        Connection c = connect();
        Statement stmt = c.createStatement();
        String sql = "SELECT type_ID, type FROM Types;";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            results.add(rs.getString("type_ID") + ". " + rs.getString("type"));
        }
        c.close();
        stmt.close();
        return results;
    }

    public ArrayList<String> getDifficulties() throws SQLException {
        ArrayList<String> results = new ArrayList<String>();
        Connection c = connect();
        Statement stmt = c.createStatement();
        String sql = "SELECT difficulty_ID, difficulty FROM Difficulties;";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            results.add(rs.getString("difficulty_ID") + ". " + rs.getString("difficulty"));
        }
        c.close();
        stmt.close();
        return results;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + this.databaseName);
    }
}
