import java.util.*;


public class BFS extends SearchAlgorithm {

    public BFS(Grid start) {
        super(start);
    }

    @Override
    public Optional<PuzzleState> solve() throws Exception {
        Set<Integer> visitedOrFrontier = new HashSet<>();
        List<Grid> visitedGrids = new ArrayList<>();

        Queue<PuzzleState> queue = new LinkedList<>();

        queue.add(this.initialStart);

        while (!queue.isEmpty()) {
            PuzzleState currentState = queue.poll();
            history.store(currentState);

            visitedGrids.add(currentState.getGrid());
            // If this state is the goal state, return the grid
            if (this.isGoal(currentState.getGrid())) {
                System.out.println("Found solution!");
                currentState.getGrid().display();
                return Optional.of(currentState);
            }


            // Add unvisited nodes to frontier in possible directions
            for (Grid.Direction direction : currentState.getPossibleMovements() ) {
                var currentGrid = currentState.getGrid();
                Grid newGrid = Grid.move(currentGrid, direction);



                if (!visitedOrFrontier.contains(newGrid.hash)) {
                    PuzzleState newState = new PuzzleState(currentState, newGrid, direction);
                    queue.add(newState);
                    visitedOrFrontier.add(newGrid.hash);
                    visitedGrids.add(newGrid);
                }
                else {
                    var indice = new ArrayList<>(visitedOrFrontier).indexOf(newGrid.hash);

                    var original = visitedGrids.get(indice);

                    System.out.println("__________");
                    System.out.printf("Hash a: \t%d e b: \t%d\n", newGrid.hash, visitedGrids.get(indice).hash);
                    System.out.printf("Colisão! Comparando a grid \n%s com \n%s\n", original.toString(), newGrid);
                    System.out.println("__________");

                }

            }
        }

        return Optional.empty();  // Return null if no solution is found
    }


}
