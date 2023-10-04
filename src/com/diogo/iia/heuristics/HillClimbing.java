package com.diogo.iia.heuristics;

import com.diogo.iia.application.*;
import com.diogo.iia.models.Direction;
import com.diogo.iia.models.DistanceHeuristics;
import com.diogo.iia.models.HillClimbingOptions;

import java.util.*;

public class HillClimbing extends SearchAlgorithm {

    private static final double COOLING_RATE = 0.995;
    private DistanceHeuristics chosenHeuristic;
    private HillClimbingOptions options;
    private double temperature;
    private int k;


    public HillClimbing(Grid start, HillClimbingOptions options, DistanceHeuristics chosenHeuristic) {
        super(start);
        this.k = options.getLateralMoveLimit();
        this.temperature = options.getStartTemperature();
        this.chosenHeuristic = chosenHeuristic;
        this.options = options;
    }

    public HillClimbing(Grid start) {
        this(start, HillClimbingOptions.REGULAR, DistanceHeuristics.MANHATTAN);
    }

    @Override
    public Optional<PuzzleState> solve() throws Exception {

        PuzzleState currentState = this.initialStart;
        history.store(currentState);
        int lateralMoves = 0;

        while (lateralMoves < k && !isGoal(currentState)) {

            PuzzleState nextNeighbor = pickNextNeighbor(currentState);

            if (nextNeighbor == null) {
                return Optional.empty();
            }

            var currentCost = calculateValue(currentState);
            var nextCost = calculateValue(nextNeighbor);

            if (nextCost == currentCost) {
                lateralMoves++;
            }

            currentState = nextNeighbor;
            history.store(currentState);
        }
        // If the current state is not goal state it was not found
        return isGoal(currentState) ? Optional.of(currentState) : Optional.empty();
    }

    private int calculateValue(PuzzleState state) {
        return state.calculateDistanceHeuristic(chosenHeuristic);
    }

    /*
        private PuzzleState pickNextNeighbor(PuzzleState currentState) throws Exception {

            var neighbors = currentState.getGrid().getNeighbors();
            var directions = currentState.getPossibleMovements();

            if (isStochastic) {
                return pickStochasticNeighbor(currentState, neighbors, directions);
            } else {
                return pickRegularNeighbor(currentState, neighbors, directions);
            }
        }*/
    public PuzzleState pickNextNeighbor(PuzzleState currentState) throws Exception {
        var neighbors = currentState.getGrid().getNeighbors();
        var directions = currentState.getPossibleMovements();

        return switch (this.options) {
            case REGULAR -> pickRegularNeighbor(currentState, neighbors, directions);
            case STOCHASTIC -> pickNeighborRandomly(currentState, neighbors, directions);
            case SIMULATED_ANNEALING -> pickStochasticNeighbor(currentState, neighbors, directions);
        };
    }

    private PuzzleState pickRegularNeighbor(PuzzleState currentState, List<Grid> neighbors, List<Direction> directions) {

        PuzzleState bestNeighbor = null;
        int bestDistance = calculateValue(currentState);

        for (int i = 0; i < neighbors.size(); i++) {
            var newState = new PuzzleState(currentState, neighbors.get(i), directions.get(i));

            if (calculateValue(newState) <= bestDistance) {
                bestNeighbor = newState;
                bestDistance = calculateValue(newState);
            }
        }
        return bestNeighbor;
    }

    private PuzzleState pickNeighborRandomly(PuzzleState currentState, List<Grid> neighbors, List<Direction> directions) {

        List<PuzzleState> candidateNeighbors = new ArrayList<>();

        // Collect neighbors that are not worse than the current state
        for (int i = 0; i < neighbors.size(); i++) {
            var newState = new PuzzleState(currentState, neighbors.get(i), directions.get(i));

            if (calculateValue(newState) <= calculateValue(currentState)) {
                candidateNeighbors.add(newState);
            }
        }

        // Randomly choose among candidate neighbors
        if (!candidateNeighbors.isEmpty()) {
            int randomIndex = new Random().nextInt(candidateNeighbors.size());
            return candidateNeighbors.get(randomIndex);
        } else {
            return null;
        }
    }

    private PuzzleState pickStochasticNeighbor(PuzzleState currentState, List<Grid> neighbors, List<Direction> directions) {

        double coolingRate = HillClimbing.COOLING_RATE;

        PuzzleState selectedNeighbor = null;
        double currentVal = calculateValue(currentState);

        for (int i = 0; i < neighbors.size(); i++) {
            var newState = new PuzzleState(currentState, neighbors.get(i), directions.get(i));
            double neighborVal = calculateValue(newState);

            if (shouldAccept(currentVal, neighborVal, temperature)) {
                selectedNeighbor = newState;
                break;
            }
        }

        temperature *= 1 - coolingRate;

        return selectedNeighbor;
    }

    private boolean shouldAccept(double currentValue, double neighborValue, double temperature) {
        if (neighborValue <= currentValue) {
            return true;
        }

        double acceptanceProbability = Math.exp((currentValue - neighborValue) / temperature);
        return new Random().nextDouble() < acceptanceProbability;
    }


}
