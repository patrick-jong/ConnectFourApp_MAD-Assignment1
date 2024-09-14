package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.mad_assignment1.R;

import java.util.Arrays;

public class GameFragment extends Fragment {
    // Connect4 Grid
    private GridView gameGrid;
    private ArrayAdapter<Integer> gridAdapter;

    // Displays
    private TextView playerTurnIndicator;
    private Button btnBack;
    private Button btnReset;

    // Game board state
    private int rows = 6;
    private int columns = 7; // TODO - What if user changes from the settings menu?
    private String[][] board;
    private String currentPlayer;

    // Game active state
    private boolean gameActive;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        playerTurnIndicator = view.findViewById(R.id.player_turn_indicator);
        gameGrid = view.findViewById(R.id.game_grid);
        btnBack = view.findViewById(R.id.btn_back);
        btnReset = view.findViewById(R.id.btn_reset);

        startNewGame(); // Initialize game board (array) with rows and columns
        initialiseGrid(); // Fill up the grid with icons, and start gameplay

        // Back button
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_gameFragment_to_mainMenuFragment);
        });

        btnReset.setOnClickListener(v -> {
            startNewGame();
            updateUI();
            Toast.makeText(getContext(), "Game has been reset!", Toast.LENGTH_SHORT).show(); // Notify the user
        });

        return view;
    }

    // startNewGame() method - initializes board with respective sizes
    private void startNewGame() {
        // Initialize the game board and set the current player
        board = new String[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = "";  // Empty cell
            }
        }
        currentPlayer = "Player 1";  // Player 1 starts
        gameActive = true;  // Set game to active state
    }

    // initialiseGrid() method - initializes grid with images, and implements gameplay
    private void initialiseGrid() {
        // Initialize the grid with empty values represented by drawable resource IDs
        Integer[] initialGrid = new Integer[rows * columns];
        Arrays.fill(initialGrid, R.drawable.empty_disc); // Fill with empty_disc icon

        // Set up the adapter for the GridView
        gridAdapter = new ArrayAdapter<Integer>(requireContext(), android.R.layout.simple_list_item_1, initialGrid) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                ImageView imageView;
                if (convertView == null) {
                    imageView = new ImageView(getContext());
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));  // Adjust cell size as needed
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else {
                    imageView = (ImageView) convertView;
                }

                // Determine the disc at this position (row, column) and set the appropriate image
                int row = position / columns;
                int col = position % columns;
                String disc = board[row][col];

                if (disc.equals("Player 1")) {
                    imageView.setImageResource(R.drawable.red_disc);  // Set Player 1's disc
                } else if (disc.equals("Player 2")) {
                    imageView.setImageResource(R.drawable.yellow_disc);  // Set Player 2's disc
                } else {
                    imageView.setImageResource(R.drawable.empty_disc);  // Set empty spot
                }

                return imageView;
            }
        };

        gameGrid.setAdapter(gridAdapter);

        // Set a click listener to handle player moves
        gameGrid.setOnItemClickListener((parent, view, position, id) -> {
            if (!gameActive) { // Check if the game is still active
                Toast.makeText(getContext(), "Game over! Start a new game.", Toast.LENGTH_SHORT).show();
                return;
            }

            int column = position % columns; // Calculate column from grid position
            // Attempt to make a move in the selected column
            if (makeMove(column)) {
                updateUI(); // Update UI after a successful move
            } else {
                Toast.makeText(getContext(), "Invalid move. Try a different column.", Toast.LENGTH_SHORT).show();
            }
        });

        updateUI();
    }

    private boolean makeMove(int column) {
        // Validate move and place the disc in the lowest available row in the specified column
        if (column < 0 || column >= columns) {
            return false;  // Invalid column
        }

        for (int row = rows - 1; row >= 0; row--) {
            if (board[row][column].isEmpty()) {
                board[row][column] = currentPlayer;  // Place the current player's disc
                if (checkWin(row, column)) {
                    Toast.makeText(getContext(), currentPlayer + " wins!", Toast.LENGTH_LONG).show();
                    gameActive = false;  // Set game to inactive state
                    playerTurnIndicator.setText(currentPlayer + " wins!");
                } else {
                    switchPlayer();  // Switch to the next player
                }
                return true;
            }
        }

        return false;  // Column is full
    }

    private void switchPlayer() {
        if (currentPlayer.equals("Player 1")) {
            currentPlayer = "Player 2";
        } else {
            currentPlayer = "Player 1";
        }
    }

    private boolean checkWin(int row, int column) {
        // Check all directions: horizontal, vertical, and two diagonals
        return (checkDirection(row, column, 0, 1) ||  // Horizontal
                checkDirection(row, column, 1, 0) ||  // Vertical
                checkDirection(row, column, 1, 1) ||  // Diagonal /
                checkDirection(row, column, 1, -1));  // Diagonal \
    }

    private boolean checkDirection(int row, int column, int rowDelta, int colDelta) {
        int count = 1;  // Include the last placed disc

        // Check in the positive direction (e.g., right, down, diagonal down-right)
        count += countConsecutive(row, column, rowDelta, colDelta);
        // Check in the negative direction (e.g., left, up, diagonal up-left)
        count += countConsecutive(row, column, -rowDelta, -colDelta);

        return count >= 4;  // Winning condition: 4 or more in a row
    }

    private int countConsecutive(int startRow, int startCol, int rowDelta, int colDelta) {
        int count = 0;
        String playerDisc = currentPlayer;

        int row = startRow + rowDelta;
        int col = startCol + colDelta;

        // Continue counting as long as the discs match the current player
        while (row >= 0 && row < rows && col >= 0 && col < columns && board[row][col].equals(playerDisc)) {
            count++;
            row += rowDelta;
            col += colDelta;
        }

        return count;
    }

    private void updateUI() {
        if (gameActive) {
            playerTurnIndicator.setText(currentPlayer + "'s Turn"); // Update the player turn indicator
        }
        gridAdapter.notifyDataSetChanged();
    }

}
