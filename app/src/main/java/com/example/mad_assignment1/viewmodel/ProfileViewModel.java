package com.example.mad_assignment1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mad_assignment1.profile.UserProfile;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<UserProfile> userProfile;

    public ProfileViewModel() {
        userProfile = new MutableLiveData<>(new UserProfile("Guest"));
    }

    public LiveData<UserProfile> getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile profile) {
        userProfile.setValue(profile);
    }

    public void incrementWins() {
        UserProfile profile = userProfile.getValue();
        if (profile != null) {
            profile.incrementWins();
            userProfile.setValue(profile);
        }
    }

    public void incrementLosses() {
        UserProfile profile = userProfile.getValue();
        if (profile != null) {
            profile.incrementLosses();
            userProfile.setValue(profile);
        }
    }

    public void incrementTotalGamesPlayed() {
        UserProfile profile = userProfile.getValue();
        if (profile != null) {
            profile.incrementGamesPlayed();
            userProfile.setValue(profile);
        }
    }

    public void updateStats(String status) {
        UserProfile profile = userProfile.getValue();
        if (profile != null) {
            if (status == "Win") {
                incrementWins();
                incrementTotalGamesPlayed();
            } else if (status == "Loss") {
                incrementLosses();
                incrementTotalGamesPlayed();
            } else if (status == "Draw") {
                incrementTotalGamesPlayed();
            }
        }
    }

    public int getWins() {
        UserProfile profile = userProfile.getValue();
        return profile != null ? profile.getWins() : 0;
    }

    public int getLosses() {
        UserProfile profile = userProfile.getValue();
        return profile != null ? profile.getLosses() : 0;
    }

    public int getTotalGamesPlayed() {
        UserProfile profile = userProfile.getValue();
        return profile != null ? profile.getTotalGamesPlayed() : 0;
    }

    public double getWinPercentage() {
        UserProfile profile = userProfile.getValue();
        return profile != null ? profile.getWinPercentage() : 0;
    }
}
