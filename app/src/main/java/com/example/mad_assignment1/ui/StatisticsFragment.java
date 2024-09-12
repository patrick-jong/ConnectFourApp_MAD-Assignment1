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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mad_assignment1.R;
import com.example.mad_assignment1.statistics.GameStatistics;

public class StatisticsFragment extends Fragment {

    private TextView statisticsTextView;
    private GameStatistics gameStatistics;
    private Button btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        statisticsTextView = view.findViewById(R.id.tv_wins); //TODO: Changeeeeee
        btnBack = view.findViewById(R.id.btn_back);

        // Initialize statistics
        gameStatistics = new GameStatistics();
        updateStatisticsDisplay();

        // Back button
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_gameFragment_to_mainMenuFragment);
        });

        return view;
    }

    private void updateStatisticsDisplay() {
        statisticsTextView.setText(gameStatistics.getStatisticsSummary());
    }

}

