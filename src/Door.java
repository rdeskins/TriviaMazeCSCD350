
public class Door {
    private boolean isUnlocked = true;

    public void lockDoor() {
        this.isUnlocked = false;
    }

    public boolean isUnlocked() {
        return this.isUnlocked;
    }
}
