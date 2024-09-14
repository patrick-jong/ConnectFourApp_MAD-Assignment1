package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        totalGamesPlayed = view.findViewById(R.id.tv_total_games);
        wins = view.findViewById(R.id.tv_wins);
        losses = view.findViewById(R.id.tv_losses);
        winPercentage = view.findViewById(R.id.tv_win_percentage);
        btnBack = view.findViewById(R.id.btn_back);

        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        // Initialize statistics
        updateStatisticsDisplay();

        // Back button
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_statisticsFragment_to_mainMenuFragment);
        });

        return view;
    }

    private void updateStatisticsDisplay() {
        // Retrieve stats from ProfileViewModel
        Integer gamesPlayed = profileViewModel.getTotalGamesPlayed();
        Integer winsCount = profileViewModel.getWins();
        Integer lossesCount = profileViewModel.getLosses();
        Double winPercentageDouble = profileViewModel.getWinPercentage();

        // Calculate win percentage
        float winPercent = 0;
        if (gamesPlayed != null && gamesPlayed > 0) {
            winPercent = (float) (winsCount != null ? winsCount : 0) / gamesPlayed * 100;
        }

        // Update UI elements
        totalGamesPlayed.setText("Total Games Played: " + (gamesPlayed != null ? gamesPlayed : 0));
        wins.setText("Wins: " + (winsCount != null ? winsCount : 0));
        losses.setText("Losses: " + (lossesCount != null ? lossesCount : 0));
        winPercentage.setText(String.format("Win Percentage: %.2f%%", winPercentageDouble));
    }
}
