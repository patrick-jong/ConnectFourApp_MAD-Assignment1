package com.example.mad_assignment1.profile;

import com.example.mad_assignment1.R;
import java.util.List;

public class UserProfile {
    private String name;
    private int gamesPlayed;
    private int wins;
    private int losses;

    // Constructor
    public UserProfile(String name) {
        this.name = name;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public int getTotalGamesPlayed() {
        return gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void incrementWins() {
        this.wins++;
    }

    public int getLosses() {
        return losses;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public double getWinPercentage() {
        if (gamesPlayed == 0) return 0;
        return (wins / (double) gamesPlayed) * 100;
    }

}
