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

    private boolean gameActive;

    private GameViewModel gameViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        playerTurnIndicator = view.findViewById(R.id.player_turn_indicator);
        gameGrid = view.findViewById(R.id.game_grid);
        btnBack = view.findViewById(R.id.btn_back);
        btnReset = view.findViewById(R.id.btn_reset);

        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        player1 = new UserProfile("Player 1");
        player2 = new UserProfile("Player 2");

        if (gameViewModel.getBoard().getValue() != null) {
            board = gameViewModel.getBoard().getValue();
            currentPlayer = gameViewModel.getCurrentPlayer().getValue();
            gameActive = gameViewModel.getGameActive().getValue();
            playerTurnIndicator.setText(gameViewModel.getPlayerTurnIndicator().getValue());
        } else {
            startNewGame();
        }

        initialiseGrid();

        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_gameFragment_to_mainMenuFragment);
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
        gameViewModel.setBoard(board);
        currentPlayer = player1;
        gameViewModel.setCurrentPlayer(currentPlayer);
        gameActive = true;
        gameViewModel.setGameActive(gameActive);
        gameViewModel.setPlayerTurnIndicator(currentPlayer.getName() + "'s Turn");
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

    private boolean isBoardFull() {
        for (int i = 0; i < columns; i++) {
            if (board[0][i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
        gameViewModel.setCurrentPlayer(currentPlayer);
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
