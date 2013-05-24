package com.sam.hex;

import com.hex.core.Game;
import com.hex.core.Game.GameOptions;
import com.hex.core.PlayerObject;
import com.hex.core.PlayingEntity;

public class GameInfo {
    public GameInfo(GameOptions options, PlayingEntity player1, PlayingEntity player2) {
        this.options = options;
        this.player1 = player1;
        this.player2 = player2;
        // these lines set the player for unplaced pieces
        this.players[0] = new PlayerObject(0);
        this.players[0].setColor(0xFFFFFFFF);
        this.players[1] = player1;
        this.players[2] = player2;

    }

    GameOptions options;
    PlayingEntity player1;
    PlayingEntity player2;
    PlayingEntity[] players = new PlayingEntity[3];

    public Game makeGame() {

        return new Game(options, player1, player2);
    }

}
