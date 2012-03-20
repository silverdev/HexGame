package com.sam.hex;

import java.awt.Shape; //import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Global {
	public static int gridSize = 7;
	public static int windowHeight = 600;
	public static BufferedImage background;
	public static int windowWidth = 800;
	public static RegularPolygonGameObject[][] gamePiece = new RegularPolygonGameObject[0][0];
	public static Shape[][] hexes = gamePiece;
	public static Color playerOne=Color.blue;
	public static Color playerTwo=Color.red;
	public static String playerOneName;
	public static String playerTwoName;
	public static byte gameType; //0 Human v Human,1 Human v ai, 2 ai v Human, 3 ai v ai;
	public static String aiType;
	public static int moveNumber;
	public static MoveList moveList;
	public static int playerturn;
	public static PlayingEntity player1;
	public static PlayingEntity player2;
	public static boolean gameOver=false;
	public static Thread runningGame;
	public static HexGameWindow window;

	// public static Color[][] background;
	public static void set(int gS, int wH, int wW) {
		if (gS > 0)
			gridSize = gS;
		if (wH > 10)
			windowHeight = wH;
		if (wW > 10)
			windowWidth = wW;
		gamePiece = new RegularPolygonGameObject[gridSize][gridSize];
		hexes = gamePiece;
		gameOver=false;
		background = new BufferedImage(windowWidth, windowHeight,
				BufferedImage.TYPE_INT_ARGB);
		// background=new Color[windowWidth][windowHeight];
	}
	public static void set(int gS, int wH, int wW, byte AI){
		set(gS, wH, wW);
		if (gameType < 4)
		gameType=AI;
		gameOver=false;
	}
	// public static int windowHeight=200;
	// public static int windowWidth=400;
}
