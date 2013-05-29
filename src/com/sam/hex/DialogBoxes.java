package com.sam.hex;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.hex.ai.AiTypes;
import com.hex.core.Player;

public class DialogBoxes {

    public static void resetGameOption() {

        Object[] options = { "Yes, make it so", "Cancel" };
        byte b = (byte) JOptionPane.showOptionDialog(Hexgame.window,
                "are you sure you want to reset all your options\n all your personal settings will be lost", "Are you sure", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        if(b == 0) {
            // Clear Preferences
            Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);

            try {
                prefs.clear();

            }
            catch(BackingStoreException e) {
                e.printStackTrace();
            }

            // restart game

            Hexgame.restart();

        }

    }

    public static Boolean chooseGameTypePlayer(int player_num) {
        Object[] possibilities = Player.values();
        Player s;

        s = (Player) JOptionPane.showInputDialog(Hexgame.window, "Choose a type of game:\n", "Game Type", JOptionPane.PLAIN_MESSAGE, null, possibilities,
                possibilities[Hexgame.gameInfo.players[player_num].getType().ordinal()]);

        if((s != null)) {
            int type = s.ordinal();

            if(s != Hexgame.gameInfo.players[player_num].getType()) {
                Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
                prefs.putInt("player" + player_num + "Type", type);
                Hexgame.restart();
            }
            return true;
        }
        return false;

    }

    public static Boolean chooseAiType(int player_num) {
        Object[] possibilities = AiTypes.values();
        AiTypes s;
        Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
        int aiType = prefs.getInt("ai" + player_num + "Type", GlobalDefaults.ai1Type);

        s = (AiTypes) JOptionPane.showInputDialog(Hexgame.window, "Choose a type of Ai Player:\n", "Game Type", JOptionPane.PLAIN_MESSAGE, null, possibilities,
                possibilities[aiType]);

        if((s != null)) {
            int type = s.ordinal();

            if(type != aiType) {
                prefs.putInt("ai" + player_num + "Type", type);
                Hexgame.restart();
            }
            return true;
        }
        return false;

    }

    public static String chooseName(int player_num) {

        String s = (String) JOptionPane.showInputDialog(Hexgame.window, "Choose name for player one:\n", "Player One", JOptionPane.PLAIN_MESSAGE, null, null,
                Hexgame.gameInfo.players[player_num].getName());

        // If a string was returned, say so.
        if((s != null) && (s.length() > 0)) {
            Hexgame.gameInfo.players[player_num].setName(s);
            Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
            prefs.put("player" + player_num + "Name", Hexgame.gameInfo.players[player_num].getName());
            return s;
        }

        return null;

    }

    public static int chooseColor(final int player_num) {
        final JColorChooser chooser = new JColorChooser(new Color(Hexgame.gameInfo.players[player_num].getColor()));
        JColorChooser.createDialog(Hexgame.window, "Pick a color", true, chooser, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Hexgame.gameInfo.players[player_num].setColor(chooser.getColor().getRGB());
                Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
                prefs.putInt("player" + player_num + "Color", Hexgame.gameInfo.players[player_num].getColor());
            }
        }, null).setVisible(true);

        return Hexgame.gameInfo.players[player_num].getColor();
    }

    public static void chooseGridsize() {

        String s = (String) JOptionPane.showInputDialog(Hexgame.window, "Choose a new grid size:\n", "Grid Size", JOptionPane.PLAIN_MESSAGE, null, null,
                Hexgame.gameInfo.options.gridSize);

        int newSize = Hexgame.gameInfo.options.gridSize;
        // If a string was returned, say so.
        if((s != null) && (s.length() > 0) && (s.matches("[0-9]") || s.matches("[0-9][0-9]"))) {
            try {
                newSize = Integer.parseInt(s);
            }
            catch(NumberFormatException e) {
                e.printStackTrace();
                newSize = Hexgame.gameInfo.options.gridSize;
            }
            if(newSize < 4) {
                newSize = 4;
            }
            if(Hexgame.gameInfo.options.gridSize != newSize) {
                Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
                prefs.putInt("gridSize", newSize);
                Hexgame.restart();
            }
        }

    }

    public static String loadReplayFile() {
        JFileChooser theFileToLoad = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Replay Hexboards", "rhex");
        theFileToLoad.setFileFilter(filter);
        int returnVal = theFileToLoad.showOpenDialog(Hexgame.window);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return theFileToLoad.getSelectedFile().toString();
        }
        else {
            return null;
        }

    }

    public static boolean saveReplayfile() {
        JFileChooser theFileToSave = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Replay Hexboards", "rhex");
        theFileToSave.setFileFilter(filter);
        int returnVal = theFileToSave.showSaveDialog(Hexgame.window);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            fileIO.textToFile(theFileToSave.getSelectedFile(), Hexgame.runningGame.save());
            return true;
        }
        else {
            return false;
        }
    }

    public static void announceWinner(String name) {

        Object[] options = { "Ok", "Save Replay" };
        byte b = (byte) JOptionPane.showOptionDialog(Hexgame.window, name + " wins!", "Do you want to save a replay?", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(b == 1) saveReplayfile();
    }
}
