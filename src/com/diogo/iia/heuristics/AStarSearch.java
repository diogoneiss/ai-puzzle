package com.diogo.iia.heuristics;

import com.diogo.iia.application.*;

import java.util.*;

public class AStarSearch extends SearchAlgorithm {

    public AStarSearch(Grid start) {
        super(start);
    }

    public Optional<PuzzleState> solve() throws Exception {
        var defaultHeuristic = PuzzleState.DistanceHeuristics.MANHATTAN;

        return solve(defaultHeuristic);
    }


    public Optional<PuzzleState> solve(PuzzleState.DistanceHeuristics distanceHeuristic) throws Exception {
        //A Star uses the sum of the distance to solution and the distance heuristic as the priority
        PriorityQueue<PuzzleState> priorityQueue =
                new PriorityQueue<>(Comparator.comparingInt(node ->
                        node.calculateDistanceHeuristic(distanceHeuristic) + node.getDepth()
                ));

        Set<Grid> visitedGrids = new HashSet<>();

        priorityQueue.add(initialStart);

        while (!priorityQueue.isEmpty()) {

            PuzzleState currentState = priorityQueue.poll();
            history.store(currentState);

            if (this.isGoal(currentState)) {
                return Optional.of(currentState);
            }

            visitedGrids.add(currentState.getGrid());

            var neighbors = currentState.getGrid().getNeighbors();
            var directions = currentState.getPossibleMovements();

            for (int i = 0; i < neighbors.size(); i++) {
                Grid newNeighbor = neighbors.get(i);
                Grid.Direction direction = directions.get(i);
                if (!visitedGrids.contains(newNeighbor)) {
                    var newState = new PuzzleState(currentState, newNeighbor, direction);
                    newState.setCost(currentState.getCost() + 1);
                    priorityQueue.add(newState);
                }
            }
        }
        return Optional.empty();
    }
}
