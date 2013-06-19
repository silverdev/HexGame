package com.sam.hex;

import com.hex.core.Game;
import com.hex.core.PlayingEntity;
import com.hex.core.Timer;
import com.hex.network.NetworkCallbacks;

public class PcNetworkCallbacks implements NetworkCallbacks {
    PlayingEntity[] players;

    public PcNetworkCallbacks(PlayingEntity player1, PlayingEntity player2) {
        players = new PlayingEntity[3];
        this.players[0] = null;
        this.players[1] = player1;
        this.players[2] = player2;

    }

    @Override
    public void newGame(String state) {

        Game game = Game.load(state, players[1], players[2]);
        game.gameOptions.timer = new Timer(0, 0, 0);
        Hexgame.swapGame(game);
    }

    @Override
    public String newGameReqest() {

        String state = Hexgame.buildGame().makeGame().save();

        return state;
    }

    @Override
    public boolean undoRequest(int turnNumer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void undo(int turnNumer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void error() {
        // TODO Auto-generated method stub

    }

    @Override
    public void chat(String data) {
        // TODO Auto-generated method stub

    }

}
