package com.example.mad_assignment1.profile;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mad_assignment1.R;

public class UserProfile implements Parcelable {
    private String name;
    private Integer avatar;
    private int gamesPlayed;
    private int wins;
    private int losses;

    public UserProfile(String name) {
        this.name = name;
        this.avatar = 1;
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

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
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

    public int getLosses() {
        return losses;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public int getTotalGamesPlayed() {
        return gamesPlayed;
    }

    public double getWinPercentage() {
        return ((double)wins / (double)gamesPlayed) * 100;
    }

    public List<Integer> getAvailableAvatars() {
        return List.of(R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3);
    }
}
