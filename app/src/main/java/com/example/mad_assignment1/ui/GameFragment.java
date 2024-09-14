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
import com.example.mad_assignment1.livemodel.UserProfileViewModel;
import com.example.mad_assignment1.profile.UserProfile;
import com.example.mad_assignment1.R;

import java.util.Arrays;

public class GameFragment extends Fragment {
    private GridView gameGrid;
    private ArrayAdapter<Integer> gridAdapter;
    private TextView playerTurnIndicator;
    private Button btnBack;
    private Button btnReset;

    // Use ViewModel to hold game state
    private ConnectFourViewModel connectFourViewModel;
    private UserProfileViewModel userProfileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        // Initialize UserProfileViewModel to get user profiles
        userProfileViewModel = new ViewModelProvider(requireActivity()).get(UserProfileViewModel.class);

        // Retrieve user profiles (assuming you have methods to get these from the UserProfileViewModel)
        UserProfile player1 = new UserProfile("Player 1"); // Retrieve player 1 profile
        UserProfile player2 = new UserProfile("Player 2"); // Retrieve player 2 profile

        // Initialize ConnectFourViewModel with the user profiles using a ViewModelFactory
        connectFourViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(ConnectFourViewModel.class)) {
                    return (T) new ConnectFourViewModel(player1, player2);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(ConnectFourViewModel.class);

        // XML stuff
        playerTurnIndicator = view.findViewById(R.id.player_turn_indicator);
        gameGrid = view.findViewById(R.id.game_grid);
        btnBack = view.findViewById(R.id.btn_back);
        btnReset = view.findViewById(R.id.btn_reset);

        initialiseGrid();

        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_gameFragment_to_mainMenuFragment);
        });

        btnReset.setOnClickListener(v -> {
            connectFourViewModel.getConnectFourGame().startNewGame();  // Reset the game logic
            connectFourViewModel.setStatusMessage(connectFourViewModel.getConnectFourGame().getCurrentPlayer().getName() + "'s Turn"); // Reset status message
            updateUI();
            Toast.makeText(getContext(), "Game has been reset!", Toast.LENGTH_SHORT).show(); // Notify the user
        });

        // Observe status message from ViewModel to update UI
        connectFourViewModel.getStatusMessage().observe(getViewLifecycleOwner(), message -> {
            playerTurnIndicator.setText(message);
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

                // Retrieve player names from UserProfile instances
                UserProfile player1 = connectFourViewModel.getConnectFourGame().getPlayer1();
                UserProfile player2 = connectFourViewModel.getConnectFourGame().getPlayer2();

                if (disc.equals(player1.getName())) {
                    imageView.setImageResource(R.drawable.red_disc);  // Set Player 1's disc
                } else if (disc.equals(player2.getName())) {
                    imageView.setImageResource(R.drawable.yellow_disc);  // Set Player 2's disc
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
                connectFourViewModel.updateStatusAfterMove(); // Update status in the ViewModel
                updateUI(); // Update UI after a successful move
            } else {
                Toast.makeText(getContext(), "Invalid move. Try a different column.", Toast.LENGTH_SHORT).show();
            }
        });

        updateUI();
    }

    private void updateUI() {
        gridAdapter.notifyDataSetChanged(); // Notify adapter to refresh the grid view with updated board state
    }
}
