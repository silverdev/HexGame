package com.hex.network;

import com.hex.core.Move;

public interface Client {

    public abstract String getPlayerName();

    public abstract void start();

    public abstract void kill();

    public abstract boolean giveUp();

    public abstract Move getPlayerTurn();

    public abstract void sendMove(Move move);

    public abstract String getGame();

    public abstract int getTeam();

}
