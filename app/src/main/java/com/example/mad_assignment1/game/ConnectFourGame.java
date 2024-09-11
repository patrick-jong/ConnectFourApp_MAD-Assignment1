package com.example.mad_assignment1.game;

public class ConnectFourGame {

    private int rows;
    private int columns;
    private String currentPlayer;

    public ConnectFourGame(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public void startNewGame() {
        // Initialize game state
        currentPlayer = "Player 1";
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void makeMove(int column) {
        // Implement move logic
    }
}
