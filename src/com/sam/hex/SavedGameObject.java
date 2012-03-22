package com.sam.hex;

import java.awt.Color;
import java.io.Serializable;

public class SavedGameObject implements Serializable{
	private static final long serialVersionUID = -9030402201397427965L;
		public Color player1Color;
		public Color player2Color;
		public String player1Name;
		public String player2Name;
		public MoveList moveList;
		public int gridSize;
		
		SavedGameObject(Color p1Color, Color p2Color, String p1Name, String p2Name, MoveList moves, int grid){
			player1Color = p1Color;
			player2Color = p2Color;
			player1Name = p1Name;
			player2Name = p2Name;
			moveList = moves;
			gridSize = grid;
		}
	}