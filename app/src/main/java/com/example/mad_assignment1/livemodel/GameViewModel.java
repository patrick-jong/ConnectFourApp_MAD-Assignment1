//package com.example.mad_assignment1.livemodel;
//
//import androidx.lifecycle.ViewModel;
//
//import com.example.mad_assignment1.game.ConnectFourGame;
//import com.example.mad_assignment1.statistics.GameStatistics;
//import com.example.mad_assignment1.profile.UserProfile;
//
//public class GameViewModel extends ViewModel {
//    private ConnectFourGame connectFourGame;
//    private UserProfile currentUserProfile;
//    private GameStatistics gameStatistics;
//
//    // Constructor for default players (Player 1 and Player 2)
//    public GameViewModel() {
//        // Create two default UserProfile instances
//        UserProfile player1 = new UserProfile("Player 1");
//        UserProfile player2 = new UserProfile("Player 2");
//
//        // Initialize ConnectFourGame with two default players
//        connectFourGame = new ConnectFourGame(player1, player2);
//
//        // Initialize GameStatistics or any other additional state
//        gameStatistics = new GameStatistics();
//    }
//
//    // Constructor for setting current user as Player 1 and AI as Player 2
//    public GameViewModel(UserProfile currentUser) {
//        this.currentUserProfile = currentUser;
//
//        // Create AI player profile
//        UserProfile aiPlayer = new UserProfile("AI Player");
//
//        // Initialize ConnectFourGame with current user and AI player
//        connectFourGame = new ConnectFourGame(currentUserProfile, aiPlayer);
//
//        // Initialize GameStatistics or any other additional state
//        gameStatistics = new GameStatistics();
//    }
//
//    // Getter for ConnectFourGame instance
//    public ConnectFourGame getConnectFourGame() {
//        return connectFourGame;
//    }
//
//    // Getter for GameStatistics instance
//    public GameStatistics getGameStatistics() {
//        return gameStatistics;
//    }
//
//    // Optionally add methods to modify state, like updating statistics or current player
//    public void updateCurrentUserProfile(UserProfile newUserProfile) {
//        this.currentUserProfile = newUserProfile;
//    }
//
//    public UserProfile getCurrentUserProfile() {
//        return currentUserProfile;
//    }
//}
