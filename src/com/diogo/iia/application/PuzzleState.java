package com.diogo.iia.application;

import java.util.ArrayList;
import java.util.List;

public class PuzzleState {
    private Grid grid;
    private List<Grid.Direction> previousMovements;
    private List<Grid.Direction> possibleMovements;
    private int cost;

    // Constructor for the first state
    public PuzzleState(Grid initialGrid) {
        this.grid = initialGrid;
        this.possibleMovements = this.calculatePossibleMovements();
        this.previousMovements = new ArrayList<Grid.Direction>();
        this.cost = computeDistanceToSolution();
    }

    // Constructor with previous PuzzleState, grid, and direction
    public PuzzleState(PuzzleState prevState, Grid newGrid, Grid.Direction direction) {
        this.grid = newGrid;
        this.possibleMovements = this.calculatePossibleMovements();
        this.previousMovements = new ArrayList<>(prevState.getPreviousMovements());
        this.previousMovements.add(direction);

        this.cost = computeDistanceToSolution();
    }

    public List<Grid.Direction> getPossibleMovements() {
        return possibleMovements;
    }

    public int calculateDistanceHeuristic(DistanceHeuristics heuristic) {
        double distance = 0;
        var gridMatrix = this.grid.getGrid();

        for (int i = 0; i < gridMatrix.length; i++) {
            for (int j = 0; j < gridMatrix[i].length; j++) {
                int value = gridMatrix[i][j];
                if (value != 0) {
                    var goalPos = this.grid.findPosition(value);

                    int dx = i - goalPos.x();
                    int dy = j - goalPos.y();

                    distance += heuristic.distance(dx, dy);
                }
            }
        }
        if (Double.isNaN(distance) || distance == Double.POSITIVE_INFINITY) {
            throw new IllegalArgumentException("Cannot safely convert " + distance + " to int.");
        }
        return (int) Math.ceil(distance);
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

    public int getDepth() {
        var previous = this.previousMovements;
        return previous.size();
    }

    public int getCost() {
        return this.cost;

    }

    public void setCost(int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost for a given state cannot be < 0");
        }
        this.cost = cost;
    }

    public List<Grid.Direction> calculatePossibleMovements() {
        return Grid.possibleSwaps(this.grid.getBlankX(), this.grid.getBlankY());

    }

    public enum DistanceHeuristics {
        MANHATTAN {
            @Override
            public double distance(int dx, int dy) {
                return Math.abs(dx) + Math.abs(dy);
            }
        },
        EUCLIDEAN {
            @Override
            public double distance(int dx, int dy) {
                return Math.sqrt(dx * dx + dy * dy);
            }
        };

        public abstract double distance(int dx, int dy);
    }


}
