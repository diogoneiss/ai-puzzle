package com.diogo.iia.models;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public Direction getOpposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
