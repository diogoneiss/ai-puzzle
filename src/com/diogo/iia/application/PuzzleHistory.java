package com.diogo.iia.application;

import com.diogo.iia.models.Direction;

import java.util.ArrayList;
import java.util.List;

public class PuzzleHistory {
    private List<PuzzleState> history = new ArrayList<>();

    public void store(PuzzleState state) {
        history.add(state);
    }

    public void printGrids() {
        for (int i = 0; i < history.size(); i++) {
            var current = history.get(i);
            var lastMovement = "None";
            var movements = current.getPreviousMovements();
            if (!movements.isEmpty()) {
                lastMovement = movements.get(movements.size() - 1).toString();
            }
            System.out.printf("Grid %d. Movement: %s\n", i, lastMovement);
            current.getGrid().display();

        }
    }

    public String formatMovements() {
        StringBuilder movementList = new StringBuilder();

        for (var movements : this.getMovements()) {
            movementList.append(movements).append(" ");
        }

        return movementList.toString();
    }

    public List<Direction> getMovements() {
        return this.getLastState().getPreviousMovements();
    }

    public List<Grid> getSolutionPath() throws Exception {

        var moves = this.getLastState().getPreviousMovements();
        var startingGrid = history.get(0).getGrid();
        var gridEvolution = new ArrayList<Grid>();
        gridEvolution.add(startingGrid);
        for (var movement : moves) {
            var last = gridEvolution.get(gridEvolution.size() - 1);
            var nextMove = Grid.move(last, movement);
            gridEvolution.add(nextMove);
        }

        return gridEvolution;
    }

    public void printSolutionPath() throws Exception {
        var solutionPath = this.getSolutionPath();
        //System.out.println("Solution path: ");
        for (var grid : solutionPath) {
            grid.display();
        }
        System.out.println("______________________");
    }

    public List<PuzzleState> getHistory() {
        return history;
    }

    public PuzzleState getLastState() {
        var index = history.size() - 1;
        if (index < 0) {
            System.out.println("Alerta! Index menor que zero");
            index = 0;
        }
        return history.get(index);
    }


}