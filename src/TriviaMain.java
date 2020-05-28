/*
* Name: Robin Deskins, Jasper Walden, Christopher Dobbins
* Description: Trivia game's main. Menu traversal is handled here. 
*/

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TriviaMain {
    private static Database db;
    private static Scanner kb;
    private static final String databaseName = "trivia.db";
    private static final String adminPassword = "password";
    private static final String mainMenuText = 
    "Main Menu:\n" +
    "1) New Game\n" +
    "2) Load Game\n" +
    "3) Admin Options\n" +
    "4) Exit Game\n";

    private static final String adminMenuText =
    "Admin Options Menu:\n" +
    "1) Add Trivia\n" +
    "2) Clear database\n" +
    "3) Reset database\n" +
    "4) Return\n";

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

                if(menuInput == 1){
                    newGame();
                }
                else if(menuInput == 2){
                    loadGame();
                }
                else if(menuInput == 3){
                    adminMenu();
                }
                else if(menuInput == 4){
                    System.out.println("Goodbye!");
                }
                else{
                    System.out.println("Invalid input: must be between 1 and 4");
                }
            } catch(NumberFormatException e){
                System.out.println("Invalid input: must be an integer.");
            }
        } while (menuInput != 4);
        kb.close();
    }

    private static void newGame() {
        TriviaGame game = new TriviaGame(new Maze(4));
        game.playGame();
    }

    private static void loadGame() {

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
                    System.out.println("Returning to main menu.");
                }
                else{
                    System.out.println("Invalid input: must be between 1 and 4");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input: must be an integer.");
            }
        } while (menuInput != 4);
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
        System.out.println("Coming soon.");
        //Not implemented
        System.out.println("Returning to admin options menu.");
    }
}
