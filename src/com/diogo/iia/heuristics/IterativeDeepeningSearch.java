package com.diogo.iia.heuristics;

import com.diogo.iia.application.Grid;
import com.diogo.iia.application.PuzzleState;
import com.diogo.iia.application.SearchAlgorithm;
import com.diogo.iia.models.Direction;

import java.util.*;

public class IterativeDeepeningSearch extends SearchAlgorithm {

    public IterativeDeepeningSearch(Grid start) {
        super(start);
    }

    @Override
    public Optional<PuzzleState> solve() throws Exception {
        int depth = 0;
        int MAX_DEPTH = 100_000_000;
        while (depth < MAX_DEPTH) {
            Deque<PuzzleState> stack = new ArrayDeque<>();
            Set<Grid> visitedGrids = new HashSet<>();

            stack.push(initialStart);

            while (!stack.isEmpty()) {
                PuzzleState currentState = stack.pop();
                history.store(currentState);

                if (this.isGoal(currentState)) {
                    return Optional.of(currentState);
                }

                if (currentState.getDepth() < depth) {
                    visitedGrids.add(currentState.getGrid());

                    var neighbors = currentState.getGrid().getNeighbors();
                    var directions = currentState.getPossibleMovements();

                    for (int i = 0; i < neighbors.size(); i++) {
                        Grid newNeighbor = neighbors.get(i);
                        Direction direction = directions.get(i);
                        if (!visitedGrids.contains(newNeighbor)) {
                            var newState = new PuzzleState(currentState, newNeighbor, direction);
                            stack.push(newState);
                        }
                    }
                }
            }
            depth++;
        }
        return Optional.empty();
    }
}
