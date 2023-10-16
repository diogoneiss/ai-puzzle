package com.diogo.iia.Inputs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class PuzzleReader {
    public static String csvPath = "npuzzles.csv";

    /*
    public static List<PuzzleInput> readPuzzlesFromCSV() {
        return readPuzzlesFromCSV(csvPath);
    }

    public static List<PuzzleInput> readPuzzlesFromCSV(String filePath) {
        List<PuzzleInput> puzzles = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            // Skip the header
            for (int i = 1; i < lines.size(); i++) {
                String[] values = lines.get(i).split(",");
                int[] grid = Arrays.stream(values, 0, 9) // first 9 are grid
                        .mapToInt(Integer::parseInt)
                        .toArray();
                int solution = Integer.parseInt(values[9]); // 10th is solution
                puzzles.add(new PuzzleInput(grid, solution));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return puzzles;
    }*/
    public static List<PuzzleInput> readPuzzlesFromCSV() {
        List<PuzzleInput> puzzles = new ArrayList<>();

        try (InputStream is = PuzzleReader.class.getResourceAsStream("/npuzzles.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            // Skip csv header
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                int[] grid = Arrays.stream(values, 0, 9)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                int solution = Integer.parseInt(values[9]);
                puzzles.add(new PuzzleInput(grid, solution));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return puzzles;
    }
}
