package com.example.mad_assignment1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mad_assignment1.profile.UserProfile;

public class GameAIViewModel extends ViewModel {
    private MutableLiveData<String[][]> board = new MutableLiveData<>();
    private MutableLiveData<UserProfile> currentPlayer = new MutableLiveData<>();
    private MutableLiveData<Boolean> gameActive = new MutableLiveData<>();
    private MutableLiveData<String> playerTurnIndicator = new MutableLiveData<>();
    private MutableLiveData<Integer> player1Moves = new MutableLiveData<>(0);
    private MutableLiveData<Integer> player2Moves = new MutableLiveData<>(0);
    private MutableLiveData<Integer> movesLeft = new MutableLiveData<>(42); //TODO: Change (if options say so)

    public LiveData<String[][]> getBoard() { return board; }
    public void setBoard(String[][] board) { this.board.setValue(board); }

    public LiveData<UserProfile> getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(UserProfile currentPlayer) { this.currentPlayer.setValue(currentPlayer); }

    public LiveData<Boolean> getGameActive() { return gameActive; }
    public void setGameActive(Boolean gameActive) { this.gameActive.setValue(gameActive); }

    public LiveData<String> getPlayerTurnIndicator() { return playerTurnIndicator; }
    public void setPlayerTurnIndicator(String playerTurnIndicator) { this.playerTurnIndicator.setValue(playerTurnIndicator); }

    public LiveData<Integer> getPlayer1Moves() { return player1Moves; }
    public void setPlayer1Moves(int moves) { this.player1Moves.setValue(moves); }

    public LiveData<Integer> getPlayer2Moves() { return player2Moves; }
    public void setPlayer2Moves(int moves) { this.player2Moves.setValue(moves); }

    public LiveData<Integer> getMovesLeft() { return movesLeft; }
    public void setMovesLeft(int moves) { this.movesLeft.setValue(moves); }
}
