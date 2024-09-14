package com.example.mad_assignment1.profile;

import com.example.mad_assignment1.R;

import java.util.List;

public class UserProfile {

    private String name;
    private int selectedAvatar;

    // Constructor
    public UserProfile(String inName) {
        name = inName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
