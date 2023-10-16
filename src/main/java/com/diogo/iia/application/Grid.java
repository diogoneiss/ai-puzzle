package com.diogo.iia.application;

import com.diogo.iia.models.Direction;
import com.diogo.iia.models.Position;

import java.util.*;

public class Grid {
    // As the grid target positions are fixed, I only need to compute them once.
    private static final Map<Integer, Position> goalPositions = new HashMap<>();

    static {
        int[][] goal = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal[i].length; j++) {
                goalPositions.put(goal[i][j], new Position(i, j));
            }
        }
    }

    public int hash;
    private int[][] grid;
    private int blankX, blankY;


    // Constructor for two-dimensional array
    public Grid(int[][] twoDimArray) throws Exception {
        if (!isSolvable(twoDimArray)) {
            throw new Exception("Puzzle is not solvable!");
        }
        this.grid = twoDimArray;
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
    public Grid(int[] singleDimArray) throws Exception {
        this(convertArrayDimentions(singleDimArray));
    }

    public static List<Direction> possibleSwaps(int blankX, int blankY) {
        List<Direction> possibleDirections = new ArrayList<>();

        if (blankX > 0) possibleDirections.add(Direction.UP);
        if (blankX < 2) possibleDirections.add(Direction.DOWN);
        if (blankY > 0) possibleDirections.add(Direction.LEFT);
        if (blankY < 2) possibleDirections.add(Direction.RIGHT);

        return possibleDirections;
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

        // recalculate hash
        swappedGrid.hash = swappedGrid.computeGridHash();

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
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid1 = (Grid) o;
        return blankX == grid1.blankX && blankY == grid1.blankY && hash == grid1.hash && Arrays.equals(grid, grid1.grid);
    }*/

    public static boolean isSolvable(int[] puzzle) {
        return isSolvable(convertArrayDimentions(puzzle));
    }

    public static boolean isSolvable(int[][] puzzle) {
        if (puzzle.length != 3 || puzzle[0].length != 3) {
            throw new IllegalArgumentException("Puzzle must be a 3x3 matrix.");
        }

        int inversions = countInversions(puzzle);

        return inversions % 2 == 0;
    }

    private static int countInversions(int[][] puzzle) {
        int inversions = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int currentVal = puzzle[i][j];

                if (currentVal == 0) {
                    continue;
                }

                // Count inversions in subsequent tiles
                for (int k = i; k < 3; k++) {
                    int l = j;
                    if (k > i) {
                        l = 0;
                    }
                    for (; l < 3; l++) {
                        int subsequentVal = puzzle[k][l];
                        if (subsequentVal != 0 && currentVal > subsequentVal) {
                            inversions++;
                        }
                    }
                }
            }
        }
        return inversions;
    }

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

    // Display method to print the current state of the puzzle
    public void display() {
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
        System.out.println(renderedGrid);
    }

    @Override
    public String toString() {
        StringBuilder renderedGrid = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == blankX && j == blankY) {
                    renderedGrid.append("_");
                } else {
                    renderedGrid.append(grid[i][j]);
                }
            }
        }
        return renderedGrid.toString();

    }

    private void checkBlankFound() throws Exception {
        if (grid[blankX][blankY] != 0) {
            throw new Exception("Blank space not found in the provided grid.");
        }
    }

    /*
        @Override
        public int hashCode() {
            return this.computeGridHash();
        }
    */
    public int computeGridHash() {
        return Arrays.deepHashCode(this.grid);
        /*
        StringBuilder gridString = new StringBuilder(9);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == 0) {
                    gridString.append('-');
                } else {
                    gridString.append(grid[i][j]);
                }
            }
        }

        return gridString.toString().hashCode();*/
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Grid other = (Grid) obj;
        return Arrays.deepEquals(this.grid, other.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.grid);
    }

    public List<Grid> getNeighbors() throws Exception {
        var neighbors = new ArrayList<Grid>();
        for (Direction direction : this.possibleSwaps()) {

            Grid newGrid = Grid.move(this, direction);
            neighbors.add(newGrid);

        }
        return neighbors;
    }

    public Position findPosition(int value) {
        var retrived = goalPositions.get(value);

        return Optional
                .ofNullable(retrived)
                .orElseThrow(() -> new IllegalArgumentException("Value not found in the grid."));
    }


}
