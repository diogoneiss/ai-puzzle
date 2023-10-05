package com.diogo.iia.tracking;

import com.diogo.iia.AlgorithmFactory;
import com.diogo.iia.Inputs.PuzzleReader;
import com.diogo.iia.application.Grid;
import com.diogo.iia.application.PuzzleState;
import com.diogo.iia.application.SearchAlgorithm;
import com.diogo.iia.models.AlgorithmStatistics;
import com.diogo.iia.models.RunInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlgorithmExecutor {

    private static String CSV_FILE_NAME = "results.csv";

    public static void executeAndSave() throws Exception {
        var statistics = computeMetrics();
        System.out.println("Saving results to " + CSV_FILE_NAME);
        writeCsv(statistics);
    }

    public static List<AlgorithmStatistics> computeMetrics() throws Exception {
        boolean useSlice = false;
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
        algorithmTypes.remove(algorithmTypes.indexOf("I1"));
        var results = new ArrayList<AlgorithmStatistics>();

        int gridIndex = 0;
        for (Grid grid : grids) {
            for (String algorithmType : algorithmTypes) {
                System.out.print("Executing " + algorithmType + " for the " + gridIndex + "th grid. ");
                SearchAlgorithm algorithm = AlgorithmFactory.createAlgorithm(algorithmType, grid);

                Optional<PuzzleState> solution = algorithm.solve();
                RunInfo solutionInfo = algorithm.showRunInfo();
                double timeSeconds = solutionInfo.timeElapsed() / 1_000_000_000.0;

                System.out.printf("Took %.4f seconds.%n", timeSeconds);

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


                var statistics = new AlgorithmStatistics(
                        algorithmType,
                        gridIndex,
                        solutionInfo.nodesVisited(),
                        solutionInfo.movements(),
                        correctSolution,
                        solutionGap,
                        solutionInfo.timeElapsed()
                );

                results.add(statistics);
            }
            gridIndex++;
        }

        return List.copyOf(results);
    }


    private static void writeCsv(List<AlgorithmStatistics> statistics) throws FileNotFoundException {
        File csvOutputFile = new File(CSV_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(AlgorithmStatistics.csvHeader());

            statistics.stream()
                    .map(s -> s.toCsv())
                    .forEach(pw::println);
        }

    }

}
