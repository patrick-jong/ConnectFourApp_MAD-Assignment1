package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// Import statements
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mad_assignment1.R;
import com.example.mad_assignment1.viewmodel.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private RadioGroup gridSizeGroup;
    private RadioButton rbStandardGrid, rbSmallGrid, rbLargeGrid;
    private Button btnSaveSettings;
    private Button btnBack;

    private int tempNumRows;
    private int tempNumColumns;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize UI components
        gridSizeGroup = view.findViewById(R.id.grid_size_group);
        rbStandardGrid = view.findViewById(R.id.rb_standard_grid);
        rbSmallGrid = view.findViewById(R.id.rb_small_grid);
        rbLargeGrid = view.findViewById(R.id.rb_large_grid);
        btnSaveSettings = view.findViewById(R.id.btn_save_settings);
        btnBack = view.findViewById(R.id.btn_back);

        // Initialize SettingsViewModel
        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        // Get the current grid size from the ViewModel
        int currentRows = settingsViewModel.getNumRows().getValue() != null ? settingsViewModel.getNumRows().getValue() : 6;
        int currentColumns = settingsViewModel.getNumColumns().getValue() != null ? settingsViewModel.getNumColumns().getValue() : 7;

        // Set the initial selection in the RadioGroup based on the current grid size
        if (currentRows == 6 && currentColumns == 7) {
            rbStandardGrid.setChecked(true);
        } else if (currentRows == 5 && currentColumns == 6) {
            rbSmallGrid.setChecked(true);
        } else if (currentRows == 7 && currentColumns == 8) {
            rbLargeGrid.setChecked(true);
        }

        // Initialize temporary variables with current grid size
        tempNumRows = currentRows;
        tempNumColumns = currentColumns;

        // Set up listeners for RadioGroup changes
        gridSizeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Store the user's selection in temporary variables
            if (checkedId == rbStandardGrid.getId()) {
                tempNumRows = 6;
                tempNumColumns = 7;
            } else if (checkedId == rbSmallGrid.getId()) {
                tempNumRows = 5;
                tempNumColumns = 6;
            } else if (checkedId == rbLargeGrid.getId()) {
                tempNumRows = 7;
                tempNumColumns = 8;
            }
        });

        // Handle Save Settings button click
        btnSaveSettings.setOnClickListener(v -> {
            if (tempNumRows != 0 && tempNumColumns != 0) {
                // Update the ViewModel with the new grid size
                settingsViewModel.setGridSize(tempNumRows, tempNumColumns);

                Toast.makeText(getContext(), "Settings saved.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please select a grid size.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Back button click
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_settingsFragment_to_mainMenuFragment);
        });

        return view;
    }
}
