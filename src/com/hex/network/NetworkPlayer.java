package com.hex.network;

import java.io.Serializable;

import com.hex.core.Game;
import com.hex.core.Move;
import com.hex.core.MoveList;
import com.hex.core.Player;
import com.hex.core.PlayingEntity;
import com.hex.core.TurnMismatchException;
import com.hex.pc.network.NetworkCallbacks;

public class NetworkPlayer implements PlayingEntity {
    private static final long serialVersionUID = 1L;

    private int color;
    private long timeLeft;
    public final int team;
    private boolean skipMove = false;
    private NetworkCallbacks callbacks;
    private Client tc;

    public NetworkPlayer(Client tc, NetworkCallbacks callbacks) {
        this.tc = tc;
        this.tc.start();
        this.team = tc.getTeam();

        System.out.println("the team is ____" + team);

        this.callbacks = callbacks;
    }

    @Override
    public void undoCalled() {
        setSkipMove(true);
    }

    @Override
    public void newgameCalled() {
        endMove();

    }

    @Override
    public boolean supportsUndo(Game game) {
        return false;
    }

    @Override
    public boolean supportsNewgame() {
        return false;
    }

    @Override
    public void quit() {
        endMove();
        tc.kill();
    }

    @Override
    public boolean supportsSave() {
        return false;
    }

    @Override
    public void endMove() {
        setSkipMove(true);
    }

    @Override
    // cant set the name of remote player
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return tc.getPlayerName();
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setTime(long time) {
        this.timeLeft = time;
    }

    @Override
    public long getTime() {
        return timeLeft;
    }

    @Override
    public boolean giveUp() {
        return tc.giveUp();
    }

    public boolean getSkipMove() {
        return skipMove;
    }

    private void setSkipMove(boolean skipMove) {
        this.skipMove = skipMove;
    }

    @Override
    // remote player is always player two
    public byte getTeam() {
        return (byte) 2;
    }

    @Override
    public Player getType() {
        return Player.Net;
    }

    @Override
    public void getPlayerTurn(Game game) {
        // if this is not the first move send play the last move
        MoveList moveList = game.getMoveList();
        if(moveList.size() > 0) {
            tc.sendMove(moveList.getMove());
        }
        // get the other players Move
        Move move = tc.getPlayerTurn();
        if(tc == null) return; // for quitting?
        if(move.getMoveNumber() != game.getMoveNumber()) {
            throw new TurnMismatchException("NetGame error");
        }
        game.getMoveList().makeMove(move);
        game.gamePieces[move.getX()][move.getY()].setTeam((byte) this.team, game);
    }

    @Override
    public Serializable getSaveState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setSaveState(Serializable state) {

    }

    @Override
    public void lose(Game game) {
        System.out.println("i win");
        tc.sendMove(game.getMoveList().getMove());
        System.out.println("I let the looser know");

    }

    @Override
    public void win() {
        // TODO Auto-generated method stub

    }
}
