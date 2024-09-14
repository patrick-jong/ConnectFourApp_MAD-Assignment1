package com.example.mad_assignment1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mad_assignment1.profile.UserProfile;

public class GameViewModel extends ViewModel {
    private final MutableLiveData<String[][]> board = new MutableLiveData<>();
    private final MutableLiveData<UserProfile> currentPlayer = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gameActive = new MutableLiveData<>();
    private final MutableLiveData<String> playerTurnIndicator = new MutableLiveData<>();

    public LiveData<String[][]> getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board.setValue(board);
    }

    public LiveData<UserProfile> getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(UserProfile player) {
        this.currentPlayer.setValue(player);
    }

    public LiveData<Boolean> getGameActive() {
        return gameActive;
    }

    public void setGameActive(boolean active) {
        this.gameActive.setValue(active);
    }

    public LiveData<String> getPlayerTurnIndicator() {
        return playerTurnIndicator;
    }

    public void setPlayerTurnIndicator(String indicator) {
        this.playerTurnIndicator.setValue(indicator);
    }
}
