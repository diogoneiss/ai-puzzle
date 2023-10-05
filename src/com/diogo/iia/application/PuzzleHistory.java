package com.diogo.iia.application;

import com.diogo.iia.models.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleHistory {
    private PuzzleState last;


    private long nodesVisited = 0;
    private long timeStart = 0;
    private long timeElapsed = 0;

    public PuzzleHistory() {
        this.startTime();
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public void store(PuzzleState state) {
        this.last = state;
        nodesVisited++;
    }

    private void startTime() {
        this.timeStart = System.nanoTime();
    }

    public void endTime() {
        this.timeElapsed = System.nanoTime() - this.timeStart;
    }


    public String formatMovements() {
        StringBuilder movementList = new StringBuilder();

        for (var movements : this.getMovements()) {
            movementList.append(movements).append(" ");
        }

        return movementList.toString();
    }

    public List<Direction> getMovements() {
        return this.last.getPreviousMovements();
    }


    public List<Grid> getSolutionPath() throws Exception {
        List<Grid> path = new ArrayList<>();

        // Traverse the "linked list" of movements to retrieve the grid
        for (PuzzleState state = this.last; state != null; state = state.getPredecessor()) {
            path.add(state.getGrid());
        }

        Collections.reverse(path);  // Since we want the path from start to goal, not goal to start
        return path;
    }

    public void printSolutionPath() throws Exception {
        var solutionPath = this.getSolutionPath();
        //System.out.println("Solution path: ");
        for (var grid : solutionPath) {
            grid.display();
        }
        System.out.println("______________________");
    }

    public PuzzleState getLast() {
        return last;
    }

    public long getNodesVisited() {
        return this.nodesVisited;
    }


}