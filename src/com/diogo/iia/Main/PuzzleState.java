package com.diogo.iia.Main;

import java.util.ArrayList;
import java.util.List;

public class PuzzleState {
    private Grid grid;
    private List<Grid.Direction> previousMovements;
    private List<Grid.Direction> possibleMovements;

    public List<Grid.Direction> getPossibleMovements() {
        return possibleMovements;
    }

    private int distanceToSolution;

    // Constructor for the first state
    public PuzzleState(Grid initialGrid) {
        this.grid = initialGrid;
        this.possibleMovements = this.calculatePossibleMovements();
        this.previousMovements = new ArrayList<Grid.Direction>();
        this.distanceToSolution = computeDistanceToSolution();
    }

    // Constructor with previous com.diogo.iia.Main.PuzzleState, grid, and direction
    public PuzzleState(PuzzleState prevState, Grid newGrid, Grid.Direction direction) {
        this.grid = newGrid;
        this.possibleMovements = this.calculatePossibleMovements();
        this.previousMovements = new ArrayList<>(prevState.getPreviousMovements());
        this.previousMovements.add(direction);

        this.distanceToSolution = computeDistanceToSolution();  // Assuming a method to compute it
    }

    // Assuming a method to compute distance to the solution. Placeholder for now.
    private int computeDistanceToSolution() {
        // TODO: Implement logic to compute distance to solution
        return 0;  // Placeholder value
    }

    public Grid getGrid() {
        return grid;
    }

    public List<Grid.Direction> getPreviousMovements() {
        return previousMovements;
    }

    public int getDistanceToSolution() {
        return distanceToSolution;
    }

    public List<Grid.Direction> calculatePossibleMovements() {
        return Grid.possibleSwaps(this.grid.getBlankX(), this.grid.getBlankY());

    }

}
