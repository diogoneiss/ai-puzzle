package com.diogo.iia.application;

import com.diogo.iia.models.Direction;
import com.diogo.iia.models.DistanceHeuristics;

import java.util.ArrayList;
import java.util.List;

public class PuzzleState {
    private Grid grid;
    private List<Direction> previousMovements;
    private List<Direction> possibleMovements;
    private int correctTiles;

    private int cost = 0;


    // Constructor for the first state
    public PuzzleState(Grid initialGrid) {
        this.grid = initialGrid;
        this.possibleMovements = this.calculatePossibleMovements();
        this.previousMovements = new ArrayList<Direction>();
        this.correctTiles = computeGridCorrectness();
        this.cost = 0;
    }

    // Constructor with previous PuzzleState, grid, and direction
    public PuzzleState(PuzzleState prevState, Grid newGrid, Direction direction) {
        this.grid = newGrid;
        this.possibleMovements = this.calculatePossibleMovements();
        this.previousMovements = new ArrayList<>(prevState.getPreviousMovements());
        this.previousMovements.add(direction);

        this.correctTiles = computeGridCorrectness();
        this.cost = this.previousMovements.size();

    }

    public PuzzleState(Grid predecessorGrid, List<Direction> predecessorMovements) {
        this.grid = predecessorGrid;
        this.possibleMovements = this.calculatePossibleMovements();
        this.previousMovements = predecessorMovements;
        this.correctTiles = computeGridCorrectness();
    }

    public List<Direction> getPossibleMovements() {
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

    public void increaseCost() {
        this.cost++;
    }

    private int computeGridCorrectness() {
        var heuristic = DistanceHeuristics.CORRECTNESS;
        return calculateDistanceHeuristic(heuristic);
    }

    public Grid getGrid() {
        return grid;
    }

    public List<Direction> getPreviousMovements() {
        return previousMovements;
    }

    public int getDepth() {
        var movementsSize = this.previousMovements.size();
        assert movementsSize <= this.cost;
        return this.cost;
    }

    public int getCorrectTiles() {
        return this.correctTiles;
    }

    public void setCorrectTiles(int correctTiles) {
        if (correctTiles < 0) {
            throw new IllegalArgumentException("Cost for a given state cannot be < 0");
        }
        this.correctTiles = correctTiles;
    }

    public List<Direction> calculatePossibleMovements() {
        return Grid.possibleSwaps(this.grid.getBlankX(), this.grid.getBlankY());

    }

    public PuzzleState getPredecessor() throws Exception {
        if (this.previousMovements == null || this.previousMovements.isEmpty()) {
            return null;
        }

        Direction lastMove = this.previousMovements.get(this.previousMovements.size() - 1);
        Direction oppositeOfLastMove = lastMove.getOpposite();

        Grid predecessorGrid = Grid.move(this.getGrid(), oppositeOfLastMove);

        // We will copy all movements excluding the last one to create the predecessor PuzzleState
        List<Direction> predecessorMovements = new ArrayList<>(this.previousMovements.subList(0, this.previousMovements.size() - 1));

        return new PuzzleState(predecessorGrid, predecessorMovements);
    }
}
