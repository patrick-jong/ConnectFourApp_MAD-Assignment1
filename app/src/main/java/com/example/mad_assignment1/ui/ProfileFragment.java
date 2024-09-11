package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mad_assignment1.R;
import com.example.mad_assignment1.profile.AvatarAdapter;
import com.example.mad_assignment1.profile.UserProfile;

public class ProfileFragment extends Fragment {

    private EditText playerNameInput;
    private RecyclerView avatarRecyclerView;
    private Button saveProfileButton;
    private UserProfile userProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        playerNameInput = view.findViewById(R.id.et_player_name);
        avatarRecyclerView = view.findViewById(R.id.avatar_recycler_view);
        saveProfileButton = view.findViewById(R.id.btn_save_profile);

        // Initialize profile and adapter
        userProfile = new UserProfile();
        AvatarAdapter avatarAdapter = new AvatarAdapter(getContext(), userProfile.getAvailableAvatars());
        avatarRecyclerView.setAdapter(avatarAdapter);

        saveProfileButton.setOnClickListener(v -> saveProfile());

        return view;
    }

    private void saveProfile() {
        // Save user profile logic
        userProfile.setName(playerNameInput.getText().toString());
        // Save avatar selection, etc.
    }
}
