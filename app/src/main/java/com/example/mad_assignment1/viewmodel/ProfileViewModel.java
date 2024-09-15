// Class: ProfileViewModel
// Description: Holds data from the GameAIFragment

package com.example.mad_assignment1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mad_assignment1.profile.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    // List of all user profiles
    private final MutableLiveData<List<UserProfile>> userProfiles;

    // Currently selected user profile
    private final MutableLiveData<UserProfile> currentUserProfile;

    public ProfileViewModel() {
        // Initialize the list of user profiles
        userProfiles = new MutableLiveData<>(new ArrayList<>());

        // Initialize the default "Guest" profile
        UserProfile guestProfile = new UserProfile("Guest");

        // Add the guest profile to the list
        userProfiles.getValue().add(guestProfile);

        // Set the guest profile as the current user profile
        currentUserProfile = new MutableLiveData<>(guestProfile);
    }

    // Get the list of user profiles
    public LiveData<List<UserProfile>> getUserProfiles() {
        return userProfiles;
    }

    // Get the currently selected user profile
    public LiveData<UserProfile> getCurrentUserProfile() {
        return currentUserProfile;
    }

    // Set the current user profile
    public void setCurrentUserProfile(UserProfile profile) {
        if (profile != null) {
            currentUserProfile.setValue(profile);
        }
    }

    // Add a new user profile and set it as current
    public void addUserProfile(UserProfile profile) {
        if (profile != null) {
            List<UserProfile> profiles = userProfiles.getValue();
            profiles.add(profile);
            userProfiles.setValue(profiles); // Update the LiveData
            setCurrentUserProfile(profile);
        }
    }

    // Check if a profile name is unique
    public boolean isProfileNameUnique(String name) {
        for (UserProfile profile : userProfiles.getValue()) {
            if (profile.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    // Methods to update statistics on the current user profile
    public void incrementWins() {
        UserProfile profile = currentUserProfile.getValue();
        if (profile != null) {
            profile.incrementWins();
            currentUserProfile.setValue(profile);
        }
    }

    public void incrementLosses() {
        UserProfile profile = currentUserProfile.getValue();
        if (profile != null) {
            profile.incrementLosses();
            currentUserProfile.setValue(profile);
        }
    }

    public void incrementGamesPlayed() {
        UserProfile profile = currentUserProfile.getValue();
        if (profile != null) {
            profile.incrementGamesPlayed();
            currentUserProfile.setValue(profile);
        }
    }

    public void updateStats(String status) {
        UserProfile profile = currentUserProfile.getValue();
        if (profile != null) {
            if ("Win".equals(status)) {
                profile.incrementWins();
            } else if ("Loss".equals(status)) {
                profile.incrementLosses();
            }
            // Increment games played for any result
            profile.incrementGamesPlayed();
            currentUserProfile.setValue(profile);
        }
    }

    // Getters for statistics
    public int getWins() {
        UserProfile profile = currentUserProfile.getValue();
        return profile != null ? profile.getWins() : 0;
    }

    public int getLosses() {
        UserProfile profile = currentUserProfile.getValue();
        return profile != null ? profile.getLosses() : 0;
    }

    public int getTotalGamesPlayed() {
        UserProfile profile = currentUserProfile.getValue();
        return profile != null ? profile.getGamesPlayed() : 0;
    }

    public double getWinPercentage() {
        UserProfile profile = currentUserProfile.getValue();
        return profile != null ? profile.getWinPercentage() : 0;
    }

    public void resetStatistics() {
        UserProfile profile = currentUserProfile.getValue();
        profile.setWins(0);
        profile.setLosses(0);
        profile.setGamesPlayed(0);
    }
}
