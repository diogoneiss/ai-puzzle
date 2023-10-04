package com.diogo.iia.heuristics;

import com.diogo.iia.application.*;
import com.diogo.iia.models.Direction;
import com.diogo.iia.models.DistanceHeuristics;

import java.util.*;

public class GreedyBestFirstSearch extends SearchAlgorithm {

    private boolean useCost;

    public GreedyBestFirstSearch(Grid start, boolean useCost) {
        super(start);
        this.useCost = useCost;
    }

    public GreedyBestFirstSearch(Grid start) {
        this(start, false);
    }

    @Override
    public Optional<PuzzleState> solve() throws Exception {

        Comparator<PuzzleState> comparator;

        if (this.useCost) {
            // Its the same thing as using the Correctness distance, but this is faster
            comparator = Comparator.comparingInt(PuzzleState::getCost);
        } else {
            comparator = Comparator.comparingInt(ps -> ps.calculateDistanceHeuristic(DistanceHeuristics.MANHATTAN));
        }

        PriorityQueue<PuzzleState> priorityQueue = new PriorityQueue<>(comparator);
        Set<Grid> visitedGrids = new HashSet<>();

        priorityQueue.add(initialStart);

        while (!priorityQueue.isEmpty()) {
            PuzzleState currentState = priorityQueue.poll();
            history.store(currentState);

            assert currentState != null;

            if (this.isGoal(currentState)) {
                return Optional.of(currentState);
            }

            visitedGrids.add(currentState.getGrid());

            var neighbors = currentState.getGrid().getNeighbors();
            var directions = currentState.getPossibleMovements();

            for (int i = 0; i < neighbors.size(); i++) {
                Grid newNeighbor = neighbors.get(i);
                Direction direction = directions.get(i);
                if (!visitedGrids.contains(newNeighbor)) {
                    var newState = new PuzzleState(currentState, newNeighbor, direction);
                    priorityQueue.add(newState);
                }
            }
        }
        return Optional.empty();
    }
}
