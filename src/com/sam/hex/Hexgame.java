package com.sam.hex;

import java.util.prefs.Preferences;

import com.hex.ai.GameAI;
import com.hex.core.Game;
import com.hex.core.PlayerObject;
import com.hex.core.PlayingEntity;
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

        gameInfo = buildGame();
        runningGame = gameInfo.makeGame();
        window = new HexGameWindow(hight, width);

        window.setVisible(true);
        runningGame.setGameListener(new Callbacks(runningGame, window));
        runningGame.gameOptions.timer = new Timer(0, 0, 0);
        window.cPolygons.setShapes(runningGame, gameInfo);
        HexGameWindow.cPolygons.repaint();
        runningGame.start();
    }

    public static GameInfo buildGame() {
        Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);

        Game.GameOptions options = new Game.GameOptions();
        int player1Type = prefs.getInt("player1Type", GlobalDefaults.player1Type);
        int player2Type = prefs.getInt("player2Type", GlobalDefaults.player2Type);
        PlayingEntity player1 = loadPlayer(player1Type, 1);
        PlayingEntity player2 = loadPlayer(player2Type, 2);
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

    private static PlayingEntity loadPlayer(int playerType, int playerNumber) {
        switch(playerType) {
        case 0:
            return new PlayerObject(playerNumber);
        case 1:
            return loadIA(playerNumber);
        case 2:
            throw new RuntimeException("not yet implented");
        }
        throw new RuntimeException("invalid player type");
    }

    private static PlayingEntity loadIA(int playerNumber) {
        // return new PlayerObject(playerNumber);
        return new GameAI(playerNumber);
    }

    public static void restart() {
        runningGame.stop();
        gameInfo = buildGame();
        runningGame = gameInfo.makeGame();
        runningGame.setGameListener(new Callbacks(runningGame, window));
        window.cPolygons.setShapes(runningGame, gameInfo);
        HexGameWindow.cPolygons.repaint();
        runningGame.gameOptions.timer = new Timer(0, 0, 0);
        runningGame.start();
    }
}
