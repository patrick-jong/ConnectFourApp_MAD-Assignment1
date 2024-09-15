// Class: Statistics Fragment
// Description: Fragment which handles statistics for the current selected user

package com.example.mad_assignment1.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
// Import statements
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mad_assignment1.R;
import com.example.mad_assignment1.viewmodel.ProfileViewModel;

public class StatisticsFragment extends Fragment {

    private TextView totalGamesPlayed;
    private TextView wins;
    private TextView losses;
    private TextView winPercentage;

    private Button btnBack;
    private Button btnReset;

    private ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        // Initialize UI components
        totalGamesPlayed = view.findViewById(R.id.tv_total_games);
        wins = view.findViewById(R.id.tv_wins);
        losses = view.findViewById(R.id.tv_losses);
        winPercentage = view.findViewById(R.id.tv_win_percentage);
        btnBack = view.findViewById(R.id.btn_back);
        btnReset = view.findViewById(R.id.btn_reset);

        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        updateStatisticsDisplay();

        // Back button
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_statisticsFragment_to_mainMenuFragment);
        });

        // Reset button
        btnReset.setOnClickListener(v -> {
            showResetConfirmationDialog();
        });

        return view;
    }

    private void showResetConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Reset Statistics")
                .setMessage("Are you sure you want to reset your statistics? This action cannot be undone.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    profileViewModel.resetStatistics();
                    updateStatisticsDisplay();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void updateStatisticsDisplay() {
        // Retrieve stats from ProfileViewModel
        Integer gamesPlayed = profileViewModel.getTotalGamesPlayed();
        Integer winsCount = profileViewModel.getWins();
        Integer lossesCount = profileViewModel.getLosses();
        Double winPercentageDouble = profileViewModel.getWinPercentage();

        // Update UI elements
        totalGamesPlayed.setText("Total Games Played: " + (gamesPlayed != null ? gamesPlayed : 0));
        wins.setText("Wins: " + (winsCount != null ? winsCount : 0));
        losses.setText("Losses: " + (lossesCount != null ? lossesCount : 0));
        winPercentage.setText(String.format("Win Percentage: %.2f%%", winPercentageDouble));
    }
}
