package com.diogo.iia.models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record AlgorithmStatistics(
        String algorithmName,
        String gridHash,
        long nodesVisited,
        int movementsTaken,
        int optimalMovementsTaken,
        boolean solutionFound,
        double solutionGap,
        long timeElapsed

) {
    public static String csvHeader() {
        return Stream.of(AlgorithmStatistics.class.getRecordComponents())
                .map(component -> component.getName())
                .collect(Collectors.joining(","));
    }

    public String toCsv() {
        return Stream.of(AlgorithmStatistics.class.getRecordComponents())
                .map(component -> {
                    try {
                        // Using reflection I'll retrieve each property class, in order to call toString
                        Method accessor = getClass().getMethod(component.getName());
                        Object value = accessor.invoke(this);

                        // Quoting strings to handle any special characters or commas
                        /*
                        if (value instanceof String) {
                            value = "\"" + value + "\"";
                        }*/
                        return String.valueOf(value);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException("Error accessing component value", e);
                    }
                })
                .collect(Collectors.joining(","));
    }

}
