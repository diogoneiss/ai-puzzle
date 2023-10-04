package com.diogo.iia.heuristics;

import com.diogo.iia.application.Grid;
import com.diogo.iia.application.PuzzleState;
import com.diogo.iia.application.SearchAlgorithm;
import com.diogo.iia.models.Direction;

import java.util.*;

public class UniformSearch extends SearchAlgorithm {

    public UniformSearch(Grid start) {
        super(start);
    }

    @Override
    public Optional<PuzzleState> solve() throws Exception {
        PriorityQueue<PuzzleState> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(PuzzleState::getCost));
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
