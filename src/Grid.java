import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Grid {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid1 = (Grid) o;
        return blankX == grid1.blankX && blankY == grid1.blankY && hash == grid1.hash && Arrays.equals(grid, grid1.grid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(blankX, blankY, hash);
        result = 31 * result + Arrays.hashCode(grid);
        return result;
    }

    private int[][] grid;
    private int blankX, blankY;
    public int hash;

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int getBlankX() {
        return blankX;
    }

    public void setBlankX(int blankX) {
        this.blankX = blankX;
    }

    public int getBlankY() {
        return blankY;
    }

    public void setBlankY(int blankY) {
        this.blankY = blankY;
    }

    // Constructor for two-dimensional array
    public Grid(int[][] twoDimArray) throws Exception  {
        this.grid = twoDimArray;
        //TODO check length of matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (twoDimArray[i][j] == 0) {
                    this.blankX = i;
                    this.blankY = j;
                    break;
                }
            }
        }
        this.checkBlankFound();
        this.hash = this.computeGridHash();
    }

    // Constructor for single-dimensional array
    public Grid(int[] singleDimArray) throws Exception  {
        this(convertArrayDimentions(singleDimArray));
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static List<Direction> possibleSwaps(int blankX, int blankY) {
        List<Direction> possibleDirections = new ArrayList<>();

        if (blankX > 0) possibleDirections.add(Direction.UP);
        if (blankX < 2) possibleDirections.add(Direction.DOWN);
        if (blankY > 0) possibleDirections.add(Direction.LEFT);
        if (blankY < 2) possibleDirections.add(Direction.RIGHT);

        return possibleDirections;
    }
    public List<Direction> possibleSwaps() {
        return Grid.possibleSwaps(this.blankX, this.blankY);
    }

    public Grid copy() {
        int[][] newGrid = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(this.grid[i], 0, newGrid[i], 0, 3);
        }

        try {
            return new Grid(newGrid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Grid move(Grid original, Direction direction) throws Exception {
        Grid swappedGrid = original.copy();

        int newBlankX = original.blankX;
        int newBlankY = original.blankY;

        switch (direction) {
            case UP:
                newBlankX--;
                break;
            case DOWN:
                newBlankX++;
                break;
            case LEFT:
                newBlankY--;
                break;
            case RIGHT:
                newBlankY++;
                break;
        }
        // put the numbered tile on the blank space
        swappedGrid.grid[original.blankX][original.blankY] = swappedGrid.grid[newBlankX][newBlankY];
        // move the blank space to the tile
        swappedGrid.grid[newBlankX][newBlankY] = 0;
        swappedGrid.blankX = newBlankX;
        swappedGrid.blankY = newBlankY;

        return swappedGrid;
    }

    private static int[][] convertArrayDimentions(int[] singleDimArray) {
        int[][] twoDimArray = new int[3][3];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                twoDimArray[i][j] = singleDimArray[k++];
            }
        }
        return twoDimArray;
    }

    // Display method to print the current state of the puzzle
    public void display() {
        System.out.println(this);
    }
    @Override
    public String toString() {
        StringBuilder renderedGrid = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == blankX && j == blankY) {
                   renderedGrid.append("  ");
                } else {
                    renderedGrid.append(grid[i][j]).append(" ");
                }
            }
            renderedGrid.append("\n");
        }
        return renderedGrid.toString();

    }
    private void checkBlankFound() throws Exception {
        if (grid[blankX][blankY] != 0) {
            throw new Exception("Blank space not found in the provided grid.");
        }
    }

    public int computeGridHash() {
        StringBuilder gridString = new StringBuilder(9);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridString.append(grid[i][j]);
            }
        }

        return gridString.toString().hashCode();
    }

}
