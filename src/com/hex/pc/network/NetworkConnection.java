package com.hex.pc.network;

import com.hex.core.Game;
import com.hex.core.PlayerObject;
import com.hex.network.Client;
import com.hex.network.NetworkPlayer;
import com.sam.hex.DialogBoxes;

public class NetworkConnection {
    public static Game netGame() {
        boolean host = DialogBoxes.amIHost();
        if(host) {
            return hostGame();
        }
        else return connectToGame();
    }

    private static Game connectToGame() {
        String IP = DialogBoxes.getIP();
        Client com = new PcClient("guest", IP);
        NetworkPlayer netPlayer = new NetworkPlayer(com, new NetworkCallbacks());
        Game netGame;
        String gameData;
        synchronized(com) {
            while(com.getGame() == null) {
                try {
                    com.wait();
                }
                catch(InterruptedException e) {

                    e.printStackTrace();
                }
            }
            gameData = com.getGame();
        }
        netGame = Game.load(gameData, netPlayer, new PlayerObject(2));

        return netGame;
    }

    private static Game hostGame() {
        Host host = new Host("host");
        NetworkPlayer netPlayer = new NetworkPlayer(host, new NetworkCallbacks());
        String gameData = host.getGame();

        Game netGame = Game.load(gameData, new PlayerObject(1), netPlayer);
        return netGame;
    }
}
