package com.example.mad_assignment1.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mad_assignment1.R;

import java.util.Arrays;
import java.util.List;

public class UserProfile implements Parcelable {
    private String name;
    private int avatar; // Changed from Integer to int
    private int gamesPlayed;
    private int wins;
    private int losses;

    public UserProfile(String name) {
        this.name = name;
        this.avatar = R.drawable.avatar1; // Set to a valid drawable resource ID
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
    }

    // Parcelable implementation
    protected UserProfile(Parcel in) {
        name = in.readString();
        avatar = in.readInt();
        gamesPlayed = in.readInt();
        wins = in.readInt();
        losses = in.readInt();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(avatar);
        dest.writeInt(gamesPlayed);
        dest.writeInt(wins);
        dest.writeInt(losses);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatarResourceId) {
        this.avatar = avatarResourceId;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public int getWins() {
        return wins;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void setWins(int inWin) {
        wins = inWin;
    }

    public void setLosses(int inLoss) {
        losses = inLoss;
    }

    public int getLosses() {
        return losses;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public double getWinPercentage() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        return ((double) wins / gamesPlayed) * 100;
    }

    public void setGamesPlayed(int inInt) {
        gamesPlayed = inInt;
    }

    public List<Integer> getAvailableAvatars() {
        return Arrays.asList(
                R.drawable.avatar1,
                R.drawable.avatar2,
                R.drawable.avatar3,
                R.drawable.avatar4,
                R.drawable.avatar5
        );
    }
}
