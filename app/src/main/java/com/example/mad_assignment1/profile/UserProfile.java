package com.example.mad_assignment1.profile;

import com.example.mad_assignment1.R;

import java.util.List;

public class UserProfile {

    private String username;
    private int selectedAvatar;

    // Constructor
    public UserProfile(String inName) {
        username = inName;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getName() {
        return username;
    }

    public List<Integer> getAvailableAvatars() {
        // Return a list of drawable resource IDs for avatars
        return List.of(R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3);
    }

    public void setSelectedAvatar(int avatar) {
        this.selectedAvatar = avatar;
    }

    public int getSelectedAvatar() {
        return selectedAvatar;
    }

}
