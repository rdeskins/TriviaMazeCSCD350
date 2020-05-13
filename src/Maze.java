
public class Maze {
    private Room[][] maze;
    private int mazeSize, playerRow, playerColumn, goalRow, goalColumn;

    public Maze(int size) {
        if (size < 4)
            throw new IllegalArgumentException("Size of maze must be at least 4");
        this.mazeSize = size;
        this.maze = new Room[size][size];
        initializeMaze();
        this.playerRow = 0;
        this.playerColumn = 0;
        this.goalRow = size - 1;
        this.goalColumn = size - 1;
    }

    private void initializeMaze() {
        Door[][] HorizontalDoors = new Door[this.mazeSize + 1][this.mazeSize];
        Door[][] VerticalDoors = new Door[this.mazeSize][this.mazeSize + 1];

        for (int i = 0; i < HorizontalDoors.length; i++) {
            for (int j = 0; j < HorizontalDoors[0].length; j++) {
                HorizontalDoors[i][j] = new Door();
            }
        }

        for (int i = 0; i < VerticalDoors.length; i++) {
            for (int j = 0; j < VerticalDoors[0].length; j++) {
                VerticalDoors[i][j] = new Door();
            }
        }

        Door north, south, west, east;
        for (int i = 0; i < this.mazeSize; i++) {
            for (int j = 0; j < this.mazeSize; j++) {
                north = HorizontalDoors[i][j];
                south = HorizontalDoors[i + 1][j];
                west = VerticalDoors[i][j];
                east = VerticalDoors[i][j + 1];

                maze[i][j] = new Room(north, south, west, east);
                if (j == 0)
                    maze[i][j].lockWestDoor();
                else if (j == this.mazeSize - 1)
                    maze[i][j].lockEastDoor();
                if (i == 0)
                    maze[i][j].lockNorthDoor();
                else if (i == this.mazeSize - 1)
                    maze[i][j].lockSouthDoor();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (4 * mazeSize) + 1; i++) {
            sb.append("*");
        }

        for (int i = 0; i < mazeSize; i++) {
            sb.append("\n*");
            for (int j = 0; j < mazeSize; j++) {
                if (playerRow == i && playerColumn == j)
                    sb.append(" P ");
                else if (goalRow == i && goalColumn == j)
                    sb.append(" G ");
                else
                    sb.append("   ");

                if (maze[i][j].eastDoorUnlocked())
                    sb.append("|");
                else
                    sb.append("*");
            }
            sb.append("\n*");
            for (int j = 0; j < mazeSize; j++) {
                if (maze[i][j].southDoorUnlocked())
                    sb.append("---*");
                else
                    sb.append("****");
            }
        }

        return sb.toString();
    }

    public void moveNorth() {
        if (maze[this.playerRow][this.playerColumn].northDoorUnlocked())
            this.playerRow--;
        else
            System.out.println("North door is locked!");
    }

    public void moveSouth() {
        if (maze[this.playerRow][this.playerColumn].southDoorUnlocked())
            this.playerRow++;
        else
            System.out.println("South door is locked!");
    }

    public void moveWest() {
        if (maze[this.playerRow][this.playerColumn].westDoorUnlocked())
            this.playerColumn--;
        else
            System.out.println("West door is locked!");
    }

    public void moveEast() {
        if (maze[this.playerRow][this.playerColumn].eastDoorUnlocked())
            this.playerColumn++;
        else
            System.out.println("East door is locked!");
    }
}
