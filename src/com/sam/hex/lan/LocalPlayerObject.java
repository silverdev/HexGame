package com.sam.hex.lan;

import com.sam.hex.BoardTools;
import com.sam.hex.PlayingEntity;

public class LocalPlayerObject implements PlayingEntity {
	byte[][] gameBoard; 
	byte team;
	UnicastListener listener;
	
	public LocalPlayerObject(byte team) {
		this.team=team;//Set the player's team
		listener = new UnicastListener();
	}
	
	//Do not use
	public void getPlayerTurn(byte[][] gameBoard) {
		this.gameBoard=gameBoard;
	}
	
	//Do not use
	public void getPlayerTurn() {
		this.gameBoard=BoardTools.teamGrid();
	}
	
	public void undoCalled(){
	}

	

	@Override
	public boolean supportsSave() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsUndo() {
		// TODO Auto-generated method stub
		return false;
	}
}