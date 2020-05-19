
public class TriviaGame {
    private Maze maze;

    public TriviaGame(Maze maze) {
        this.maze = maze;
    }

    public void playGame() {
        //game logic
    }

    private boolean winConditionMet() { // redundant
        return this.maze.goalReached();
    }

    private boolean loseConditionMet() { // redundant
        return !this.maze.pathToGoalExists();
    }
}
