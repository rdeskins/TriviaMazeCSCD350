/*
* Name: Chris Dobbins
* Description: Door functionality to be used for Rooms. Has a locked or unlocked state.
*/

package TriviaGame;

public class Door implements java.io.Serializable{
    private static final long serialVersionUID = 8606443677152636377L;
    private boolean isUnlocked = true;

    public void lockDoor() {
        this.isUnlocked = false;
    }

    public boolean isUnlocked() {
        return this.isUnlocked;
    }
}
