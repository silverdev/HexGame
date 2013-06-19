package com.hex.pc.network;

import com.hex.core.PlayerObject;
import com.hex.network.NetworkPlayer;
import com.sam.hex.PcNetworkCallbacks;

public class NetworkConnection {

    public static void netGame(boolean host, String IP) {

        if(host) {
            hostGame();
        }
        connectToGame(IP);
    }

    private static void connectToGame(String IP) {

        Talker com = new Talker(IP);
        NetworkPlayer netPlayer = new NetworkPlayer(1, com);
        com.master = netPlayer;
        netPlayer.setCallbacks(new PcNetworkCallbacks(netPlayer, new PlayerObject(2)));
        com.start();

    }

    private static void hostGame() {
        Server s = new Server();

        NetworkPlayer netPlayer = new NetworkPlayer(2, s);
        s.c = netPlayer;
        netPlayer.setCallbacks(new PcNetworkCallbacks(new PlayerObject(1), netPlayer));
        s.start();
        System.out.println("hi");
        netPlayer.supportsNewgame();

    }
}
