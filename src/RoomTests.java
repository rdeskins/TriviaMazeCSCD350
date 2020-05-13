
import static org.junit.Assert.*;
import org.junit.Test;

public class RoomTests {

    @Test
    public void createRoomSuccess() {
        Room room = new Room(new Door(), new Door(), new Door(), new Door());
        assertTrue(room.northDoorUnlocked());
        assertTrue(room.southDoorUnlocked());
        assertTrue(room.westDoorUnlocked());
        assertTrue(room.eastDoorUnlocked());
    }

    @Test
    public void lockNorthRoom() {
        Room room = new Room(new Door(), new Door(), new Door(), new Door());
        room.lockNorthDoor();
        assertFalse(room.northDoorUnlocked());
    }

    @Test
    public void lockSouthRoom() {
        Room room = new Room(new Door(), new Door(), new Door(), new Door());
        room.lockSouthDoor();
        assertFalse(room.southDoorUnlocked());
    }

    @Test
    public void lockWestRoom() {
        Room room = new Room(new Door(), new Door(), new Door(), new Door());
        room.lockWestDoor();
        assertFalse(room.westDoorUnlocked());
    }

    @Test
    public void lockEastRoom() {
        Room room = new Room(new Door(), new Door(), new Door(), new Door());
        room.lockEastDoor();
        assertFalse(room.eastDoorUnlocked());
    }
}
