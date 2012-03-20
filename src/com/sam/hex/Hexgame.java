package com.sam.hex;

import java.awt.Color;
import java.lang.Integer;
import java.util.prefs.Preferences;

public class Hexgame {

	public static void main(String[] args){
		if (args.length > 3)
			Global.set(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
					Integer.parseInt(args[2]), Byte.parseByte(args[3]));
		else {
			Global.set(7, 600, 800);
		}
		
		grabPreferences();
		HexGameWindow fr = new HexGameWindow();
		
		fr.setVisible(true);
		@SuppressWarnings("unused")
		GameObject RunningGame = new GameObject(); // GameObject is very much in
													// use, do not delete!

		while (true) {
			GameAction.updateBoard();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void grabPreferences(){
		Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
		Global.player1Name = prefs.get("player1Name", "Player1");
		Global.player2Name = prefs.get("player2Name", "Player2");
		try{
			Global.player1Color = Color.decode(prefs.get("player1Color", Color.BLUE.toString()));
			Global.player2Color = Color.decode(prefs.get("player2Color", Color.RED.toString()));
		}
		catch(Exception e){
			Global.player1Color = Color.BLUE;
			Global.player2Color = Color.RED;
		}
		Global.player1Type = prefs.getInt("player1Type", 0);
		Global.player2Type = prefs.getInt("player2Type", 0);
	}
}
