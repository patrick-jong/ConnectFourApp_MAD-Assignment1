// Class: GameFragment.java
// Description: Fragment for handling player vs player. Results are NOT included in UserProfile statistics

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mad_assignment1.R;
import com.example.mad_assignment1.profile.UserProfile;
import com.example.mad_assignment1.viewmodel.GameViewModel;
import com.example.mad_assignment1.viewmodel.SettingsViewModel;

import java.util.Arrays;

public class GameFragment extends Fragment {
    private GridView gameGrid;
    private ArrayAdapter<Integer> gridAdapter;
    private TextView playerTurnIndicator;
    private Button btnBack;
    private Button btnReset;

    private int rows = 6;
    private int columns = 7;
    private String[][] board;
    private UserProfile player1;
    private UserProfile player2;
    private UserProfile currentPlayer;
    private TextView player1MovesView;
    private TextView player2MovesView;
    private TextView movesLeftView;

    private boolean gameActive;
    private GameViewModel gameViewModel;
    private SettingsViewModel settingsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        // Initialize UI components
        playerTurnIndicator = view.findViewById(R.id.player_turn_indicator);
        gameGrid = view.findViewById(R.id.game_grid);
        btnBack = view.findViewById(R.id.btn_back);
        btnReset = view.findViewById(R.id.btn_reset);
        player1MovesView = view.findViewById(R.id.player1_moves);
        player2MovesView = view.findViewById(R.id.player2_moves);
        movesLeftView = view.findViewById(R.id.moves_left);

        player1 = new UserProfile("Player 1");
        player2 = new UserProfile("Player 2");

        // ViewModel classes
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        // Observe ViewModel data to update UI
        observeViewModel();

        // Set up the game state or start a new game if no state exists
        if (gameViewModel.getBoard().getValue() != null) {
            board = gameViewModel.getBoard().getValue();
            currentPlayer = gameViewModel.getCurrentPlayer().getValue();
            gameActive = gameViewModel.getGameActive().getValue();
            playerTurnIndicator.setText(gameViewModel.getPlayerTurnIndicator().getValue());
            initialiseGrid(); // Set up the grid with existing state
        } else {
            startNewGame();
        }

        // Retrieve grid size from SettingsViewModel
        settingsViewModel.getNumRows().observe(getViewLifecycleOwner(), numRows -> {
            rows = numRows;
            settingsViewModel.getNumColumns().observe(getViewLifecycleOwner(), numColumns -> {
                columns = numColumns;
                initialiseGrid();
            });
        });

