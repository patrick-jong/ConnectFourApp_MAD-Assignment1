package com.example.mad_assignment1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mad_assignment1.R;
import com.example.mad_assignment1.game.ConnectFourGame;
import com.example.mad_assignment1.game.GameLogic;

public class GameFragment extends Fragment {

    private TextView playerTurnIndicator;
    private GridView gameGrid;
    private ConnectFourGame connectFourGame;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        playerTurnIndicator = view.findViewById(R.id.player_turn_indicator);
        gameGrid = view.findViewById(R.id.game_grid);

        // Initialize game logic
        connectFourGame = new ConnectFourGame(7, 6); // Example standard size
        connectFourGame.startNewGame();

        // Update UI based on game state
        updateUI();

        return view;
    }

    private void updateUI() {
        // Update the game grid and player turn indicator
        playerTurnIndicator.setText(connectFourGame.getCurrentPlayer() + "'s Turn");
    }
}
