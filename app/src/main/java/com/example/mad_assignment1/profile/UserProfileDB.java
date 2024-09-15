// Class: UserProfileDB.java
// Description: Manages a list of UserProfile objects.

package com.example.mad_assignment1.profile;

import java.util.ArrayList;
import java.util.List;

public class UserProfileDB {
    // List of all user profiles
    private final List<UserProfile> userProfiles;

    public UserProfileDB() {
        userProfiles = new ArrayList<>();

        // Add default guest profile
        UserProfile guestProfile = new UserProfile("Guest");
        guestProfile.setAvatar(guestProfile.getAvailableAvatars().get(4)); // Setting default avatar
        userProfiles.add(guestProfile);
    }

    // Get the list of all user profiles
    public List<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void addProfile(UserProfile profile) {
        if (profile != null && isProfileNameUnique(profile.getName())) {
            userProfiles.add(profile);
        }
    }

    public UserProfile getProfileByName(String name) {
        for (UserProfile profile : userProfiles) {
            if (profile.getName().equals(name)) {
                return profile;
            }
        }
        return null;
    }

    public void updateProfile(UserProfile updatedProfile) {
        if (updatedProfile != null) {
            for (int i = 0; i < userProfiles.size(); i++) {
                if (userProfiles.get(i).getName().equals(updatedProfile.getName())) {
                    userProfiles.set(i, updatedProfile); // Update the existing profile
                    return;
                }
            }
        }
    }

    public boolean isProfileNameUnique(String name) {
        for (UserProfile profile : userProfiles) {
            if (profile.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
}
