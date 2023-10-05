package com.diogo.iia.heuristics;

import com.diogo.iia.application.Grid;
import com.diogo.iia.application.PuzzleState;
import com.diogo.iia.application.SearchAlgorithm;
import com.diogo.iia.models.Direction;

import java.util.*;


public class BFS extends SearchAlgorithm {

    public BFS(Grid start) {
        super(start);
    }

    @Override
    public Optional<PuzzleState> solve() throws Exception {
        Set<String> visitedOrFrontier = new HashSet<>();

        Queue<PuzzleState> queue = new LinkedList<>();

        queue.add(this.initialStart);

        while (!queue.isEmpty()) {
            PuzzleState currentState = queue.poll();
            history.store(currentState);

            // If this state is the goal state, return the grid
            // uses the distance to  solution as a heuristic
            if (this.isGoal(currentState)) {
                return Optional.of(currentState);
            }

            // Add unvisited nodes to frontier in possible directions
            for (Direction direction : currentState.getPossibleMovements()) {
                var currentGrid = currentState.getGrid();
                Grid newGrid = Grid.move(currentGrid, direction);
                String gridHash = newGrid.toString();
                if (!visitedOrFrontier.contains(gridHash)) {
                    PuzzleState newState = new PuzzleState(currentState, newGrid, direction);
                    if (this.isGoal(newState)) {
                        history.store(newState);
                        return Optional.of(newState);
                    }
                    queue.add(newState);
                    visitedOrFrontier.add(gridHash);
                } /*else {
                    var indiceHash = new ArrayList<>(visitedOrFrontier).indexOf(newGrid.hash);
                    var indiceGrid = new ArrayList<>(visitedGrids).indexOf(newGrid);

                    assert indiceHash == indiceGrid;

                    var originalStr = new ArrayList<>(visitedGrids).get(indiceGrid).toString();
                    var currentStr = newGrid.toString();

                    assert originalStr.equals(currentStr);

                }*/
                /*
                else {
                    var indice = new ArrayList<>(visitedOrFrontier).indexOf(newGrid.hash);

                    var original = new ArrayList<>(visitedGrids).get(indice);

                    System.out.println("__________");
                    System.out.printf("Hash a: \t%d e b: \t%d\n", newGrid.hash, original.hash);
                    System.out.printf("Colisão! Comparando a grid \n%s com \n%s\n", original, newGrid);
                    System.out.println("__________");

                }*/

            }
        }

        return Optional.empty();
    }


}
