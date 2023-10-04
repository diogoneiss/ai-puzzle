package com.diogo.iia;

import com.diogo.iia.application.Grid;
import com.diogo.iia.application.SearchAlgorithm;
import com.diogo.iia.heuristics.*;
import com.diogo.iia.models.AlgorithmType;
import com.diogo.iia.models.DistanceHeuristics;
import com.diogo.iia.models.HillClimbingOptions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AlgorithmFactory {
    //public final static List<String> algorithmTypes = List.of("B", "I", "U", "A", "A2", "G", "G2", "H", "HR", "HS1", "HS2", "HS3");
    public final static List<String> algorithmTypes =
            Arrays.stream(AlgorithmType.values())
                    .map(AlgorithmType::name)
                    .collect(Collectors.toList());

    public static SearchAlgorithm createAlgorithm(String algorithmTypeStr, Grid start) {
        AlgorithmType algorithmType = AlgorithmType.fromString(algorithmTypeStr);

        return switch (algorithmType) {
            case B -> new BFS(start);
            case I -> new IterativeDeepeningSearch(start);
            case U -> new UniformSearch(start);
            case A -> new AStarSearch(start);
            case A2 -> new AStarSearch(start, DistanceHeuristics.EUCLIDEAN);
            case G -> new GreedyBestFirstSearch(start, true);
            case G2 -> new GreedyBestFirstSearch(start, false);
            case H -> new HillClimbing(start, HillClimbingOptions.REGULAR, DistanceHeuristics.MANHATTAN);
            case HR -> new HillClimbing(start, HillClimbingOptions.STOCHASTIC, DistanceHeuristics.MANHATTAN);
            case HS1 -> new HillClimbing(start, HillClimbingOptions.SIMULATED_ANNEALING, DistanceHeuristics.MANHATTAN);
            case HS2 -> new HillClimbing(start, HillClimbingOptions.SIMULATED_ANNEALING, DistanceHeuristics.EUCLIDEAN);
            case HS3 ->
                    new HillClimbing(start, HillClimbingOptions.SIMULATED_ANNEALING, DistanceHeuristics.CORRECTNESS);
            case R -> new RandomSearch(start);
        };
    }
}
