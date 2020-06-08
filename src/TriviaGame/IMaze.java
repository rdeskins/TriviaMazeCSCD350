
package TriviaGame;

public interface IMaze extends java.io.Serializable{
    public String toString();
    public void moveNorth();
    public void moveSouth();
    public void moveWest();
    public void moveEast();
    public boolean pathToGoalExists();
    public boolean goalReached();
    public void lockNorthDoor();
    public void lockSouthDoor();
    public void lockWestDoor();
    public void lockEastDoor();
    public boolean playerNorthDoorUnlocked();
    public boolean playerSouthDoorUnlocked();
    public boolean playerWestDoorUnlocked();
    public boolean playerEastDoorUnlocked();
}
