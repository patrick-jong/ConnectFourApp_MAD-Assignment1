// Class: ProfileViewModel.java
// Description: ViewModel that manages the state of UserProfileDB and handles user profile operations.

package com.example.mad_assignment1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mad_assignment1.profile.UserProfile;
import com.example.mad_assignment1.profile.UserProfileDB;

public class ProfileViewModel extends ViewModel {
    // LiveData holding the UserProfileDB instance
    private final MutableLiveData<UserProfileDB> userProfileDBLiveData;

    // Currently selected user profile
    private final MutableLiveData<UserProfile> currentUserProfile;

    public ProfileViewModel() {
        // Initialize the UserProfileDB
        UserProfileDB userProfileDB = new UserProfileDB();

        // Initialize the LiveData with UserProfileDB
        userProfileDBLiveData = new MutableLiveData<>(userProfileDB);

        // Set the guest profile as the current user profile
        currentUserProfile = new MutableLiveData<>(userProfileDB.getProfileByName("Guest"));
    }

    // Get the LiveData containing UserProfileDB
    public LiveData<UserProfileDB> getUserProfileDB() {
        return userProfileDBLiveData;
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

    // Add a new user profile
    public void addProfile(UserProfile profile) {
        UserProfileDB db = userProfileDBLiveData.getValue();
        if (db != null) {
            db.addProfile(profile);
            userProfileDBLiveData.setValue(db); // Update LiveData to reflect the changes
        }
    }

    // Update an existing user profile
    public void updateProfile(UserProfile updatedProfile) {
        UserProfileDB db = userProfileDBLiveData.getValue();
        if (db != null) {
            db.updateProfile(updatedProfile);
            userProfileDBLiveData.setValue(db); // Update LiveData to reflect the changes
        }
    }

    // Check if a profile name is unique
    public boolean isProfileNameUnique(String name) {
        UserProfileDB db = userProfileDBLiveData.getValue();
        return db != null && db.isProfileNameUnique(name);
    }

    // Get a profile by its name
    public UserProfile getProfileByName(String name) {
        UserProfileDB db = userProfileDBLiveData.getValue();
        return db != null ? db.getProfileByName(name) : null;
    }

    // Methods to update statistics on the current user profile
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

    // Increment wins for the current profile
    public void incrementWins() {
        UserProfile profile = currentUserProfile.getValue();
        if (profile != null) {
            profile.incrementWins();
            currentUserProfile.setValue(profile);
        }
    }

    // Increment losses for the current profile
    public void incrementLosses() {
        UserProfile profile = currentUserProfile.getValue();
        if (profile != null) {
            profile.incrementLosses();
            currentUserProfile.setValue(profile);
        }
    }

    // Increment games played for the current profile
    public void incrementGamesPlayed() {
        UserProfile profile = currentUserProfile.getValue();
        if (profile != null) {
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

    // Reset statistics for the current profile
    public void resetStatistics() {
        UserProfile profile = currentUserProfile.getValue();
        if (profile != null) {
            profile.setWins(0);
            profile.setLosses(0);
            profile.setGamesPlayed(0);
            currentUserProfile.setValue(profile);
        }
    }
}
