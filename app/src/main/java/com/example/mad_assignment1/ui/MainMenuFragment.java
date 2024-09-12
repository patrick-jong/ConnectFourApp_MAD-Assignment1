package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.mad_assignment1.R;

public class MainMenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Loading main fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // Buttons
        Button btnTwoPlayer = view.findViewById(R.id.btn_two_player);
        Button btnAiMode = view.findViewById(R.id.btn_ai_mode);
        Button btnStatistics = view.findViewById(R.id.btn_statistics);
        Button btnProfile = view.findViewById(R.id.btn_profile);
        Button btnSettings = view.findViewById(R.id.btn_settings);

        // Navigation (used for easier fragment management)
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        // Buttons loading up the respective fragments
        btnTwoPlayer.setOnClickListener(v -> navController.navigate(R.id.action_mainMenuFragment_to_gameFragment)); //TODO: not 2?
        btnAiMode.setOnClickListener(v -> navController.navigate(R.id.action_mainMenuFragment_to_gameFragment)); // TODO: Set different argument for AI mode
        btnStatistics.setOnClickListener(v -> navController.navigate(R.id.action_mainMenuFragment_to_statisticsFragment));
        btnProfile.setOnClickListener(v -> navController.navigate(R.id.action_mainMenuFragment_to_profileFragment));
        btnSettings.setOnClickListener(v -> navController.navigate(R.id.action_mainMenuFragment_to_settingsFragment));

        return view;
    }
}
