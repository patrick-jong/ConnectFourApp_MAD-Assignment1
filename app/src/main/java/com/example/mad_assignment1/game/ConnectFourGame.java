package com.example.mad_assignment1.game;

import com.example.mad_assignment1.profile.UserProfile;

public class ConnectFourGame {
    private int rows = 6;
    private int columns = 7;
    private String[][] board;
    private UserProfile player1;
    private UserProfile player2;
    private UserProfile currentPlayer;
    private boolean gameActive;

    // Constructor to initialize the game with user profiles
    public ConnectFourGame(UserProfile player1, UserProfile player2) {
        this.player1 = player1;
        this.player2 = player2;
        startNewGame();
    }

    public void startNewGame() {
        board = new String[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = "";  // Empty cell
            }
        }
        currentPlayer = player1;  // Player 1 starts
        gameActive = true;  // Set game to active state
    }

    public boolean makeMove(int column) {
        if (column < 0 || column >= columns) {
            return false;  // Invalid column
        }

        for (int row = rows - 1; row >= 0; row--) {
            if (board[row][column].isEmpty()) {
                board[row][column] = currentPlayer.getName();  // Place the current player's disc
                if (checkWin(row, column)) {
                    gameActive = false;  // Set game to inactive state
                } else {
                    switchPlayer();  // Switch to the next player
                }
                return true;
            }
        }

        return false;  // Column is full
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;  // Switch between players
    }

    private boolean checkWin(int row, int column) {
        return (checkDirection(row, column, 0, 1) ||  // Horizontal
                checkDirection(row, column, 1, 0) ||  // Vertical
                checkDirection(row, column, 1, 1) ||  // Diagonal /
                checkDirection(row, column, 1, -1));  // Diagonal \
    }

    private boolean checkDirection(int row, int column, int rowDelta, int colDelta) {
        int count = 1;  // Include the last placed disc

        // Check in the positive direction
        count += countConsecutive(row, column, rowDelta, colDelta);
        // Check in the negative direction
        count += countConsecutive(row, column, -rowDelta, -colDelta);

        return count >= 4;  // Winning condition: 4 or more in a row
    }

    private int countConsecutive(int startRow, int startCol, int rowDelta, int colDelta) {
        int count = 0;
        String playerDisc = currentPlayer.getName();

        int row = startRow + rowDelta;
        int col = startCol + colDelta;

        while (row >= 0 && row < rows && col >= 0 && col < columns && board[row][col].equals(playerDisc)) {
            count++;
            row += rowDelta;
            col += colDelta;
        }

        return count;
    }

    // Method to check if the game is a draw
    public boolean isDraw() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j].isEmpty()) {
                    return false;  // Found an empty cell, so it's not a draw
                }
            }
        }
        return true;  // No empty cells found, it's a draw
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public String[][] getBoard() {
        return board;
    }

    public UserProfile getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameActive() {
        return gameActive;
    }

    public UserProfile getPlayer1() { return player1; }

    public UserProfile getPlayer2() { return player2; }

}
