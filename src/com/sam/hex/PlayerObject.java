package com.sam.hex;

public class PlayerObject implements playingEntity {
	
	byte[][] gameBoard;
	byte team;
	
	public PlayerObject(byte i) {
	this.team=i;	
	}

	@Override
	public void getPlayerTurn(byte[][] gameBoard) {
		 this.gameBoard=gameBoard;
		 makeMove();
	}

	@Override
	public void getPlayerTurn() {
		this.gameBoard=BoardTools.teamGrid();
		makeMove();
	}
	public void makeMove(){
		
	}

}
