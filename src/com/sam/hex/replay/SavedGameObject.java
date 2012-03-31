package com.sam.hex.replay;

import java.io.Serializable;

import com.sam.hex.MoveList;

public class SavedGameObject implements Serializable{
	private static final long serialVersionUID = 1L;
	public int player1Color;
	public int player2Color;
	public String player1Name;
	public String player2Name;
	public MoveList moveList;
	public int gridSize;
	public int moveNumber;
	
	public SavedGameObject(int player1Color, int player2Color, String player1Name, String player2Name, MoveList moveList, int grid, int moveNumber){
		this.player1Color = player1Color;
		this.player2Color = player2Color;
		this.player1Name = player1Name;
		this.player2Name = player2Name;
		this.moveList = moveList;
		this.gridSize = grid;
		this.moveNumber = moveNumber;
	}
}