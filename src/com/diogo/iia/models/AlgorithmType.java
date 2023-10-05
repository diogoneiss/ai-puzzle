package com.diogo.iia.models;

public enum AlgorithmType {
    B, I, I1, U, A, A1, A2, G, G1, H, HR, HS1, HS2, HS3, R;

    public static AlgorithmType fromString(String str) {
        try {
            return AlgorithmType.valueOf(str);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown algorithm type: " + str);
        }
    }
}

