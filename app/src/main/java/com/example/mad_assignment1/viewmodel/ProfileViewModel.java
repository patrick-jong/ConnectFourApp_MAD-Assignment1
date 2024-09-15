// Class: ProfileViewModel.java
// Description: ViewModel that manages the state of UserProfileDB and handles user profile operations.

package com.example.mad_assignment1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mad_assignment1.profile.UserProfile;
import com.example.mad_assignment1.profile.UserProfileDB;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<UserProfileDB> userProfileDBLiveData;
    private final MutableLiveData<UserProfile> currentUserProfile;

    public ProfileViewModel() {
        UserProfileDB userProfileDB = new UserProfileDB();
        userProfileDBLiveData = new MutableLiveData<>(userProfileDB);        // Initialize the LiveData with UserProfileDB
        currentUserProfile = new MutableLiveData<>(userProfileDB.getProfileByName("Guest"));         // Set the guest profile as the current user profile
    }

    public LiveData<UserProfileDB> getUserProfileDB() {
        return userProfileDBLiveData;
    }

    public LiveData<UserProfile> getCurrentUserProfile() {
        return currentUserProfile;
    }

    public void setCurrentUserProfile(UserProfile profile) {
        if (profile != null) {
            currentUserProfile.setValue(profile);
        }
    }

    public void addProfile(UserProfile profile) {
        UserProfileDB db = userProfileDBLiveData.getValue();
        if (db != null) {
            db.addProfile(profile);
            userProfileDBLiveData.setValue(db); // Update LiveData to reflect the changes
        }
    }

    public void updateProfile(UserProfile updatedProfile) {
        UserProfileDB db = userProfileDBLiveData.getValue();
        if (db != null) {
            db.updateProfile(updatedProfile);
            userProfileDBLiveData.setValue(db); // Update LiveData to reflect the changes
        }
    }

    public boolean isProfileNameUnique(String name) {
        UserProfileDB db = userProfileDBLiveData.getValue();
        return db != null && db.isProfileNameUnique(name);
    }

    public UserProfile getProfileByName(String name) {
        UserProfileDB db = userProfileDBLiveData.getValue();
        return db != null ? db.getProfileByName(name) : null;
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
        if (profile != null) {
            profile.setWins(0);
            profile.setLosses(0);
            profile.setGamesPlayed(0);
            currentUserProfile.setValue(profile);
        }
    }

}
