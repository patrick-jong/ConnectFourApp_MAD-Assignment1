package com.example.mad_assignment1;


import androidx.lifecycle.ViewModel;
import com.example.mad_assignment1.game.ConnectFourGame;

public class ConnectFourViewModel extends ViewModel {
    private ConnectFourGame connectFourGame;

    public ConnectFourViewModel() {
        connectFourGame = new ConnectFourGame();
    }

    public ConnectFourGame getConnectFourGame() {
        return connectFourGame;
    }
}
