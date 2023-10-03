package com.diogo.iia.Main;

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
    protected boolean isGoal(Grid candidateGrid) {
        int[][] goal = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        var grid = candidateGrid.getGrid();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }



    public void showRunInfo() throws Exception {
        var solution = this.solve();
        System.out.println("Starting grid: ");

        this.initialStart.getGrid().display();

        var historySteps = this.history.getHistory();
        System.out.printf("Solution took %d steps.\n", historySteps.size());


        System.out.printf("Steps taken in solution: %s \n", this.history.formatMovements());
        System.out.println(this.history.getMovements().size());
        //System.out.println("All grids: ");
        //this.history.printGrids();
       this.history.printSolutionPath();

    }
}