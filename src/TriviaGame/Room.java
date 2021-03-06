/*
* Name: Chris Dobbins
* Description: Rooms to be used in the Maze. Contains 4 doors, one each for North/South/West/East
*/

package TriviaGame;

public class Room implements java.io.Serializable{
    private static final long serialVersionUID = 6638522852046318769L;
    private Door northDoor, southDoor, westDoor, eastDoor;
    private boolean visited = false;

    public Room(Door northDoor, Door southDoor, Door westDoor, Door eastDoor) {
        this.northDoor = northDoor;
        this.southDoor = southDoor;
        this.westDoor = westDoor;
        this.eastDoor = eastDoor;
    }

    public void lockNorthDoor() {
        this.northDoor.lockDoor();
    }

    public void lockSouthDoor() {
        this.southDoor.lockDoor();
    }

    public void lockWestDoor() {
        this.westDoor.lockDoor();
    }

    public void lockEastDoor() {
        this.eastDoor.lockDoor();
    }

    public boolean northDoorUnlocked() {
        return this.northDoor.isUnlocked();
    }

    public boolean southDoorUnlocked() {
        return this.southDoor.isUnlocked();
    }

    public boolean westDoorUnlocked() {
        return this.westDoor.isUnlocked();
    }

    public boolean eastDoorUnlocked() {
        return this.eastDoor.isUnlocked();
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean status) {
        this.visited = status;
    }
}
