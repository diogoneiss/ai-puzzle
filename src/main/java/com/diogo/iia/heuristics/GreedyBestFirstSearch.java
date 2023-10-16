package com.diogo.iia.heuristics;

import com.diogo.iia.models.Direction;
import com.diogo.iia.models.DistanceHeuristics;
import com.diogo.iia.application.Grid;
import com.diogo.iia.application.PuzzleState;
import com.diogo.iia.application.SearchAlgorithm;

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
            comparator = Comparator.comparingInt(PuzzleState::getDepth);
        } else {
            comparator = Comparator.comparingInt(ps -> ps.calculateDistanceHeuristic(DistanceHeuristics.MANHATTAN));
        }

        PriorityQueue<PuzzleState> priorityQueue = new PriorityQueue<>(comparator);
        Set<String> visitedGrids = new HashSet<>();

        priorityQueue.add(initialStart);

        while (!priorityQueue.isEmpty()) {
            PuzzleState currentState = priorityQueue.poll();
            history.store(currentState);

            assert currentState != null;

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
                    priorityQueue.add(newState);
                }
            }
        }
        return Optional.empty();
    }
}
