package com.diogo.iia.Inputs;

import com.diogo.iia.models.AlgorithmStatistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CsvHandler {

    public static String CSV_FILE_NAME = "results.csv";

    public static void readCsv(List<AlgorithmStatistics> statistics) throws FileNotFoundException {


    }


    public static void writeCsv(List<AlgorithmStatistics> statistics) throws FileNotFoundException {

        //TODO Before writing, load the existing CSV file

        //TODO Check colisions and remove the previous entry from the list
        // Maybe write a get missing entries method


        File csvOutputFile = new File(CSV_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(AlgorithmStatistics.csvHeader());

            statistics.stream()
                    .map(s -> s.toCsv())
                    .forEach(pw::println);
        }

    }
}