        // Back button listener
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_gameFragment_to_mainMenuFragment);
        });

        // Reset button listener
        btnReset.setOnClickListener(v -> {
            startNewGame();
            updateUI();
            Toast.makeText(getContext(), "Game has been reset!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void observeViewModel() {
        gameViewModel.getPlayer1Moves().observe(getViewLifecycleOwner(), moves -> {
            if (moves != null) {
                player1MovesView.setText(player1.getName() + " Moves: " + moves);
            }
        });

        gameViewModel.getPlayer2Moves().observe(getViewLifecycleOwner(), moves -> {
            if (moves != null) {
                player2MovesView.setText(player2.getName() + " Moves: " + moves);
            }
        });

        gameViewModel.getMovesLeft().observe(getViewLifecycleOwner(), moves -> {
            if (moves != null) {
                movesLeftView.setText("Moves Left: " + moves);
            }
        });

        gameViewModel.getCurrentPlayer().observe(getViewLifecycleOwner(), player -> {
            currentPlayer = player;
            if (gameActive) {
                playerTurnIndicator.setText(currentPlayer.getName() + "'s Turn");
            }
        });

        gameViewModel.getGameActive().observe(getViewLifecycleOwner(), isActive -> {
            gameActive = isActive;
        });
    }

    private void startNewGame() {
        gameViewModel.initializeMovesLeft(rows * columns);
        board = new String[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = "";
            }
        }
        gameViewModel.setBoard(board);

        currentPlayer = player1;
        gameViewModel.setCurrentPlayer(currentPlayer);

        gameActive = true;
        gameViewModel.setGameActive(gameActive);

        gameViewModel.setPlayerTurnIndicator(currentPlayer.getName() + "'s Turn");

        // Reset move counts
        gameViewModel.setPlayer1Moves(0);
        gameViewModel.setPlayer2Moves(0);

        // Reset moves left to the initial number of moves
        gameViewModel.setMovesLeft(rows * columns);

        player1MovesView.setText(player1.getName() + " Moves: 0");
        player2MovesView.setText(player2.getName() + " Moves: 0");
        movesLeftView.setText("Moves Left: " + (rows * columns));
    }

    private void initialiseGrid() {
        Integer[] initialGrid = new Integer[rows * columns];
        Arrays.fill(initialGrid, R.drawable.empty_disc);

        gridAdapter = new ArrayAdapter<Integer>(requireContext(), android.R.layout.simple_list_item_1, initialGrid) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                ImageView imageView;
                if (convertView == null) {
                    imageView = new ImageView(getContext());
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else {
                    imageView = (ImageView) convertView;
                }

                int row = position / columns;
                int col = position % columns;

                String disc = board[row][col];

                if (disc.equals(player1.getName())) {
                    imageView.setImageResource(R.drawable.red_disc);
                } else if (disc.equals(player2.getName())) {
                    imageView.setImageResource(R.drawable.yellow_disc);
                } else {
                    imageView.setImageResource(R.drawable.empty_disc);
                }

                return imageView;
            }
        };

        gameGrid.setNumColumns(columns);
        gameGrid.setAdapter(gridAdapter);

        gameGrid.setOnItemClickListener((parent, view, position, id) -> {
            if (!gameActive) {
                Toast.makeText(getContext(), "Game over! Start a new game.", Toast.LENGTH_SHORT).show();
                return;
            }

            int column = position % columns;
            if (makeMove(column)) {
                updateUI();
            } else {
                Toast.makeText(getContext(), "Invalid move. Try a different column.", Toast.LENGTH_SHORT).show();
            }
        });

        updateUI();
    }

    private boolean makeMove(int column) {
        if (column < 0 || column >= columns || !gameActive) {
            return false;
        }

        for (int row = rows - 1; row >= 0; row--) {
            if (board[row][column].isEmpty()) {
                board[row][column] = currentPlayer.getName();

                Integer player1MovesValue = gameViewModel.getPlayer1Moves().getValue();
                Integer player2MovesValue = gameViewModel.getPlayer2Moves().getValue();
                int newMoves;

                if (currentPlayer.equals(player1)) {
                    newMoves = (player1MovesValue != null ? player1MovesValue : 0) + 1;
                    gameViewModel.setPlayer1Moves(newMoves);
                } else {
                    newMoves = (player2MovesValue != null ? player2MovesValue : 0) + 1;
                    gameViewModel.setPlayer2Moves(newMoves);
                }

                Integer movesLeftValue = gameViewModel.getMovesLeft().getValue();
                int movesLeft = (movesLeftValue != null ? movesLeftValue : 42) - 1; // Example default value
                gameViewModel.setMovesLeft(movesLeft);

                if (checkWin(row, column)) {
                    Toast.makeText(getContext(), currentPlayer.getName() + " wins !!", Toast.LENGTH_LONG).show();
                    gameActive = false;
                    gameViewModel.setGameActive(false);
                    gameViewModel.setPlayerTurnIndicator(currentPlayer.getName() + " wins!");
                } else if (isBoardFull()) {
                    Toast.makeText(getContext(), "It's a draw!", Toast.LENGTH_LONG).show();
                    gameActive = false;
                    gameViewModel.setGameActive(false);
                    gameViewModel.setPlayerTurnIndicator("It's a draw!");
                } else {
                    switchPlayer();
                }

                gameViewModel.setBoard(board);
                return true;
            }
        }

        return false;
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
        gameViewModel.setCurrentPlayer(currentPlayer);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < columns; i++) {
            if (board[0][i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkWin(int row, int column) {
        return (checkDirection(row, column, 0, 1) ||
                checkDirection(row, column, 1, 0) ||
                checkDirection(row, column, 1, 1) ||
                checkDirection(row, column, 1, -1));
    }

    private boolean checkDirection(int row, int column, int rowDelta, int colDelta) {
        int count = 1;
        count += countConsecutive(row, column, rowDelta, colDelta);
        count += countConsecutive(row, column, -rowDelta, -colDelta);
        return count >= 4;
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

    private void updateUI() {
        if (gameActive) {
            playerTurnIndicator.setText(currentPlayer.getName() + "'s Turn");
        } else {
            playerTurnIndicator.setText(gameViewModel.getPlayerTurnIndicator().getValue());
        }
        gridAdapter.notifyDataSetChanged();
    }
}
