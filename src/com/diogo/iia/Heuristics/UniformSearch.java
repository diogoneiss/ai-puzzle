package com.diogo.iia.Heuristics;

import com.diogo.iia.Main.Grid;
import com.diogo.iia.Main.PuzzleState;
import com.diogo.iia.Main.SearchAlgorithm;

import java.util.*;

public class UniformSearch extends SearchAlgorithm {

    public UniformSearch(Grid start) {
        super(start);
    }

    @Override
    public Optional<PuzzleState> solve() throws Exception {
        PriorityQueue<PuzzleState> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(PuzzleState::getDistanceToSolution));
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
                Grid.Direction direction = directions.get(i);
                if (!visitedGrids.contains(newNeighbor)) {
                    var newState = new PuzzleState(currentState, newNeighbor, direction);
                    priorityQueue.add(newState);
                }
            }
        }

        return Optional.empty();
    }

}
