package com.sam.hex;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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

    public static String chooseGameTypePlayer1() {
        Object[] possibilities = { "Human", "Computer (Kinda easy)", "Computer (Kinda hard)" };
        String s;

        s = (String) JOptionPane.showInputDialog(Hexgame.window, "Choose a type of game:\n", "Game Type", JOptionPane.PLAIN_MESSAGE, null, possibilities,
                possibilities[Hexgame.gameInfo.player1.getType()]);

        // If a string was returned, say so.
        if((s != null) && (s.length() > 0)) {
            int type = 0;
            if(s == "Human") type = 0;
            else if(s == "Computer (Kinda easy)") type = 1;
            else if(s == "Computer (Kinda hard)") type = 2;
            if(type != Hexgame.gameInfo.player1.getType()) {
                Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
                prefs.putInt("player1Type", type);
                Hexgame.restart();
            }
            return s;
        }

        return null;
    }

    public static String chooseGameTypePlayer2() {
        Object[] possibilities = { "Human", "Computer (Kinda easy)", "Computer (Kinda hard)" };
        String s;
        s = (String) JOptionPane.showInputDialog(Hexgame.window, "Choose a type of game:\n", "Game Type", JOptionPane.PLAIN_MESSAGE, null, possibilities,
                possibilities[Hexgame.gameInfo.player2.getType()]);

        // If a string was returned, say so.
        if((s != null) && (s.length() > 0)) {
            int type;
            if(s == "Human") type = 0;
            else if(s == "Computer (Kinda easy)") type = 1;
            else if(s == "Computer (Kinda hard)") type = 2;
            Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
            prefs.putInt("player2Type", Hexgame.gameInfo.player2.getType());
            return s;
        }

        return null;
    }

    public static String chooseName1() {

        String s = (String) JOptionPane.showInputDialog(Hexgame.window, "Choose name for player one:\n", "Player One", JOptionPane.PLAIN_MESSAGE, null, null,
                Hexgame.gameInfo.player1.getName());

        // If a string was returned, say so.
        if((s != null) && (s.length() > 0)) {
            Hexgame.gameInfo.player1.setName(s);
            Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
            prefs.put("player1Name", Hexgame.gameInfo.player1.getName());
            return s;
        }

        return null;

    }

    public static int chooseColor1() {
        final JColorChooser chooser = new JColorChooser(new Color(Hexgame.gameInfo.player1.getColor()));
        JColorChooser.createDialog(Hexgame.window, "Pick a color", true, chooser, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hexgame.gameInfo.player1.setColor(chooser.getColor().getRGB());
                Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
                prefs.putInt("player1Color", Hexgame.gameInfo.player1.getColor());
            }
        }, null).setVisible(true);

        return Hexgame.gameInfo.player1.getColor();
    }

    public static String chooseName2() {

        String s = (String) JOptionPane.showInputDialog(Hexgame.window, "Choose name for player one:\n", "Player One", JOptionPane.PLAIN_MESSAGE, null, null,
                Hexgame.gameInfo.player2.getName());

        // If a string was returned, say so.
        if((s != null) && (s.length() > 0)) {
            Hexgame.gameInfo.player2.setName(s);
            Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
            prefs.put("player2Name", Hexgame.gameInfo.player2.getName());
            return s;
        }

        return null;

    }

    public static int chooseColor2() {
        final JColorChooser chooser = new JColorChooser(new Color(Hexgame.gameInfo.player2.getColor()));
        JColorChooser.createDialog(Hexgame.window, "Pick a color", true, chooser, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hexgame.gameInfo.player2.setColor(chooser.getColor().getRGB());
                Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
                prefs.putInt("player2Color", Hexgame.gameInfo.player2.getColor());
            }
        }, null).setVisible(true);

        return Hexgame.gameInfo.player2.getColor();
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
                prefs.putInt("gridSize", Hexgame.gameInfo.options.gridSize);
                Hexgame.restart();
            }
        }

    }

    public static File loadReplayFile() {
        JFileChooser theFileToLoad = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Replay Hexboards", "rhex");
        theFileToLoad.setFileFilter(filter);
        int returnVal = theFileToLoad.showOpenDialog(Hexgame.window);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return theFileToLoad.getSelectedFile();
        }
        else {
            return null;
        }

    }

    public static File saveReplayfile() {
        JFileChooser theFileToSave = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Replay Hexboards", "rhex");
        theFileToSave.setFileFilter(filter);
        int returnVal = theFileToSave.showSaveDialog(Hexgame.window);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return theFileToSave.getSelectedFile();
        }
        else {
            return null;
        }
    }

    public static void announceWinner(String name) {

        Object[] options = { "Ok", "Save Replay" };
        byte b = (byte) JOptionPane.showOptionDialog(Hexgame.window, name + " wins!", "Do you want to save a replay?", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(b == 1) saveReplayfile();
    }
}
