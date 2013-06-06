package com.hex.network;

import java.util.List;

import com.hex.core.Game;
import com.hex.core.Move;

public class ServerResponse {

    public int responseCode;
    public String playerToken;
    public List<String> error;
    public String playerName;
    public int playerID;
    public int resources;
    public Move move;
    public Action action;
    public Game game;

    public ServerResponse(String PlayerName, int PlayerID, Move move, Action action, Game game) {

        this.playerID = playerID;
        this.playerName = playerName;
        this.resources = resources;
        this.move = move;
        this.action = action;
        this.game = game;
    }

}
