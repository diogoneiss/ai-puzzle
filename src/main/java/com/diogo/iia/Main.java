package com.diogo.iia;

import com.diogo.iia.application.Grid;
import com.diogo.iia.tracking.AlgorithmExecutor;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments provided!");
        }

        if (args.length == 1 && (args[0].equals("--all") || args[0].equals("-a"))) {
            AlgorithmExecutor.executeAndSave(false);
        } else if (args.length == 1 && (args[0].equals("--partial") || args[0].equals("-p"))) {
            AlgorithmExecutor.executeAndSave(true);
        } else if (args.length >= 10) {
            String algorithmType = args[0];

            int[] grid = new int[9];
            for (int i = 0; i < 9; i++) {
                try {
                    grid[i] = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Grid must be composed of integers!");
                }
            }

            boolean print = false;
            if (args.length == 11 && args[10].equals("PRINT")) {
                print = true;
            } else if (args.length > 11) {
                throw new IllegalArgumentException("Too many arguments provided!");
            }

            //Check if the desired algorithm is valid
            if (!AlgorithmFactory.algorithmTypes.contains(algorithmType)) {
                throw new IllegalArgumentException("Invalid algorithm type provided!");
            }

            //Check if the grid is valid is done inside the constructor
            Grid gridObject = new Grid(grid);
            var desiredAlgorithm = AlgorithmFactory.createAlgorithm(algorithmType, gridObject);
            var solution = desiredAlgorithm.solveAndDisplay(print);

        } else {
            throw new IllegalArgumentException("Insufficient arguments provided!");
        }
    }
}

