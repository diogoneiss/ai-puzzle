package com.diogo.iia;// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import com.diogo.iia.Inputs.PuzzleReader;
import com.diogo.iia.application.Grid;
import com.diogo.iia.heuristics.*;
import com.diogo.iia.tracking.AlgorithmExecutor;

public class Main {
    public static void main(String[] args) throws Exception {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        //int[] sampleInput = { 1, 2, 3, 4, 0, 5, 7, 8, 6};

        boolean executeSingleAlgorithm = false;
        var gridList = PuzzleReader.readPuzzlesFromCSV();
        int testCaseIndex = 0;
        int desiredCase = 4;

        if (executeSingleAlgorithm) {
            for (var grid : gridList) {

                testCaseIndex++;
                if (testCaseIndex == desiredCase) {
                    continue;
                }
                //int[] sampleInput = {1, 5, 2, 0, 4, 3, 7, 8, 6};

                Grid base = new Grid(grid.grid());

                var searchHeuristic = new BFS(base);

                var finalState = searchHeuristic.solve();
                var runInfo = searchHeuristic.showRunInfo(true);
                System.out.printf("Case %d: Solution was %d and expected %d\n", testCaseIndex, runInfo.movements(), grid.solution());
                //searchHeuristic.showRunInfo();

            }
        } else {
            AlgorithmExecutor.executeAndSave();
        }
        System.out.println("Done");

    }
}