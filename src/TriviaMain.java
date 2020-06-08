/*
* Name: Robin Deskins, Jasper Walden, Christopher Dobbins
* Description: Trivia game's main. Menu traversal is handled here. 
* Cheat answer: cscd350
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;
import static java.nio.file.StandardCopyOption.*;

public class TriviaMain {
    private static IDatabase db;
    private static Scanner kb;
    private static final String saveName = "SavedGame.txt";
    private static final String databaseName = "trivia.db";
    private static final String adminPassword = "password";
    private static final String mainMenuText = 
    "Main Menu:\n" +
    "1) New Game\n" +
    "2) Load Game\n" +
    "3) Read Tutorial\n" +
    "4) Admin Options\n" +
    "5) Exit Game\n";

    private static final String adminMenuText =
    "Admin Options Menu:\n" +
    "1) Add Trivia\n" +
    "2) Clear database\n" +
    "3) Reset database\n" +
    "4) Use Extra Simple Trivia\n" +
    "5) Return\n";

    public static void main(String[] args) {
        File dbFile = new File(databaseName);
        boolean firstUse = !dbFile.exists();
        db = new Database(databaseName);
        if (firstUse) {
            db.initialize();
        }
        mainMenu();
    }

    private static void mainMenu(){
        int menuInput = 0;
        kb = new Scanner(System.in);
        do {
            try{
                System.out.print(mainMenuText);
                menuInput = Integer.parseInt(kb.nextLine());

                if (menuInput == 1) {
                    newGame();
                }
                else if (menuInput == 2) {
                    loadGame();
                }
                else if (menuInput == 3) {
                    displayTutorial();
                }
                else if(menuInput == 4) {
                    adminMenu();
                }
                else if (menuInput == 5) {
                    System.out.println("Goodbye!");
                }
                else {
                    System.out.println("Invalid input: must be between 1 and 4");
                }
            } catch(NumberFormatException e){
                System.out.println("Invalid input: must be an integer.");
            }
        } while (menuInput != 5);
        kb.close();
    }

    private static void newGame() {
        if (db.getQuestionTotal() > 0) {
            TriviaGame game = new TriviaGame(new Maze(getMazeSize()), db, getDifficultyInput());
            game.playGame();
        }
        else {
            System.out.println("Sorry! The database has no questions. You can't play the game right now!");
            System.out.println("Go to admin options in main menu to add questions to the database.");
        }
    }

    private static void loadGame() {
        if (db.getQuestionTotal() > 0) {
            Maze maze;
            try {
                FileInputStream file = new FileInputStream(saveName);
                ObjectInputStream in = new ObjectInputStream(file);

                maze = (Maze) in.readObject();

                in.close();
                file.close();
            
                System.out.println("Game loaded successfully!");
            
                TriviaGame game = new TriviaGame(maze, db, getDifficultyInput());
                game.playGame();
            }
            catch (Exception e) {
                System.out.println("Failed to load game.");
            }
        }
        else {
            System.out.println("Sorry! The database has no questions. You can't play the game right now!");
            System.out.println("Go to admin options in main menu to add questions to the database.");
        }
        
    }

    private static void displayTutorial() {
        File file = new File("tutorial.txt");
        try {
            Scanner scnf = new Scanner(file);
            while (scnf.hasNextLine()) {
                System.out.println(scnf.nextLine());
            }
            scnf.close();
        }
        catch(Exception e) {
            System.out.println("Sorry! The tutorial could not be displayed!");
        }
    }

    private static void adminMenu() {
        if (!checkAdmin()) {
            System.out.println("Sorry! Incorrect password! Returning to main menu.");
            return;
        }

        int menuInput = 0;
        do {
            try {
                System.out.print(adminMenuText);
                menuInput = Integer.parseInt(kb.nextLine());

                if (menuInput == 1) {
                    addTrivia();
                }
                else if (menuInput == 2) {
                    clearDatabase();
                }
                else if (menuInput == 3) {
                    resetDatabase();
                }
                else if (menuInput == 4) {
                    useSimpleDatabase();
                }
                else if (menuInput == 5) {
                    System.out.println("Returning to main menu.");
                }
                else{
                    System.out.println("Invalid input: must be between 1 and 5");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input: must be an integer.");
            }
        } while (menuInput != 5);
    }

    private static boolean checkAdmin() {
        System.out.println("Please enter admin password. (Hint: password is just 'password')");
        String input = kb.nextLine();
        return (input.equals(adminPassword));
    }

    private static void addTrivia() {
        String input = "";
        do {
            int typeID = takeType();
            int diffID = takeDifficulty();
            String questionText = takeQuestion();
            if (typeID == 1) {
                questionText += takeChoices();
            }
            String answerText = takeAnswer();
            Question question = new Question(questionText, answerText);
            boolean result = db.insertQuestion(diffID, typeID, question);

            if (result) {
                System.out.println("Trivia successfully added to the database!");
            } else {
                System.out.println("Sorry, your trivia could not be added :(");
            }

            System.out.println("Would you like to enter another question? Y/N");
            input = kb.nextLine();
        } while (input.equalsIgnoreCase("y"));
    }

    private static int takeType() {
        ArrayList<String> list = db.getQuestionTypes();
        int input = -1;
        do {
            System.out.println("What is the type?");
            for (String s : list) {
                System.out.println(s);
            }

            try {
                input = Integer.parseInt(kb.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: must be an integer.");
            }

            if (input < 0 || input > list.size() -1) {
                System.out.println("Please select a valid option.");
            }
        } while (input < 0 || input > list.size() - 1);
        return input;
    }

    private static int takeDifficulty() {
        ArrayList<String> list = db.getDifficulties();
        int input = -1;
        do {
            System.out.println("What is the difficulty?");
            for (String s : list) {
                System.out.println(s);
            }

            try {
                input = Integer.parseInt(kb.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: must be an integer.");
            }

            if (input < 0 || input > list.size() -1) {
                System.out.println("Please select a valid option.");
            }
        } while (input < 0 || input > list.size() - 1);
        return input;
    }

    private static String takeChoices() {
        String[] letters = {"a", "b", "c", "d"};
        String result = "\n";
        for (int i = 0; i < 4; i++) {
            System.out.println("Enter choice " + letters[i]);
            result += letters[i] + ".) " + kb.nextLine() + "\n";
        }
        return result;
    }

    private static String takeQuestion() {
        System.out.println("What is the question?");
        return kb.nextLine();
    }

    private static String takeAnswer() {
        System.out.println("What is the answer?");
        return kb.nextLine();
    }

    private static void clearDatabase() {
        System.out.println("Are you sure you want to delete all trivia from the database? Y/N");
        if (kb.nextLine().equalsIgnoreCase("y")) {
            boolean success = db.deleteAllQuestions();
            if (success) {
                System.out.println("All questions have been deleted!");
            }
            else {
                System.out.println("Sorry, could not complete your request!");
            }
        }
        System.out.println("Returning to admin options menu.");
    }

    private static void resetDatabase() {
        System.out.println("Are you sure you want to reset the database? All new questions added will be erased and" +
            " replaced with default questions. Y/N");
        if (kb.nextLine().equalsIgnoreCase("Y")) {
            File defaultDb = new File("defaultTrivia.db");
            File currentDb = new File("trivia.db");
            try {
                Files.copy(defaultDb.toPath(), currentDb.toPath(), REPLACE_EXISTING);
                System.out.println("Database successfully reset.");
            }
            catch (Exception e) {
                System.out.println("Sorry, database could not be reset.");
            }
        }
        System.out.println("Returning to admin options menu.");
    }

    private static void useSimpleDatabase() {
        System.out.println("Are you sure? All new questions you added will be erased. This option uses a database " +
        "with simple questions. It is inteded for testing purposes. Y/N");
        if (kb.nextLine().equalsIgnoreCase("Y")) {
            File testDb = new File("testTrivia.db");
            File currentDb = new File("trivia.db");
            try {
                Files.copy(testDb.toPath(), currentDb.toPath(), REPLACE_EXISTING);
                System.out.println("Database successfully reset.");
            }
            catch (Exception e) {
                System.out.println("Sorry, database could not be reset.");
            }
        }
        System.out.println("Returning to admin options menu.");
    }

    private static int getMazeSize() {
        System.out.println("Enter the preferred size of the square maze (4-8):");
        int size = 0;
        while (invalidMazeSize(size)) {
            try {
                size = Integer.parseInt(kb.nextLine());
                if (invalidMazeSize(size)) {
                    System.out.println("Input size must be no less than 4 and no greater than 8");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Input must be an Integer!");
            }
        }
        
        return size;
    }

    private static boolean invalidMazeSize(int size) {
        return size < 4 || size > 8;
    }

    private static int getDifficultyInput() {
        int difficulty = 0;
        while (invalidDifficulty(difficulty)) {
            System.out.println("Enter the desired difficulty for the game:\n" + 
                               "1) Easy\n" + 
                               "2) Hard\n" + 
                               "3) Mixed\n");
            try {
                difficulty = Integer.parseInt(kb.nextLine());
                if (invalidDifficulty(difficulty)) {
                    System.out.println("Invalid input: Input must be 1, 2, or 3.");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input: Input must be an integer.");
            }

        }

        return difficulty - 1;
    }

    private static boolean invalidDifficulty(int difficulty) {
        return difficulty < 1 || difficulty > 3;
    }
}
