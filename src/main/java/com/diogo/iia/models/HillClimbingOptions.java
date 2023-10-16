package com.diogo.iia.models;

public enum HillClimbingOptions {
    REGULAR {
        @Override
        public String getDescription() {
            return "Regular Hill Climbing";
        }
    },
    STOCHASTIC {
        @Override
        public String getDescription() {
            return "Stochastic Hill Climbing";
        }
    },
    SIMULATED_ANNEALING {
        @Override
        public String getDescription() {
            return "Simulated Annealing";
        }
    };

    private static final int DEFAULT_K = 50;
    private static final double DEFAULT_TEMPERATURE = 50.0;

    private final int lateralMoveLimit;
    private final double startTemperature;

    HillClimbingOptions(int lateralMoveLimit, double startTemperature) {
        this.lateralMoveLimit = lateralMoveLimit;
        this.startTemperature = startTemperature;
    }

    HillClimbingOptions() {
        this(DEFAULT_K, DEFAULT_TEMPERATURE);
    }

    public int getLateralMoveLimit() {
        return lateralMoveLimit;
    }

    public double getStartTemperature() {
        return startTemperature;
    }

    public abstract String getDescription();
}
