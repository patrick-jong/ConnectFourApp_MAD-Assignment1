package com.example.mad_assignment1.statistics;

public class GameStatistics {

    private int totalGamesPlayed;
    private int wins;
    private int losses;

    public GameStatistics() {
        totalGamesPlayed = 0;
        wins = 0;
        losses = 0;
    }

    public void incrementGamesPlayed() {
        totalGamesPlayed++;
    }

    public void incrementWins() {
        wins++;
    }

    public void incrementLosses() {
        losses++;
    }

    public String getStatisticsSummary() {
        return "Games Played: " + totalGamesPlayed + "\nWins: " + wins + "\nLosses: " + losses;
    }
}
