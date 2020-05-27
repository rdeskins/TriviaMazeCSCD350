
import java.util.Scanner;

public class TriviaGame {
    private Maze maze;
    private Database db;
    private Scanner kb;
    private boolean endConditionMet = false;
    private final String cheat = "CSCD350";

    public TriviaGame(Maze maze, Database db) {
        this.maze = maze;
        this.db = db;
        this.kb = new Scanner(System.in);
    }

    public void playGame() {
        int userInput;
        while (!this.endConditionMet) {
            System.out.println("Current maze:\n" + this.maze);
            printMenu();
            userInput = getInput();

            if (userInput == 1) {
                attemptMoveNorth();
            }
            else if (userInput == 2) {
                attemptMoveSouth();
            }
            else if (userInput == 3) {
                attemptMoveWest();
            }
            else if (userInput == 4) {
                attemptMoveEast();
            }
            else return;

            if (winConditionMet()) {
                System.out.println("You win!");
                this.endConditionMet = true;
            }
            else if (loseConditionMet()) {
                System.out.println("You lose :(");
                this.endConditionMet = true;
            }
        }
        System.out.println("Final maze:\n" + this.maze);

    }

    private boolean askQuestion() {
        String input = "";
        Question question = db.getRandomQuestion();
        System.out.println(question.getQuestion());
        input = kb.nextLine();
        return checkAnswer(input, question);
    }

    private void attemptMoveNorth() {
        if (askQuestion()) {
            System.out.println("Correct!");
            this.maze.moveNorth();
        }
        else {
            System.out.println("Incorrect answer, North door will be locked.");
            this.maze.lockNorthDoor();
        }
    }

    private void attemptMoveSouth() {
        if (askQuestion()) {
            System.out.println("Correct!");
            this.maze.moveSouth();
        }
        else {
            System.out.println("Incorrect answer, South door will be locked.");
            this.maze.lockSouthDoor();
        }
    }

    private void attemptMoveWest() {
        if (askQuestion()) {
            System.out.println("Correct!");
            this.maze.moveWest();
        }
        else {
            System.out.println("Incorrect answer, West door will be locked.");
            this.maze.lockWestDoor();
        }
    }

    private void attemptMoveEast() {
        if (askQuestion()) {
            System.out.println("Correct!");
            this.maze.moveEast();
        }
        else {
            System.out.println("Incorrect answer, East door will be locked.");
            this.maze.lockEastDoor();
        }
    }

    private boolean checkAnswer(String userAnswer, Question question) {
        return userAnswer.equals(this.cheat) || userAnswer.equals(question.getAnswer());
    }

    private void printMenu() {
        System.out.println("What would you like to do?\n" + 
                           "1) Move North\n" + 
                           "2) Move South\n" + 
                           "3) Move West\n" + 
                           "4) Move East\n" + 
                           "5) Save Game\n" + 
                           "6) Return to Main Menu\n");
    }

    private int getInput() {
        int choice = 0;
        while (choice < 1 || choice > 6) {
            try {
                choice = Integer.parseInt(kb.nextLine());
                if (choice < 1 || choice > 6)
                    System.out.println("User choice must be between 1 and 6");
            }
            catch(NumberFormatException e) {
                System.out.println("User choice must be an integer between 1 and 6");
            }
        }
        return choice;
    }

    private boolean winConditionMet() { // redundant
        return this.maze.goalReached();
    }

    private boolean loseConditionMet() { // redundant
        return !this.maze.pathToGoalExists();
    }
}
