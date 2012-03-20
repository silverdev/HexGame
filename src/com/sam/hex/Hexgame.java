package com.sam.hex;

import java.awt.Color;
import java.lang.Integer;
import java.util.prefs.Preferences;

public class Hexgame {

	public static void main(String[] args){
		if (args.length > 4)
			Global.set(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
					Integer.parseInt(args[2]), Byte.parseByte(args[3]), Byte.parseByte(args[4]));
		else {
			Global.set(11, 600, 800);
		}
		
		grabPreferences();
		HexGameWindow fr = new HexGameWindow();

		Global.window=fr;

		fr.setVisible(true);
		new GameObject();

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
			Global.player1Color = new Color(prefs.getInt("player1Color", Color.BLUE.getRGB()));
			Global.player2Color = new Color(prefs.getInt("player2Color", Color.RED.getRGB()));
		}
		catch(Exception e){
			Global.player1Color = Color.BLUE;
			Global.player2Color = Color.RED;
		}
		Global.player1Type = prefs.getInt("player1Type", 0);
		Global.player2Type = prefs.getInt("player2Type", 0);
		Global.gridSize = prefs.getInt("gridSize", 11);
	}
}