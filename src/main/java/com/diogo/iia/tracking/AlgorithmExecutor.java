package com.diogo.iia.tracking;

import com.diogo.iia.AlgorithmFactory;
import com.diogo.iia.Inputs.CsvHandler;
import com.diogo.iia.Inputs.PuzzleReader;
import com.diogo.iia.application.Grid;
import com.diogo.iia.application.PuzzleState;
import com.diogo.iia.application.SearchAlgorithm;
import com.diogo.iia.models.RunInfo;
import com.diogo.iia.models.AlgorithmStatistics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AlgorithmExecutor {

    public static void executeAndSave() throws Exception {
        executeAndSave(false);
    }

    public static void executeAndSave(boolean useSlice) throws Exception {
        var statistics = computeMetrics(useSlice);
        System.out.println("Saving results to " + CsvHandler.CSV_FILE_NAME);
        CsvHandler.writeCsv(statistics);
    }

    public static List<AlgorithmStatistics> computeMetrics(boolean useSlice) throws Exception {

        var inputs = PuzzleReader.readPuzzlesFromCSV();

        if (useSlice) {
            inputs = inputs.subList(0, 25);
        }

        List<Grid> grids = new ArrayList<>();
        List<Integer> solutions = new ArrayList<>();

        for (var input : inputs) {
            grids.add(new Grid(input.grid()));
            solutions.add(input.solution());
        }

        var algorithmTypes = AlgorithmFactory.algorithmTypes;
        //algorithmTypes.remove(algorithmTypes.indexOf("I1"));
        var results = new ArrayList<AlgorithmStatistics>();

        int gridIndex = 0;
        for (Grid grid : grids) {
            for (String algorithmType : algorithmTypes) {
                if (Objects.equals(algorithmType, "I1") && gridIndex >19 ){
                    System.out.println("Skipping iterative deepening, it'll take too long");
                    continue;
                }
                String gridHash = grid.toString();
                System.out.println("Executing " + algorithmType + " for the " + gridIndex + "th grid, " + gridHash);
                SearchAlgorithm algorithm = AlgorithmFactory.createAlgorithm(algorithmType, grid);

                Optional<PuzzleState> solution = algorithm.solve();
                System.out.println("Solution: ");
                RunInfo solutionInfo = algorithm.showRunInfo();
                double timeSeconds = solutionInfo.timeElapsed() / 1_000_000_000.0;

                System.out.printf("\nTook %.4f seconds.\n", timeSeconds);

                int correctSolution = solutions.get(gridIndex);
                long movementsTaken = solutionInfo.movements();
                double solutionGap;

                if (correctSolution != 0) {
                    // Gap = | y' - y | / y
                    solutionGap = Math.abs(movementsTaken - correctSolution) / (double) correctSolution;
                } else {
                    solutionGap = 0;
                }

                solutionGap = Math.round(solutionGap * 100.0) / 100.0;

                boolean solutionFound = solution.isPresent();
                var statistics = new AlgorithmStatistics(
                        algorithmType,
                        gridHash,
                        solutionInfo.nodesVisited(),
                        solutionInfo.movements(),
                        correctSolution,
                        solutionFound,
                        solutionGap,
                        solutionInfo.timeElapsed()
                );

                results.add(statistics);
            }
            gridIndex++;
        }

        return List.copyOf(results);
    }


}
