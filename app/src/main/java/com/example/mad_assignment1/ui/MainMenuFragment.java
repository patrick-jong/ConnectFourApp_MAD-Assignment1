package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// Import necessary classes
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
// Import ViewModelProvider for accessing the ProfileViewModel
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mad_assignment1.R;
import com.example.mad_assignment1.viewmodel.ProfileViewModel;

public class MainMenuFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextView usernameTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the main menu layout
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // Initialize the ProfileViewModel
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        // Initialize the username TextView
        usernameTextView = view.findViewById(R.id.username);

        // Observe the current user profile and update the TextView
        profileViewModel.getCurrentUserProfile().observe(getViewLifecycleOwner(), userProfile -> {
            if (userProfile != null) {
                usernameTextView.setText(userProfile.getName());
            }
        });

        // Buttons
        Button btnTwoPlayer = view.findViewById(R.id.btn_two_player);
        Button btnAiMode = view.findViewById(R.id.btn_ai_mode);
        Button btnStatistics = view.findViewById(R.id.btn_statistics);
        Button btnProfile = view.findViewById(R.id.btn_profile);
        Button btnSettings = view.findViewById(R.id.btn_settings);

        // Navigation controller for fragment management
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        // Set up button click listeners to navigate to respective fragments
        btnTwoPlayer.setOnClickListener(v -> navController.navigate(R.id.action_mainMenuFragment_to_gameFragment));
        btnAiMode.setOnClickListener(v -> navController.navigate(R.id.action_mainMenuFragment_to_gameAIFragment));
        btnStatistics.setOnClickListener(v -> navController.navigate(R.id.action_mainMenuFragment_to_statisticsFragment));
        btnProfile.setOnClickListener(v -> navController.navigate(R.id.action_mainMenuFragment_to_profileFragment));
        btnSettings.setOnClickListener(v -> navController.navigate(R.id.action_mainMenuFragment_to_settingsFragment));

        return view;
    }
}
