package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static int GRID_SIZE = 3;

    private CellStatus[][] mGameState;
    private ImageButton[][] mButtons;

    private Button mResetButton;
    private TextView mMessageText;

    private int mCurrentPlayer = CellStatus.FIRST_PLAYER;

    private void resetGameState() {
        mMessageText.setText("Tic Tac Toe");
        for (int i = 0; i < GRID_SIZE; ++i) {
            for (int j = 0; j < GRID_SIZE; ++j) {
                mGameState[i][j] = new CellStatus();
                mButtons[i][j].setImageResource(R.drawable.empty);
            }
        }
    }

    protected int checkGameWin() {
        if (checkPlayerWon(CellStatus.FIRST_PLAYER)) return CellStatus.FIRST_PLAYER;
        if (checkPlayerWon(CellStatus.SECOND_PLAYER)) return CellStatus.SECOND_PLAYER;
        int count = 0;
        for (int i = 0; i < GRID_SIZE; ++i) {
            for (int j = 0; j < GRID_SIZE; ++j) {
                if (mGameState[i][j].getPlayer() != CellStatus.NO_PLAYER) ++count;
            }
        }
        if (count == 9) return CellStatus.DRAW;
        return CellStatus.NO_PLAYER;
    }

    private boolean checkPlayerWon(int player) {
        boolean winDiag1 = true, winDiag2 = true;
        for (int i = 0; i < GRID_SIZE; ++i) {
            winDiag1 = winDiag1 && mGameState[i][i].getPlayer() == player;
            winDiag2 = winDiag2 && mGameState[i][2 - i].getPlayer() == player;
            boolean winRow = true, winCol = true;
            for (int j = 0; j < GRID_SIZE; ++j) {
                winRow = winRow && mGameState[i][j].getPlayer() == player;
                winCol = winCol && mGameState[j][i].getPlayer() == player;
            }

            if (winRow || winCol) return true;
        }


        if (winDiag1 || winDiag2) return true;

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGameState = new CellStatus[GRID_SIZE][GRID_SIZE];
        mButtons = new ImageButton[GRID_SIZE][GRID_SIZE];
        mResetButton = findViewById(R.id.new_game_button);
        mMessageText = findViewById(R.id.top_text);

        for (int i = 0; i < GRID_SIZE; ++i) {
            for (int j = 0; j < GRID_SIZE; ++j) {
                String buttonId = "cell_" + i + j;
                int resID = getResources().getIdentifier(buttonId, "id", getPackageName());
                mButtons[i][j] = findViewById(resID);
                final int row = i, col = j;
                mButtons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mGameState[row][col].getPlayer() == CellStatus.NO_PLAYER) {
                            mGameState[row][col].setPlayer(mCurrentPlayer);
                            if (mCurrentPlayer == CellStatus.FIRST_PLAYER) {
                                mButtons[row][col].setImageResource(R.drawable.nought);
                            } else {
                                mButtons[row][col].setImageResource(R.drawable.cross);
                            }
                            mCurrentPlayer = mCurrentPlayer == CellStatus.FIRST_PLAYER ? CellStatus.SECOND_PLAYER : CellStatus.FIRST_PLAYER;
                            int winner = checkGameWin();
                            if (winner == CellStatus.FIRST_PLAYER)
                                mMessageText.setText("Winner is First Player!");
                            else if (winner == CellStatus.SECOND_PLAYER)
                                mMessageText.setText("Winner is Second Player!");
                            else if (winner == CellStatus.DRAW)
                                mMessageText.setText("Match is draw!");

                        }
                    }
                });
            }
        }

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGameState();
            }
        });


        resetGameState();
    }


}
