
public class Room {
    private Door northDoor, southDoor, westDoor, eastDoor;

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

    public boolean northDoorStatus() {
        return this.northDoor.isUnlocked();
    }

    public boolean southDoorStatus() {
        return this.southDoor.isUnlocked();
    }

    public boolean westDoorStatus() {
        return this.westDoor.isUnlocked();
    }

    public boolean eastDoorStatus() {
        return this.eastDoor.isUnlocked();
    }
}
