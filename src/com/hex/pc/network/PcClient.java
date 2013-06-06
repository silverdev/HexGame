package com.hex.pc.network;

import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;
import com.hex.core.Game;
import com.hex.core.Move;
import com.hex.network.Action;
import com.hex.network.Client;
import com.hex.network.ServerResponse;

public class PcClient extends Thread implements Client {
    public PcClient() {

    }

    private String name;
    private Talker talk;
    private int id = 2;
    private GameLogger logger;
    private Gson gson = new Gson();
    private String otherNane = "Not connected!";
    private Game game = null;
    private final LinkedBlockingQueue<Move> moves = new LinkedBlockingQueue<Move>();

    public PcClient(String n, String IP) {
        System.out.println("Creating New TestClient: " + n);
        name = n;
        talk = new Talker(this, IP);
        logger = new GameLogger(name);
    }

    public void messageDispach(String message) {
        logger.log(message);
        ServerResponse sr = gson.fromJson(message, ServerResponse.class);
        switch(sr.action) {
        case MOVE:
            moves.add(sr.move);
            break;
        }

    }

    public void initialResponse(String message) {
        ServerResponse sr = gson.fromJson(message, ServerResponse.class);
        if(sr.action == Action.NEW_GAME) {
            this.otherNane = sr.playerName;
            this.game = sr.game;
        }

    }

    public String setup() {
        String message = new String();

        System.out.println(message);

        System.out.println(message);
        logger.log(message);
        return message;
    }

    public void run() {
        talk.run();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hex.pc.network.Client#getPlayerName()
     */
    @Override
    public String getPlayerName() {
        return this.otherNane;
    }

    @Override
    public void kill() {
        talk.kill();

    }

    @Override
    public boolean giveUp() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Move getPlayerTurn() {
        try {
            return this.moves.take();

        }
        catch(InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("failed to get move");
        }

    }

    @Override
    public void sendMove(Move move) {
        ServerResponse sr = new ServerResponse(name, this.id, move, Action.MOVE, null);
        String json = gson.toJson(sr);
        logger.log(json);
        this.talk.sendMessage(json);

    }

}
