package com.sam.hex;

import java.awt.Point;

public class PlayerObject implements PlayingEntity {
	
	byte[][] gameBoard; 
	byte team;
	
	public PlayerObject(byte i) {
	this.team=i;	//sets the players team
	}

	
	public void getPlayerTurn(byte[][] gameBoard) {
		 this.gameBoard=gameBoard;
		 GameAction.hex = null;
		 makeMove();
	
	}

	@Override
	public void getPlayerTurn() {
		this.gameBoard=BoardTools.teamGrid();
		GameAction.hex = null;
		makeMove();
	}
	
	public void undoCalled(){
		return;
	}
	
	public void makeMove(){
		while (true) {
			Point hex = GameAction.hex;
			while (hex == null) {
				hex = GameAction.hex;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (hex.equals(new Point(-1,-1))){
				GameAction.hex = null;
				break;
			}
			if (Global.gamePiece[hex.x][hex.y].getTeam() == 0) {
				GameAction.makeMove(this,team, hex);
				GameAction.hex = null;
				break;
			}
			GameAction.hex = null;
		}
	}


	@Override
	public boolean supportsSave() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean supportsUndo() {
		// TODO Auto-generated method stub
		return true;
	}

	
}
