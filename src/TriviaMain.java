
import java.sql.SQLException;
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
    "3) Restore database\n" +
    "4) Return\n";

    public static void main(String[] args) {
        try {
            db = new Database(databaseName);
        }
        catch (SQLException e) {
            System.out.println("Sorry, database could not be connected to. Try again later!");
            System.exit(0);
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
        //deserialize selected maze to load
        //pass maze to TriviaGame constructer
        //game.playGame();
    }

    private static void adminMenu() {
        if (!checkAdmin()) {
            System.out.println("Sorry! Incorrect password!");
            System.out.println();
            return;
        }

        int menuInput = 0;
        do {
            try {
                System.out.print(adminMenuText);
                menuInput = Integer.parseInt(kb.nextLine());

                if(menuInput == 1){
                    addTrivia();
                }
                else if(menuInput == 2){
                    clearDatabase();
                }
                else if(menuInput == 3){
                    restoreDatabase();
                }
                else if(menuInput == 4){
                    System.out.println("Returning to main menu!");
                    System.out.println();
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
        //option to return to main menu
        //grab user question and answers
        //store in database
    }

    private static void clearDatabase() {
        //not implemented
    }

    private static void restoreDatabase() {
        //not implemented
    }

}
