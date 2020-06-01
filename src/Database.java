
import java.sql.*;
import java.util.ArrayList;

/*
* Name: Robin Deskins
* Description: Code related to creating the database and adding/retrieving data from the database.
*/
public class Database {
    private String databaseName;

    public Database(String fileName) {
        this.databaseName = fileName;
    }

    public boolean initialize() {
        boolean success = false;
        Connection c = null;
        Statement stmt = null;
        try {
            c = connect();
            stmt = c.createStatement();
            this.createTables(c, stmt);
            this.insertDefaultDifficulties(c, stmt);
            this.insertDefaultQuestionTypes(c, stmt);
            success = true;
        }
        catch (SQLException e) {
            success = false;
            e.printStackTrace();
        }
        finally {
            try {
                disconnect(c, stmt);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
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
                "VALUES (1, 'Hard');";
        stmt.executeUpdate(sql);
    }

    private void insertDefaultQuestionTypes(Connection c, Statement stmt) throws SQLException {
        String sql = "INSERT INTO Types (Type_ID, type) " +
            "VALUES (0, 'True or False');";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO Types (Type_ID, type) " +
            "VALUES (1, 'Multiple Choice');";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO Types (Type_ID, type) " +
            "VALUES (2, 'Short Answer');";
        stmt.executeUpdate(sql);
    }

    public boolean insertQuestion(int diffID, int typeID, Question q) {
        boolean success = false;
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = connect();
            String sql = "INSERT INTO Questions (ID, difficulty_ID, type_ID, question_text, answer_text) " +
                "VALUES (?, ?, ?, ?, ?);";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, getQuestionTotal());
            stmt.setInt(2, diffID);
            stmt.setInt(3, typeID);
            stmt.setString(4, q.getQuestion());
            stmt.setString(5, q.getAnswer());
            stmt.executeUpdate();
            success = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                disconnect(c, stmt);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean deleteAllQuestions() {
        boolean success = false;
        Connection c = null;
        Statement stmt = null;
        try {
            c = connect();
            stmt = c.createStatement();
            String sql = "DELETE FROM Questions;";
            stmt.executeUpdate(sql);
            success = true;
        }
        catch (SQLException e) {
            success = false;
            e.printStackTrace();
        }
        finally {
            try {
                disconnect(c, stmt);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    //Return null if error
    public Question getRandomQuestion() throws IllegalArgumentException {
        if (getQuestionTotal() == 0) {
            throw new IllegalArgumentException();
        }
        Question result = null;
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            c = connect();
            stmt = c.createStatement();
            String sql = "SELECT type_id, question_text, answer_text FROM Questions ORDER BY RANDOM() LIMIT 1;";
            rs = stmt.executeQuery(sql);
            String typeString = getTypeString(rs.getInt("type_id"), c);
            result = new Question(typeString + ": " + rs.getString("question_text"), rs.getString("answer_text"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                disconnect(c, stmt, rs);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Question getRandomQuestion(int difficultyID) throws IllegalArgumentException {
        if (getQuestionTotal() == 0) {
            throw new IllegalArgumentException();
        }
        Question result = null;
        Connection c = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            c = connect();
            String sql = "SELECT type_id, question_text, answer_text FROM Questions " +
                "WHERE difficulty_ID=? " +
                "ORDER BY RANDOM() LIMIT 1;";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, difficultyID);
            rs = stmt.executeQuery();
            String typeString = getTypeString(rs.getInt("type_id"), c);
            result = new Question(typeString + ": " + rs.getString("question_text"), rs.getString("answer_text"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                disconnect(c, stmt, rs);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Question getQuestion(int qID) throws IllegalArgumentException {
        if (qID >= getQuestionTotal() || qID < 0) {
            throw new IllegalArgumentException();
        }
        Question result = null;
        Connection c = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            c = connect();
            String sql = "SELECT type_id, question_text, answer_text FROM Questions WHERE ID=?;";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, qID);
            rs = stmt.executeQuery();
            String typeString = getTypeString(rs.getInt("type_id"), c);
            result = new Question(typeString + ": " + rs.getString("question_text"), rs.getString("answer_text"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                disconnect(c, stmt, rs);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String getTypeString(int typeID, Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        String sql = "SELECT type FROM Types WHERE type_id = " + typeID + ";";
        ResultSet rs = stmt.executeQuery(sql);
        String result = rs.getString("type");
        stmt.close();
        rs.close();
        return result;
    }

    //Returns -1 if error
    public int getQuestionTotal() {
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        int total = -1;
        try {
            c = connect();
            stmt = c.createStatement();
            String sql = "SELECT COUNT(*) FROM Questions;";
            rs = stmt.executeQuery(sql);
            total = rs.getInt("COUNT(*)");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                disconnect(c, stmt, rs);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    public ArrayList<String> getQuestionTypes() {
        ArrayList<String> results = new ArrayList<String>();
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            c = connect();
            stmt = c.createStatement();
            String sql = "SELECT type_ID, type FROM Types;";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                results.add(rs.getString("type_ID") + ". " + rs.getString("type"));
            }
        }
        catch (SQLException e) {
            results = null;
            e.printStackTrace();
        }
        finally {
            try {
                disconnect(c, stmt, rs);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public ArrayList<String> getDifficulties() {
        ArrayList<String> results = new ArrayList<String>();
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            c = connect();
            stmt = c.createStatement();
            String sql = "SELECT difficulty_ID, difficulty FROM Difficulties;";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                results.add(rs.getString("difficulty_ID") + ". " + rs.getString("difficulty"));
            }
        }
        catch (SQLException e) {
            results = null;
            e.printStackTrace();
        }
        finally {
            try {
                disconnect(c, stmt, rs);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + this.databaseName);
    }

    private void disconnect(Connection c, Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        if (c != null) {
            c.close();
        }
    }

    private void disconnect(Connection c, Statement stmt, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        disconnect(c, stmt);
    }
}
