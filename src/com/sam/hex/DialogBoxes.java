package com.sam.hex;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogBoxes {

	public static String choseGameTypePlayer1() {
		Object[] possibilities = { "human on human", "human on AI",
				"AI on human", "AI on AI" };

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Chose a type of game:\n", "Game Type",
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
				"Chose a type of Ai:\n", "AI Type",
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
				"Chose name for player One:\n", "player One",
				JOptionPane.PLAIN_MESSAGE);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			return s;
		}

		return null;

	}

	public static String choseColor1() {

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Chose Color for player One:\n", "player One",
				JOptionPane.PLAIN_MESSAGE);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			return s;
		}

		return null;

	}

	public static String choseName2() {

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Chose name for player Two:\n", "player Two",
				JOptionPane.PLAIN_MESSAGE);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			return s;
		}

		return null;

	}

	public static String choseColor2() {

		String s = (String) JOptionPane.showInputDialog(null,
				"Chose Color for player Two:\n", "player two",
				JOptionPane.PLAIN_MESSAGE);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			return s;
		}

		return null;

	}
	public static int choseGridsize() {

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Chose Color for player Two:\n", "Customized Dialog",
				JOptionPane.PLAIN_MESSAGE);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}

		return 0;

	}
}
