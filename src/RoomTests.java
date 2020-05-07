
import static org.junit.Assert.*;
import org.junit.Test;

public class RoomTests {

    @Test
    public void createRoomSuccess() {
        Room room = new Room(new Door(), new Door(), new Door(), new Door());
        assertTrue(room.northDoorStatus());
        assertTrue(room.southDoorStatus());
        assertTrue(room.westDoorStatus());
        assertTrue(room.eastDoorStatus());
    }

    @Test
    public void lockNorthRoom() {
        Room room = new Room(new Door(), new Door(), new Door(), new Door());
        room.lockNorthDoor();
        assertFalse(room.northDoorStatus());
    }

    @Test
    public void lockSouthRoom() {
        Room room = new Room(new Door(), new Door(), new Door(), new Door());
        room.lockSouthDoor();
        assertFalse(room.southDoorStatus());
    }

    @Test
    public void lockWestRoom() {
        Room room = new Room(new Door(), new Door(), new Door(), new Door());
        room.lockWestDoor();
        assertFalse(room.westDoorStatus());
    }

    @Test
    public void lockEastRoom() {
        Room room = new Room(new Door(), new Door(), new Door(), new Door());
        room.lockEastDoor();
        assertFalse(room.eastDoorStatus());
    }
}
