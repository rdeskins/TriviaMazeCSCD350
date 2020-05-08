
import java.sql.*;

/*
* Name: Robin Deskins
* Description: Code that creates a SQLite database file named trivia.db in the current directory. 
* Tables in the database schema are created and difficulties and types are added to their respective tables.
* Difficulties: 0 = Easy, 1 = Medium, 2 = Hard
* Types: 0 = True/False, 1 = Multiple Choice, 2 = Short Answer
*/
public class CreateDatabase
{
  public static void main( String args[] ) {
	Connection c = null;
	Statement stmt = null;
	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:trivia.db");
	    System.out.println("Opened database successfully");
        stmt = c.createStatement();
        
	    String sql = "CREATE TABLE QuestionData " +
	                "(ID INT PRIMARY KEY NOT NULL," +
	                " difficulty_ID INT NOT NULL, " + 
	                " type_ID INT NOT NULL)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE Questions " +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    " question_text VARCHAR(255) NOT NULL)";
        stmt.executeUpdate(sql);  
        
        sql = "CREATE TABLE Answers " +
                    "(ID INT PRIMARY KEY NOT NULL," +
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

        System.out.println("Tables created sucessfully!");

        sql = "INSERT INTO Difficulties (difficulty_ID, difficulty) " +
                    "VALUES (0, 'Easy');";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO Difficulties (difficulty_ID, difficulty) " +
                    "VALUES (1, 'Medium');";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO Difficulties (difficulty_ID, difficulty) " +
                    "VALUES (2, 'Hard');";
        stmt.executeUpdate(sql);
        System.out.println("Difficulties added successfully!");

        sql = "INSERT INTO Types (Type_ID, type) " +
                "VALUES (0, 'True/False');";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO Types (Type_ID, type) " +
                "VALUES (1, 'Multiple Choice');";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO Types (Type_ID, type) " +
                "VALUES (2, 'Short Answer');";
        stmt.executeUpdate(sql);
        System.out.println("Types added successfully!");

	    stmt.close();
	    c.close();
    } 
    catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
    }
        
	System.out.println("Database created successfully");
  }
}
