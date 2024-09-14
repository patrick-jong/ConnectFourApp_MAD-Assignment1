package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.mad_assignment1.viewmodel.GameAIViewModel;

import java.util.Arrays;
import java.util.Random;

public class GameAIFragment extends Fragment {
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
    private GameAIViewModel gameAIViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ai_game, container, false);

        playerTurnIndicator = view.findViewById(R.id.player_turn_indicator);
        gameGrid = view.findViewById(R.id.game_grid);
        btnBack = view.findViewById(R.id.btn_back);
        btnReset = view.findViewById(R.id.btn_reset);
        player1MovesView = view.findViewById(R.id.player1_moves);
        player2MovesView = view.findViewById(R.id.player2_moves);
        movesLeftView = view.findViewById(R.id.moves_left);

        player1 = new UserProfile("Guest");
        player2 = new UserProfile("AI");

        gameAIViewModel = new ViewModelProvider(requireActivity()).get(GameAIViewModel.class);

        gameAIViewModel.getPlayer1Moves().observe(getViewLifecycleOwner(), moves -> {
            if (moves != null) {
                player1MovesView.setText(player1.getName() + " Moves: " + moves);
            }
        });

        gameAIViewModel.getPlayer2Moves().observe(getViewLifecycleOwner(), moves -> {
            if (moves != null) {
                player2MovesView.setText(player2.getName() + " Moves: " + moves);
            }
        });

        gameAIViewModel.getMovesLeft().observe(getViewLifecycleOwner(), moves -> {
            if (moves != null) {
                movesLeftView.setText("Moves Left: " + moves);
            }
        });

        if (gameAIViewModel.getBoard().getValue() != null) {
            board = gameAIViewModel.getBoard().getValue();
            currentPlayer = gameAIViewModel.getCurrentPlayer().getValue();
            gameActive = gameAIViewModel.getGameActive().getValue();
            playerTurnIndicator.setText(gameAIViewModel.getPlayerTurnIndicator().getValue());
        } else {
            startNewGame();
        }

        initialiseGrid();

        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_gameAIFragment_to_mainMenuFragment);
        });

        btnReset.setOnClickListener(v -> {
            startNewGame();
            updateUI();
            Toast.makeText(getContext(), "Game has been reset!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void startNewGame() {
        board = new String[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = "";
            }
        }
        gameAIViewModel.setBoard(board);

        currentPlayer = player1;
        gameAIViewModel.setCurrentPlayer(currentPlayer);

        gameActive = true;
        gameAIViewModel.setGameActive(gameActive);

        gameAIViewModel.setPlayerTurnIndicator(currentPlayer.getName() + "'s Turn");

        // Reset move counts
        gameAIViewModel.setPlayer1Moves(0);
        gameAIViewModel.setPlayer2Moves(0);

        // Reset moves left to the initial number of moves
        gameAIViewModel.setMovesLeft(rows * columns); // Assuming movesLeft is the total number of moves available

        // Optionally, you may want to update the UI elements showing move counts and moves left
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

        gameGrid.setAdapter(gridAdapter);

        gameGrid.setOnItemClickListener((parent, view, position, id) -> {
            if (!gameActive) {
                Toast.makeText(getContext(), "Game over! Start a new game.", Toast.LENGTH_SHORT).show();
                return;
            }

            int column = position % columns;
            if (makeMove(column)) {
                updateUI();
                if (gameActive && currentPlayer.equals(player1)) {
                    playerTurnIndicator.setText("AI's Turn");
                    new Handler().postDelayed(this::makeAIMove, 1000); // 1 second delay for AI move
                }
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

                Integer player1MovesValue = gameAIViewModel.getPlayer1Moves().getValue();
                Integer player2MovesValue = gameAIViewModel.getPlayer2Moves().getValue();
                int newMoves;

                if (currentPlayer.equals(player1)) {
                    newMoves = (player1MovesValue != null ? player1MovesValue : 0) + 1;
                    gameAIViewModel.setPlayer1Moves(newMoves);
                } else {
                    newMoves = (player2MovesValue != null ? player2MovesValue : 0) + 1;
                    gameAIViewModel.setPlayer2Moves(newMoves);
                }

                Integer movesLeftValue = gameAIViewModel.getMovesLeft().getValue();
                int movesLeft = (movesLeftValue != null ? movesLeftValue : 42) - 1; // Example default value
                gameAIViewModel.setMovesLeft(movesLeft);

                if (checkWin(row, column)) {
                    Toast.makeText(getContext(), currentPlayer.getName() + " wins !!", Toast.LENGTH_LONG).show();
                    gameActive = false;
                    gameAIViewModel.setGameActive(false);
                    gameAIViewModel.setPlayerTurnIndicator(currentPlayer.getName() + " wins!");
                } else if (isBoardFull()) {
                    Toast.makeText(getContext(), "It's a draw!", Toast.LENGTH_LONG).show();
                    gameActive = false;
                    gameAIViewModel.setGameActive(false);
                    gameAIViewModel.setPlayerTurnIndicator("It's a draw!");
                } else {
                    switchPlayer();
                    if (gameActive && currentPlayer.equals(player2)) {
                        playerTurnIndicator.setText("AI's Turn");
                        new Handler().postDelayed(this::makeAIMove, 1000); // 1 second delay for AI move
                    }
                }

                gameAIViewModel.setBoard(board);
                return true;
            }
        }

        return false;
    }

    private void makeAIMove() {
        if (!gameActive) return;

        Random random = new Random();
        int column;
        do {
            column = random.nextInt(columns);
        } while (!canPlayInColumn(column));

        if (makeMove(column)) {
            updateUI();
            if (gameActive) {
                playerTurnIndicator.setText(player1.getName() + "'s Turn");
            }
        }
    }

    private boolean canPlayInColumn(int column) {
        return !board[0][column].equals(player1.getName()) && !board[0][column].equals(player2.getName());
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
        gameAIViewModel.setCurrentPlayer(currentPlayer);
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
            playerTurnIndicator.setText(gameAIViewModel.getPlayerTurnIndicator().getValue());
        }
        gridAdapter.notifyDataSetChanged();
    }
}
