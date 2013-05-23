package com.sam.hex;

import com.hex.core.Game;
import com.hex.core.Game.GameListener;
import com.hex.core.PlayingEntity;

public class Callbacks implements GameListener {
    Game game;
    HexGameWindow window;

    public Callbacks(Game runningGame, HexGameWindow window) {
        this.game = runningGame;
        this.window = window;
    }

    @Override
    public void onWin(PlayingEntity player) {
        DialogBoxes.announceWinner(player.getName());

    }

    @Override
    public void onClear() {
        window.cPolygons.repaint();

    }

    @Override
    public void onStart() {
        window.cPolygons.repaint();
        System.out.println("callback start");
    }

    @Override
    public void onStop() {
        window.cPolygons.repaint();
        System.out.println("callback stop");
    }

    @Override
    public void onTurn(PlayingEntity player) {
        window.cPolygons.repaint();
        System.out.println("turn");
    }

    @Override
    public void onReplayStart() {
        window.cPolygons.repaint();
        System.out.println("callback replay start");

    }

    @Override
    public void onReplayEnd() {
        window.cPolygons.repaint();
        System.out.println("callback replay end");
    }

    @Override
    public void onUndo() {
        window.cPolygons.repaint();

    }

    @Override
    public void startTimer() {
        // TODO Auto-generated method stub

    }

    @Override
    public void displayTime(int minutes, int seconds) {
        // TODO Auto-generated method stub

    }

}
