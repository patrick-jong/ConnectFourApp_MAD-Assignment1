// Class: ProfileFragment.java
// Description: Profile fragment which handles username and avatar selection

package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_assignment1.R;
import com.example.mad_assignment1.profile.AvatarAdapter;
import com.example.mad_assignment1.profile.UserProfile;
import com.example.mad_assignment1.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private UserProfile userProfile;
    private EditText playerNameInput;
    private RecyclerView avatarRecyclerView;
    private AvatarAdapter avatarAdapter;
    private Button btnBack;
    private Button btnSave;
    private Button btnLogIn;
    private int selectedAvatarResourceId; // Temporary storage for selected avatar

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI components
        playerNameInput = view.findViewById(R.id.et_player_name);
        avatarRecyclerView = view.findViewById(R.id.avatar_recycler_view);

        btnSave = view.findViewById(R.id.btn_save_profile);
        btnLogIn = view.findViewById(R.id.btn_login_profile);
        btnBack = view.findViewById(R.id.btn_back);

        // Initialize ProfileViewModel
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        // Get the current user profile
        userProfile = profileViewModel.getCurrentUserProfile().getValue();

        // Set the player name and initialize selectedAvatarResourceId
        if (userProfile != null) {
            playerNameInput.setText(userProfile.getName());
            selectedAvatarResourceId = userProfile.getAvatar(); // Initialize with current avatar
        } else {
            selectedAvatarResourceId = R.drawable.avatar1; // Or any default avatar
        }

        // Set up the avatar RecyclerView
        setupAvatarRecyclerView();

        // Handle Log In Button
        btnLogIn.setOnClickListener(v -> {
            String inUserName = playerNameInput.getText().toString().trim();
            if (!inUserName.isEmpty()) {
                UserProfile existingProfile = profileViewModel.getProfileByName(inUserName);
                if (existingProfile != null) {
                    if (!existingProfile.equals(profileViewModel.getCurrentUserProfile().getValue())) {
                        profileViewModel.setCurrentUserProfile(existingProfile);
                        Toast.makeText(getContext(), "Logged in as " + inUserName, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Already logged into this profile.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Profile does not exist. Select \"Save Profile\" to create a new profile", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter a profile name.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Save Profile button click
        btnSave.setOnClickListener(v -> {
            String newName = playerNameInput.getText().toString().trim();
            if (!newName.isEmpty()) {
                UserProfile existingProfile = profileViewModel.getProfileByName(newName);
                if (existingProfile != null && !newName.equals(userProfile.getName())) {
                    Toast.makeText(getContext(), "Profile name already exists.", Toast.LENGTH_SHORT).show();
                } else {
                    if (existingProfile == null) {
                        // Create a new profile
                        UserProfile newProfile = new UserProfile(newName);
                        newProfile.setAvatar(selectedAvatarResourceId);
                        profileViewModel.addProfile(newProfile);
                        profileViewModel.setCurrentUserProfile(newProfile);
                        Toast.makeText(getContext(), "Profile created and logged in as " + newName, Toast.LENGTH_SHORT).show();
                    } else {
                        // Update existing profile
                        existingProfile.setAvatar(selectedAvatarResourceId);
                        profileViewModel.updateProfile(existingProfile);
                        profileViewModel.setCurrentUserProfile(existingProfile);
                        Toast.makeText(getContext(), "Profile updated.", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getContext(), "Please enter a profile name.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Back button click
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_profileFragment_to_mainMenuFragment);
        });

        return view;
    }

    private void setupAvatarRecyclerView() {
        avatarRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3)); // 3 columns
        avatarAdapter = new AvatarAdapter(getContext(), userProfile.getAvailableAvatars(), selectedAvatarResourceId);
        avatarRecyclerView.setAdapter(avatarAdapter);

        avatarAdapter.setOnAvatarSelectedListener(avatarResourceId -> {
            selectedAvatarResourceId = avatarResourceId;
            avatarAdapter.setSelectedAvatarResourceId(avatarResourceId);
        });
    }
}
