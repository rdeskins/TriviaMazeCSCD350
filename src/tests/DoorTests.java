/*
* Name: Chris Dobbins
* Description: Tests for Door.java
*/

package tests;

import TriviaGame.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class DoorTests {
    
    @Test
    public void doorTest() {
        Door door = new Door();
        assertTrue(door.isUnlocked());
    }

    @Test
    public void lockDoorSuccess() {
        Door door = new Door();
        door.lockDoor();
        assertFalse(door.isUnlocked());
    }
}
