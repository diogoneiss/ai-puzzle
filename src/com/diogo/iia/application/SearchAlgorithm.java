package com.diogo.iia.application;

import com.diogo.iia.models.RunInfo;

import java.util.Optional;

public abstract class SearchAlgorithm {
    protected PuzzleHistory history;

    protected PuzzleState initialStart;


    public SearchAlgorithm(Grid start) {
        this.history = new PuzzleHistory();
        this.initialStart = new PuzzleState(start);
    }

    public PuzzleHistory getHistory() {
        return history;
    }

    public void setHistory(PuzzleHistory history) {
        this.history = history;
    }

    public abstract Optional<PuzzleState> solve() throws Exception;

    // Check if the grid is the goal state
    protected boolean isGoal(PuzzleState current) {
        return current.getCorrectTiles() == 0;
    }

    public RunInfo showRunInfo() throws Exception {
        return this.showRunInfo(false);
    }

    public RunInfo showRunInfo(boolean showSolution) throws Exception {

        // Stop memory usage and time
        this.history.endTime();

        long timeElapsed = this.history.getTimeElapsed();


        var nodesVisited = this.history.getNodesVisited();
        var movesToSolution = this.history.getMovements().size();
        if (showSolution) {
            System.out.println("Starting grid: ");

            this.initialStart.getGrid().display();

            System.out.printf("Solution visited %d nodes and took %d ms.\n", nodesVisited, timeElapsed);


            System.out.printf("Steps taken in solution: %s \n", this.history.formatMovements());
            System.out.println(movesToSolution);
            //System.out.println("All grids: ");
            //this.history.printGrids();
            this.history.printSolutionPath();
        }

        return new RunInfo(nodesVisited, movesToSolution, this.history, timeElapsed);

    }
}