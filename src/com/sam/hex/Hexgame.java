package com.sam.hex;

import java.awt.Color;
import java.lang.Integer;
import java.util.prefs.Preferences;

import com.sam.hex.lan.LocalLobbyWindow;

public class Hexgame {
	
	public static HexGameWindow window; 
	public static GameObject runningGame;

	public static void main(String[] args){
		if (args.length > 4)
			Global.set(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
					Integer.parseInt(args[2]), Byte.parseByte(args[3]), Byte.parseByte(args[4]));
		else {
			Global.set(11, 600, 800);
		}
		
		grabPreferences();
		
		window = new HexGameWindow();
//		new LocalLobbyWindow();
		
		window.setVisible(true);
		runningGame = new GameObject();
		while (true) {
			GameAction.updateBoard();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void grabPreferences(){
		Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
		GameAction.hex = null;
		setNames(prefs);
		setColors(prefs);
		Global.player1Type = prefs.getInt("player1Type", GlobalDefaults.player1Type);
		Global.player2Type = prefs.getInt("player2Type", GlobalDefaults.player2Type);
		Global.gridSize = prefs.getInt("gridSize", GlobalDefaults.gridSize);
	}
	
	public static void setNames(Preferences prefs){
		Global.player1Name = prefs.get("player1Name", GlobalDefaults.player1Name);
		Global.player2Name = prefs.get("player2Name", GlobalDefaults.player2Name);
	}
	
	public static void setColors(Preferences prefs){
		try{
			Global.player1Color = new Color(prefs.getInt("player1Color", GlobalDefaults.player1Color.getRGB()));
			Global.player2Color = new Color(prefs.getInt("player2Color", GlobalDefaults.player2Color.getRGB()));
		}
		catch(Exception e){
			Global.player1Color = GlobalDefaults.player1Color;
			Global.player2Color = GlobalDefaults.player2Color;
		}
	}
}