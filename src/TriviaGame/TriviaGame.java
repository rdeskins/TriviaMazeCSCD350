
package TriviaGame;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class TriviaGame {
    private IMaze maze;
    private IDatabase db;
    private Scanner kb;
    private final String cheat = "cscd350";
    private String saveName = "SavedGame.txt";
    private int difficulty;

    public TriviaGame(IMaze maze, IDatabase db, int difficulty) {
        this.maze = maze;
        this.db = db;
        this.difficulty = difficulty;
        this.kb = new Scanner(System.in);
    }

    public void playGame() {
        while (!endConditionMet()) {
            System.out.println("\nCurrent maze:\n" + this.maze);
            printMenu();
            if(takeTurn(getInput())) return;
        }
        System.out.println("\nFinal maze:\n" + this.maze);
    }

    private boolean takeTurn(String userInput) {
        if (userInput.equals("w")) {
            attemptMoveNorth();
        }
        else if (userInput.equals("s")) {
            attemptMoveSouth();
        }
        else if (userInput.equals("a")) {
            attemptMoveWest();
        }
        else if (userInput.equals("d")) {
            attemptMoveEast();
        }
        else if (userInput.equals("e")) {
            saveGame();
        }
        else if (userInput.equals("q")) {
            System.out.println("Returning to Main Menu");
            return true;
        }
        else {
            System.out.println("Invalid input");
        }
        return false;
    }

    private boolean askQuestion() {
        String input = "";
        Question question = db.getRandomQuestion(difficulty);
        System.out.println(question.getQuestion());
        input = getInput();
        return checkAnswer(input, question);
    }

    private void attemptMoveNorth() {
        if (!this.maze.playerNorthDoorUnlocked()) {
            System.out.println("Cannot move North. The North door is locked!");
        }
        else if (askQuestion()) {
            System.out.println("Correct!");
            this.maze.moveNorth();
        }
        else {
            System.out.println("Incorrect answer, North door will be locked.");
            this.maze.lockNorthDoor();
        }
    }

    private void attemptMoveSouth() {
        if (!this.maze.playerSouthDoorUnlocked()) {
            System.out.println("Cannot move South. The South door is locked!");
        }
        else if (askQuestion()) {
            System.out.println("Correct!");
            this.maze.moveSouth();
        }
        else {
            System.out.println("Incorrect answer, South door will be locked.");
            this.maze.lockSouthDoor();
        }
    }

    private void attemptMoveWest() {
        if (!this.maze.playerWestDoorUnlocked()) {
            System.out.println("Cannot move West. The West door is locked!");
        }
        else if (askQuestion()) {
            System.out.println("Correct!");
            this.maze.moveWest();
        }
        else {
            System.out.println("Incorrect answer, West door will be locked.");
            this.maze.lockWestDoor();
        }
    }

    private void attemptMoveEast() {
        if (!this.maze.playerEastDoorUnlocked()) {
            System.out.println("Cannot move East. The East door is locked!");
        }
        else if (askQuestion()) {
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
                           "W) Move North\n" + 
                           "S) Move South\n" + 
                           "A) Move West\n" + 
                           "D) Move East\n" + 
                           "E) Save Game\n" + 
                           "Q) Return to Main Menu\n");
    }

    protected String getInput() {
        String userInput;
        userInput = kb.nextLine();
        userInput = userInput.toLowerCase();
        return userInput;
    }

    private boolean endConditionMet() {
        boolean gameOver = false;
        if (this.maze.goalReached()) {
            System.out.println("You win!");
            gameOver = true;
        }
        else if (!this.maze.pathToGoalExists()) {
            System.out.println("You lose :(");
            gameOver = true;
        }
        return gameOver;
    }

    private void saveGame(){
        try {
            FileOutputStream file = new FileOutputStream(this.saveName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this.maze);

            out.close();
            file.close();

            System.out.println("The game has been saved.");
        }
        catch (Exception e) {
            System.out.println("Failed to save the game.");
        }
    }
}
