package com.diogo.iia.heuristics;

import com.diogo.iia.application.Grid;
import com.diogo.iia.application.PuzzleState;
import com.diogo.iia.application.SearchAlgorithm;
import com.diogo.iia.models.Direction;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomSearch extends SearchAlgorithm {

    public RandomSearch(Grid start) {
        super(start);
    }

    @Override
    public Optional<PuzzleState> solve() throws Exception {
        Set<Integer> visitedOrFrontier = new HashSet<>();
        Set<Grid> visitedGrids = new HashSet<>();

        List<PuzzleState> list = new ArrayList<>(); // Random access support

        list.add(this.initialStart);

        while (!list.isEmpty()) {
            // Randomly select a state to explore
            int randomIndex = ThreadLocalRandom.current().nextInt(list.size());
            PuzzleState currentState = list.remove(randomIndex);
            history.store(currentState);

            // If this state is the goal state, return the grid
            if (this.isGoal(currentState)) {
                return Optional.of(currentState);
            }

            // Add unvisited nodes to frontier in possible directions
            for (Direction direction : currentState.getPossibleMovements()) {
                var currentGrid = currentState.getGrid();
                Grid newGrid = Grid.move(currentGrid, direction);

                if (!visitedOrFrontier.contains(newGrid.hash) || true) {
                    PuzzleState newState = new PuzzleState(currentState, newGrid, direction);
                    list.add(newState);  // Adding to the frontier
                    visitedOrFrontier.add(newGrid.hash);
                    visitedGrids.add(newGrid);
                }
            }
        }

        return Optional.empty();
    }
}
