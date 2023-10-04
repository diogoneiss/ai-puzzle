package com.diogo.iia.Inputs;

public record PuzzleInput(int[] grid, int solution) {
    public PuzzleInput(int[] grid, int solution) {
        this.grid = grid;
        this.solution = solution;

        try {
            assertValidPuzzle(grid);
        } catch (Exception e) {
            StringBuilder message = new StringBuilder("Error at puzzle with solution ").append(solution).append(": ");
            message.append(e.getMessage());
            throw new IllegalArgumentException(message.toString());
        }
    }

    private static void assertValidPuzzle(int[] grid) throws Exception {
        // Check if the grid has exactly 9 elements
        if (grid.length != 9) {
            throw new Exception("Inserted grid does not have 9 elements, there are just " + grid.length + ".");
        }

        // Check if the grid contains all numbers from 0 to 8
        boolean[] numbersPresent = new boolean[9];
        for (int value : grid) {
            if (value < 0 || value >= 9) {
                throw new Exception("Invalid 8-puzzle input, number " + value + " is out of bounds.");
            } else if (numbersPresent[value]) {
                throw new Exception("Invalid 8-puzzle input, number " + value + " is repeated.");
            }
            numbersPresent[value] = true;
        }
    }
}

