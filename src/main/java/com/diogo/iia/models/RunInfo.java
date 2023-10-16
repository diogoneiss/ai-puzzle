package com.diogo.iia.models;

import com.diogo.iia.application.PuzzleHistory;

public record RunInfo(long nodesVisited, int movements, PuzzleHistory history, long timeElapsed) {
}
