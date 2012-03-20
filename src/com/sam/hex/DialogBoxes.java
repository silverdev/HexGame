package com.sam.hex;

import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogBoxes {

	public static String choseGameTypePlayer1() {
		Object[] possibilities = { "human on human", "human on AI",
				"AI on human", "AI on AI" };

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose a type of game:\n", "Game Type",
				JOptionPane.PLAIN_MESSAGE, null, possibilities,
				(Object) "human on human");

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			return s;
		}

		return null;

	}

	public static String choseGameTypePlayer2() {
		Object[] possibilities = { "gameAI", "Randon" };
		JFrame frame = new JFrame("AI type Type");
		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose a type of AI:\n", "AI Type",
				JOptionPane.PLAIN_MESSAGE, null, possibilities,
				possibilities[0]);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			return s;
		}

		return null;

	}

	public static String choseName1() {

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

	public static String choseColor1() {

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose color for player one:\n", "Player One",
				JOptionPane.PLAIN_MESSAGE);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			return s;
		}

		return null;

	}

	public static String choseName2() {

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

	public static String choseColor2() {

		String s = (String) JOptionPane.showInputDialog(null,
				"Choose color for player two:\n", "Player Two",
				JOptionPane.PLAIN_MESSAGE);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			return s;
		}

		return null;

	}
	public static int choseGridsize() {

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
