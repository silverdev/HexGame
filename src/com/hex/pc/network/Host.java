package com.hex.pc.network;

import com.hex.core.Game;
import com.hex.network.Action;
import com.hex.network.ServerResponse;
import com.sam.hex.GameInfo;
import com.sam.hex.Hexgame;

public class Host extends PcClient {

    public Host(String name) {
        super(name);
        super.id = 2;
        super.talk = setUpServer();
        String gameData = setUpGame().save();
        super.setGame(gameData);
        sendGame(gameData, name);

    }

    private void sendGame(String savedGame, String name) {

        ServerResponse sr = new ServerResponse(name, id, null, Action.NEW_GAME, savedGame);
        System.out.println("the game is " + savedGame);
        String json = super.gson.toJson(sr);
        super.talk.sendMessage(json);

    }

    private Game setUpGame() {
        GameInfo gameInfo = Hexgame.buildGame();
        Game g = gameInfo.makeGame();
        return g;
    }

    private Server setUpServer() {
        Server server = new Server(this);

        return server;
    }

}
