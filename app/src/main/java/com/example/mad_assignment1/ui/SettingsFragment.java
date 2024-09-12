package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mad_assignment1.R;

public class SettingsFragment extends Fragment {

    private RadioGroup gridSizeGroup;
    private Button saveSettingsButton;
    private Button btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        gridSizeGroup = view.findViewById(R.id.grid_size_group);
        saveSettingsButton = view.findViewById(R.id.btn_save_settings);
        btnBack = view.findViewById(R.id.btn_back);

        saveSettingsButton.setOnClickListener(v -> {
            //TODO: Implement setting save logic
        });

        // Back button
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_gameFragment_to_mainMenuFragment);
        });

        return view;
    }

}
