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
        PriorityQueue<PuzzleState> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(PuzzleState::getDepth));

        //Set<Grid> visitedGrids = new HashSet<>();
        // List<Integer> bestCosts = new ArrayList<>();
        // Its more efficient to use a HashMap to store the visited grids and their costs than a HashSet and an ArrayList
        HashMap<String, Integer> visitedGridCosts = new HashMap<>();

        priorityQueue.add(initialStart);

        while (!priorityQueue.isEmpty()) {
            PuzzleState currentState = priorityQueue.poll();
            history.store(currentState);

            if (this.isGoal(currentState)) {
                return Optional.of(currentState);
            }

            // Mark popped node as visited
            visitedGridCosts.put(currentState.getGrid().toString(), currentState.getDepth());

            var neighbors = currentState.getGrid().getNeighbors();
            var directions = currentState.getPossibleMovements();

            for (int i = 0; i < neighbors.size(); i++) {
                Grid newNeighbor = neighbors.get(i);
                String newNeighborHash = newNeighbor.toString();
                Direction direction = directions.get(i);
                var newState = new PuzzleState(currentState, newNeighbor, direction);

                // Check if key is in the HashMap
                if (!visitedGridCosts.containsKey(newNeighborHash)) {
                    priorityQueue.add(newState);
                } else {

                    var previousCost = visitedGridCosts.get(newNeighborHash);
                    var newCost = currentState.getDepth();
                    //Replace if the new cost is better
                    if (previousCost > newCost) {
                        priorityQueue.add(newState);
                        visitedGridCosts.put(currentState.getGrid().toString(), currentState.getDepth());
                    }
                }
            }
        }

        return Optional.empty();
    }

}
