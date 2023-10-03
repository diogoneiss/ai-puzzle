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

    // Constructor with previous PuzzleState, grid, and direction
    public PuzzleState(PuzzleState prevState, Grid newGrid, Grid.Direction direction) {
        this.grid = newGrid;
        this.possibleMovements = this.calculatePossibleMovements();
        this.previousMovements = new ArrayList<>(prevState.getPreviousMovements());
        this.previousMovements.add(direction);

        this.distanceToSolution = computeDistanceToSolution();
    }


    private int computeDistanceToSolution() {
        int distance = 0;

        int[][] goal = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        var gridMatrix = this.grid.getGrid();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gridMatrix[i][j] != goal[i][j]) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public Grid getGrid() {
        return grid;
    }

    public List<Grid.Direction> getPreviousMovements() {
        return previousMovements;
    }

    public int getDistanceToSolution() {
           return this.distanceToSolution;

    }

    public List<Grid.Direction> calculatePossibleMovements() {
        return Grid.possibleSwaps(this.grid.getBlankX(), this.grid.getBlankY());

    }



}
