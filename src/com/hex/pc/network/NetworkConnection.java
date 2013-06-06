package com.hex.pc.network;

import com.hex.network.Client;
import com.hex.network.NetworkPlayer;
import com.sam.hex.DialogBoxes;

public class NetworkConnection {
    public static NetworkPlayer netGame() {
        boolean host = DialogBoxes.amIHost();
        if(host) {
            return hostGame();
        }
        else return connectToGame();
    }

    private static NetworkPlayer connectToGame() {
        String IP = DialogBoxes.getIP();
        Client com = new PcClient("guest", IP);
        NetworkPlayer netPlayer = new NetworkPlayer(com, new NetworkCallbacks());
        return netPlayer;
    }

    private static NetworkPlayer hostGame() {
        Host host = new Host("host");
        NetworkPlayer netPlayer = new NetworkPlayer(host, new NetworkCallbacks());
        return netPlayer;
    }
}
