package com.example.mad_assignment1.game;

import com.example.mad_assignment1.profile.UserProfile;

import java.util.Random;

public class GameAI extends ConnectFourGame {
    private Random random;

    // Constructor that initializes the game with user profiles, including an AI profile
    public GameAI(UserProfile player1, UserProfile aiPlayer) {
        super(player1, aiPlayer);  // Initialize the game with one human player and one AI player
        random = new Random();
    }

    // AI makes a random valid move
    public boolean makeAIMove() {
        if (!isGameActive()) {
            return false;  // Do nothing if the game is not active
        }

        int column;
        boolean moveMade = false;

        // Try random columns until a valid move is made
        while (!moveMade) {
            column = random.nextInt(getColumns()); // Choose a random column
            moveMade = super.makeMove(column); // Try to make a move in that column
        }

        return true;
    }
}
