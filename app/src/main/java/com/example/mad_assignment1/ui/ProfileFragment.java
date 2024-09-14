package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mad_assignment1.profile.UserProfile;
import com.example.mad_assignment1.viewmodel.ProfileViewModel;
import com.example.mad_assignment1.R;

import java.util.List;

public class ProfileFragment extends Fragment {

    private EditText playerNameInput;
    private ProfileViewModel profileViewModel;
    private Button btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnBack = view.findViewById(R.id.btn_back);
        playerNameInput = view.findViewById(R.id.et_player_name);

        // Initialize ProfileViewModel
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        // Example of adding a new profile
        view.findViewById(R.id.btn_save_profile).setOnClickListener(v -> {
            String newName = playerNameInput.getText().toString().trim();
            if (!TextUtils.isEmpty(newName)) {
                if (profileViewModel.isProfileNameUnique(newName)) {
                    UserProfile newProfile = new UserProfile(newName);
                    profileViewModel.addUserProfile(newProfile);
                    Toast.makeText(getContext(), "Profile created and selected.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Profile name already exists.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter a profile name.", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe current user profile changes
        profileViewModel.getCurrentUserProfile().observe(getViewLifecycleOwner(), userProfile -> {
            if (userProfile != null) {
                playerNameInput.setText(userProfile.getName());
            }
        });

        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_profileFragment_to_mainMenuFragment);
        });

        // Handle profile selection (assuming you have a list of profiles in the UI)
        // Example: when a profile is selected from a list
        // profileViewModel.setCurrentUserProfile(selectedProfile);

        return view;
    }
}
