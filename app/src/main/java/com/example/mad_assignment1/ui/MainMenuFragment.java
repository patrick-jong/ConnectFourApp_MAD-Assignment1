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
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        Button btnTwoPlayer = view.findViewById(R.id.btn_two_player);
        Button btnAiMode = view.findViewById(R.id.btn_ai_mode);
        Button btnSettings = view.findViewById(R.id.btn_settings);
        Button btnProfile = view.findViewById(R.id.btn_profile);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        btnTwoPlayer.setOnClickListener(v -> navController.navigate(R.id.action_mainMenu_to_game));
        btnAiMode.setOnClickListener(v -> navController.navigate(R.id.action_mainMenu_to_game)); // Set different argument for AI mode
        btnSettings.setOnClickListener(v -> navController.navigate(R.id.action_mainMenu_to_settings));
        btnProfile.setOnClickListener(v -> navController.navigate(R.id.action_mainMenu_to_profile));

        return view;
    }
}
