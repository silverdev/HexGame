package com.sam.hex;




public class GameAI implements PlayingEntity {
	byte teamNumber;
	byte difficalty;

	
	public GameAI(byte teamNumberT,byte difficaltyT){
		teamNumber=teamNumberT;
		difficalty=difficaltyT;
		
	}

	@Override
	public void getPlayerTurn(byte[][] gameBoard){
		
	}
	
}
