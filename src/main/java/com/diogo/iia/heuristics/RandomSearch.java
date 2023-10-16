package com.diogo.iia.heuristics;

import com.diogo.iia.application.Grid;
import com.diogo.iia.application.PuzzleState;
import com.diogo.iia.application.SearchAlgorithm;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomSearch extends SearchAlgorithm {

    public RandomSearch(Grid start) {
        super(start);
    }

    @Override
    public Optional<PuzzleState> solve() throws Exception {
        Set<String> visitedOrFrontier = new HashSet<>();

        Deque<PuzzleState> list = new ArrayDeque<>();

        boolean allowRepetition = false;

        list.add(this.initialStart);

        while (!list.isEmpty()) {
            PuzzleState currentState = list.pop();
            history.store(currentState);

            if (this.isGoal(currentState)) {
                return Optional.of(currentState);
            }

            // Get possible unvisited neighbors to choose from
            List<PuzzleState> unvisitedNeighbors = new ArrayList<>();

            var neighbors = currentState.getGrid().getNeighbors();
            var directions = currentState.getPossibleMovements();

            int i = 0;

            // Add unvisited neighbors to a list
            for (var neightborGrid : neighbors) {

                if (!visitedOrFrontier.contains(neightborGrid.toString())) {
                    PuzzleState newState = new PuzzleState(currentState, neightborGrid, directions.get(i));
                    unvisitedNeighbors.add(newState);
                    if (!allowRepetition) {
                        visitedOrFrontier.add(neightborGrid.toString());
                    }
                }
            }

            // Pick the next state randomly among the neighbors
            if (!unvisitedNeighbors.isEmpty()) {
                int randomIndex = ThreadLocalRandom.current().nextInt(unvisitedNeighbors.size());
                PuzzleState randomNeighbor = unvisitedNeighbors.get(randomIndex);
                list.add(randomNeighbor);
            }
        }

        return Optional.empty();
    }

}
