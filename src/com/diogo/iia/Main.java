package com.diogo.iia;// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import com.diogo.iia.application.Grid;
import com.diogo.iia.heuristics.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        //int[] sampleInput = { 1, 2, 3, 4, 0, 5, 7, 8, 6};
        int[] sampleInput = {1, 5, 2, 0, 4, 3, 7, 8, 6};

        Grid base = new Grid(sampleInput);

        var searchHeuristic = new HillClimbing(base);

        var solution = searchHeuristic.solve();
        System.out.println(solution);
        searchHeuristic.showRunInfo();

    }
}