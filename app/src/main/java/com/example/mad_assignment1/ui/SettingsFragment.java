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
import com.example.mad_assignment1.R;

public class SettingsFragment extends Fragment {

    private RadioGroup gridSizeGroup;
    private Button saveSettingsButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        gridSizeGroup = view.findViewById(R.id.grid_size_group);
        saveSettingsButton = view.findViewById(R.id.btn_save_settings);

        saveSettingsButton.setOnClickListener(v -> saveSettings());

        return view;
    }

    private void saveSettings() {
        // Implement settings save logic
    }
}
