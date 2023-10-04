package com.diogo.iia.models;


public enum DistanceHeuristics {
    MANHATTAN {
        @Override
        public double distance(int dx, int dy) {
            return Math.abs(dx) + Math.abs(dy);
        }
    },
    EUCLIDEAN {
        @Override
        public double distance(int dx, int dy) {
            return Math.sqrt(dx * dx + dy * dy);
        }
    },

    CORRECTNESS {
        @Override
        public double distance(int dx, int dy) {
            if (dx == 0 || dy == 0) {
                return 1.0;
            } else {
                return 0.0;
            }
        }
    };

    public abstract double distance(int dx, int dy);
}
