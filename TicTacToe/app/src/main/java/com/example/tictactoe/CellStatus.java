package com.example.tictactoe;

public class CellStatus {
    public static final int NO_PLAYER = 100;
    public static final int FIRST_PLAYER = 101;
    public static final int SECOND_PLAYER = 102;

    private int player = NO_PLAYER;

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }
}
