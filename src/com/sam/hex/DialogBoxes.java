package com.sam.hex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

public class DialogBoxes {

	public static String chooseGameTypePlayer1() {
		Object[] possibilities = { "Human", "Computer" };

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose a type of game:\n", "Game Type",
				JOptionPane.PLAIN_MESSAGE, null, possibilities,
				(Object) "Human");

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			if (s=="Human") Global.player1Type=0;
			else if (s=="Computer") Global.player1Type=1;
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			prefs.putInt("player1Type", Global.player1Type);
			return s;
		}

		return null;
	}

	public static String chooseGameTypePlayer2() {
		Object[] possibilities = { "Human", "Computer" };

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose a type of game:\n", "Game Type",
				JOptionPane.PLAIN_MESSAGE, null, possibilities,
				(Object) "Human");

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			if (s=="Human") Global.player2Type=0;
			else if (s=="Computer") Global.player2Type=1;
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			prefs.putInt("player2Type", Global.player2Type);
			return s;
		}

		return null;
	}

	public static String chooseName1() {

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose name for player one:\n", "Player One",
				JOptionPane.PLAIN_MESSAGE);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			Global.player1Name = s;
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			prefs.put("player1Name", Global.player1Name);
			return s;
		}

		return null;

	}

	public static int chooseColor1() {
		final JColorChooser chooser = new JColorChooser(Global.player1Color);
		JColorChooser.createDialog(Global.window, "Pick a color", true, chooser, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Global.player1Color=chooser.getColor();
				Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
				prefs.putInt("player1Color", Global.player1Color.getRGB());
			}}, null).setVisible(true);
		
		//TODO Reset board, colors
		
		return Global.player1Color.getRGB();
	}

	public static String chooseName2() {

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose name for player two:\n", "Player Two",
				JOptionPane.PLAIN_MESSAGE);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			Global.player2Name = s;
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			prefs.put("player2Name", Global.player2Name);
			return s;
		}

		return null;

	}

	public static int choseColor2() {
		final JColorChooser chooser = new JColorChooser(Global.player1Color);
		JColorChooser.createDialog(Global.window, "Pick a color", true, chooser, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Global.player2Color=chooser.getColor();
				Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
				prefs.putInt("player2Color", Global.player2Color.getRGB());
			}}, null).setVisible(true);
		
		return Global.player2Color.getRGB();
	}
	public static int chooseGridsize() {

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose a new grid size:\n", "Grid Size",
				JOptionPane.PLAIN_MESSAGE);
		
		int newSize = Global.gridSize;
		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			try {
				newSize = Integer.parseInt(s);
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
				newSize = Global.gridSize;
			}
			if (newSize<4){
				newSize=4;
			}
			Global.runningGame.stop();
			Global.gridSize=newSize;
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			prefs.putInt("gridSize", Global.gridSize);
		}
		return newSize;
	}
	
	public static void announce(int team) {
		if(team==1) JOptionPane.showMessageDialog(Global.window, Global.player1Name+" wins!");
		else JOptionPane.showMessageDialog(Global.window, Global.player2Name+" wins!");
	}
}
