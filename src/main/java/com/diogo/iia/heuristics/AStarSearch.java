package com.diogo.iia.heuristics;

import com.diogo.iia.models.Direction;
import com.diogo.iia.models.DistanceHeuristics;
import com.diogo.iia.application.Grid;
import com.diogo.iia.application.PuzzleState;
import com.diogo.iia.application.SearchAlgorithm;

import java.util.*;

public class AStarSearch extends SearchAlgorithm {
    private DistanceHeuristics distanceHeuristic;

    public AStarSearch(Grid start) {
        this(start, DistanceHeuristics.MANHATTAN);
    }

    public AStarSearch(Grid start, DistanceHeuristics heuristic) {
        super(start);
        this.distanceHeuristic = heuristic;
    }

    @Override
    public Optional<PuzzleState> solve() throws Exception {
        //A Star uses the sum of the distance to solution and the distance heuristic as the priority
        PriorityQueue<PuzzleState> priorityQueue =
                new PriorityQueue<>(Comparator.comparingInt(node ->
                        node.calculateDistanceHeuristic(this.distanceHeuristic) + node.getDepth()
                ));

        Set<String> visitedGrids = new HashSet<>();

        priorityQueue.add(initialStart);

        while (!priorityQueue.isEmpty()) {

            PuzzleState currentState = priorityQueue.poll();
            history.store(currentState);

            if (this.isGoal(currentState)) {
                return Optional.of(currentState);
            }

            visitedGrids.add(currentState.getGrid().toString());

            var neighbors = currentState.getGrid().getNeighbors();
            var directions = currentState.getPossibleMovements();

            for (int i = 0; i < neighbors.size(); i++) {
                Grid newNeighbor = neighbors.get(i);
                Direction direction = directions.get(i);
                if (!visitedGrids.contains(newNeighbor.toString())) {
                    var newState = new PuzzleState(currentState, newNeighbor, direction);
                    newState.increaseCost();
                    priorityQueue.add(newState);
                }
            }
        }
        return Optional.empty();
    }
}
