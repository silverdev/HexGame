package com.sam.hex;

import java.awt.Shape; //import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.ArrayList;

import com.sam.hex.lan.LocalNetworkObject;

public class Global {
	public static int gridSize = 11;
	public static int windowHeight = 600;
	public static BufferedImage background;
	public static int windowWidth = 800;
	public static RegularPolygonGameObject[][] gamePiece = new RegularPolygonGameObject[0][0];
	public static Shape[][] hexes = gamePiece;


	public static String playerOneName;
	public static String playerTwoName;

	public static String player1Name="Player1";
	public static Color player1Color=Color.blue;
	public static int player1Type=0;
	public static String player2Name="Player2";
	public static Color player2Color=Color.red;
	public static int player2Type=0;

	public static ArrayList<LocalNetworkObject> localObjects;
	public static LocalNetworkObject localPlayer;
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
	public static void set(int gridSize, int windowHeight, int windowWidth) {
		if (gridSize > 0)
			Global.gridSize = gridSize;
		if (windowHeight > 10)
			Global.windowHeight = windowHeight;
		if (windowWidth > 10)
			Global.windowWidth = windowWidth;
		gamePiece = new RegularPolygonGameObject[gridSize][gridSize];
		hexes = gamePiece;
		gameOver=false;
		background = new BufferedImage(windowWidth, windowHeight,
				BufferedImage.TYPE_INT_ARGB);
		// background=new Color[windowWidth][windowHeight];
	}
	public static void set(int gridSize, int windowHeight, int windowWidth, byte p1, byte p2){
		set(gridSize, windowHeight, windowWidth);
		if (p1 < 2) Global.player1Type=p1;
		if (p2 < 2) Global.player2Type=p2;
		gameOver=false;
	}
	// public static int windowHeight=200;
	// public static int windowWidth=400;
}
