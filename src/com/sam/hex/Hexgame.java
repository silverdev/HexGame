package com.sam.hex;

import java.util.prefs.Preferences;

import com.hex.core.Game;
import com.hex.core.PlayerObject;
import com.hex.core.Timer;

public class Hexgame {

    public static HexGameWindow window;
    public static Game runningGame;
    static GameInfo gameInfo;

    public static void main(String[] args) {
        int hight, width;
        if(args.length > 2) {
            hight = Integer.parseInt(args[0]);
            width = Integer.parseInt(args[1]);
        }
        else {
            hight = 600;
            width = 800;
        }

        gameInfo = buildGame(new PlayerObject(1), new PlayerObject(2));
        runningGame = gameInfo.makeGame();
        window = new HexGameWindow(hight, width);

        window.setVisible(true);
        runningGame.setGameListener(new Callbacks(runningGame, window));
        runningGame.gameOptions.timer = new Timer(0, 0, 0);
        window.cPolygons.setShapes(runningGame, gameInfo);
        HexGameWindow.cPolygons.repaint();
        runningGame.start();
    }

    public static GameInfo buildGame(PlayerObject player1, PlayerObject player2) {
        Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);

        Game.GameOptions options = new Game.GameOptions();
        player1.player1Type = prefs.getInt("player1Type", GlobalDefaults.player1Type);
        player2.player2Type = prefs.getInt("player2Type", GlobalDefaults.player2Type);
        options.gridSize = prefs.getInt("gridSize", GlobalDefaults.gridSize);

        player1.setName(prefs.get("player1Name", GlobalDefaults.player1Name));
        player2.setName(prefs.get("player2Name", GlobalDefaults.player2Name));

        try {
            player1.setColor(prefs.getInt("player1Color", GlobalDefaults.player1Color.getRGB()));
            player2.setColor(prefs.getInt("player2Color", GlobalDefaults.player2Color.getRGB()));
        }
        catch(Exception e) {
            player1.setColor(GlobalDefaults.player1Color.getRGB());
            player2.setColor(GlobalDefaults.player2Color.getRGB());
        }
        return new GameInfo(options, player1, player2);
    }

    public static void restart() {
        runningGame.stop();
        gameInfo = buildGame(new PlayerObject(1), new PlayerObject(2));
        runningGame = gameInfo.makeGame();
        runningGame.setGameListener(new Callbacks(runningGame, window));
        window.cPolygons.setShapes(runningGame, gameInfo);
        HexGameWindow.cPolygons.repaint();
        runningGame.gameOptions.timer = new Timer(0, 0, 0);
        runningGame.start();
    }
}
