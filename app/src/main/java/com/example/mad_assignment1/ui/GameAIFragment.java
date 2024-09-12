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
import com.example.mad_assignment1.livemodel.ConnectFourViewModel;
import com.example.mad_assignment1.R;
import com.example.mad_assignment1.game.GameAI;
import com.example.mad_assignment1.livemodel.UserProfileViewModel;

import java.util.Arrays;

public class GameAIFragment extends Fragment {
    private GridView gameGrid;
    private ArrayAdapter<Integer> gridAdapter;
    private TextView playerTurnIndicator;
    private Button btnBack;
    private Button btnReset;

    // ViewModel for managing game state
    private ConnectFourViewModel connectFourViewModel;
    private UserProfileViewModel userProfileViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ai_game, container, false);

        // Use ViewModelProvider with requireActivity() to scope the ViewModel to the activity
        connectFourViewModel = new ViewModelProvider(requireActivity()).get(ConnectFourViewModel.class);

        playerTurnIndicator = view.findViewById(R.id.player_turn_indicator);
        gameGrid = view.findViewById(R.id.game_grid);
        btnBack = view.findViewById(R.id.btn_back);
        btnReset = view.findViewById(R.id.btn_reset);

        playerTurnIndicator.setText(userProfileViewModel.getUsername());
        initialiseGrid();

        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_gameAIFragment_to_mainMenuFragment);
        });

        btnReset.setOnClickListener(v -> {
            connectFourViewModel.getConnectFourGame().startNewGame();  // Reset the game logic
            updateUI();
            Toast.makeText(getContext(), "Game has been reset!", Toast.LENGTH_SHORT).show(); // Notify the user
        });

        return view;
    }

    private void initialiseGrid() {
        Integer[] initialGrid = new Integer[connectFourViewModel.getConnectFourGame().getRows() * connectFourViewModel.getConnectFourGame().getColumns()];
        Arrays.fill(initialGrid, R.drawable.empty_disc); // Fill with empty_disc icon

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

                int row = position / connectFourViewModel.getConnectFourGame().getColumns();
                int col = position % connectFourViewModel.getConnectFourGame().getColumns();
                String disc = connectFourViewModel.getConnectFourGame().getBoard()[row][col];

                if (disc.equals("userProfileViewModel.getUsername()")) {
                    imageView.setImageResource(R.drawable.red_disc);  // Set Player 1's disc
                } else if (disc.equals("AI")) {
                    imageView.setImageResource(R.drawable.yellow_disc);  // Set AI's disc
                } else {
                    imageView.setImageResource(R.drawable.empty_disc);  // Set empty spot
                }

                return imageView;
            }
        };

        gameGrid.setAdapter(gridAdapter);

        gameGrid.setOnItemClickListener((parent, view, position, id) -> {
            if (!connectFourViewModel.getConnectFourGame().isGameActive()) { // Check if the game is still active
                Toast.makeText(getContext(), "Game over! Start a new game.", Toast.LENGTH_SHORT).show();
                return;
            }

            int column = position % connectFourViewModel.getConnectFourGame().getColumns(); // Calculate column from grid position
            if (connectFourViewModel.getConnectFourGame().makeMove(column)) {
                updateUI(); // Update UI after Player 1's move

                // Check for win or draw after Player 1's move
                if (connectFourViewModel.getConnectFourGame().isDraw()) {
                    playerTurnIndicator.setTypeface(null, android.graphics.Typeface.BOLD);
                    playerTurnIndicator.setTextColor(getResources().getColor(R.color.pastel_green_dark));
                    playerTurnIndicator.setText("It's a draw :(");
                } else if (!connectFourViewModel.getConnectFourGame().isGameActive()) {
                    playerTurnIndicator.setTypeface(null, android.graphics.Typeface.BOLD);
                    playerTurnIndicator.setTextColor(getResources().getColor(R.color.pastel_green_dark));
                    playerTurnIndicator.setText("Player 1 wins !!");
                } else {
                    // Make AI move after Player 1's move
                    ((GameAI) connectFourViewModel.getConnectFourGame()).makeAIMove();
                    updateUI();

                    // Check for win or draw after AI's move
                    if (connectFourViewModel.getConnectFourGame().isDraw()) {
                        playerTurnIndicator.setTypeface(null, android.graphics.Typeface.BOLD);
                        playerTurnIndicator.setTextColor(getResources().getColor(R.color.pastel_green_dark));
                        playerTurnIndicator.setText("It's a draw :(");
                    } else if (!connectFourViewModel.getConnectFourGame().isGameActive()) {
                        playerTurnIndicator.setTypeface(null, android.graphics.Typeface.BOLD);
                        playerTurnIndicator.setTextColor(getResources().getColor(R.color.pastel_green_dark));
                        playerTurnIndicator.setText("AI wins !!");
                    }
                }
            } else {
                Toast.makeText(getContext(), "Invalid move. Try a different column.", Toast.LENGTH_SHORT).show();
            }
        });

        updateUI();
    }

    private void updateUI() {
        if (connectFourViewModel.getConnectFourGame().isGameActive()) {
            playerTurnIndicator.setText(connectFourViewModel.getConnectFourGame().getCurrentPlayer() + "'s Turn"); // Update the player turn indicator
        }
        gridAdapter.notifyDataSetChanged();
    }
}
