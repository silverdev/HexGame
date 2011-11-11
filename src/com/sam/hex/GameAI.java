package com.sam.hex;



public class GameAI implements playingEntity {
	byte teamNumber;
	byte difficalty;
	byte[][] gameBoard;
	
	
	public GameAI(byte teamNumberT,byte difficaltyT){
		teamNumber=teamNumberT;
		difficalty=difficaltyT;
		
	}

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

