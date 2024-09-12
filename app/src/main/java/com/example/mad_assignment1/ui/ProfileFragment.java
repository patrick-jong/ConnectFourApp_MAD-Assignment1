package com.example.mad_assignment1.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mad_assignment1.R;
import com.example.mad_assignment1.profile.AvatarAdapter;
import com.example.mad_assignment1.profile.UserProfile;

public class ProfileFragment extends Fragment {

    private EditText playerNameInput;
    private RecyclerView avatarRecyclerView;
    private Button saveProfileButton;
    private UserProfile userProfile;
    private Button btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        playerNameInput = view.findViewById(R.id.et_player_name);
        avatarRecyclerView = view.findViewById(R.id.avatar_recycler_view);
        saveProfileButton = view.findViewById(R.id.btn_save_profile);
        btnBack = view.findViewById(R.id.btn_back);

        // Initialize profile and adapter
        userProfile = new UserProfile();
        AvatarAdapter avatarAdapter = new AvatarAdapter(getContext(), userProfile.getAvailableAvatars());
        avatarRecyclerView.setAdapter(avatarAdapter);

        // Safe Profile Button
        saveProfileButton.setOnClickListener(v -> {
            // Save user profile logic
            userProfile.setName(playerNameInput.getText().toString());
            // TODO: Save avatar selection, etc.
        });

        // Back button
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_gameFragment_to_mainMenuFragment);
        });

        return view;
    }

}
